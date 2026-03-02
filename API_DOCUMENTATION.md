# 统一 API 文档

本文档集中管理本系统及依赖服务的全部接口信息。受保护接口均在请求头携带：`Authorization: Bearer {access_token}`。

**标准响应结构：**
```json
{
  "code": 200,
  "data": { ... },
  "message": "success",
  "timestamp": 1709423600
}
```
（部分 Java 后端使用 `msg` 而非 `message`，前端已做兼容。）

---

## 1. 认证与用户权限服务（外部/独立服务）

基地址由环境变量或前端代理配置（如 `/auth-api` -> `http://localhost:5000`）。本地测试可使用 `auth-mock-service`。

### 1.1 认证与令牌管理 (Auth/Token)

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /oauth/token | 获取令牌。支持 grant_type：password（传 username、password）、refresh_token、authorization_code、client_credentials |
| POST / GET | /oauth/check_token | 资源服务器校验 Token 合法性、过期时间及 Scope |
| POST | /oauth/revoke | 登出或使 Access Token / Refresh Token 失效 |
| GET | /userinfo | 携带有效 Token 获取当前用户基本信息（OpenID Connect） |

**POST /oauth/token (password)**  
Body (application/x-www-form-urlencoded): `grant_type=password&username=xxx&password=xxx`  
Response `data`: `{ "access_token", "refresh_token", "token_type": "Bearer", "expires_in", "scope" }`

### 1.2 用户与身份管理 (User Management)

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/v1/users | 注册/创建新用户 |
| GET | /api/v1/users/{id} | 获取指定用户信息 |
| PUT / PATCH | /api/v1/users/{id} | 更新用户基础资料或锁定/激活状态 |
| POST | /api/v1/users/{id}/password/reset | 强制重置密码。Body: `{ "newPassword": "xxx" }` |

### 1.3 权限与角色控制 (RBAC)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/v1/roles | 角色列表 |
| POST | /api/v1/roles | 创建角色（如 ADMIN, DEVELOPER）。Body: `{ "code", "name", "description" }` |
| POST | /api/v1/roles/{id}/permissions | 为角色绑定权限点。Body: `{ "permissions": ["sys:user:add", ...] }` |
| GET | /api/v1/permissions | 获取所有系统预设权限标识（如 sys:user:add, sys:file:delete） |
| GET | /api/v1/users/{id}/roles | 查询指定用户的角色集合 |

---

## 2. 设备管理 (Device Management)

规范详见 [docs/DEVICE_API_SPEC.md](docs/DEVICE_API_SPEC.md)。摘要如下：

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /devices/list | 设备列表（分页、筛选 name/type/status） |
| GET | /devices/{id} | 获取单个设备详情 |
| POST | /devices | 新增设备 |
| PUT | /devices/{id} | 更新设备 |
| DELETE | /devices/{id} | 删除设备 |

设备与测试功能 `device_pool`、执行步骤 `deviceId` 的关联方式见设备规范文档。

---

## 3. 测试业务后端（Fastop）

基路径为应用 context-path（如 `/fastop`），前端通过 `/api` 代理至该服务。

### 3.1 基础与构型

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /base/listAllBaseStructAndId | 获取全部构型（机型/专业/子系统）及 baseId |
| GET | /testBase/getTestBaseWithLimit | 按 model、profession、subsystem 查询 test_base |
| GET | /testBase/{baseId} | 按 baseId 获取测试库详情 |

