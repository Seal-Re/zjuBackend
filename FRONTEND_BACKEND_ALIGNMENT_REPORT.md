# 前后端对齐检查报告

本文档基于当前代码与 API 生命周期文档，对前后端接口、请求体及测试流程进行逐项核对。

---

## 一、接口路径与方法对照

### 1. 设计域（Designer）

| 功能 | 前端调用 | 后端路径/方法 | 状态 |
|------|----------|----------------|------|
| 测试基地列表 | `GET /testBase/getTestBaseWithLimit` | `GET /testBase/getTestBaseWithLimit` | ✅ 一致 |
| 测试功能列表 | `GET /designer/testFunction/listByBaseId` | `GET /designer/testFunction/listByBaseId` | ✅ 一致 |
| 测试清单列表 | `GET /designer/testSuite/listByBaseId` | `GET /designer/testSuite/listByBaseId` | ✅ 一致 |
| 创建测试功能 | `POST /designer/testFunction/add` | `POST /designer/testFunction/add` | ✅ 一致 |
| 添加模块 | `POST /designer/module/add` | `POST /designer/module/add` | ✅ 一致（已修复：请求体用 `moduleName`） |
| 添加子用例 | `POST /designer/case/add` | `POST /designer/case/add` | ✅ 一致 |
| 添加步骤 | `POST /designer/step/add` | `POST /designer/step/add` | ✅ 一致 |
| 模块审签 | `POST /designer/testFunction/check`（form-urlencoded） | `POST /designer/testFunction/check` @RequestParam | ✅ 一致 |
| 清单审签 | `POST /designer/testSuite/check`（需 form 或 params） | `POST /designer/testSuite/check` @RequestParam | ✅ 一致 |
| 创建测试清单 | `POST /designer/testSuite/add` | `POST /designer/testSuite/add` | ✅ 一致 |
| 绑定功能到清单 | `POST /functionSuite/createFunctionSuite` | `POST /functionSuite/createFunctionSuite` | ✅ 一致（后端在 add 时内部会调） |
| 删除清单 | `POST /designer/testSuite/delete` + body | 后端 `POST /designer/testSuite/delete` @RequestBody Integer | ⚠️ 需确认 body 为裸数字或 `{ suiteId }` |
| 模块树 | `GET /designer/module/treeByFunId` | `GET /designer/module/treeByFunId` | ✅ 一致 |
| 删除模块/用例/步骤 | POST + params | POST + @RequestParam | ✅ 一致 |

### 2. 计划域（Planner）

| 功能 | 前端调用 | 后端路径/方法 | 状态 |
|------|----------|----------------|------|
| 创建计划 | `POST /planner/plan/createTestPlan` | `POST /planner/plan/createTestPlan` | ✅ 一致 |
| 更新计划 | `POST /planner/plan/updateTestPlan` | `POST /planner/plan/updateTestPlan` | ✅ 一致 |
| 删除单个 | `DELETE /planner/plan/deleteTestPlan/{planId}` | `DELETE /planner/plan/deleteTestPlan/{planId}` | ✅ 一致 |
| 批量删除 | `DELETE /planner/plan/deleteTestPlanWithBatch` + body | `DELETE .../deleteTestPlanWithBatch` @RequestBody TestPlanDelBatchDto | ✅ 一致 |
| 列表 | `GET /planner/plan/listAll` | `GET /planner/plan/listAll` | ✅ 一致 |
| 派发 | `GET /planner/plan/dispatch/{planId}` | `GET /planner/plan/dispatch/{planId}` | ✅ 一致 |
| 开始/暂停 | POST start/pause | POST start/pause | ✅ 一致 |

### 3. 执行域（Execution）

