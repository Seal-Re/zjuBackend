# Fastop 本地基础设施

一键拉起 Fastop 全栈：MySQL + auth-mock + device-mock + backend + frontend。

## 端口分配 (统一 20001-20020)

| 端口  | 组件                      | 容器内端口 | 备注                          |
|-------|---------------------------|------------|-------------------------------|
| 20001 | MySQL 8.0                 | 3306       | 数据库 `autosys_1014`         |
| 20002 | auth-mock-service         | 5000       | OAuth2 + RBAC mock (Flask)    |
| 20003 | device-controller-mock    | 5001       | 设备话题/示例 mock (Flask)    |
| 20004 | fastop backend            | 8080       | Spring Boot, context `/fastop`|
| 20005 | frontend (nginx)          | 80         | 生产构建 + 静态托管           |
| 20006-20010 | 保留              | -          | fastop 扩展                   |
| 20011 | EMS affair-center (预留)  | -          | 若并入 EMS 时启用             |
| 20012 | EMS subscribe-center (预留) | -        | 若并入 EMS 时启用             |
| 20013 | RocketMQ NameServer (预留) | -         | EMS 依赖                      |
| 20014 | RocketMQ Broker (预留)    | -          | EMS 依赖                      |
| 20015 | Nacos (预留)              | -          | EMS 依赖                      |
| 20016 | Redis (预留)              | -          | EMS 依赖                      |
| 20017-20020 | 通用预留          | -          |                               |

## 快速启动

```powershell
cd infra
copy .env.example .env

# 拉起全部
docker compose up -d

# 仅基础组件 (MySQL + mocks)，本地跑 backend/frontend
docker compose up -d mysql auth-mock device-mock

# 看日志
docker compose logs -f backend

# 停止 + 保留数据
docker compose down

# 停止 + 删卷（重置数据库）
docker compose down -v
```

## 数据库初始化

`fastop/dataset/` 目录被挂载为 MySQL 的 `/docker-entrypoint-initdb.d`：

- `260302.sql` — 主 DDL（基础结构、测试函数、用例、步骤、计划等）
- `alter_step_device_ems.sql` — `exe_step` 表扩展字段（device/EMS 调用相关）
- `operation_log.sql` — 操作审计日志表

仅在卷为空（首次启动 / `down -v` 之后）时执行。脚本按文件名字典序运行 — `260302.sql` 自然排在 `alter_*` 之前，无需手工排序。

## 仅本地开发（不容器化 backend / frontend）

仅起依赖：

```powershell
docker compose up -d mysql auth-mock device-mock
```

后端：

```powershell
$env:FASTOP_DATASOURCE_URL='jdbc:mysql://localhost:20001/autosys_1014?useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true'
$env:FASTOP_DEVICE_CONTROLLER_URL='http://localhost:20003'
$env:AUTH_SERVICE_URL='http://localhost:20002'
cd fastop; mvn spring-boot:run -pl fastop-service
```

前端：

```powershell
cd frontend; npm install; npm run dev
```

> 注意：dev 模式 `vite.config.ts` 默认代理到 `localhost:10001` / `localhost:5000`。本地全用 docker 时按需改 proxy target，或后端不容器化沿用默认端口。

## 测试账号

`admin / 123456`（ADMIN）、`designer1 / 123456`（DESIGNER）、`worker1 / 123456`（EXECUTOR）。

## 当前已知缺口

执行 `up` 之前请知悉：

- **EMS 链路独立部署**：`FASTOP_EMS_URL` 默认空 → ExeStep 执行步骤会返回 `未配置 fastop.integration.ems-url`。EMS 是独立项目（`docs/EMS_INTEGRATION.md`），需要联调时单独 `docker compose up`，预留端口位 20011-20016。
- **Auth 仍为 mock**：Java 后端无 Spring Security / JWT 验签，Token 信任 Flask `/userinfo` 返回（按设计保留）。
- **EMS 副作用未回写**：device-controller 接收到 EMS 推送指令后无回调链路；如需实测端到端 ack，需自行扩展。

已修复（之前的 BREAKING 缺口）：

- 设备 CRUD 后端已落 (`/devices/*` 全套 + `dataset/device.sql`)。
- TestPlan.remark 已落库 (`alter_test_plan_remark.sql`)。
- 状态机三态转移补齐 (`/planner/plan/{verify,mverify,finish,reset}/{planId}`)。
