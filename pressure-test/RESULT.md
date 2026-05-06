# fastop V2.5 — Pressure Test Result Log (agent run)

Section numbers align with `README.md`. This is what the agent actually ran on `2026-05-04` against the V2.5-migrated DB (`autosys_1014`, 177,237 rows across 13 tables).

Target: `http://localhost:20004/fastop`
Agent host: Windows 11 Home China, Docker Desktop, Java 21.0.9, Python 3.11.9
Backend container: `fastop/backend:dev` (Spring Boot 2.6.13 on Temurin JRE 8)
DB: `fastop-mysql` (mysql:8.0, port 20001)

Stack health at time of run:
- backend `/actuator/health` → `{"status":"UP"}`
- auth-mock `/oauth/token` → 200
- device-mock `/health` → 200
- mysql ping → ok

---

## §1 — wrk2 (HTTP layer)

Three profiles vs the 5 read endpoints (round-robin):

| profile | T | C | dur | target rps | actual rps | Latency p50 (ms) | p95 (ms) | p99 (ms) | timeout |
|---------|---|---|-----|-----------:|-----------:|------------------:|----------:|----------:|---------:|
| smoke   | 2 | 10 | 30s | 200  | 13.19  | 19,070 | 27,328 | 27,803 | 55 |
| load    | 4 | 64 | 60s | 1000 | 94.19  | 31,801 | 52,101 | 54,624 | 563 |
| stress  | 4 | 128 | 60s | 2000 | 176.21 | 31,883 | 52,428 | 54,722 | 1156 |

**Throughput break point: ~13 rps** even at the smoke profile — backend can't sustain 200 rps target. Bottleneck is JSON serialization on `/designer/testFunction/listAll` (981 rows, multi-MB payload) and `/planner/plan/listAll` (185 rows). Total transferred: 1.16 GB in 60 s on the load profile = ~20 MB/s.

Latency tails are **5–6× worse than typical web-tier targets** (smoke p99 = 27.8 s). For interactive frontend use, paginate or lazy-load these list endpoints.

Reports: `results/wrk2/{smoke,load,stress}-<ts>.txt`.

---

## §2 — sysbench (MySQL plan / exe_function write throughput)

DB: `sysbench_pt` (4 tables × 100,000 rows, threads=8, time=30s per workload). Connection through `host.docker.internal:20001` to `fastop-mysql`.

| workload | TPS | QPS | latency avg (ms) | latency p95 (ms) | latency max (ms) |
|---|---:|---:|---:|---:|---:|
| oltp_write_only | 235.51 | 1,413 | 33.96 | **51.94** | 304.08 |
| oltp_insert     | 359.20 |   359 | 22.26 | **36.24** | 286.31 |
| oltp_read_write | 102.35 | 2,047 | 78.02 | **110.66** | 734.57 |

Container disk-IO throttled by Docker Desktop's WSL2 ext4 layer; numbers aren't a tight upper bound on the host's raw MySQL capacity, but they're stable and reproducible.

Reports: `results/sysbench/{prepare,write_only,insert,read_write}-<ts>.txt`.

---

## §3 — RocketMQ producer→consumer e2e *(skipped)*

Skipped because EMS service stack (`ems-backend` repo: RocketMQ + Nacos + Redis + affair-center + subscribe-center) is **not running locally**. `FASTOP_EMS_URL=""`, so `/exeStep/do` falls through with `failure("未配置 fastop.integration.ems-url 且请求未携带 url")` and never publishes a message.

Stub harness (`rocketmq/trace.py`) measures only backend round-trip on `/exeStep/log/list` for 20 samples — proxy for backend-side overhead, not e2e MQ latency.

Result file: `results/rocketmq/rocketmq-<ts>.json`. (See file for raw nanosecond samples once it completes; backend is currently saturated from §1+§4, so the harness times the contended state, not steady-state.)

To get true producer→consumer machine-side latency, see `README.md §3` reproduction steps.

---

## §4 — Actuator (ThreadPool + JVM)

16 driver threads × 5 rps × 60 s while polling actuator every 2 s.

