# 项目分析与任务清单（P0 优先）

## 一、项目工作流程理解（基于代码与 API_Lifecycle_Verification.md）

### 1. 设计域（Designer）

| 阶段 | 说明 | 后端路径/表 | 前端页面 |
|------|------|-------------|----------|
| 创建测试模块 | 添加测试功能（test_function） | `POST /designer/testFunction/add` | 模块库 → 添加模块 |
| 构建层级 | 模块 → 子用例 → 步骤 | `/designer/module/add`, `/designer/case/add`, `/designer/step/add` | 模块编排（ModuleOrchestration） |
| 模块审签 | 多级审签（0–5 级） | `POST /designer/testFunction/check` | 测试审签 → 模块审签 |
| 创建测试清单并绑定 | 新建 test_suite，绑定 test_function | `POST /designer/testSuite/add`, `POST /functionSuite/createFunctionSuite` | 清单库 → 新建清单 |
| 清单审签 | 清单审签 | `POST /designer/testSuite/check` | 测试审签 → 清单审签 |

### 2. 执行域（Planner）

| 阶段 | 说明 | 后端路径 | 前端页面 |
|------|------|----------|----------|
| 创建测试计划 | 基于已发布清单生成计划快照 | `POST /planner/plan/createTestPlan` | 测试计划 → 新建计划 |
| 派工 | 派发计划，生成 exe_function / exe_step | `GET /planner/plan/dispatch/{planId}` | 测试计划 → 派发 |

### 3. 执行控制（Execution）

| 阶段 | 说明 | 后端路径 | 前端页面 |
|------|------|----------|----------|
| 获取任务/步骤 | 按计划、功能获取执行步骤 | `GET /exeFunction/testFunctions/inexe/{planId}`, `GET /exeStep/getinexe/{functionId}` | 测试执行 |
| 步骤操作 | 通过/失败/跳过、执行设备指令 | `POST /exeStep/stepOperate`, `POST /exeStep/do` | 测试执行 |
| 暂停 | 暂停功能下步骤 | `POST /exeStep/pause/{exeFunctionId}` | 测试执行 |

说明：生命周期文档中写的是 `POST /exeFunction/pause/{exeFunctionId}`，当前实现为 `POST /exeStep/pause/{exeFunctionId}`，需在文档或接口上统一。

---

## 二、数据库与缺失能力（基于 260302.sql）

### 现有表（节选）

- **设计域**：`base_struct`, `test_base`, `test_function`, `test_function_module`, `test_function_case`, `test_function_step`, `test_suite`, `function_suite`
- **计划与执行**：`test_plan`, `exe_function`, `exe_step`, `exe_log`

### 缺失/薄弱部分

1. **用户系统**  
   - 无独立 `user` / `sys_user` 等用户表。  
   - 审签、计划等仅用字符串（如 `checkWorker`、`proofer`、`approver`）。  
   - 前端 Layout 用户区为写死「管理员」。  
   → 需要：用户表、登录/登出、权限或角色与现有审签/计划字段对接。

2. **设备管理**  
   - `test_function` 有 `device_pool`（JSON），`exe_step` 有设备相关字段，但无统一「设备」主数据表与 CRUD。  
   - 无 `DeviceController` 或设备管理接口。  
   → 需要：设备表、设备 CRUD、与步骤/功能绑定的模型与接口。

---

## 三、前后端联调与接口问题

### 3.1 已对齐的接口（示例）

- 设计：`/designer/testFunction/*`, `/designer/testSuite/*`, `/designer/module/*`, `/designer/case/*`, `/designer/step/*`, `/functionSuite/*`
- 计划：`/planner/plan/*`（创建、更新、删除、listAll、dispatch、start、pause）
- 执行：`/exeFunction/testFunctions/*`, `/exeStep/getinexe/*`, `/exeStep/pause/*`, `/exeStep/stepOperate`, `/exeStep/do`
- 基础：`/base/listAllBaseStructAndId`, `/testBase/getTestBaseWithLimit`

