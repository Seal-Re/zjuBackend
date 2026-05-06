# fastop V2.5 — Pressure Test Reproduction Guide

User reproduction document. **All commands assume `cwd = pressure-test/`** unless noted.

```powershell
cd D:/AgentWorkStation/zjuBackend/pressure-test
```

This guide pairs 1:1 with `RESULT.md` (agent run log). Section numbers match.

## Prerequisites

| tool | install |
|---|---|
| docker desktop | required for wrk2/sysbench (not on PATH locally) |
| python ≥3.11 | already on PATH (3.11.9) |
| maven 3.9 + JDK 8/21 | already on PATH (jdk-21) |

Backend stack must be running on ports 20001-20005 (`docker compose -f infra/docker-compose.yml up -d`).
DB must already hold the V2.5 migrated dataset (`data-migration/output/fastop_v25_full.sql` loaded — see `data-migration/MIGRATION_MAP.md`).

Test account: `admin / 123456` (used by every script to OAuth a Bearer token automatically).

---

## §1 — wrk2 (HTTP layer)

`wrk2` runs in a docker container (`cylab/wrk2`). The Lua script round-robins 5 read endpoints (`plan/listAll`, `testFunction/listAll`, `testSuite/listAll`, `devices/list`, `base/listAllBaseStruct`).

```bash
bash wrk2/run.sh
```

Three profiles:
| profile | threads | conn | duration | target rps |
|---|---|---|---|---|
| smoke | 2 | 10 | 30s | 200 |
| load | 4 | 64 | 60s | 1000 |
| stress | 4 | 128 | 60s | 2000 |

Reports: `results/wrk2/<profile>-<ts>.txt` (full HdrHistogram + summary).

**Windows note:** `MSYS_NO_PATHCONV=1` is set inside `run.sh` to prevent Git Bash from mangling `/scripts/...` into `D:/Git/scripts/...`.

---

## §2 — sysbench (MySQL)

A dedicated DB `sysbench_pt` is created in fastop-mysql to avoid polluting `autosys_1014`.

```bash
bash sysbench/run.sh
```

Workloads (`severalnines/sysbench` docker image):
- `oltp_write_only` — only writes
- `oltp_insert` — only inserts
- `oltp_read_write` — 70/30 mixed

Reports: `results/sysbench/<workload>-<ts>.txt`.

---

## §3 — RocketMQ producer→consumer e2e *(skipped — EMS not deployed)*

> **Skipped because:** EMS service (separate `ems-backend` repo) is not started locally. `FASTOP_EMS_URL` is empty, so `/exeStep/do` returns the "未配置" failure rather than producing to RocketMQ. To run end-to-end:
> 1. `git clone https://github.com/Seal-Re/ems` (or the local copy at `D:/AgentWorkStation/workspace/ems-backend`)
> 2. Bring up its compose (RocketMQ + Nacos + Redis + affair-center + subscribe-center) on the reserved 20011-20016 ports
> 3. Set `FASTOP_EMS_URL=http://localhost:20011` in `infra/.env`, restart fastop backend
> 4. Re-run `python rocketmq/trace.py` — the script will measure backend→EMS HTTP RT; combine with RocketMQ Dashboard's broker-side and consumer-side timestamps for full e2e

What this stub measures (proxy): backend round-trip on `/exeStep/log/list` per request (since EMS path is dead).

```bash
python rocketmq/trace.py
```

Reports: `results/rocketmq/rocketmq-<ts>.json`.

---

## §4 — Spring Actuator (ThreadPool + JVM)

```bash
python actuator/scrape.py
```

Drives 16 threads × 5 rps for 60s while polling `/actuator/metrics/{executor.*,jvm.*,http.server.requests}` every 2s. Aggregates client-side latency + actuator peaks.

Reports: `results/actuator/actuator-load-<ts>.json` (full raw samples + aggregate).

To enable actuator endpoints (default deploys only health/info), `infra/docker-compose.yml` injects:

```yaml
MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE: "health,info,metrics,threaddump,heapdump,prometheus,mappings,beans"
```

This must be reverted before going to production.

---

## §5 — JMH (auth interceptor / HTTP-vs-JVM)

Standalone Maven project; benchmarks the auth-mock `/userinfo` HTTP path vs an in-process `ConcurrentHashMap` lookup.

```bash
cd jmh
mvn -B clean package
java -jar target/benchmarks.jar AuthBench -wi 2 -i 3 -f 1 -bm avgt -tu us
```

Output: console table + JMH JSON (when `-rf json -rff <path>` passed).

Three benchmarks:
- `feignHttpUserinfo` — OkHttp HTTP RT to auth-mock
- `feignWithCacheLayer` — same, but cache hit (matches `UserContextInterceptor`)
- `jvmInProcessLookup` — direct `cache.get(token)`

---

## §6 — JUnit + nanoTime state-machine trace

```bash
python state-machine/test_state_machine.py
```

Two phases:
- **Illegal-transition rejection:** from DISPATCH(5), every action that is NOT `dispatch` should return `code != 200`.
- **Happy-path nanoTime trace:** walks DISPATCH → UNEXE → EXEING → VERIFY → MVERIFY → FINISH, recording `time.perf_counter_ns()` per transition.

Reports: `results/state-machine/state-machine-<ts>.json`.

---

## §7 — Aggregating

Each module writes to `results/<module>/`. There is no single dashboard; open the per-module JSON / TXT and the matching section in `RESULT.md`.

To regenerate everything from scratch:

```bash
bash wrk2/run.sh
bash sysbench/run.sh
python actuator/scrape.py
python state-machine/test_state_machine.py
python rocketmq/trace.py
cd jmh && mvn -B clean package && java -jar target/benchmarks.jar AuthBench -wi 2 -i 3 -f 1
```

Total wall time: **~10–15 min** (state-machine alone is ~110 s × 1 run; sysbench is ~3 min; wrk2 is ~3 min; actuator is 60 s; JMH is ~25 s).
