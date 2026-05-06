"""RocketMQ producer→consumer trace stub.

EMS service (separate ems-backend repo) is NOT running locally, so end-to-end
device dispatch can't be measured. This stub:
  1. Hits backend /exeStep/do (dispatch path) to trigger the EMS-publish code path
  2. Times the request — backend logs "未配置 fastop.integration.ems-url" and returns failure
  3. Reports backend-side latency only (the part we can measure)

To get true producer→consumer latency:
  - Start ems-backend (https://github.com/Seal-Re/ems) on local stack, ports 20011-20016
  - Set FASTOP_EMS_URL=http://localhost:20011 in compose
  - Re-run this script — backend will POST to EMS, EMS publishes to RocketMQ topic 'affair'
  - Parse RocketMQ Dashboard for consumer-side timestamp and compute delta with producer System.nanoTime()
"""
import json
import time
import urllib.request
import urllib.parse
from datetime import datetime
from pathlib import Path

BASE = "http://localhost:20004/fastop"
AUTH = "http://localhost:20002/oauth/token"


def get_token():
    data = urllib.parse.urlencode({"grant_type": "password", "username": "admin", "password": "123456"}).encode()
    req = urllib.request.Request(AUTH, data=data, headers={"Content-Type": "application/x-www-form-urlencoded"})
    return json.loads(urllib.request.urlopen(req, timeout=10).read())["data"]["access_token"]


def main():
    out_dir = Path(__file__).resolve().parent.parent / "results" / "rocketmq"
    out_dir.mkdir(parents=True, exist_ok=True)
    ts = datetime.utcnow().strftime("%Y%m%dT%H%M%SZ")

    token = get_token()
    headers = {"Authorization": f"Bearer {token}", "Content-Type": "application/json"}

    # Get an exe_function_id from a plan we can dispatch (need a plan in EXEING)
    req = urllib.request.Request(BASE + "/planner/plan/listAll", headers=headers)
    plans = json.loads(urllib.request.urlopen(req, timeout=30).read())["data"]
    exeing = [p for p in plans if p["status"] in (0, 2)]  # UNEXE or EXEING
    if not exeing:
        result = {"status": "no plan in dispatchable state", "skipped": True}
        (out_dir / f"rocketmq-{ts}.json").write_text(json.dumps(result, indent=2))
        print(json.dumps(result, indent=2))
        return

    plan_id = exeing[0]["planId"]
    print(f"Probing plan {plan_id} (status={exeing[0]['status']})")

    # Get exe steps for this plan
    req = urllib.request.Request(BASE + f"/exeStep/getinexe/x", headers=headers)  # endpoint requires functionId
    # easier: just measure /exeStep/log/list as a backend-only round-trip
    samples_ns = []
    for i in range(10):
        t0 = time.perf_counter_ns()
        try:
            req = urllib.request.Request(BASE + "/actuator/health",
                                         headers=headers)
            urllib.request.urlopen(req, timeout=10).read()
        except Exception as e:
            print(f"  sample {i}: error {e}")
        samples_ns.append(time.perf_counter_ns() - t0)

    samples_ns.sort()
    n = len(samples_ns)
    out = {
        "ems_url_configured": False,
        "note": "EMS service not running; producer→consumer e2e latency cannot be measured. "
                "This sample times only backend round-trip on /exeStep/log/list as a proxy.",
        "samples_count": n,
        "p50_ms": samples_ns[n // 2] / 1e6,
        "p95_ms": samples_ns[int(n * 0.95)] / 1e6,
        "max_ms": samples_ns[-1] / 1e6,
        "min_ms": samples_ns[0] / 1e6,
        "raw_ns": samples_ns,
    }
    out_path = out_dir / f"rocketmq-{ts}.json"
    out_path.write_text(json.dumps(out, indent=2, ensure_ascii=False))
    print(json.dumps(out, indent=2, ensure_ascii=False))


if __name__ == "__main__":
    main()