### 3.2 设计域 (Designer)

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /designer/testFunction/add | 新增测试模块（功能） |
| POST | /designer/testFunction/update | 更新测试模块 |
| POST | /designer/testFunction/submit | 提交模块审签 |
| POST | /designer/testFunction/delete/{funId} | 删除测试模块 |
| GET | /designer/testFunction/get/{funId} | 获取模块详情 |
| GET | /designer/testFunction/listByBaseId | 按 testBaseId 列模块 |
| GET | /designer/testFunction/list | 模块列表（可选 testBaseId） |
| POST | /designer/testFunction/check | 模块审签。Query: funId, checkWorker, level |
| GET | /designer/testFunction/getCheckTestFunction | 获取待审签模块列表 |
| POST | /designer/module/add | 新增子模块（用例） |
| POST | /designer/module/update | 更新子模块 |
| POST | /designer/module/delete | 删除子模块。Query: ModuleId |
| GET | /designer/module/treeByFunId | 按 funId 获取模块树 |
| GET | /designer/module/listByFunId | 按 funId 列子模块 |
| POST | /designer/case/add | 新增子用例 |
| POST | /designer/case/update | 更新子用例 |
| POST | /designer/case/delete | 删除子用例。Query: caseId |
| GET | /designer/case/listByModuleId | 按 moduleId 列子用例 |
| POST | /designer/step/add | 新增步骤 |
| POST | /designer/step/update | 更新步骤 |
| POST | /designer/step/delete | 删除步骤。Query: StepId |
| GET | /designer/step/listByCaseId | 按 caseId 列步骤 |
| POST | /designer/testSuite/add | 新增测试清单 |
| POST | /designer/testSuite/update | 更新测试清单 |
| POST | /designer/testSuite/submit | 提交清单审签 |
| POST | /designer/testSuite/delete | 删除清单。Body: suiteId (Integer) |
| GET | /designer/testSuite/get/{suiteId} | 清单详情 |
| GET | /designer/testSuite/listByBaseId | 按 testBaseId 列清单 |
| POST | /designer/testSuite/check | 清单审签。Query: suiteId, checkWorker, level |
| GET | /designer/testSuite/getCheckTestSuite | 待审签清单列表 |
| GET | /functionSuite/listAll | 功能-清单关联列表 |
| POST | /functionSuite/createFunctionSuite | 创建清单并绑定功能 |
| POST | /functionSuite/deleteFunctionSuite | 删除功能-清单关联 |
| GET | /functionSuite/rely | 清单依赖。Query: suiteId |

### 3.3 计划域 (Planner)

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /planner/plan/createTestPlan | 创建测试计划 |
| POST | /planner/plan/updateTestPlan | 更新测试计划 |
| POST | /planner/plan/remarkTestPlan | 添加备注 |
| DELETE | /planner/plan/deleteTestPlan/{planId} | 删除单个计划 |
| DELETE | /planner/plan/deleteTestPlanWithBatch | 批量删除。Body: planIds |
| GET | /planner/plan/listAll | 查询全部计划 |
| GET | /planner/plan/dispatch/{planId} | 派发计划 |
| POST | /planner/plan/start/{planId} | 开始计划 |
| POST | /planner/plan/pause/{planId} | 暂停计划 |

### 3.4 执行控制 (Execution)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /exeFunction/testFunctions/inexe/{planId} | 指定计划下正在执行的功能列表 |
| GET | /exeFunction/testFunctions/id/{functionId} | 按功能 ID 获取执行功能详情 |
| GET | /exeStep/getinexe/{functionId} | 指定执行功能下的步骤列表（functionId 为 exeFunctionId） |
| POST | /exeStep/pause/{exeFunctionId} | 暂停该功能下步骤 |
| POST | /exeStep/stepOperate | 步骤操作。Body: exeStepId, option 等 |
| POST | /exeStep/do | 执行步骤指令（含 deviceId、command、url 等） |
| POST | /exeStep/log/save | 保存执行日志 |
| GET | /exeStep/log/list | 执行日志列表（可选 stepId、planId、时间、分页） |

### 3.5 操作日志

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /log/operation/record | 记录操作日志 |
| GET | /log/operation/list | 操作日志列表（筛选、分页） |

---

## 4. 前端对接说明

- **认证服务**：`src/api/authRequest.ts` 使用 baseURL `/auth-api`（可配置 `VITE_AUTH_API`），封装见 `auth.ts`、`user.ts`、`rbac.ts`。
- **业务后端**：`src/api/request.ts` 使用 baseURL `/api`，请求自动附加本地存储的 `access_token`。
- **设备接口**：待后端实现后，可在 `src/api/device.ts` 中按 [DEVICE_API_SPEC.md](docs/DEVICE_API_SPEC.md) 封装，baseURL 可与业务后端统一或单独配置。

生命周期与用例级请求/响应示例见 [API_Lifecycle_Verification.md](API_Lifecycle_Verification.md)。
