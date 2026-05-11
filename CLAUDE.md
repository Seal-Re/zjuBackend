# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Fastop is a test management system for aircraft inspection workflows. It consists of three services: a Spring Boot backend, a Vue 3 frontend, and a Flask-based OAuth2 mock service for local auth.

## Development Commands

### Backend (Java/Spring Boot)

```bash
cd fastop
mvn clean package              # Build all modules
mvn clean package -DskipTests  # Build without tests (used in CI)
mvn test                       # Run all tests
```

The backend runs on `http://localhost:10001/fastop`. Requires MySQL on port 3304 (database: `autosys_1014`).

### Frontend (Vue 3 / Vite)

```bash
cd frontend
npm install          # Install dependencies
npm run dev          # Dev server (proxies /api -> backend, /auth-api -> auth mock)
npm run build        # Type-check (vue-tsc) + production build
npm run preview      # Preview production build
```

### Auth Mock Service (Python/Flask)

```bash
cd auth-mock-service
pip install -r requirements.txt   # flask>=3.0.0, flask-cors>=4.0.0
python app.py                     # Runs on http://localhost:5000
```

Test accounts: `admin/123456` (ADMIN), `designer1/123456` (DESIGNER), `worker1/123456` (EXECUTOR).

## Architecture

### Three-Service Local Stack

1. **Frontend** (Vite dev server) — proxies API calls via `vite.config.ts`:
   - `/api/*` → `http://localhost:10001/fastop` (backend)
   - `/auth-api/*` → `http://localhost:5000` (auth mock)
2. **Backend** (`fastop/`) — Spring Boot 2.6.13, Java 8, serves REST APIs under `/fastop`
3. **Auth Mock** (`auth-mock-service/`) — Flask OAuth2 mock with in-memory token/user storage

### Backend Module Structure (Maven multi-module)

```
fastop/
├── fastop-base/          # Common utilities (fastop-base-common submodule)
├── fastop-model/         # Entity/DTO models
│   ├── fastop-model-designer/   # Designer domain entities
│   └── fastop-model-planner/    # Planner domain entities
├── fastop-dal/           # Data access layer (MyBatis mappers)
│   └── fastop-dal-designer/
└── fastop-service/       # Spring Boot app — controllers & services
```

### Backend Domain Separation

The service layer is split into two domains:

- **designer/** — Test module/case/step/suite CRUD (design-time artifacts)
- **planner/** — Test plan management, execution functions/steps, operation logs

Each domain follows: `controller/` → `service/` (interface) → `service/impl/` (implementation).

### ORM & Database

- **MyBatis** with XML mappers at `fastop-service/src/main/resources/mapper/`
- **Druid** connection pool
- Database config in `fastop-service/src/main/resources/application.yml`
- Schema/seed data in `fastop/dataset/` (SQL files)

### Frontend Structure

- **UI library**: Element Plus
- **State management**: Pinia (`src/store/auth.ts`, `src/store/globalFilter.ts`)
- **HTTP client**: Axios with Bearer token interceptor
- **Path alias**: `@/` → `src/`
- **Routing**: Vue Router with auth guard (whitelist: `/login`; token check via `localStorage.access_token`)

Key frontend areas: `src/views/design/` (module/suite library), `src/views/plan/` (test plans), `src/views/review/` (test review + system logs), `src/views/device/` (device management), `src/views/command/` (command dashboard).

### Auth Flow

OAuth2 password grant via the auth mock service. Frontend stores `access_token` in localStorage. Axios interceptor attaches `Authorization: Bearer {token}` to all API requests. RBAC with three roles: ADMIN, DESIGNER, EXECUTOR.

### API Response Convention

All backend APIs return:
```json
{ "code": 200, "data": {}, "message": "success", "timestamp": 1709423600 }
```

## CI

GitHub Actions (`.github/workflows/maven.yml`): triggers on push/PR to main for changes under `fastop/`. Runs `mvn -B package -DskipTests` with JDK 8 (Temurin).

## Key Documentation

- `API_DOCUMENTATION.md` — Full API specification
- `docs/DEVICE_API_SPEC.md` — Device management API spec
- `.postman/` — Postman collection for API lifecycle testing