### 3.2 需核对或修复的问题

| 问题类型 | 说明 | 建议 |
|----------|------|------|
| 响应结构不统一 | 前端有的地方用 `res.data`，有的用 `res`；拦截器已统一返回 `res.data`，但部分页面仍做 `res.data \|\| res` 兼容 | 统一约定：后端统一 `{ code, msg, data }`，前端请求层只暴露 `data`，页面只按「直接为列表/对象」处理 |
| 删除清单 | 后端 `deleteTestSuite` 为 `@RequestBody Integer suiteId`，前端传 `data: suiteId`（裸数字） | 确认 Spring 能正确反序列化；否则改为 `{ suiteId }` 或后端改为 `@RequestParam` |
| 创建计划 entityId | 前端用「构型选择」得到的 `baseId` 作为 `entityId` 提交；后端 DTO 有 `entityId`、`entityStructId` | 确认业务上 entityId 是否即 test_base.id，并统一命名与文档 |
| 执行树/步骤数据结构 | 执行页用「计划 → 功能 → 步骤」树，需与 `exe_function`、`exe_step` 及接口返回字段一致 | 对照后端实际返回定前端类型与字段映射，避免字段名/类型不一致 |
| 生命周期文档与实现不一致 | 文档写「暂停」为 `/exeFunction/pause`，实际为 `/exeStep/pause` | 以实际实现为准，更新 API_Lifecycle_Verification.md |

### 3.3 前端功能异常（需逐项验证）

- 模块库：构型级联 → 模块列表加载、提交审签、跳转编排。
- 清单库：新建/编辑清单、绑定模块、提交审签、删除。
- 测试计划：新建（含 entityId/suiteId/时间）、编辑、派发、开始/暂停、删除/批量删除。
- 测试审签：模块审签/清单审签列表、审签提交（level/checkWorker 等参数）。
- 测试执行：计划选择、执行树加载、步骤操作（通过/失败/跳过）、执行指令、暂停、日志。
- 测试指挥：与执行/计划接口的联动。
- 系统日志：操作日志、执行日志的列表与筛选。

---

## 四、项目冗余与可清理内容

### 4.1 重复/过时文件

| 项 | 说明 |
|----|------|
| `frontend/vite.config.js` | 与 `vite.config.ts` 功能重复，建议保留 ts 版本，删除 .js |
| `frontend/vite.config.d.ts` | 若为自动生成或仅类型声明，确认后保留或从版本控制中忽略 |

### 4.2 重复度高的前端逻辑

| 位置 | 说明 |
|------|------|
| 模块库 / 清单库 / 测试计划 | 三者均有「机型 → 专业 → 子系统」级联 + 同一套 `listAllBaseStructAndId` 与选项推导逻辑，代码重复 | 抽成公共组件（如 `GlobalFilterCascader`）与 composable（如 `useBaseStructCascade`） |
| 审签状态展示 | 多处 `APPROVE_STATUS_MAP`、`getStatusType`、`getStatusText` 等 | 抽成公共常量 + 工具函数或 composable |

### 4.3 可删除或标记废弃

- 未使用的 API 方法（例如仅在注释或旧代码中出现的请求）。
- 未使用的路由、视图或菜单项。
- 与当前后端不一致的 Mock 数据或接口占位。

---

## 五、前端美化方向

- 统一设计符号：颜色、圆角、阴影、间距（可沿用 Layout 中已有 CSS 变量）。
- 列表与表格：统一空态、加载态、错误态与操作列样式。
- 表单与弹窗：统一表单项宽度、按钮区、必填与校验提示样式。
- 审签/执行等关键流程：步骤条、状态标签、结果反馈样式统一。
- 响应式：关键列表与表单在小屏下的布局与操作可及性。

---

## 六、任务清单（按 P0 → P2 排序）

### P0（必须优先：阻塞主流程或安全）