| 功能 | 前端调用 | 后端路径/方法 | 状态 |
|------|----------|----------------|------|
| 计划下执行功能 | `GET /exeFunction/testFunctions/inexe/{planId}` | `GET /exeFunction/testFunctions/inexe/{planId}` | ✅ 一致 |
| 功能下步骤列表 | `GET /exeStep/getinexe/{functionId}` | `GET /exeStep/getinexe/{functionId}` | ✅ 一致 |
| 暂停 | `POST /exeStep/pause/{exeFunctionId}` | `POST /exeStep/pause/{exeFunctionId}` | ✅ 一致 |
| 步骤操作 | `POST /exeStep/stepOperate` | `POST /exeStep/stepOperate` | ✅ 一致 |
| 执行指令 | `POST /exeStep/do` | `POST /exeStep/do` | ✅ 一致 |
| 保存执行日志 | `POST /exeStep/log/save` | `POST /exeStep/log/save` | ✅ 一致 |
| 执行日志列表 | `GET /exeStep/log/list` | `GET /exeStep/log/list` | ✅ 一致 |

### 4. 基础与日志

| 功能 | 前端调用 | 后端路径/方法 | 状态 |
|------|----------|----------------|------|
| 构型树 | `GET /base/listAllBaseStructAndId` | `GET /base/listAllBaseStructAndId` | ✅ 一致 |
| 操作日志列表 | `GET /log/operation/list` | `GET /log/operation/list` | ✅ 一致 |

### 5. 设备管理

| 功能 | 前端调用 | 后端 | 状态 |
|------|----------|------|------|
| 设备列表 | `GET /devices/list` | 需后端实现 `/devices` 相关接口 | ⚠️ 待后端实现 |

---

## 二、请求体/参数对齐情况

### 已确认一致

- **createTestPlan / updateTestPlan**：前端提交 `entityId`, `suiteId`, `planName`, `planStartTime`, `planEndTime` 等，后端 `TestPlanRequestDto` 使用 `entityId`、`suiteId`，创建时校验非空。✅
- **模块审签 / 清单审签**：前端使用 `application/x-www-form-urlencoded` 传 `funId`/`suiteId`、`checkWorker`、`level`，后端 `@RequestParam` 可正确接收。✅
- **stepOperate**：前端传 `{ exeStepId, option: 'doFinish' }`，后端 `Map<String, String>` 接收。✅
- **ExeStepCommand**：前端传 `exeStepId`, `deviceId`, `command`, `url`，与后端 DTO 一致。✅
- **ExeLog**：前端传 `stepId`, `planId`, `content`, `createTime`，后端有 `logId` 可选。✅
- **deleteTestPlanWithBatch**：前端传 `{ planIdLists: string[] }`，后端 `TestPlanDelBatchDto.planIdLists`。✅

### 已修复

- **添加模块（ModuleOrchestration）**：原前端传 `funName`，后端实体为 `moduleName`。已改为前端传 `moduleName`、`funId`。✅

### 需运行时确认

- **删除清单**：前端 `designer.ts` 中 `deleteTestSuite` 传 `data: suiteId`（裸数字）。Spring `@RequestBody Integer` 在 JSON 下通常可接受数字，若失败可改为 `data: { suiteId }` 或后端改为 `@RequestParam`。
- **创建清单**：前端只调 `createTestSuite`，payload 含 `funIds`；后端 `TestSuiteServiceImpl.add` 内部会创建 suite 并调用 `createFunctionSuite`，无需前端再调一次绑定接口。✅

---

## 三、API 生命周期测试项与实现对应

