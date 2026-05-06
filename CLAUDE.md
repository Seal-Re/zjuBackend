# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Fastop is a test management system for aircraft inspection workflows. It is currently a **modular monolith** (Spring Boot multi-module Maven project, evolved from an earlier Spring Cloud micro-service split — old `service-designer` artifact name retained as residue, see `application.yml:3`). Local stack: Spring Boot backend + Vue 3 frontend + two Flask mocks (auth, device-controller). An external EMS event-bus (separate repo) is the planned async dispatch path for device commands; currently optional and **not started by default**.

## Development Commands

### Backend (Java 8 / Spring Boot 2.6.13)

```bash
cd fastop
mvn clean package              # Build all modules
mvn clean package -DskipTests  # Build without tests (used in CI)
mvn test                       # Run all tests
mvn test -pl fastop-service -Dtest=EmsMessageServiceTest    # Single test class
mvn spring-boot:run -pl fastop-service                      # Run backend (needs MySQL up)
```

Default port: `http://localhost:10001/fastop` (override via `FASTOP_SERVER_PORT`).

### Frontend (Vue 3 / Vite)

```bash
cd frontend
npm install
npm run dev          # Dev server (proxies /api -> backend, /auth-api -> auth mock)
npm run build        # vue-tsc type-check + production build
npm run preview      # Preview production build
```

### Auth Mock Service (Python / Flask)

```bash
cd auth-mock-service
pip install -r requirements.txt   # flask>=3.0.0, flask-cors>=4.0.0
python app.py                     # http://localhost:5000
```

### Device Controller Mock (Python / Flask)

```bash
cd device-controller-mock
pip install -r requirements.txt
python app.py                     # http://localhost:5001
```

Test accounts: `admin/123456` (ADMIN), `designer1/123456` (DESIGNER), `worker1/123456` (EXECUTOR).

### One-shot stack (Docker)

```bash
cd infra
cp .env.example .env
docker compose up -d              # All services on ports 20001-20005
```

See `infra/README.md` for full port map (20001-20020 reserved range, MySQL→20001, mocks→20002/20003, backend→20004, frontend→20005).

## Architecture

### Service Topology

| Service                | Tech                  | Default Port | Container Port | Container Name        |
|------------------------|-----------------------|--------------|----------------|-----------------------|
| MySQL 8                | DB (`autosys_1014`)   | 20001        | 3306           | fastop-mysql          |
| auth-mock-service      | Flask, OAuth2 + RBAC  | 20002        | 5000           | fastop-auth-mock      |
| device-controller-mock | Flask, `/topics`      | 20003        | 5001           | fastop-device-mock    |
| fastop backend         | Spring Boot           | 20004        | 8080           | fastop-backend        |
| frontend               | Vue 3 / nginx (prod)  | 20005        | 80             | fastop-frontend       |

Native dev (no docker) uses historical ports: 3304 / 5000 / 5001 / 10001 / 5173. Both modes coexist via env-var overrides; `application.yml` reads `FASTOP_DATASOURCE_URL`, `FASTOP_DEVICE_CONTROLLER_URL`, `FASTOP_EMS_URL`, `AUTH_SERVICE_URL`.

### Backend Module Structure (Maven multi-module)

```
fastop/
├── fastop-base/                       # Shared common (config, response wrappers, status constants)
│   └── fastop-base-common/
├── fastop-model/                      # Entity + DTO definitions
│   ├── fastop-model-designer/
│   └── fastop-model-planner/         # Includes EMS DTOs (MessageEtt/MessageEvents/MessageParams)
├── fastop-dal/                        # MyBatis mappers
│   └── fastop-dal-designer/
└── fastop-service/                    # Spring Boot app — controllers, services, integration adapters
    └── src/main/java/.../service/
        ├── designer/                  # Module / case / suite / step CRUD (design-time)
        ├── planner/                   # Test plan, execution function, exe-step, op-log (runtime)
        ├── integration/               # External adapters: EmsMessageService, DeviceIntegrationService
        └── config/                    # WebMvcConfig, UserContextInterceptor, IntegrationProperties
```

Each domain follows `controller/` → `service/` (interface) → `service/impl/`. ORM is **MyBatis** (XML mappers at `fastop-service/src/main/resources/mapper/`) with **Druid** pool. Schema and seed in `fastop/dataset/` (`260302.sql` main DDL, `alter_step_device_ems.sql` device/EMS columns, `operation_log.sql` audit).

### Test-Plan Lifecycle State Machine

`TestPlanStatusContants` defines seven states (`UNEXE / VERIFY / EXEING / PAUSE / MVERIFY / DISPATCH / FINISH`). `TestPlanServiceImpl` covers the full forward path with explicit guards on every transition — illegal moves return `ResponseFactory.failure(...)` rather than silently advancing.

```
DISPATCH(5) ──dispatch──► UNEXE(0)
UNEXE(0)    ──start───►   EXEING(2)
EXEING(2)   ──pause───►   PAUSE(3)
PAUSE(3)    ──start───►   EXEING(2)   (恢复)
EXEING(2)   ──verify──►   VERIFY(1)
VERIFY(1)   ──mverify─►   MVERIFY(4)
MVERIFY(4)  ──finish──►   FINISH(6)
any-non-FINISH ──reset──► DISPATCH(5)
```

Endpoints: `POST /planner/plan/{dispatch|start|pause|verify|mverify|finish|reset}/{planId}`. The function-level state machine on `ExeFunctionServiceImpl.updateFunctionStatusByOption:237` is independent (per-step transitions) and is also guarded.

### EMS Async Dispatch Path (separate repo, do NOT merge)

