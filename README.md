# zjuBackend

The repository is named **zjuBackend**; the software inside it is the **Fastop** platform
(the Maven `artifactId` is `fastop`, group `com.hengtiansoft`). It is a test-management
system for aircraft-inspection workflows: test engineers design reusable modules, cases and
steps, assemble them into plans, dispatch plans to devices, and review the results.

Team project — contributors: [Seal-Re](https://github.com/Seal-Re), [bddye](https://github.com/bddye).

## Services

| Service | Port | Runtime | Purpose |
|---|---|---|---|
| `fastop/fastop-service` | `10001/fastop` | Java 8 · Spring Boot 2.6.13 | REST APIs; MyBatis + Druid; OAuth2/OIDC login |
| `frontend` | `5173` | Vue 3 · Vite 5 · TypeScript | Element Plus UI; Pinia stores; Axios to `/api` |
| `device-controller-mock` | `5001` | Python 3 · Flask | Returns `GET /topics` (device topics + example payloads) for local development |

Authentication is delegated to an external OIDC identity provider (see [Auth](#auth)); the
backend also calls an external EMS service for device messaging. Both endpoints are
configurable, so a full run needs an IdP and a MySQL database.

## Backend module layout

```
fastop/
├── fastop-base/       # Common utilities
│   └── fastop-base-common/     # Response envelope, user context, config
├── fastop-model/      # Entity / DTO models
│   ├── fastop-model-designer/  # Design-time entities
│   └── fastop-model-planner/   # Runtime entities (incl. EMS DTOs)
├── fastop-dal/        # Data access
│   └── fastop-dal-designer/    # MyBatis mappers + XML
├── fastop-service/    # Spring Boot app — controllers + services + main()
└── dataset/           # Schema + seed SQL
```

The service layer is split into two domains, each with its own
`controller → service (interface) → service.impl`:

- `designer/` — reusable artifacts: modules, functions, cases, steps, suites, base structs
  (`/designer/module`, `/designer/testFunction`, `/designer/case`, `/designer/step`, …).
- `planner/` — execution: plans, execution functions, execution steps, operation log
  (`/planner/plan`, `/exeFunction`, `/exeStep`, `/log/operation`).

An `integration/` package bridges to external systems: `GET /integration/device/topics`
proxies the device controller, and `EmsMessageService` builds outbound EMS messages from
execution steps.

## Auth

Login uses the OAuth2 authorization-code flow through Spring Security (`oauth2Login`), with
the provider registered as `idp` in `application.yml`. All requests except `/` and `/error`
require an authenticated session; the login session is carried in the `FASTOP_SESSIONID`
cookie. `UserContextInterceptor` copies the OIDC username into `UserContextHolder` for the
request. The frontend starts the flow by redirecting to
`{backend}/oauth2/authorization/idp`, then reads the current user from `GET /me` and logs out
via `POST /logout`.

The `UserInfo.roles` array returned by `/me` is exposed through `useAuthStore().hasRole()`,
but no role-based authorization is enforced on the backend — there is no role enum or
`@PreAuthorize` usage in the code, and the role names are supplied by the IdP.

## API response envelope

All backend APIs return:

```json
{ "code": 200, "msg": "操作成功", "data": {}, "totalNum": 0 }
```

`ResponseBody` fields are `code`, `msg`, `data`, `totalNum`; the Axios interceptor unwraps
`data` and surfaces non-200 `code` values as errors.

## Getting started

Prerequisites: JDK 8 (`JAVA_HOME`), Maven, Node.js, Python 3, a running MySQL 8, and an OIDC
IdP reachable at the configured issuer.

```bash
# 1. MySQL (any image works)
docker run -d --name fastop-mysql \
  -e MYSQL_ROOT_PASSWORD=<root-password> -e MYSQL_DATABASE=autosys_1014 \
  -p 3304:3306 mysql:8
# then load fastop/dataset/260302.sql (schema + seed), plus operation_log.sql and
# alter_step_device_ems.sql if your database predates them

# 2. backend
cd fastop && mvn clean package -DskipTests \
  && java -jar fastop-service/target/fastop-service-*.jar

# 3. device mock (optional, separate terminal)
cd device-controller-mock && pip install -r requirements.txt && python app.py

# 4. frontend
cd frontend && npm install && npm run dev
# open http://localhost:5173
```

Configuration comes from `fastop-service/src/main/resources/application.yml` and can be
overridden by environment variables:

| Variable | Default | Meaning |
|---|---|---|
| `FASTOP_SERVER_PORT` | `10001` | Backend port (context path `/fastop`) |
| `FASTOP_DATASOURCE_URL` | `jdbc:mysql://localhost:3304/autosys_1014` | JDBC URL |
| `FASTOP_DATASOURCE_USERNAME` / `FASTOP_DATASOURCE_PASSWORD` | `root` / — | DB credentials |
| `IDP_CLIENT_ID` / `IDP_CLIENT_SECRET` | `client` / — | OIDC client credentials |
| `FASTOP_DEVICE_CONTROLLER_URL` | `http://localhost:5001` | Device mock base URL |
| `FASTOP_EMS_URL`, `FASTOP_EMS_SEND_PATH`, `FASTOP_EMS_ABILITY_DEFAULT` | — | EMS messaging |
| `FASTOP_FRONTEND_ORIGIN`, `FASTOP_POST_LOGIN_REDIRECT` | `http://localhost:5173` | CORS + post-login redirect |

The OIDC `issuer-uri` is set in `application.yml`.

The frontend proxies `/api/*` to `http://localhost:10001/fastop` via `vite.config.ts`.

## CI

`.github/workflows/maven.yml` runs `mvn -B package -DskipTests` on Temurin JDK 8 for pushes
and pull requests that touch `fastop/`.

## Notes and limitations

- A local run requires an external OIDC IdP; the default `issuer-uri` points at a fixed
  host, so change it (or the environment) before starting the backend.
- `fastop-service/src/main/resources/bootstrap.yml` declares Nacos discovery/config, but the
  Maven build pulls in no Spring Cloud or Nacos dependency, so that file has no effect.
- `application.yml` ships default credentials (`jdbc` password, `IDP_CLIENT_SECRET`); supply
  real values through environment variables.
- `.cursor/debug-ea6e3f.log` is a committed editor debugging log.

## License

MIT. See [LICENSE](LICENSE).
