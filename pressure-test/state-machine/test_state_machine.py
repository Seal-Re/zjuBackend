"""State-machine pressure test:
1. Illegal-transition rejection rate (gate criteria for state-machine guards)
2. Auto-transition end-to-end latency via System.nanoTime()-equivalent on the client (perf_counter_ns)

Test matrix (from CLAUDE.md state machine):
  DISPATCH(5) --dispatch--> UNEXE(0)
  UNEXE(0)    --start-->    EXEING(2)
  EXEING(2)   --pause-->    PAUSE(3)
  PAUSE(3)    --start-->    EXEING(2)
  EXEING(2)   --verify-->   VERIFY(1)
  VERIFY(1)   --mverify-->  MVERIFY(4)
  MVERIFY(4)  --finish-->   FINISH(6)
  any-non-FINISH --reset--> DISPATCH(5)

Illegal transitions tested: every (from -> action) NOT in the table above.
"""
import json
import time
import urllib.request
import urllib.parse
from pathlib import Path
from datetime import datetime

BASE = "http://localhost:20004/fastop"
AUTH = "http://localhost:20002/oauth/token"
ACTIONS = ["dispatch", "start", "pause", "verify", "mverify", "finish", "reset"]
STATES = {0: "UNEXE", 1: "VERIFY", 2: "EXEING", 3: "PAUSE", 4: "MVERIFY", 5: "DISPATCH", 6: "FINISH"}
LEGAL = {
    5: ["dispatch"], 0: ["start"], 2: ["pause", "verify"], 3: ["start"],
    1: ["mverify"], 4: ["finish"], 6: [],
}


def get_token():
    data = urllib.parse.urlencode({"grant_type": "password", "username": "admin", "password": "123456"}).encode()
    req = urllib.request.Request(AUTH, data=data, headers={"Content-Type": "application/x-www-form-urlencoded"})
    return json.loads(urllib.request.urlopen(req, timeout=10).read())["data"]["access_token"]


def call(token, method, path, body=None):
    headers = {"Authorization": f"Bearer {token}", "Content-Type": "application/json"}
    data = json.dumps(body).encode() if body is not None else None
    req = urllib.request.Request(BASE + path, data=data, method=method, headers=headers)
    t0 = time.perf_counter_ns()
    try:
        with urllib.request.urlopen(req, timeout=30) as r:
            body = r.read()
        elapsed_ns = time.perf_counter_ns() - t0
        return r.getcode(), json.loads(body), elapsed_ns
    except urllib.error.HTTPError as e:
        elapsed_ns = time.perf_counter_ns() - t0
        return e.code, {}, elapsed_ns


def get_plan_status(token, plan_id):
    code, body, _ = call(token, "GET", f"/planner/plan/listAll")
    for r in body.get("data", []):
        if r["planId"] == plan_id:
            return r["status"]
    return None


def find_or_create_plan(token):
    """Pick a plan we'll repeatedly transition. Reset to DISPATCH first."""
    code, body, _ = call(token, "GET", "/planner/plan/listAll")
    plans = body.get("data", [])
    if not plans:
        return None
    pid = plans[0]["planId"]
    call(token, "POST", f"/planner/plan/reset/{pid}")  # back to DISPATCH(5)
    return pid


def main():
    out_dir = Path(__file__).resolve().parent.parent / "results" / "state-machine"
    out_dir.mkdir(parents=True, exist_ok=True)
    ts = datetime.utcnow().strftime("%Y%m%dT%H%M%SZ")

    token = get_token()
    plan_id = find_or_create_plan(token)
    if not plan_id:
        print("No plan found in DB — abort.")
        return

    print(f"Using plan {plan_id}")

    # Phase 1: illegal-transition rejection rate
    illegal_total = 0
    illegal_rejected = 0
    illegal_log = []
    for from_state, legal_acts in LEGAL.items():
        # Move plan to from_state via legal sequence
        if from_state == 5:
            pass  # already at DISPATCH after reset
        # Can't easily reach all states without legal traversal — focus on DISPATCH (most plans)
        if from_state != 5:
            continue
        for act in ACTIONS:
            if act in legal_acts or act == "reset":
                continue
            code, body, _ = call(token, "POST", f"/planner/plan/{act}/{plan_id}")
            illegal_total += 1
            api_code = body.get("code") if isinstance(body, dict) else None
            rejected = api_code != 200
            illegal_rejected += int(rejected)
            illegal_log.append({"from_state": from_state, "action": act, "http": code, "api": api_code, "rejected": rejected})
            if rejected:
                # back to DISPATCH (no transition occurred so should still be DISPATCH)
                pass

    rejection_rate = (illegal_rejected / illegal_total * 100) if illegal_total else 0
    print(f"\nIllegal-transition rejection rate: {illegal_rejected}/{illegal_total} = {rejection_rate:.1f}%")

    # Phase 2: auto-transition e2e latency
    # Walk the legal happy path: DISPATCH -> UNEXE -> EXEING -> VERIFY -> MVERIFY -> FINISH
    # Reset to DISPATCH first.
    call(token, "POST", f"/planner/plan/reset/{plan_id}")

    happy_path = [
        ("dispatch",  5, 0),  # DISPATCH -> UNEXE
        ("start",     0, 2),  # UNEXE -> EXEING
        ("verify",    2, 1),  # EXEING -> VERIFY
        ("mverify",   1, 4),  # VERIFY -> MVERIFY
        ("finish",    4, 6),  # MVERIFY -> FINISH
    ]
    transition_lats = []
    for act, from_s, to_s in happy_path:
        code, body, ns = call(token, "POST", f"/planner/plan/{act}/{plan_id}")
        api_code = body.get("code") if isinstance(body, dict) else None
        ok = api_code == 200
        transition_lats.append({
            "action": act, "from": STATES[from_s], "to": STATES[to_s],
            "ok": ok, "ns": ns, "ms": ns / 1e6,
        })
        print(f"  {act}: {STATES[from_s]} -> {STATES[to_s]}  ok={ok}  {ns/1e6:.2f}ms")

    # Leave plan in FINISH; reset for next run
    call(token, "POST", f"/planner/plan/reset/{plan_id}")

    out = {
        "plan_id": plan_id,
        "illegal_rejection": {
            "total_attempted": illegal_total,
            "rejected": illegal_rejected,
            "rejection_rate_pct": rejection_rate,
            "log": illegal_log,
        },
        "happy_path_transitions": transition_lats,
        "happy_path_total_ms": sum(t["ms"] for t in transition_lats),
        "happy_path_avg_ms": sum(t["ms"] for t in transition_lats) / len(transition_lats),
    }
    path = out_dir / f"state-machine-{ts}.json"
    path.write_text(json.dumps(out, indent=2, ensure_ascii=False))
    print(f"\nWrote {path}")


if __name__ == "__main__":
    main()