EMS is the **separate `ems-backend` repo** (https://github.com/Seal-Re/ems, local at `D:/AgentWorkStation/workspace/ems-backend`). Spring Cloud Alibaba — Java 17, RocketMQ, Nacos, Redis, MySQL. Two services: `affair-center` (REST inbound) + `subscribe-center` (MQ consumer + HTTP outbound). Architectural divergence vs. fastop (Java 8 monolith) is too large to merge — see `docs/EMS_INTEGRATION.md` for the full call-chain, payload contract, and the four conditions under which a future merge would be reconsidered.

Fastop side: `ExeStepServiceImpl` builds a `MessageEtt` per step (via `EmsMessageService.buildFromExeStep`) and POSTs to `${fastop.integration.ems-url}${ems-send-path}` (default path `/addDefault`). Default `ems-url: ""` — production **must** inject `FASTOP_EMS_URL`; the old default leaked an internal IP (see `IntegrationPropertiesTest:21`). Empty URL → fastop returns `failure("未配置 fastop.integration.ems-url 且请求未携带 url")`.

```
fastop ExeStepServiceImpl
   └─ POST {emsUrl}/addDefault  (MessageEtt JSON)
        └─ EMS affair-center
             └─ RocketMQ publish (topic="affair")
                  └─ EMS subscribe-center consumer
                       └─ HTTP POST to Subscribe.eventDest (device-controller)
```

No callback path back into fastop — EMS is fire-and-forget. If end-to-end ack is needed, device-controller must POST a new event back through EMS, with fastop registering a subscriber.

### Auth Flow

OAuth2 password grant via the Flask auth-mock-service. Frontend stores `access_token` in localStorage. Axios attaches `Authorization: Bearer {token}` to every request.

`UserContextInterceptor` (Java backend) extracts the Bearer token, calls `${auth.service.url}/userinfo`, caches `token → username` for 5 min (`CACHE_TTL_MS=5*60*1000`, `CACHE_MAX_SIZE=5000`), stores into `UserContextHolder`. **No local signature/expiry verification** — backend trusts the auth service. RBAC enforcement currently lives only in the mock; backend has no `@PreAuthorize`, no Spring Security, no user/role tables.

### Frontend Structure

- **UI library**: Element Plus
- **State management**: Pinia (`src/store/auth.ts`, `src/store/globalFilter.ts`)
- **HTTP client**: Axios with Bearer token interceptor
- **Path alias**: `@/` → `src/`
- **Routing**: Vue Router with auth guard (whitelist: `/login`; token check via `localStorage.access_token`)

Key views: `src/views/design/` (module / suite library), `src/views/plan/` (test plans), `src/views/review/` (test review + system logs), `src/views/device/` (device management), `src/views/command/` (command dashboard).

### API Response Convention

All backend APIs return:

```json
{ "code": 200, "data": {}, "message": "success", "timestamp": 1709423600 }
```

`message` is unified — historical `msg` field has been renamed; do not reintroduce. Mock services already comply.

## Known Gaps (read before extending)

Resolved (implementations live in code now, listed for traceability):

- **Device CRUD** ✅ — `service/device/{controller,service}/`, mapper at `model/planner/dto/DeviceMapper.{java,xml}`, entity `model/planner/entity/Device.java`, DDL at `fastop/dataset/device.sql`. Soft delete via `deleted` flag; `code` unique-by-active-row.
- **TestPlan.remark** ✅ — column added (`fastop/dataset/alter_test_plan_remark.sql`), entity field + selective update wired in `TestPlanServiceImpl.remarkTestPlan`.
- **State-machine guards** ✅ — see "Test-Plan Lifecycle State Machine"; all seven states reachable, illegal transitions rejected.
- **`saveExeFunction` null-return removed** ✅ — now throws `IllegalArgumentException` on null `function`/`planId`/`functionSuite` so the surrounding `@Transactional` rolls back instead of writing orphan ExeStep rows.
- **`getExeStepByPlan` implemented** ✅ — was a stub returning empty list.

Still open (require new entities / external decisions, do not silently paper over):

- **`executeStepCommandv2` / `getChartData`** — declared as TODO in `ExeStepServiceImpl:277-285`; not exposed in interface or controller. Depend on unmodeled `Device` / `DeviceCommand` / `PaintModel` entities. Add endpoint *after* DDL lands.
- **`listSortExeFunction`** — `ExeFunctionServiceImpl:221` stubbed; depends on `TestFunctionGroup` / `User` / `ExecutorGroup` entities.
- **EMS not wired by default** — `FASTOP_EMS_URL` empty → step dispatch returns `failure(...)`. To exercise end-to-end, run `ems-backend` (RocketMQ + Nacos + Redis stack) and inject the URL. See `docs/EMS_INTEGRATION.md`.
- **Auth real-implementation gap** — see "Auth Flow"; no JWT verify, no RBAC enforcement in Java backend (mock-only by deliberate scope).

When adding code that depends on any "still open" item, call out the gap or wire it explicitly.

## CI

GitHub Actions (`.github/workflows/maven.yml`): triggers on push/PR to main for changes under `fastop/`. Runs `mvn -B package -DskipTests` with JDK 8 (Temurin).

## Key Documentation

- `API_DOCUMENTATION.md` — Full API specification
- `docs/DEVICE_API_SPEC.md` — Device management API spec
- `docs/EMS_INTEGRATION.md` — EMS ↔ fastop boundary, call-chain, payload, deployment independence
- `infra/README.md` — Local docker stack, port map, init-script behavior
- `.postman/` — Postman collection for API lifecycle testing
