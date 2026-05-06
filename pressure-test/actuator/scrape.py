"""Actuator metrics scrape during HTTP load.

Hits backend /actuator/metrics/<name> for thread-pool executor + JVM metrics every 2s
while a separate worker pool drives load against /planner/plan/listAll.
"""
import json
import sys
import time
import threading
import urllib.request
import urllib.parse
import statistics
from concurrent.futures import ThreadPoolExecutor
from datetime import datetime
from pathlib import Path

BACKEND = "http://localhost:20004/fastop"
AUTH = "http://localhost:20002/oauth/token"
METRICS = [
    "executor.active",
    "executor.completed",
    "executor.pool.core",
    "executor.pool.size",
    "executor.queued",
    "executor.queue.remaining",
    "jvm.threads.live",
    "jvm.threads.daemon",
    "jvm.memory.used",
    "jvm.gc.pause",
    "http.server.requests",
]
ENDPOINTS = [
    "/planner/plan/listAll",
    "/designer/testFunction/listAll",
    "/designer/testSuite/listAll",
    "/devices/list",
    "/base/listAllBaseStruct",
]
LOAD_DURATION = 60
LOAD_RPS_PER_THREAD = 5
LOAD_THREADS = 16


def get_token():
    data = urllib.parse.urlencode({
        "grant_type": "password", "username": "admin", "password": "123456"
    }).encode()
    req = urllib.request.Request(AUTH, data=data, headers={"Content-Type": "application/x-www-form-urlencoded"})
    with urllib.request.urlopen(req, timeout=10) as r:
        return json.loads(r.read())["data"]["access_token"]


def fetch_metric(name):
    try:
        with urllib.request.urlopen(f"{BACKEND}/actuator/metrics/{name}", timeout=5) as r:
            return json.loads(r.read())
    except Exception as e:
        return {"error": str(e)}


def poll_metrics(stop_event, samples):
    while not stop_event.is_set():
        snapshot = {"ts": datetime.utcnow().isoformat()}
        for m in METRICS:
            snapshot[m] = fetch_metric(m)
        samples.append(snapshot)
        if stop_event.wait(2):
            break


def driver(token, stop_event, ep_idx, rps, stats):
    headers = {"Authorization": f"Bearer {token}"}
    interval = 1.0 / rps
    while not stop_event.is_set():
        ep = ENDPOINTS[ep_idx]
        t0 = time.perf_counter()
        try:
            req = urllib.request.Request(BACKEND + ep, headers=headers)
            with urllib.request.urlopen(req, timeout=30) as r:
                r.read()
            stats["ok"] += 1
            stats["latencies"].append(time.perf_counter() - t0)
        except Exception:
            stats["err"] += 1
        time.sleep(interval)
        ep_idx = (ep_idx + 1) % len(ENDPOINTS)


def main():
    out_dir = Path(__file__).resolve().parent.parent / "results" / "actuator"
    out_dir.mkdir(parents=True, exist_ok=True)
    ts = datetime.utcnow().strftime("%Y%m%dT%H%M%SZ")
    out_path = out_dir / f"actuator-load-{ts}.json"

    token = get_token()
    print(f"Got token, driving load for {LOAD_DURATION}s with {LOAD_THREADS} threads x {LOAD_RPS_PER_THREAD} rps")

    stop_event = threading.Event()
    samples = []
    metrics_thread = threading.Thread(target=poll_metrics, args=(stop_event, samples))
    metrics_thread.start()

    stats = {"ok": 0, "err": 0, "latencies": []}
    pool = ThreadPoolExecutor(max_workers=LOAD_THREADS)
    futures = []
    for i in range(LOAD_THREADS):
        futures.append(pool.submit(driver, token, stop_event, i % len(ENDPOINTS), LOAD_RPS_PER_THREAD, stats))

    time.sleep(LOAD_DURATION)
    stop_event.set()
    pool.shutdown(wait=True)
    metrics_thread.join()

    # Aggregate
    if stats["latencies"]:
        lats = sorted(stats["latencies"])
        n = len(lats)
        agg = {
            "count": n, "ok": stats["ok"], "err": stats["err"],
            "rps": stats["ok"] / LOAD_DURATION,
            "p50_ms": lats[n // 2] * 1000,
            "p95_ms": lats[int(n * 0.95)] * 1000,
            "p99_ms": lats[int(n * 0.99)] * 1000,
            "max_ms": lats[-1] * 1000,
            "avg_ms": statistics.mean(lats) * 1000,
        }
    else:
        agg = {"count": 0, "ok": 0, "err": stats["err"]}

    # Pull executor pool peak
    pool_peak = 0
    queue_peak = 0
    threads_peak = 0
    for s in samples:
        try:
            ps = s["executor.pool.size"]["measurements"][0]["value"]
            pool_peak = max(pool_peak, ps)
        except Exception:
            pass
        try:
            qs = s["executor.queued"]["measurements"][0]["value"]
            queue_peak = max(queue_peak, qs)
        except Exception:
            pass
        try:
            ts = s["jvm.threads.live"]["measurements"][0]["value"]
            threads_peak = max(threads_peak, ts)
        except Exception:
            pass

    final = {
        "config": {
            "load_threads": LOAD_THREADS, "rps_per_thread": LOAD_RPS_PER_THREAD,
            "duration_sec": LOAD_DURATION, "endpoints": ENDPOINTS,
        },
        "aggregate": agg,
        "actuator": {
            "executor_pool_size_peak": pool_peak,
            "executor_queued_peak": queue_peak,
            "jvm_threads_live_peak": threads_peak,
        },
        "raw_samples": samples,
    }
    out_path.write_text(json.dumps(final, indent=2, ensure_ascii=False))
    print(f"\nWrote {out_path}")
    print(f"  Aggregate: rps={agg.get('rps', 0):.2f} p50={agg.get('p50_ms', 0):.1f}ms p95={agg.get('p95_ms', 0):.1f}ms")
    print(f"  Actuator peak: pool={pool_peak} queue={queue_peak} threads={threads_peak}")


if __name__ == "__main__":
    main()