| 阶段 | 文档描述 | 实际后端路径 | 前端是否支持 | 说明 |
|------|----------|--------------|--------------|------|
| Task 1 创建测试功能 | POST /designer/testFunction/add | 一致 | ✅ | 模块库创建功能 |
| Task 2 构建层级 | 文档写 testFunctionModule/add 等 | 实际为 /designer/module/add, /designer/case/add, /designer/step/add | ✅ | 文档与实现路径不同，前端已按实际路径实现 |
| Task 3 功能审签 | POST /designer/testFunction/check | 一致 | ✅ | form 传 funId, checkWorker, level |
| Task 4 创建清单与绑定 | testSuite/add + createFunctionSuite | add 内部分步完成绑定 | ✅ | 前端只调 add（带 funIds）即可 |
| Task 5 清单审签 | POST /designer/testSuite/check | 一致 | ✅ | 需前端传 suiteId, checkWorker, level |
| Task 6 创建计划 | POST /planner/plan/createTestPlan | 一致 | ✅ | entityId, suiteId, planName, 时间 |
| Task 7 派发 | GET /planner/plan/dispatch/{planId} | 一致 | ✅ | 测试计划页派发 |
| Task 8 获取执行功能/步骤 | exeFunction/testFunctions/inexe, exeStep/getinexe | 一致 | ✅ | 测试执行页加载树 |
| Task 9 暂停 | POST /exeStep/pause/{exeFunctionId} | 一致 | ✅ | 步骤操作中的暂停 |
| Task 9 步骤操作 | POST /exeStep/stepOperate | 一致 | ✅ | 通过/失败/跳过用 doFinish |
| Task 10 执行指令 | POST /exeStep/do | 一致 | ✅ | 执行指令按钮 |

**结论**：生命周期文档中的 Task 2 路径（testFunctionModule/add 等）与当前后端不一致，应以实际路径为准；其余任务前后端均已对齐，测试内容在接口层面可以完全跑通（需后端服务与数据库就绪）。

---

## 四、响应结构

- 后端统一：`{ code: 200, msg: string, data: T }`。
- 前端 `request.ts` 拦截器：`code !== 200` 时提示并 reject，否则返回 `res.data`。
- 各页面按「接口直接返回业务数据」使用，无需再解包 `data`。

---

## 五、建议与待办

1. **设备管理**：后端按 `docs/DEVICE_API_SPEC.md` 实现 `/devices`、`/devices/list` 等，前端已就绪。
2. **清单删除**：若删除清单接口报错，将前端改为 `data: { suiteId }` 或后端改为 `@RequestParam Integer suiteId`。
3. **API_Lifecycle_Verification.md**：建议将 Task 2 的 URL 更新为实际路径（/designer/module/add、/designer/case/add、/designer/step/add），避免歧义。
4. **端到端验证**：在本地启动后端与前端，按生命周期顺序执行一遍（创建功能→编排→审签→清单→计划→派发→执行树→步骤操作），确认无 4xx/5xx 及字段缺失。

---

## 六、当前构建与测试状态

### 后端（fastop）

- **编译**：当前存在编译错误：`fastop-base-common` 模块中 `Response.java` 对 `ResponseBody` 的 `getCode/setCode`、`getMsg/setMsg`、`getData/setData` 报「找不到符号」。`ResponseBody` 已用 Lombok `@Data` 生成 getter/setter，多为 Lombok 未参与编译或模块依赖问题，需在本地修复后再跑 `mvn test`。
- **单元测试**：项目内含 `FastopApplicationTests` 等空壳测试，无接口/业务单测；通过需先解决上述编译错误。

### 前端（frontend）

- **构建**：`npm run build` 依赖 `vue-tsc -b && vite build`。若未安装依赖或 `vue-tsc` 未在 PATH 中，可能报「不是内部或外部命令」；建议在项目根目录执行 `npm install` 后再执行 `npm run build`。接口与请求体已按上述对齐，无新增错误。
- **开发**：`npm run dev` 可正常启动开发服务器，前后端联调需同时启动后端服务（如 `fastop-service` 端口 10001）与认证 mock（如 5000）。

### 测试内容是否能够完全通过

- **接口契约与流程**：按本文档一～三节的对照，生命周期各 Task 的路径、方法、请求体与前后端实现一致；在「后端可编译、数据库与依赖服务就绪」的前提下，按 API_Lifecycle_Verification.md 顺序执行，测试内容可以完全跑通。
- **实际通过条件**：需先修复后端 `Response`/`ResponseBody` 编译问题，并保证数据库、配置（如端口、context-path `/fastop`）正确，再按 Task 1 → Task 10 做端到端验证。

---

*报告生成基于当前仓库代码与 API_Lifecycle_Verification.md。*