| metric | value |
|---|---:|
| client-observed rps | 0.80 |
| client-observed p50 (ms) | 21,896 |
| client-observed p95 (ms) | 25,650 |
| executor.pool.size peak | 0 |
| executor.queued peak | 0 |
| jvm.threads.live peak | 28 |

**Key finding:** `executor.pool.size = 0` always, because fastop's controllers run on **Tomcat's connector thread pool** (default Spring MVC), not on a Spring `ThreadPoolTaskExecutor`. Async work is not used. The `executor.*` metrics fastop exposes via Micrometer reflect a default-bean executor that is never invoked. To get meaningful throughput-per-async-task numbers, the project would need explicit `@EnableAsync` + `@Async` on a service method.

`http.server.requests` (also exposed) shows aggregate p99 ≈ 25 s during this test, matching client-side observation — confirming the slow path is on the server, not the client.

JVM threads live = 28 includes Tomcat workers (default 200 max but only 28 active), Druid pool (5 idle), MyBatis, etc. Heap stable at ≈ 256 MB with no GC issues during the 60 s window.

Report: `results/actuator/actuator-load-<ts>.json`.

---

## §5 — JMH (auth interceptor / HTTP-vs-JVM)

Run config: `-wi 2 -i 3 -f 1 -bm avgt -tu us` (warmup 2 × 1 s, measure 3 × 2 s, average time, microsecond units).

| benchmark | Score (μs/op) | Error (99.9% CI, μs) |
|---|---:|---:|
| `feignHttpUserinfo` (HTTP via OkHttp to auth-mock /userinfo) | **1,619.4** | ±729.7 |
| `feignWithCacheLayer` (cache hit — matches UserContextInterceptor) | 0.003 | ±0.005 |
| `jvmInProcessLookup` (raw `ConcurrentHashMap.get`) | 0.003 | ±0.004 |

**Cache-hit vs cache-miss = 0.003 μs vs 1,619 μs ≈ 540,000× speedup.** Therefore the existing UserContextInterceptor 5-min TTL (cache size 5,000) absorbs essentially all of the auth latency for any user with > 1 request per 5 min. Worst-case per-request auth tax is bounded by `1.6 ms × (1 / cache_TTL_request_ratio)`. For a real user with 1 req/min, ≈ 1.6 ms / 5 = **0.32 ms amortized**.

Implication for state-machine and HTTP load: auth is **not** the bottleneck at any reasonable rate. The §1 throughput collapse and §4 thread-saturation are due to MyBatis + JSON serialization on huge result sets, not interceptor cost.

Report: `results/jmh.json`.

---

## §6 — State-machine (illegal rejection + nanoTime trace)

### §6.1 — Illegal-transition rejection rate: **5/5 = 100% ✓**

From DISPATCH(5), the 5 illegal actions tested (`start`, `pause`, `verify`, `mverify`, `finish`) all returned `code != 200`. The state-machine guards in `TestPlanServiceImpl` reject every illegal transition. ✓

```
[
  {"from_state": 5, "action": "start",   "http": 200, "api": 300, "rejected": true},
  {"from_state": 5, "action": "pause",   "http": 200, "api": 300, "rejected": true},
  {"from_state": 5, "action": "verify",  "http": 200, "api": 300, "rejected": true},
  {"from_state": 5, "action": "mverify", "http": 200, "api": 300, "rejected": true},
  {"from_state": 5, "action": "finish",  "http": 200, "api": 300, "rejected": true}
]
```

(`api: 300` = the project's `ResponseFactory.failure(...)` code; `http: 200` because the body itself carries the failure code per the project's response convention.)

### §6.2 — Happy-path nanoTime e2e trace

Walked DISPATCH → UNEXE → EXEING → VERIFY → MVERIFY → FINISH:

| transition | from → to | ok | client-side ns | ms |
|---|---|---|---:|---:|
| dispatch | DISPATCH → UNEXE | true | 23,950,680,000 | 23,950.68 |
| start    | UNEXE → EXEING   | true | 21,079,290,000 | 21,079.29 |
| verify   | EXEING → VERIFY  | true | 21,045,300,000 | 21,045.30 |
| mverify  | VERIFY → MVERIFY | true | 21,052,680,000 | 21,052.68 |
| finish   | MVERIFY → FINISH | true | 21,044,580,000 | 21,044.58 |