| 编号 | 任务 | 说明 |
|------|------|------|
| P0-1 | **用户/认证对接** | 对接外部认证服务（OAuth2/User/RBAC 规范）；本地测试使用 `auth-mock-service`；前端已封装 `auth.ts`/`user.ts`/`rbac.ts`，登录页与 Layout 接入登录态与 Bearer Token |
| P0-2 | **前后端响应结构统一** | 约定并落实统一响应体 `{ code, msg, data }`；前端请求层统一只返回 `data`；清理各页面中 `res.data \|\| res` 等兼容写法，避免二次解包 |
| P0-3 | **创建/编辑测试计划接口联调** | 确认 createTestPlan/updateTestPlan 的 entityId、entityStructId、suiteId、时间等字段与前端传参一致；修复因字段缺失或错误导致的创建/编辑失败 |
| P0-4 | **测试执行主流程联调** | 从「选择计划 → 加载执行树 → 步骤操作（通过/失败/跳过）→ 执行指令 → 暂停」全链路与后端对齐，保证核心执行路径可用 |

### P1（重要：功能完整性与可维护性）

| 编号 | 任务 | 说明 |
|------|------|------|
| P1-1 | **设备管理模块** | 设备 API 规范已见 `docs/DEVICE_API_SPEC.md`，路径为 `/devices`、`/devices/list` 等；后端需新增设备表与 CRUD 接口；前端已预留 `api/device.ts`，需增加「设备管理」菜单与列表/编辑；与 test_function.device_pool、步骤设备字段建立关联 |
| P1-2 | **用户与权限扩展** | 在 P0-1 基础上，增加角色或权限标识；审签/计划等接口与「当前用户」关联（如 checkWorker、created_by 等）；前端根据权限控制菜单或按钮 |
| P1-3 | **审签流程端到端验证** | 模块审签、清单审签与后端 level/checkWorker 等参数一致；列表状态、审签后状态变更与前端展示一致 |
| P1-4 | **清单删除与绑定逻辑** | 确认 deleteTestSuite 请求体格式；清单编辑时已绑定模块的加载与保存（getRely、createFunctionSuite）与后端一致，修复删除或保存失败问题 |
| P1-5 | **去除冗余与重复** | 删除 `vite.config.js`（保留 `vite.config.ts`）；抽取「机型-专业-子系统」级联与审签状态展示为公共组件/ composable；删除或标记未使用 API/路由/视图 |

### P2（体验与文档）

| 编号 | 任务 | 说明 |
|------|------|------|
| P2-1 | **API 与生命周期文档同步** | 以实际后端为准，更新 API_Lifecycle_Verification.md（含暂停接口路径、请求/响应示例）；与统一文档 API_DOCUMENTATION.md 保持一致 |
| P2-5 | **统一 API 文档维护** | 已将认证/用户/RBAC、设备规范、Fastop 设计/计划/执行等全部接口汇总至根目录 **API_DOCUMENTATION.md**；后续新增或变更接口须同步更新该文档 |
| P2-2 | **前端美化与统一** | 按「五、前端美化方向」落实：设计符号、列表/表格/表单/弹窗、关键流程的视觉与交互统一；优化空态与错误态 |
| P2-3 | **系统日志与操作日志** | 确保操作日志、执行日志接口与前端列表/筛选一致，无报错或数据错位 |
| P2-4 | **测试指挥页与执行联动** | 确认指挥页所需接口与数据来源，与计划/执行状态联动正确，无功能异常 |

---

## 七、建议执行顺序（概要）

1. **P0-2 + P0-3**：先统一响应与计划接口，减少联调噪音。  
2. **P0-1**：用户登录与基础身份，为后续权限和审签打基础。  
3. **P0-4**：执行主流程打通，保证核心业务可演示、可测。  
4. **P1-4、P1-3**：清单与审签闭环。  
5. **P1-1、P1-2**：设备与用户/权限完善。  
6. **P1-5**：去冗余与抽公共。  
7. **P2-***：文档与前端美化、日志与指挥页收尾。

如需把某一条拆成更细的 sub-task（例如按页面或接口列），可以指定 P0/P1 编号，我可以再展开成可执行的子项。