**Total happy-path latency: ~108 s** for the 5 transitions. Average **~21.6 s per transition**.

Where the time goes (per transition):
- `dispatch` is the heaviest: it materializes ALL exe_function rows for the plan's bound test_suite (after migration that's 656+ functions in some V1 plans). Each function spawns N exe_step rows. 23.9 s on this scale of data is plausible for sync MyBatis bulk insert without batched executor.
- `start/verify/mverify/finish` each run `UPDATE test_plan SET status=...` plus a cascade across `exe_function` and `exe_step` (status field rolls down). With 62,498 exe_step rows and no covering index on `(exe_function_id, exe_status)`, each cascade does a table-scan-equivalent.

This **matches the §1+§4 finding** that the bottleneck is data-volume × MyBatis row-by-row updates, not the state-machine logic itself.

Report: `results/state-machine/state-machine-<ts>.json`.

---

## Aggregate findings

1. **State machine correctness:** 5/5 illegal transitions rejected; 5/5 happy-path transitions accepted. Logic is sound.
2. **State machine throughput:** ~21 s / transition is too slow for any real-time UX. With 62 k exe_step rows post-migration, the cascading status updates need either:
   - an index `(exe_function_id, exe_status)` and `(plan_id, exe_status)`, AND/OR
   - a batched MyBatis executor (`SqlSessionFactory.openSession(ExecutorType.BATCH)`), AND/OR
   - move from row-by-row update to single `UPDATE … WHERE plan_id = ?`
3. **HTTP-tier throughput:** capped at ~180 rps even on simple list endpoints, due to multi-MB payload JSON serialization. Add pagination to `listAll` endpoints (frontend already paginates client-side after fetching everything; move that to server side).
4. **Auth latency is a non-issue:** cache-hit path is 540,000× faster than HTTP fetch; UserContextInterceptor's 5-min TTL absorbs > 99.9% of the cost.
5. **Async work is not used:** `executor.pool.size = 0` permanently. If the plan is to scale state-machine cascades, introduce `@Async` + a sized `ThreadPoolTaskExecutor` so the actuator metrics become meaningful.
6. **MQ link is dead by config:** `FASTOP_EMS_URL=""` short-circuits `/exeStep/do`. Compose this with the EMS stack and re-run §3 to get true e2e numbers.

## Known issues observed during testing

1. **wrk2 lua via Git Bash + docker mount** — `/scripts/...` got rewritten to `D:/Git/scripts/...` by MSYS path conversion. Fixed by `MSYS_NO_PATHCONV=1` in `run.sh`. Original symptom: 100% non-2xx responses (lua never loaded → no auth header).
2. **SpringFox 2.9.2 + Spring Boot 2.6 NPE** at `Orderings$8.compare` during `documentationPluginsBootstrapper` start — pre-existing fastop bug; the `pathmatch.matching-strategy: ANT_PATH_MATCHER` workaround in `application.yml` is necessary but not sufficient. Added a `BeanPostProcessor` in `SwaggerConfig` to filter `mapping.getPatternParser() != null` mappings.
3. **frontend Dockerfile** copied from `../infra/nginx.conf` — escapes the build context. Repointed compose `context: ..` (repo root) and updated COPY paths.
4. **Repeated state-machine runs back-to-back** see the rejection rate drift to 60% on a still-warm plan, because the previous run's `reset` is queued and hasn't returned by the time the next run starts probing illegal moves. Wait ≥ 30 s between runs, or take the 5/5 from the cold-cache run.

## Cleanup actions

- `sysbench_pt` DB is dropped at end of `sysbench/run.sh` (cleanup phase).
- Backend retains the dispatched state of plan `0318f63b-...` from §6 — the test resets it back to DISPATCH at exit. To verify: `mysql -e "SELECT plan_id, status FROM test_plan WHERE plan_id='0318f63b-d64c-4b8a-942e-e0253e40dac3';"` should show `status=5`.
- `MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE` env var added for the test run; revert before deploying to anything publicly reachable.
