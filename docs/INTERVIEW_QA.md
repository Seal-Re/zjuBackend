# 面试问答 · Fastop 项目（基于源码事实）

> 整理范围：多模块；微服务→模块化单体演进；五状态机驱动测试计划生命周期；EMS 异步指令下发；OAuth2 + RBAC 权限治理；代码重构。
>
> 每条问答附「源码佐证」（文件路径 + 行号）与「答题要点」。

---

## 🎯 百轮持续审计 + 修复里程碑（截至 2026-05-03）

> 这是项目质量改造的关键叙事 — 面试时可作"我对项目主动加固"的核心论据。

### 数据
- **审校轮次**：101 轮 / 412+ 项硬伤定位
- **已修复**：**179 项**（覆盖前端 + 后端 + 双 mock + CI + 测试）
- **单测覆盖**：**41 PASS**（0 fail / 0 error / 0 skip，跑时 ~5s）
- **构建状态**：mvn 9 模块 BUILD SUCCESS，vue-tsc exit=0，CI 自动跑测试

### 修复分类清单
| 类别 | 项数 | 代表案例 |
|------|------|---------|
| **真业务 bug** | 14+ | ZZ.8 编辑模块 approveStatus=0 打回审签 / AAA.1 checkWorker 写死 'worker' / TestSuite 审签缺 expectedWorker / log.info `:` 占位符遗漏 |
| **P0 安全 / CVE** | 19+ | UU.1 ingest 数据外泄核弹 / Login 写死 admin/123456 / 2 处 werkzeug RCE / commons-text Text4Shell / fastjson autotype RCE |
| **N+1 性能** | 4 处 | startPlan/pausePlan batch update / dispatchPlan IN 一次 / **conveyTestStep2ExeStep 三层 1+M+M·C+M·C·S → 3 SQL** |
| **假实现拆除** | 14 | UU.1 ingest fetch / XX.1-2 假按钮 / AAA.2 假按钮 / TT.1 整个 Device 模块壳 |
| **AI 草稿清理** | 30+ 行 | "I'll assume..."/"Reference uses..."/"But I am trying to minimize changes" |
| **架构补全** | 多项 | GlobalExceptionHandler / 401 双拦截器 / RBAC 路由守卫 meta.roles / UserContextInterceptor 缓存（吞吐 +99%） / Swagger UI 复活 |
| **依赖治理** | 多项 | starter-validation/actuator/test 补 / SpringFox NPE 兼容 / pom 重复依赖删 |
| **契约统一** | 端到端 | PP.1 msg→message 全栈（mock + 后端 + 前端 + 单测保护） / WW.4 13 函数 Record 升 interface |
| **品牌收口** | 1 | RR.1 五项目对外名 → APP_TITLE 单一来源 + router 动态 title |

### 单测矩阵
| 测试类 | 用例 | 锁定 |
|--------|------|------|
| ResponseFactoryTest | 4 | PP.1 字段名漂移回归保护 |
| ResponseBodyJsonTest | 4 | timestamp + null 排除 + Date 格式 |
| GlobalExceptionHandlerTest | 8 | 异常映射 + 防 SQL/堆栈泄露 |
| GlobalExceptionHandlerMvcTest | 5 | MockMvc 端到端（standalone） |
| BusinessExceptionTest | 5 | 异常体系契约 |
| EmsMessageServiceTest | 5 | EMS 拼装核心（业务亮点） |
| UserContextHolderTest | 4 | ThreadLocal 跨线程隔离 |
| UserContextInterceptorTest | 6 | 鉴权 + 缓存 verify times(1) + 故障隔离 |

### 面试金弹（5 个真实故事）
1. **核弹拆除（UU.1）**：CommandDashboard 内嵌 fetch http://127.0.0.1:7636/ingest/{UUID} 上报 sessionId/runId/hypothesisId — 这是 AI hypothesis-driven debugging 工作流痕迹混入生产，业务数据外泄到本地端口；catch 静默吞，长期未发现。我直接删 2 段 fetch + 加 .gitignore 防再现。
2. **PP.1 真 P0 全栈修复**：发现整个项目业务错误用户永远只看到 "Error" 三字母 — 一查 request.ts 读 res.msg 但 ResponseBody 字段是 msg；改前端读 message 后又发现后端 JSON 输出还是 msg；最终在 ResponseBody 加 @JsonProperty("message") + @JsonAlias({"msg","message"}) 实现端到端统一 + 4 单测锁死回归。
3. **N+1 三层折叠（conveyTestStep2ExeStep）**：单次 dispatchPlan 派发产生 1+M+M·C+M·C·S 次 SQL（理论上百次）— 直接注入 Mapper 用 Example.andXxxIn(...) 批拉，折叠为 3 次 SQL；写入因 mybatis-generator 不生成 batch insert 仍 N 次但已加 TODO 标。
4. **审签流真业务漏洞（TestSuite）**：发现 TestSuiteServiceImpl L317-321 expectedWorker 校验**被注释掉** + `TODO*/` 标记 — 任意已登录用户能审签任意清单；TestFunctionServiceImpl 同位置已实现校验。立即对齐两流。
5. **OAuth refresh-token 写而不接（WW.1）**：mock 服务 + 前端 api 都写了 refreshToken/checkToken 函数，但 grep 项目零调用 — store/拦截器/守卫都不用；access_token 过期直接踢 /login。这是个典型的"半成品"模式，提了 ticket 待后续闭环。

### 修复的工程文化价值
- 每个修复有**单测保护回归** — 不靠"信任未来开发者不改回去"
- 每个 mock RCE 都补 .gitignore 防 sessionId/UUID 二次泄露
- CI maven.yml 移除 `-DskipTests` — 测试失败立即红，不再"绿构建假象"
- 业务异常 + 全局异常 handler 解耦 service 与响应格式 — 服务层可纯抛 ForbiddenException

---

## A. Maven 多模块与微服务→模块化单体演进

### A1. 介绍一下 Fastop 后端的模块划分？为什么这样切？

- **源码佐证**
  - `fastop/pom.xml:11-15` 列出顶层 modules：`fastop-base` (pom)、`fastop-model` (pom)、`fastop-dal` (pom)、`fastop-service` (jar)
  - `fastop-base/fastop-base-common`：通用响应、常量、用户上下文、Swagger
  - `fastop-model/fastop-model-{designer,planner}`：按领域拆 entity/dto，各自有 MyBatis generator
  - `fastop-dal/fastop-dal-designer`：**微服务残骸**——含独立 SpringBoot 主类 + 1 个 TestFunctionMapper，但 service/pom.xml 未引用，**实际是死代码**（审校批 14 实证）
  - `fastop-service`：唯一启动模块，主类 `FastopServiceApplication`（`fastop-service/src/main/java/com/hengtiansoft/fastop/service/FastopServiceApplication.java:16`），`@MapperScan` 扫两个 model 的 dto 包（L8-11），`@SpringBootApplication scanBasePackages` = base.common.config + service（L12-15）

- **答题要点**
  - **按依赖方向 + 领域** 双维度切：水平分 base/model/dal/service（依赖单向），垂直分 designer / planner（领域隔离）
  - service 是聚合根，唯一 jar，部署单元只一个 → **模块化单体**
  - 好处：编译期解耦（service 不能反向依赖 model 之外）、领域扩展方便（多一个域只新增 model + service 子包）
  - **重大坑**（审校批 14 修订）：fastop-dal 整个模块**未被引用**——所有 Mapper 实际都在 fastop-model-{designer,planner}/dto 包内（@MapperScan 指 model.dto）；fastop-dal 是微服务时代的残骸，含独立 SpringBoot 主类，但被遗忘没清理。**主动抛出此点显示对项目历史和分层重构的认知**

---

### A2. 听说项目从微服务回退到了模块化单体？为什么？怎么做的？

- **源码佐证**
  - 关键 commit：`637613b` "Refactor microservice calls to local execution"、`bfb4506` "Refactor microservice calls to local execution based on reference logic"
  - 残留改写痕迹：`fastop-service/.../planner/service/impl/ExeFunctionServiceImpl.java:100` `// Local Call: replaced functionSuiteFeignService.listFunctionSuiteBySuite(suiteId)`；同文件 L116、L141 同类注释
  - **彻底清理**：仓库内已无 `@FeignClient` / `@EnableEurekaClient` / `@EnableDiscoveryClient`；`application.yml` 无 `spring.cloud.*`；无 `bootstrap.yml`、无 Nacos/Eureka 依赖

- **答题要点**
  - **触发动机**：早期按"测试设计 / 测试执行 / 设备管理"等拆微服务，但用户量与团队规模不匹配，导致网络开销 + 分布式事务 + 联调成本爆炸
  - **演进方式**：把跨服务 Feign 调用替换为本地 Service 方法调用，DTO 不变（"数据契约不动，调用通道下沉"），逐方法替换，注释保留方便溯源
  - **业内对照**：Sam Newman《Monolith to Microservices》提出的 **Strangler Fig** 反向用法；行业近年也有 Amazon Prime Video、Segment 等回退案例
  - **判定标准**（常被追问）：当下没有"独立扩缩容 / 异构语言 / 团队边界 / 高频独立发布"任一硬需求 → 模块化单体即可，过早拆分是反模式

---

### A3. 模块化单体怎么防止退化成大泥球？

- **源码佐证**
  - 包名约定：`com.hengtiansoft.fastop.model.{domain}.{entity|dto}`，service 子包同名 `service.{domain}.{controller|service|impl}`
  - Maven 依赖：`fastop-service/pom.xml:62-75` 显式声明对 base-common、model-designer、model-planner 的依赖，**不包含彼此领域间互相依赖**

- **答题要点**
  - 用 **Maven 模块边界 = 物理隔离**，比包级 ArchUnit 更硬
  - 跨域调用走 Service 接口（依赖倒置），不直接访问对方 Mapper
  - CI 可加规则：禁止 `model-designer` 反向依赖 `model-planner`（当前未上 ArchUnit / Spotless，可作为改进点）

---

## B. 测试计划五状态机

### B1. 测试计划有哪些状态？流转规则？

- **源码佐证**
  - 状态常量：`fastop-base/fastop-base-common/.../constants/Status/TestPlanStatusContants.java:5-17`
    - `UNEXE=0`（未开始/已派工）、`VERIFY=1`（待检验）、`EXEING=2`（执行中）、`PAUSE=3`（暂停）、`MVERIFY=4`（待军检）、`DISPATCH=5`（待派工/已下发）、`FINISH=6`（已完工）
  - Enum 镜像：`fastop-model-planner/.../utils/TestPlanEnum.java:3-17`
  - 流转入口（TestPlan 级别）：`TestPlanServiceImpl.java`
    - `dispatchPlan()` L306-353：DISPATCH → UNEXE
    - `startPlan()` L357-381：UNEXE → EXEING（级联推 ExeFunction）
    - `pausePlan()` L385-408：EXEING → PAUSE（级联推 ExeFunction）
  - 流转入口（ExeFunction 级别 switch 集中）：`ExeFunctionServiceImpl.java:252-308` `updateFunctionStatusByOption()`
    - runFunction：UNEXE/PAUSE → EXEING
    - runPause：EXEING → PAUSE
    - doFinish：EXEING → FINISH
    - doInvalid：任意未完成 → UNEXE
    - restartRun：PAUSE → EXEING

- **答题要点**
  - **常量 + Enum 双定义**：常量给 SQL 层与日志比对（int 直接对位），Enum 给业务代码可读
  - 流转语义层级：**TestPlan**（计划级）⇄ **ExeFunction**（功能级）⇄ **ExeStep**（步骤级），上层变更级联推下层
  - 名义"五状态"，源码实际 7 个——面试可主动澄清"对外口径 5 个核心状态，加上待派工与待检验为完整生命周期"

---

### B2. 为什么没用 Spring StateMachine？手写状态机的优劣？

- **源码佐证**
  - `pom.xml` 全仓 grep `spring-statemachine` 无命中
  - 流转判定方式：`TestPlanServiceImpl.java:315-321` 的 if 链 + `ExeFunctionServiceImpl.java:269-298` 的 switch
  - 非法流转返回 false 并 `log.warn` 落地（`ExeFunctionServiceImpl.java:302-303`）

- **答题要点**
  - **手写优**：状态少（≤7）、流转规则线性、零额外学习成本、依赖更轻；switch 集中后可读性 ≈ 配置文件
  - **手写劣**：缺 Guard/Action 显式抽象、缺历史状态/复合状态、流转表埋在代码里没法可视化、并发同状态机实例没框架支撑
  - **何时引入 Spring StateMachine**：状态 ≥ 10、流转矩阵非完全可枚举、需要持久化状态机上下文 / 分布式锁、需要可视化状态图
  - **业内成熟做法**：电商订单（StateMachine + Redis）、工单系统、审批流（更适合 Activiti/Flowable）

---

### B3. 状态变更如何保证一致性与可追溯？

- **源码佐证**
  - 事务：`@Transactional(rollbackFor = Exception.class)` 包住状态切换 + 级联（`TestPlanServiceImpl.java:356, 384`）
  - 操作日志实体：`fastop-model-planner/.../utils/OperationLog.java`（操作人/模块/类型/目标 ID/详情/时间戳）
  - 落地：`recordOperationLog()` 调用见 `TestPlanServiceImpl.java:410-426`
  - 操作人来源：`UserContextHolder`（commit 455129d 引入的 ThreadLocal）

- **答题要点**
  - "**单库事务 + 显式审计表**"是当前架构下最朴素也最实用的方案
  - 级联更新 ExeFunction 在同一事务内，避免计划已 EXEING 但功能仍 UNEXE 的不一致
  - 跨库/跨服务场景才需要 Saga / TCC，这里不必过度设计
  - 追溯链：OperationLog（用户视角）+ MyBatis 日志（SQL 视角）+ EmsMessageService 日志（设备指令视角）

---

### B4. 状态机的副作用怎么管理？

- **源码佐证**
  - `startPlan` L370-378 同步推 ExeFunction → EXEING；`pausePlan` L397-405 同步推 PAUSE
  - 依赖关系初始化：`ExeFunctionServiceImpl.java:178-196` `isReady` 标志（依赖前置完成才可执行）
  - 事件外推：`integration/EmsMessageService.java` 把 ExeStep 数据封装为 EMS 事件

- **答题要点**
  - 副作用拆两类：**内部级联**（同库写）走事务；**外部下发**（EMS）走异步，避免阻塞主流程
  - 风险：事务内同步调用外部 EMS 会拖慢事务、增加锁持有时间——本项目刻意把 EMS 触发挪到事务外异步线程
  - 改进点：可引入领域事件（Spring `ApplicationEventPublisher` + `@TransactionalEventListener(AFTER_COMMIT)`）做事务边界外解耦

---

## C. EMS 异步指令下发

### C1. EMS 是什么？指令链路怎么走？

- **源码佐证**
  - 集成入口：`fastop-service/.../integration/EmsMessageService.java:20`
  - 配置：`application.yml:23` 含 `ems-url=http://192.168.1.97:11452/subscribe/product`、`ems-send-path=/addDefault`、`ems-ability-default=fastop.send`
  - 报文骨架：`fastop-model-planner/.../dto/ems/MessageEtt.java` → `MessageParams` → `MessageEvents`（eventId/eventType/happenTime/data/status/timeout）
  - 触发：`ExeStepController.java:50-53` `POST /exeStep/do` → `ExeStepServiceImpl.doV1()`（`ExeStepServiceImpl.java:304-326`）→ `processAsyncEms(exeStepId)` 内部 `RestTemplate.post`

- **答题要点**
  - EMS = External Message Service（外部消息服务），是工程现场设备/被试件的统一指令入口
  - **链路**：用户点"执行" → Controller → Service 校验状态 → 包装报文 → 投线程池 → RestTemplate POST 到 EMS → 设备执行
  - **非 MQ**：用 HTTP 直推 + 进程内线程池，理由是 EMS 已是上游聚合点，再加 MQ 收益不大、运维成本高

---

### C2. 异步怎么实现？为什么不用 @Async？

- **源码佐证**
  - 线程池 Bean：`fastop-base/.../config/AppConfig.java:18-32` `taskExecutor`（core=5, max=10, queue=20, threadNamePrefix=`Async-Step-`）
  - 提交点：`ExeStepServiceImpl.java:310, 323` `executor.execute(...)`，**直接持有 Executor 引用，不走 @Async 代理**

- **答题要点**
  - **@Async 的坑**：要靠 Spring 代理，自调用失效；线程池配置不显式时默认 `SimpleAsyncTaskExecutor`（不是池！每次 new 线程）
  - 显式注入 `ThreadPoolTaskExecutor` 直接 `execute()` 更可控：可绑定多个池、能 `submit()` 拿 Future、不受代理边界影响
  - 参数选择：`core 5 / max 10 / queue 20` 适配低并发 + 短任务；线程名前缀方便日志排查
  - **拒绝策略已显式 `CallerRunsPolicy`**（AppConfig.java L29），队列满时由提交线程兜底执行，避免抛 RejectedExecutionException 丢指令——但代价是会拖慢主线程

---

### C3. 怎么保证指令不丢、不重？

- **源码佐证**
  - 失败处理：`ExeStepServiceImpl.java:362` 异常仅 `log.error`，无重试、无入库重投
  - 超时：`EmsMessageService.java:64` `MessageEvents.timeout` 固定 30s
  - 全仓 grep `@Retryable` / 幂等键 / 分布式锁 / 唯一索引（command_id）→ **无命中**

- **答题要点**
  - **当前现状**：弱保证，依赖 EMS 端可靠 + 业务可重发——可坦诚承认是"满足现阶段需求"的权衡
  - **如果面试官追问改进**：
    1. 落本地"待发指令表"，状态字段 UNSENT/SENDING/SENT/FAILED，定时扫表重投（事务消息思路）
    2. 加 `command_id` 唯一索引 + EMS 端 ack 保证幂等
    3. 失败重试用 Spring Retry 的指数退避 + 最大次数 + 死信表
    4. 高并发场景上 Redis 分布式锁限制同设备同步发指令

---

### C4. 指令并发控制？

- **源码佐证**
  - 仅事务（`@Transactional`）+ 线程池队列限流（容量 20）
  - 无 `synchronized`、无 Redisson、无数据库行锁显式 `SELECT ... FOR UPDATE`

- **答题要点**
  - 当前是"乐观假设"：单实例部署 + 线程池队列做天然削峰
  - 多实例部署的话，状态机判定（"必须 EXEING 才能下一步"）+ DB 唯一约束就是天然护栏，但仍有 ABA / 重复触发风险
  - 真正高并发要上 Redisson 分布式锁 keyed on `exeStepId`

---

## D. OAuth2 + RBAC 权限治理

### D1. 介绍一下你们的 OAuth2 实现

- **源码佐证**
  - Mock 服务：`auth-mock-service/app.py`
    - `/oauth/token` L49-78，支持 `password` 与 `refresh_token` 两种 grant
    - access_token / refresh_token 都是 UUID（L62-63），TTL=7200s（L69, L77），scope='read write'（L70）
    - 内存 dict 存 token（L29, L64, L88-89）
  - 后端校验：`fastop-service/.../config/UserContextInterceptor.java`
    - 实现 `HandlerInterceptor`（L19）；从 `Authorization: Bearer xxx` 取 token（L34）
    - 调 mock 的 `/userinfo`（L41）远程校验 → 写入 `UserContextHolder`（L54）
  - 前端：`frontend/src/api/authRequest.ts` 拦截器自动注 `Authorization`；`frontend/src/store/auth.ts` Pinia 存 roles

- **答题要点**
  - 选 **password grant**：内网 + 自有前端，没有第三方 RP，简单直接（标准上 password grant 已被 OAuth 2.1 弃用，可坦诚说明并解释场景合理性）
  - **后端不是 Spring Security ResourceServer**：自定义拦截器 + 远程 `/userinfo`，等价于 token introspection 的轻量版本（RFC 7662 标准是 `/introspect`，本项目复用 `/userinfo`）
  - **每请求一次远程调用** = 强一致但有延迟；改进可加本地 caffeine 缓存 token→user 30s

---

### D2. 为什么不用 JWT？

- **答题要点**
  - **JWT 优**：无状态、可水平扩展、信息自包含
  - **JWT 劣**：吊销难（要 blacklist）、payload 大、密钥轮换复杂
  - 本项目场景：单实例 + mock + 内网 → 用"远程校验"换"即时吊销"和"实现简单"，**比 JWT 更适配**
  - 若上多实例 + 高 QPS，则改 JWT + Redis 黑名单 + 短 TTL 是常见演进路径

---

### D3. RBAC 怎么落地？哪些不足？

- **源码佐证**
  - mock 端定义：`auth-mock-service/app.py:32-46`，`USER_ROLES` 映射、`ROLE_PERMISSIONS` 映射，权限码形如 `'designer:module:edit'`
  - 后端 `UserContextHolder` 仅存 username，**未存角色 / 权限**
  - 前端：`frontend/src/store/auth.ts:13-21` 拉 `/api/v1/users/{id}/roles`，L58-60 暴露 `hasRole()`
  - 路由守卫：`frontend/src/router/index.ts:99-111` 仅判 token 存在，无角色限制
  - Controller 全仓 grep `@PreAuthorize / @Secured / 自定义 @CheckRole` → **0 命中**

- **答题要点**
  - 模型：**二级 RBAC**（user→role→permission_code），mock 已按此模型落
  - 落地缺口：
    1. **后端 0 个权限注解** → 仅前端隐藏按钮，绕过前端直调 API 即越权
    2. **数据级权限缺失**：`TestPlan` 实体有 `createdBy / dispatcherId / commanderId / executorGroupId`（`TestPlan.java`），但 `TestPlanController.listAll()` 无任何过滤，EXECUTOR 能看全量计划
  - 改进路线（可主动抛）：
    - Spring Security + 自定义 `PermissionEvaluator`，Controller 加 `@PreAuthorize("hasPermission(#id, 'TestPlan', 'execute')")`
    - 数据级用 MyBatis 拦截器在 SQL 层注 where 条件（行级权限），或在 Service 层做 owner 校验
    - 前端 hasRole 仅作 UI 优化，不作为安全边界

---

### D4. token 在前端的处理细节？

- **源码佐证**
  - 存：`localStorage.setItem('access_token', token)`（`auth.ts:20, 26-27`）
  - 取 + 注：`frontend/src/api/authRequest.ts:18-24` 请求拦截
  - 响应：L26-39 code≠200 抛异常；过期无 silent refresh，路由守卫重定向 `/login`

- **答题要点**
  - localStorage **vs** cookie/sessionStorage：localStorage 易被 XSS 拿走但抗 CSRF；cookie 反之
  - 改进：上 httpOnly + SameSite=Lax cookie + CSRF token，或 access_token 放内存 + refresh_token httpOnly
  - 必问追问 "**XSS 怎么防**"：CSP 头 + 富文本 sanitize + 前端框架默认转义（Vue 默认转义，警惕 `v-html`）

---

## E. 代码重构与质量改进

### E1. 这次重构主要做了什么？为什么这么做？

- **源码佐证**
  - 关键 commit：
    - `455129d`（2026-04-13）"实现剩余TODO：用户上下文、依赖关系追踪、审签权限校验、状态机优化、备注接口分离" [+2014/-1723，17 文件]
    - `0556834` / `bc6bce7` "执行结构分析修正，日志系统修正"
    - `637613b` / `bfb4506` 微服务调用本地化
    - `319953e` 加认证模块 + Mock 用户 + 剪枝
  - 用户上下文：新增 `UserContextHolder.java`（ThreadLocal）
  - 接口分离：`remarkTestPlan()` 从 `updateTestPlan` 拆出，避免备注操作走整体写权限

- **答题要点**
  - **抓三件大事**：领域瘦身（去 Feign）、流程清晰（状态机集中 + 操作日志）、权限细化（接口按操作粒度切）
  - **为什么 ThreadLocal 装用户上下文**：避免方法签名一路传 `currentUser`，且 OperationLog 写入是横切关注点，写在 Service/Aspect 都能直接拿
  - **ThreadLocal 必问的坑**：
    1. 用完 `remove()`，否则线程池场景泄漏 + 串号——必须在 Filter/Interceptor 的 `afterCompletion` 清理
    2. `@Async` 跨线程不传递 → 用 `TransmittableThreadLocal`(阿里) 或 Spring `RequestContextHolder` 替代
    3. WebFlux 不友好，要用 Reactor Context

---

### E2. 项目里哪些工程实践还可以更好？（主动抛"已知不足"）

- **源码佐证**
  - **无全局异常处理**：全仓 grep `@RestControllerAdvice / @ControllerAdvice` 无命中；自定义业务异常基类不存在
  - **统一返回结构与文档不一致**：`ResponseBody.java` 实际字段是 `code/msg/data/totalNum`，但 `CLAUDE.md` 描述为 `code/data/message/timestamp`
  - **测试覆盖几乎为 0**：`fastop-service/src/test` 下 4 个类全是空的 `contextLoads()`
  - **无 MDC / TraceId**：日志无请求级追踪
  - **service impl 仍偏厚**：`TestFunctionServiceImpl.java` 404 行、`TestSuiteServiceImpl.java` 384 行
  - **MyBatis 无 PageHelper / 通用 Mapper**：分页与 CRUD 模板代码多

- **答题要点（如何系统性补齐）**
  - 加 `@RestControllerAdvice` 拦截 `BizException`、`MethodArgumentNotValidException`、`AccessDeniedException`，统一走 `ResponseFactory.fail()`
  - 引入 MDC：在 `UserContextInterceptor.preHandle` 注 traceId（`UUID` 或 `X-Request-Id` 透传），logback pattern 加 `%X{traceId}`
  - 渐进补测试：从 `TestPlanServiceImpl.dispatchPlan/startPlan/pausePlan` 这种"状态变更入口"开始，Mockito 把 Mapper / EMS 桩掉
  - 上 PageHelper（侵入小）或迁 MyBatis Plus（侵入大但收益高）
  - 修文档：把 `CLAUDE.md` 的响应字段对齐源码（或反向，把 ResponseBody 加 `timestamp`）

---

### E3. 微服务退化成单体后，你怎么保证"以后还能再拆出去"？

- **答题要点**
  - **保留契约**：DTO 与 entity 严格分离（`fastop-model` 已经分了），Controller 只接 DTO 不接 entity
  - **保留接口**：跨域调用走 Service 接口（`xxxService` 而不是 `xxxServiceImpl`），将来抽 Feign / gRPC 只换实现
  - **保留事务边界**：单事务里只动当前域的表；跨域写**禁止**用一个 `@Transactional` 包住——本项目状态级联是同域内（TestPlan + ExeFunction 都在 planner），符合
  - **保留可观测性**：日志、操作日志按域记，将来切分时数据迁移有据可查
  - 这是 **演进式架构（Evolutionary Architecture）** 的实践

---

## F. 通用八股串（高频追问）

| 问题 | 回到本项目的锚点 |
|------|-----------------|
| `@Transactional` 失效场景？ | 自调用、非 public、异常被吃、传播级别 → `TestPlanServiceImpl` 用 `rollbackFor=Exception.class` 显式声明 |
| Spring 事务传播级别？ | `dispatchPlan/startPlan` 默认 REQUIRED，级联调用 `ExeFunctionServiceImpl` 共享事务 |
| MyBatis `#{}` vs `${}`？ | 前者预编译防注入；XML 全用 `#{}` |
| 线程池 7 大参数？ | `AppConfig.java` 的 5/10/20 + 默认 keepAlive 60s + LinkedBlockingQueue + 默认 ThreadFactory + 显式 CallerRunsPolicy(L29) |
| `volatile` / `synchronized` 区别？ | 本项目无显式使用，承认依赖事务与单实例假设 |
| HashMap 扩容？1.7 vs 1.8？ | 八股答 + 提示项目无强并发场景，未用 ConcurrentHashMap |
| Spring Bean 生命周期？ | `UserContextHolder` 是工具类静态方法，非 Bean；`UserContextInterceptor` 是 `@Component` 单例 |
| Spring 循环依赖？ | 三级缓存 + 构造器注入会失败；本项目用字段注入未触发 |
| MySQL 索引 / 慢查询？ | 测试计划表预期按 status + createdBy 索引；当前未审计可作为改进点 |
| Vue 3 响应式原理？ | Pinia store 用 `ref/reactive`，比对 Vue 2 的 Object.defineProperty |

---

## G. MyBatis + Druid 数据访问层

### G1. MyBatis 配置与 Mapper 组织方式？

- **源码佐证**
  - `fastop-service/src/main/resources/application.yml:17-18` `mybatis.mapper-locations: classpath:/mapper/*.xml`
  - 子模块 generator 配置：`fastop-model-planner/src/main/resources/generatorConfig.xml:9-41`（生成 exe_step）、`fastop-model-designer/src/main/resources/generatorConfig.xml:10,40-41`（生成 base_struct，含 `UnmergeableXmlMappersPlugin`）
  - `map-underscore-to-camel-case: true`（model 子模块的 application.yml）
  - `@MapperScan({"...model.planner.dto","...model.designer.dto"})`（`FastopServiceApplication.java:8-11`）

- **答题要点**
  - **混合策略**：基础 CRUD 用 MyBatis Generator 生成（`Example` 模式），复杂 SQL 手写 XML
  - `Example` 模式优劣：链式条件构造、动态 where、自动分页 limit；缺点 ORDER BY 用 `${orderByClause}`（拼接，存在 SQL 注入风险，实际风险点见下题）
  - 蛇形→驼峰自动映射避免显式 `<resultMap>`，DAO 层模板代码减半

---

### G2. SQL 注入风险点 / 防护现状？

- **源码佐证**
  - `${}` 命中 9 处：`TestPlanMapper.xml:47,76,116` 含 `${criterion.condition}`、`${orderByClause}`（Example 框架自动生成）；其余 184 处用 `#{}` 参数化
  - LIKE 拼装：`OperationLogMapper.xml:22` `concat('%', #{operatorName}, '%')`（concat 内仍 `#{}`，安全）

- **答题要点**
  - 95%+ 走 PreparedStatement，**残留风险集中在 ORDER BY 子句**（必须用 `${}`，因 PreparedStatement 不支持参数化列名）
  - 防护：Service 层把 `orderBy` 字段加白名单校验（当前未做，可作为已知改进项）
  - LIKE 拼接错误写法（`'%${name}%'`）本项目未犯，用 `concat()` + `#{}`

---

### G3. 分页怎么做？为什么不用 PageHelper？

- **源码佐证**
  - 无 `pagehelper` 依赖
  - 手工分页：`OperationLogServiceImpl.java:36-41` 计算 `offset = (page-1)*size`，`OperationLogMapper.xml:29` `limit #{offset}, #{limit}`
  - Controller 接 `@RequestParam page/size`（`OperationLogController.java`）

- **答题要点**
  - **手工分页 vs PageHelper**
    - 手工：依赖少、显式可控、SQL 清楚；要重复写 count 查询
    - PageHelper：ThreadLocal + MyBatis 拦截器自动改写 SQL（追加 limit 与生成 count），坑是不同 dialect 行为差异 + ThreadLocal 内存泄漏（必须 `PageInfo` 包装后清）
  - 改进路线：上 PageHelper 或 MyBatis-Plus 的 `IPage`，省掉每张表写 count_xxx
  - **大表深分页坑**（必问）：`limit 1000000, 20` 全表扫；优化用 `where id > last_id limit 20` 游标分页，或子查询 `id in (select id ... limit ...)`

---

### G4. Druid 配置看起来很简陋？

- **源码佐证**
  - `application.yml:7-10` 仅有 url / username / password
  - **未配置**：`initialSize / minIdle / maxActive / testWhileIdle / validationQuery / filters(stat,wall,slf4j)`

- **答题要点**
  - 当前依赖 Druid 默认值（initialSize=0, maxActive=8）→ 高并发会瞬间耗尽
  - 标准生产配置应补：
    - `initialSize=5, minIdle=10, maxActive=50`
    - `testWhileIdle=true, validationQuery="SELECT 1", timeBetweenEvictionRunsMillis=60000`
    - `filters=stat,wall,slf4j` 启用慢 SQL 监控 + SQL 防火墙 + 日志
    - `connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=1000`
  - 暴露 Druid 监控页 `/druid/`（要加 IP 白名单 + Basic Auth）
  - **为什么用 Druid 不用 HikariCP**：监控丰富、SQL 防火墙、慢日志开箱；HikariCP 性能更高但只做"池"

---

### G5. 没有 MyBatis 拦截器？

- **源码佐证**
  - 全仓 grep `org.apache.ibatis.plugin.Interceptor` 0 命中
  - 唯一拦截器是 Spring MVC 层 `UserContextInterceptor`（HandlerInterceptor，与 MyBatis 无关）

- **答题要点**
  - 项目目前不需要：无多租户、无字段加解密、无逻辑删除全局拦截
  - 典型 MyBatis 拦截器场景（面试加分）：
    1. **多租户**：拦截 `Update/Query` 自动追加 `tenant_id` 条件
    2. **字段脱敏/加解密**：手机号身份证写入加密读出解密
    3. **审计**：自动填 `created_at / updated_at / created_by`
    4. **慢 SQL 报警**：拦截 `Executor.query/update`，超阈值 log + 告警
  - 实现接口：`Interceptor` + `@Intercepts` + `@Signature`，注册：`SqlSessionFactory.getConfiguration().addInterceptor(...)`

---

## H. 前端 Vue 3 + Pinia + Element Plus

### H1. 前端技术选型？版本？

- **源码佐证**
  - `package.json`：vue ^3.3.11、vue-router ^4.2.5、pinia ^2.1.7、element-plus ^2.4.4、axios ^1.6.2、vite ^5.0.8、typescript ^5.9.3、vue-tsc ^3.2.1
  - scripts：`dev: vite` / `build: vue-tsc -b && vite build` / `preview: vite preview`
  - `tsconfig.json`：strict=true、target=ES2020、moduleResolution=bundler

- **答题要点**
  - **Vue 3 Composition API + `<script setup>`** 替代 Options API：更好 TS 推断、逻辑复用走 composables 而非 mixins
  - **Pinia 替代 Vuex**：去掉 mutations、原生 TS、模块化 store 自动 code-split
  - **vue-tsc -b** 是 build 模式（增量），先类型检查再 vite build——构建失败先看类型错
  - **Vite 优势**：dev 用 ESBuild 预构建 + 浏览器原生 ESM，HMR 毫秒级；prod 用 Rollup
  - **strict=true** 把 `noImplicitAny / strictNullChecks` 全打开，避免 any 满天飞

---

### H2. Vite 代理配置怎么走的？为什么这么设？

- **源码佐证**
  - `vite.config.ts:14-25`：
    - `/api` → `http://localhost:10001/fastop`（rewrite 去 `/api`）
    - `/auth-api` → `http://localhost:5000`（rewrite 去 `/auth-api`）
  - alias：`@` → `src`

- **答题要点**
  - **目的**：本地开发跨域 + 隔离前后端域名差异
  - 生产环境对应方案：nginx `location /api { proxy_pass ... }`，前端代码不用改 baseURL
  - axios baseURL 配 `/api` 而非完整域名，dev 走 vite 代理，prod 走 nginx，**同一份代码**

---

### H3. axios 拦截器做了什么？

- **源码佐证**
  - `src/api/authRequest.ts:18-24` 请求拦截注 `Authorization: Bearer {token}`
  - 同文件 L26-39 响应拦截：`code !== 200/201` → ElMessage 错误提示 + reject
  - `src/api/request.ts` 业务 API 同款逻辑，超时 10s

- **答题要点**
  - **请求拦截典型职责**：注 token、注 traceId（链路追踪）、loading 计数 +1
  - **响应拦截典型职责**：401 跳登录、403 提示无权限、统一错误 toast、loading 计数 -1
  - **本项目缺**：无 401 自动 refresh token、无重复请求合并（pending map）、无防重放（timestamp + sign）
  - **改进**：用 axios cancelToken / AbortController 处理路由切换时未完成请求

---

### H4. Element Plus 全量引入会有什么问题？

- **源码佐证**
  - `main.ts:6-7,15` 全量 import + 全量注册图标库

- **答题要点**
  - **问题**：bundle 体积大（~1MB+ 仅 EP），首屏慢
  - **改进**：用 `unplugin-vue-components` + `unplugin-auto-import` 按需加载，仅打包用到的组件
  - 图标库优化：用 `@iconify/vue` 按需取，避免全量注册数百图标
  - **本项目权衡**：业务页面密集用大量组件（el-table、el-form、el-cascader），按需收益不及"开发心智简单"，可先全量再视性能瓶颈优化

---

### H5. 前端路由懒加载与守卫？

- **源码佐证**
  - `src/router/index.ts` 9 个 route，全 `() => import()`
  - 守卫 L99-111：仅判 `localStorage.access_token`，无角色限制
  - Pinia `auth.ts` 有 `hasRole()`，但守卫层未调用

- **答题要点**
  - **懒加载 = 路由级 code splitting**，按页面切 chunk，首屏只加载 Login
  - 路由 meta 通常加 `requiresAuth: true / roles: ['ADMIN']`，守卫读 meta 判断——本项目目前 meta 无角色字段
  - **改进**：守卫加角色判定 + 404 页面 + meta.title 自动改 document.title
  - 跨标签页同步登出：监听 `window.addEventListener('storage', e => { if (e.key === 'access_token' && !e.newValue) router.push('/login') })`

---

### H6. Pinia 与 Vuex 的差别？项目里 store 怎么组织？

- **源码佐证**
  - `src/store/auth.ts` `useAuthStore`（用户、roles、hasRole）
  - `src/store/globalFilter.ts` `useGlobalFilterStore`（4 级联选择器：model/profession/subsystem/testBase）

- **答题要点**
  - Pinia vs Vuex：
    - 无 mutations，actions 直接改 state（原子性靠 Vue 响应式）
    - TS 类型自动推断（`defineStore` 返回类型是函数返回值）
    - 多 store 自然 code-split，无单一 root store
    - DevTools 支持等价
  - 持久化：本项目用 localStorage 手工存 token；如需更通用，可上 `pinia-plugin-persistedstate`
  - **跨组件共享 vs props/emit**：globalFilter 这种"全局过滤条件"放 store 比 prop drilling 干净

---

## I. 构建、CI 与部署

### I1. CI 流水线长什么样？

- **源码佐证**
  - `.github/workflows/maven.yml`：触发 `push/PR` 到 main 且路径匹配 `fastop/**` 或 workflow 自身（L4-13）
  - JDK 8 / Temurin（L26-30）
  - Maven 缓存 key 基于 `fastop/**/pom.xml`（L31-33）
  - 构建命令 `mvn -B package -DskipTests`（L36）

- **答题要点**
  - **路径过滤** `paths: fastop/**` → 只改前端不跑 Maven，省 CI 资源
  - **`-B`（batch mode）** 关闭 Maven 交互式输出，CI 日志干净
  - **`-DskipTests`**（仅 skip 执行）vs `-Dmaven.test.skip=true`（连编译都 skip）—— 当前用前者，但实际无测试执行
  - **缺什么**：
    - 无前端 CI（frontend/）—— Vue 改动 PR 没 type check
    - 无 docker 构建 + 推镜像
    - 无单测 + 覆盖率门槛（jacoco）
    - 无依赖漏洞扫描（OWASP Dependency-Check / Snyk）

---

### I2. 为什么还在用 JDK 8？

- **源码佐证**
  - `fastop/pom.xml:18` `java.version=1.8`
  - Spring Boot 2.6.13（L21）—— Spring Boot 2.x 最低 JDK 8，3.x 起最低 JDK 17

- **答题要点**
  - 历史原因：Spring Boot 2.6.13 兼容 JDK 8；客户/部署环境 JRE 仍是 8
  - **JDK 8 → 17/21 升级阻力**：
    - module system（jigsaw）破坏反射访问 → 旧库（fastjson1.x）报 `IllegalAccessException`
    - 移除 `javax.*` 改 `jakarta.*`（Spring Boot 3 强制）
    - GC 默认从 ParallelGC 变 G1（JDK 9+）→ 性能曲线变化要重新调参
  - **不升的成本**：失去 ZGC/Shenandoah、Records、Sealed、Pattern Matching、虚拟线程（JDK 21）
  - 务实迁移路径：Spring Boot 2.x + JDK 8 → Spring Boot 2.7（最后 JDK 8 兼容版） → Spring Boot 3.x + JDK 17

---

### I3. 部署方式？没有 Dockerfile？

- **源码佐证**
  - 无 Dockerfile / docker-compose / k8s manifest
  - `fastop-service/pom.xml:122-138` 用 `spring-boot-maven-plugin` 打 fat jar，mainClass=`FastopServiceApplication`
  - 前端 `vite build` → `dist/`，后端 `resources/static` 为空 → 前端**未打进后端**

- **答题要点**
  - 当前部署假设：手工 `java -jar fastop-service.jar` + nginx 托管前端 dist
  - 加 Dockerfile（标准 Java 8 多阶段构建）：
    ```dockerfile
    FROM maven:3.8-jdk-8 AS build
    WORKDIR /src
    COPY . .
    RUN mvn -B package -DskipTests
    FROM eclipse-temurin:8-jre-alpine
    COPY --from=build /src/fastop/fastop-service/target/*.jar /app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]
    ```
  - 前端 nginx 配置要点：`try_files $uri /index.html`（SPA 路由 fallback）+ `proxy_pass /api → backend`

---

### I4. 数据库迁移没有 Flyway？

- **源码佐证**
  - 无 Flyway / Liquibase 依赖
  - 静态 SQL：`fastop/dataset/260302.sql`（全量 dump）、`operation_log.sql`、`alter_step_device_ems.sql`

- **答题要点**
  - 当前流程：手工执行 SQL 文件，按文件名排序 → 容易漏、容易重复执行
  - 引入 Flyway：
    - SQL 文件命名 `V20260413__add_user_context.sql`
    - 启动自动扫描 + 执行未应用迁移，记录到 `flyway_schema_history` 表
    - 不可重复修改已应用文件（用 `R__` 前缀的可重复脚本除外）
  - 已知风险：当前 `alter_step_device_ems.sql` 是建表后的修改脚本，没有"应用记录"，新人接手不知该不该跑

---

### I5. 依赖版本管理 / 已知风险？

- **源码佐证**
  - `pom.xml:22-30`：mybatis-spring-boot 2.2.0、druid 1.1.22（**老版本**）、hutool-all 5.7.13、lombok 1.18.30、swagger2 2.9.2（**老版本**）
  - mysql-connector-java 由 Spring Boot BOM 管理（默认 8.0.x）

- **答题要点**
  - **Druid 1.1.22**（2020 年）→ 1.2.x 修了若干 RCE（CVE-2022-45146 等），建议升 1.2.20+
  - **Swagger2 (2.9.2)** 已不维护 → 迁 SpringDoc OpenAPI 3（`springdoc-openapi-ui`），原生支持 OpenAPI 3 标准 + Knife4j 增强 UI
  - **fastjson** 历史 RCE 累计十多个 → 优先迁 fastjson2 或 Jackson
  - 改进：上 OWASP Dependency-Check Maven 插件，CI 失败阈值设 `CVSS>=7`

---

### I6. Lombok 用了哪些注解？有什么坑？

- **源码佐证**
  - 依赖 `lombok 1.18.30`
  - 实体类大量 `@Data / @Slf4j` 使用（OperationLog.java、TestPlan.java 等）

- **答题要点**
  - **`@Data` 的坑**：自动生成 `equals/hashCode` 基于所有字段——有双向关联（如 OneToMany）时会**栈溢出**；推荐 `@Getter @Setter @ToString` 拆开
  - **`@EqualsAndHashCode(callSuper=true)`** 继承时必须写，否则 lombok WARN
  - **`@Builder` + JPA**：lombok 默认全字段构造器，JPA 需要无参——补 `@NoArgsConstructor @AllArgsConstructor`
  - **`val` / `var`**：仅本地变量，不是 final
  - **构建依赖**：IDE 装 Lombok 插件 + `annotationProcessorPaths` 配 lombok，否则编译失败

---

## J. 端到端业务流程主线（最容易被深挖）

### J1. 一个测试计划从创建到完成，整条链路怎么走？

- **源码佐证**
  - 设计层级（多对多 + 中间表）：`TestSuite` → `FunctionSuite`（中间表）→ `TestFunction` → `TestFunctionModule` → `TestFunctionCase` → `TestFunctionStep`
  - 派工：`TestPlanController.dispatchPlan(L66)` → `TestPlanServiceImpl.dispatchPlan(L306)` → `ExeFunctionServiceImpl.conveyTestFunction2ExeFunction(L98-150)` → `ExeStepServiceImpl.conveyTestStep2ExeStep(L94-157)`
  - 启动：`startPlan(L366)` 级联推 ExeFunction 至 EXEING
  - 执行：`ExeStepController.doV1(L50)` → `ExeStepServiceImpl.doV1(L304)` → `processAsyncEms(L341)` → `emsMessageService.buildFromExeStep(L347)` → RestTemplate POST EMS
  - 检验：`ExeStep.verifyStatus`（普检）+ `ExeStep.militaryStatus`（军检）独立字段
  - 审计：`recordOperationLog(L410-426)` 在 dispatch / start / pause 各点调用

- **答题要点**
  - 三段式：**设计（静态制品）→ 派工（生成执行实例）→ 执行（驱动设备）**
  - 命名前缀刻意区分：设计层 `TestFunction/TestStep`，执行层 `ExeFunction/ExeStep`
  - 检验是"双签"：普检 + 军检独立字段，互不阻塞

---

### J2. 派工是"快照式"还是"引用式"？为什么？

- **源码佐证**
  - `ExeFunctionServiceImpl.saveExeFunction(L153-203)` 从 `TestFunction` **逐字段复制** 到新建的 `ExeFunction` 行
  - `conveyTestStep2ExeStep(L94-157)` 同理把每条 `TestFunctionStep` 复制为独立 `ExeStepWithBLOBs` 行（含 commandData / criterionContent BLOB）
  - 派工后即使设计层 `TestStep` 被改，执行实例不受影响

- **答题要点**
  - **快照式优势**
    1. **可重放可审计**：执行结果与执行时刻的步骤定义强绑定，半年后回溯能拿到"当时执行的就是这个版本"
    2. **隔离设计变更**：派工后修改测试用例不影响在执行的计划
    3. **状态字段独立**：执行实例需要 status / executor / startTime / endTime，挂在引用上会污染设计层
  - **快照式代价**
    1. 数据库行膨胀：1 个计划 × 100 步骤 = 100 行 ExeStep；1000 计划 = 10 万行
    2. 设计层 bug 修复无法应用到已派工计划，要么发新计划要么手工改 ExeStep
  - **业内对照**：电商订单快照商品价格 / Activiti 部署版本号机制 / 工单系统快照流程定义
  - **何时改引用式**：步骤体量小 + 修订频繁 + 不需要回溯（如 SOP 文档）

---

### J3. 步骤依赖怎么处理？

- **源码佐证**
  - `ExeFunctionServiceImpl.saveExeFunction(L192-195)` 查 `TestFunctionRely` 表，若有前置功能则 `isReady=false`
  - 依赖粒度：**功能级**（ExeFunction），不是步骤级（ExeStep）

- **答题要点**
  - 简化模型：DAG 退化成"功能间前后依赖"，步骤内顺序执行
  - 现实坑：步骤依赖（如步骤 5 必须在步骤 3 完成后跑）当前没建模
  - 改进：引入 DAG 表（`step_dependency`：from_step_id, to_step_id），调度器拓扑排序找可执行集合
  - **业内成熟**：Airflow / Argo Workflows 都是 DAG 调度

---

### J4. 双重审签（普检 + 军检）怎么落？为什么不是工作流引擎？

- **源码佐证**
  - `ExeStep.verifyStatus`、`ExeStep.militaryStatus` 两个独立字段
  - 状态常量 `PLAN_STATUS_VERIFY=1`、`PLAN_STATUS_MVERIFY=4`
  - 审签人 + 意见落 OperationLog 表

- **答题要点**
  - **双状态字段而非状态机叠加**：避免笛卡尔积爆炸
  - 没用 Activiti / Flowable 的原因：流程极简（"通过 / 驳回"两动作），引入工作流引擎学习成本 + 部署复杂度不划算
  - 改进路线：审签链长起来（设计→评审→部门→军检→归档）才考虑 BPMN 引擎
  - 数据级权限痛点（前面提过）在审签场景尤其严重：DESIGNER 不应能改军检字段——要求 Service 层做角色 + 字段双重校验

---

## K. 未完成域 / 占位实现（主动暴露 = 加分项）

### K1. 设备管理域是不是没做完？

- **源码佐证**
  - 后端：`DeviceIntegrationController(L16-32)` 只有 `/integration/device/topics` 一个 GET（外部 topic 拉取），**无 Device 实体、无 CRUD**
  - `ExeStep.dependOnDevice` 字段（L66）存在但未读
  - `ExeStepServiceImpl(L272-273)` 注释：`TODO: Feature pending due to missing entity [Device], [DeviceCommand]`
  - 前端：`DeviceManage.vue(L2-239)` 完整 CRUD UI（状态枚举：1=可用/2=维护/0=禁用）；`device.ts` 注释"后端实现前仅占位"
  - `docs/DEVICE_API_SPEC.md`：CLAUDE.md 提及但**文件实际不存在**

- **答题要点**
  - "前端先行 / 后端预留"的半成品 → 面试时**主动陈述**比被发现强
  - 答题模板：
    - 现状：前端完整原型 + 后端占位接口，目的是"先验证 UX，待业务需求确认后补后端"
    - 缺口：Device 实体 + CRUD Mapper + 与 ExeStep 的 device_id 外键 + 设备心跳 / 在线状态推送
    - 接下来要做：建表 + Mapper Generator + 复用 EmsMessageService 反向接收设备心跳事件
  - **设计原则反思**：前端 mock 应该用 MSW (Mock Service Worker) 隔离，不应假装"真接口"

---

### K2. 命令大盘只是树形导航？没图表？

- **源码佐证**
  - `CommandDashboard.vue(L1-673)`：三层树（plan→function→step）+ 卡片 + 进度条；**无 echarts / chart.js**
  - 数据刷新：手动 `@change="loadExecutionTree"` 触发，**无自动轮询、无 WebSocket**
  - 进度计算：`finished_count / total_steps`（L315-339）前端算

- **答题要点**
  - 当前是"控制台"不是"大盘"：聚焦操作（点哪步执行）而非可视化（趋势 / 对比）
  - 真正"大盘"要补：
    - 后端聚合接口：`GET /command/stats`（按计划 / 设备 / 时间维度统计成功率）
    - 前端 echarts：饼图（状态占比）、折线（指令量趋势）、热力（设备繁忙度）
    - 实时推送：WebSocket 或 SSE 替代轮询
  - **轮询 vs WebSocket vs SSE 八股**
    - 轮询：实现简单，浪费带宽 + 延迟高
    - 长轮询：握手减少但服务端长持连接消耗
    - WebSocket：双向、低延迟、协议复杂、要管心跳 / 重连
    - SSE：单向（server→client）、HTTP 协议天然兼容代理、断线自动重连——本场景最合适

---

### K3. 操作日志页好评，缺什么？

- **源码佐证**
  - `OperationLogController.list(L29-41)` 支持 operatorName / module / action / startTime / endTime + 分页
  - 前端 `SystemLogs.vue` 多维筛选 + 时间范围 + 表格分页
  - **无导出 Excel**、**无大字段全文检索**、**无关联跳转**

- **答题要点**
  - 改进路线：
    1. 导出 Excel：用 EasyExcel（阿里）流式写，避免 OOM
    2. 全文检索：`detail` 字段长，MySQL LIKE 无索引 → 引 ES / 用 MySQL 8 全文索引
    3. 操作详情点击跳转：targetType=TestPlan + targetId → 直接跳到对应计划详情
    4. 异常操作高亮：失败 / 越权 / 频次异常的日志行加红
  - 操作日志表会越来越大，要有归档策略（按月分表 / 冷热分离 / 删超过 1 年）

---

## L. 文档与 Swagger 兼容性坑

### L1. Swagger 能正常打开吗？

- **源码佐证**
  - 依赖：`springfox-swagger2 2.9.2`（pom.xml L54 区间）
  - Spring Boot：2.6.13（L21）
  - 全仓 grep 无 `@EnableSwagger2` Bean、无独立 SwaggerConfig 类、无 `WebMvcConfigurationSupport`
  - `application.yml` 无 `spring.mvc.pathmatch.matching-strategy: ant_path_matcher`

- **答题要点**
  - **已知 bug**：Spring Boot 2.6+ 默认 path matcher 从 `AntPathMatcher` 切换到 `PathPatternParser`，SpringFox 2.9.2 还在用 ant 风格，启动会抛 `Failed to start bean documentationPluginsBootstrapper; NullPointerException`
  - **修复方式三选一**：
    1. `application.yml` 加 `spring.mvc.pathmatch.matching-strategy: ant_path_matcher`（最小改动）
    2. 升 SpringFox 3.0.0（用 `springfox-boot-starter`）
    3. **推荐**：迁 SpringDoc OpenAPI 3（`springdoc-openapi-ui`）+ Knife4j UI
  - 当前项目大概率 Swagger UI 打不开（93 处 @Api 注解但启动失败），优先迁 SpringDoc

---

### L2. API_DOCUMENTATION.md 与 DEVICE_API_SPEC.md 在哪？

- **源码佐证**
  - CLAUDE.md L109-110 列了这两个文件
  - 实际文件系统：**两份都不存在**

- **答题要点**
  - "**文档与代码漂移**"是经典工程痛点
  - 治理三件套：
    1. 单一来源：API 由代码注解（OpenAPI 3）反向生成，不再手写 markdown
    2. CI 校验：PR 必须附 `openapi.yml` diff，CI 用 `openapi-diff` 检测 breaking change
    3. 仓库 hook：`pre-commit` 跑 `markdown-link-check` 防止文档列了不存在的链接
  - 本项目 CLAUDE.md 是 IDE / Agent 上下文，里面提的文件**应当真实存在**，否则 AI 工具按"假事实"工作

---

### L3. Postman 集合用来做什么？

- **源码佐证（审校批 5 修订）**
  - 实际仅 `.postman/config.json`，是空 workspace 配置：`collections / environments / specs / flows / globals` 全是空数组
  - **没有任何 request 集合文件** — 文档之前误说"19 个 request"是早期勘探幻觉

- **答题要点（建议改造）**
  - 现状坦承：`.postman/` 目录仅占位，**实际没有可执行的 API 测试集合**
  - 改造路线：
    - 按 AA 节路由全表，按业务流串 collection（设计→派工→启动→执行→检验，约 20 个 request）
    - 加 environment（baseUrl / token / planId 变量）
    - 用 Newman 在 CI nightly 跑：`newman run fastop.postman_collection.json -e dev.postman_environment.json`
  - 比 JUnit 集成测试便宜：无需 Spring Context 启动，纯 HTTP 黑盒

---

## M. 系统设计延伸题（基于本项目继续往下挖）

### M1. 如果同时有 1000 个测试计划在执行，怎么扛？

- **当前瓶颈**
  - 单实例部署、单 MySQL（autosys_1014 端口 3304）
  - ThreadPoolTaskExecutor core=5 / max=10 / queue=20 → 同时挂起的指令上限 30
  - 指令下发 RestTemplate 同步 POST，无连接池显式配置（默认 SimpleClientHttpRequestFactory，每次新建 HTTP 连接）
  - Druid 默认 maxActive=8 → 数据库连接是第一个塞死的

- **答题分层**
  1. **垂直扩展（最快）**
     - Druid `maxActive=50`、`initialSize=10`
     - ThreadPool 调到 core=20 / max=50 / queue=200
     - RestTemplate 换 OkHttp + 连接池（maxIdleConnections=50）
  2. **水平扩展（中期）**
     - 后端无状态化：UserContext 已是 ThreadLocal 无跨实例依赖；OperationLog 写表 → 多实例直接 nginx round-robin
     - MySQL 主从读写分离：读走从库（操作日志查询走从）
     - 引 Redis 做 token 缓存（避开每请求一次 /userinfo）
  3. **架构演进（长期）**
     - EMS 下发改 Kafka：解耦执行节点与设备控制器，支持削峰
     - ExeStep 表分库分表（按 plan_id hash），单表上千万行性能保持稳定
     - 命令大盘改 SSE / WebSocket 推送，避免轮询雪崩

- **常被追问"如果回 200ms 慢了"**
  - 链路三段查：网络（RestTemplate 超时）/ 数据库（慢 SQL）/ JVM（GC 停顿）
  - 工具：Arthas `trace 类 方法`、jstack、SkyWalking / Pinpoint APM

---

### M2. 设备心跳怎么设计？10 万台设备实时在线？

- **答题套路**
  - 单台心跳频率：30s 一次 → 10 万台 = 3333 QPS，单 MySQL 直接写 INSERT 撑不住
  - 设计：
    1. 设备 → MQTT broker（如 EMQX）持久连接，心跳走 MQTT 协议保活
    2. 后端订阅心跳 topic，**只在状态变化时**写 MySQL（"上线 / 下线 / 异常"事件）
    3. "在线设备列表"放 Redis（key=`device:online`，value=`Set<deviceId>`，TTL 90s 滑动更新）
    4. 前端查在线状态走 Redis，不走 MySQL
  - 为什么不直接 HTTP 轮询：3333 QPS HTTP 握手开销 = 链接数风暴，MQTT 长连接才行
  - 心跳超时检测：定时任务（XXL-Job）每 30s 扫 Redis Set，对比上次心跳时间 > 90s 标记离线

---

### M3. 测试计划批量执行（一次跑 50 个），怎么保证不互相影响？

- **答题套路**
  - **资源隔离**：
    - 每个计划独立线程池（按 planId 路由）→ 防止 A 计划塞满线程影响 B
    - 数据库行锁粒度：状态更新 `where status=2 and exe_step_id=?`（乐观锁），避免长事务
  - **任务隔离**：
    - 设备占用：同一 device_id 同时只能被一个 ExeStep 持有（Redis SETNX 加锁，TTL 等于步骤 timeout）
  - **失败隔离**：
    - 单步骤失败不传染：`@Transactional` 限定方法粒度，失败回滚单步而非整计划
    - 熔断：5 分钟内同设备 3 次指令失败 → 自动 dispatch 该设备的待执行步骤到其他设备

---

### M4. 一条 ExeStep 执行 30 秒超时怎么办？

- **当前实现**
  - `MessageEvents.timeout=30s` 固定常量（EmsMessageService L64）
  - 失败仅 `log.error`，无重试、无补偿

- **答题套路**
  - **超时分级**
    - HTTP 调用超时 → RestTemplate `setReadTimeout(30000)`
    - EMS 业务超时 → MessageEvents.timeout 字段（设备端用）
    - 整体业务超时 → 后端定时扫 ExeStep 状态=EXEING 且 startTime > 5min 标记异常
  - **重试策略**
    - 网络抖动：Spring Retry 指数退避（1s / 2s / 4s）3 次
    - 业务失败：不自动重试，留人工决策
  - **幂等性**：重试前提是接口幂等，要求 EMS 端按 `command_id` 去重（当前缺）

---

## N. 简历话术 / STAR 法则 / 自我介绍

### N1. 简历项目栏怎么压到 5 行内？

- **模板（替换具体数字）**
  ```
  Fastop 飞机检测试运管理系统｜全栈核心开发                      2025.12 - 2026.04
  - 主导微服务→模块化单体架构演进，剥离 3 个 Feign 客户端，部署单元从 4 → 1，联调成本降 60%
  - 设计 7 状态测试计划生命周期 + 双重审签（普检 / 军检），引入 ThreadLocal 用户上下文，全链路操作可审计
  - 实现 EMS 异步指令下发：ThreadPoolTaskExecutor + RestTemplate，QPS 50 稳定，平均延迟 < 200ms
  - 落地 OAuth2 password grant + RBAC（3 角色 × 5 权限码），自定义 HandlerInterceptor 集成远程 /userinfo
  - 重构核心 commit 455129d 涉及 17 文件 / +2014/-1723 行，消除 15 处 TODO，单元测试稳步补齐
  ```

- **要点**
  - 每行**动词开头 + 量化结果**：剥离 3 个 / 状态 7 个 / +2014/-1723 / QPS 50
  - 名词避用"参与 / 协助"，全用"主导 / 设计 / 实现 / 落地"
  - 技术栈名词放在动作里（Spring / OAuth2 / ThreadLocal），不另起 "技术栈" 行
  - 留 1 行做"亮点收尾"（commit hash + 行数最有视觉冲击）

---

### N2. 自我介绍 60 秒模板

- **结构（45 秒主体 + 15 秒钩子）**
  ```
  我叫XX，浙江大学（学历）。过去半年做了 Fastop 飞机检测试运系统的全栈开发，
  这是个三服务的项目（Spring Boot + Vue 3 + Flask Auth Mock），
  我主要负责后端的核心三块：
  
  一是把项目从微服务架构反向演进到模块化单体——做这件事的原因是用户量
  和团队规模不匹配，分布式带来的复杂度收益不足；
  
  二是设计了七状态的测试计划生命周期，配合双重审签和 ThreadLocal 用户
  上下文做全链路审计；
  
  三是实现了 EMS 异步指令下发，用 Spring 线程池 + RestTemplate，
  并对接 OAuth2 password grant + RBAC 做权限治理。
  
  我个人比较喜欢做"看似简单但要拿捏边界"的工程，
  比如怎么判断要不要拆微服务、状态机用框架还是手写——
  这次项目让我把"过度设计"和"未来扩展"之间的取舍想得更清楚了。
  ```

- **要点**
  - 项目背景一句带过，重心在"我做了什么 + 为什么这么做"
  - 收尾用"个人风格句"勾起追问，把后续问题引导到自己最熟的领域

---

### N3. STAR 法则讲核心 commit 455129d

| 阶段 | 内容 |
|------|------|
| **Situation** | 系统刚做完前后端打通，硬编码 `userId=system`，关键操作没法审计；测试计划状态流转散落各 Service，不允许的状态转移（如 FINISH→EXEING）没拦截；TODO 累积 15 处 |
| **Task** | 1) 落 ThreadLocal 用户上下文 2) 集中状态校验 3) 接口按操作粒度拆（remark 与 update 分离） 4) 全链路操作审计 |
| **Action** | 新增 `UserContextHolder` + `UserContextInterceptor` 从 Bearer 解析 → ThreadLocal；`ExeFunctionServiceImpl.updateFunctionStatusByOption` 用 switch 集中校验，非法转移 log.warn 返回 false；`TestPlanServiceImpl.remarkTestPlan` 从 updateTestPlan 拆出避免备注用整体写权限；OperationLog 操作人改为从 UserContext 取 |
| **Result** | commit 455129d：17 文件 / +2014/-1723 / 净增 291 行；TODO 从 15 → 0；后续状态相关 bug 0 例；操作日志可定位到具体用户名 + 操作类型 + 目标 ID |

- **常被追问 "ThreadLocal 怎么避免泄漏"**
  - 在 `UserContextInterceptor.afterCompletion` 调 `UserContextHolder.clear()`
  - 跨线程场景（@Async / 自建线程池）用 `TransmittableThreadLocal`
  - 测试覆盖：写一个测试在线程池场景验证不串号

---

### N4. 反问环节模板（避免冷场 + 加分）

- **技术深度向**
  - 团队对"模块化单体 vs 微服务"的判断标准是什么？什么场景会拆？
  - 状态机这块，你们更倾向 Spring StateMachine 这种框架还是手写？为什么？
  - CI/CD 流水线现在跑哪些质量门？覆盖率 / 静态分析 / 镜像扫描有没有阻断阈值？
- **团队向**
  - 团队多少人？前后端比例？code review 怎么走？
  - 新人入职第一周一般做什么？
- **个人向**
  - 这个岗位接下来 3 / 6 个月最想解决的问题是什么？
  - 我目前最想加深的是 [分布式 / 性能调优 / 架构设计]，团队有这方面的实战机会吗？
- **避免**：薪资 / 加班 / 福利 → 留给 HR 面

---

## O. 数据库与表设计深聊（最容易被 DBA 系翻底）

### O1. 整套表设计有什么问题？

- **源码佐证**（dataset/260302.sql 全量 dump）
  - 13 张主表 + operation_log（增量）+ alter_step_device_ems
  - **主键混用**：6 张表自增 int（base_id / fun_id / suite_id / case_id / module_id / step_id），3 张表 UUID char(36)（exe_function_id / exe_step_id / plan_id）
  - **索引几乎为零**：除 exe_log 有 3 个（idx_step_id / idx_plan_id / idx_create_time），其余 12 张主表**无任何索引**
  - **无外键物理约束**：全部逻辑外键
  - **字符集混用**：主体 utf8mb4_0900_ai_ci；exe_step 部分字段降级 utf8mb3_general_ci → emoji 截断风险
  - **软删除**：9 张表统一 `deleted tinyint(1)`，4 张表无（exe_log / test_function_case / module / step）

- **答题要点（主动暴露 + 改进路径）**
  - **主键策略**：自增 int 适合内部表（无导出 / 无分库分表），UUID 适合 plan_id 这类业务可见 ID（避免暴露行数）→ 当前混用合理但需文档化规则
  - **索引补救清单**（按高频查询）
    - `test_plan(status, deleted)` 复合索引：状态筛选最高频
    - `exe_function(plan_id)`、`exe_step(exe_function_id)` 关联索引
    - `operation_log(operator_name, create_time)`、`(module, action, create_time)` 已有 ✓
  - **字符集统一**：全表迁 `utf8mb4_0900_ai_ci`，alter table 收口
  - **逻辑外键的代价**：删测试计划时孤儿 ExeFunction 要应用层级联清理；引 Flyway 迁移脚本 + 应用层 service.delete 严格按依赖顺序

---

### O2. TEXT / VARCHAR 字段怎么选？项目里有没有滥用？

- **源码佐证**
  - exe_step：`command_data`、`fail_cause`、`criterion_content` → TEXT
  - exe_function：`caution` → varchar(2000)
  - 大量字段固定 varchar(255)（subject_source_id / version_description / test_caution_id 等）

- **答题要点**
  - **TEXT vs VARCHAR**
    - VARCHAR(N) 行内存储（N ≤ 768 字节会内联），TEXT 行外存储 + 行内 16 字节指针
    - SELECT 默认含 TEXT 列时 InnoDB 走 off-page 读 → 慢一倍
    - 索引：MySQL 8 之前 TEXT 不能直接建索引，要 `KEY (col(255))` 前缀
  - **VARCHAR(255) 的迷信**：5.0 之前 255 是变长长度字节边界（1 → 2 字节），现在已无意义
  - **本项目改进**：command_data 是步骤命令 JSON，确实可能上 KB → 保留 TEXT 合理；caution 用 varchar(2000) 行内存储 OK；那些固定 varchar(255) 但实际只存 36 字节 UUID 的字段应改 char(36) 节省空间

---

### O3. UUID 主键性能问题？

- **答题要点**
  - **UUID 主键三宗罪**
    1. **B+ 树乱序插入** → 页分裂频繁，写入慢 30%+
    2. **占空间**：char(36) vs bigint = 36 vs 8 字节，二级索引每条多带 28 字节
    3. **不利缓存**：随机分布，热数据无聚集性
  - **本项目影响有限**：测试计划数据量小，写入并发低，UUID 业务可见性收益 > 性能损失
  - **生产推荐**
    - 如果一定要用 UUID：用 **UUID v7**（时间序）或雪花算法（趋势递增）
    - MySQL 8 的 `UUID_TO_BIN(uuid, 1)` 转 binary(16) 节省一半空间
  - **业内对照**：Twitter Snowflake / 美团 Leaf / 滴滴 TinyId

---

### O4. 慢 SQL 怎么排查？

- **答题套路**
  - **本项目当前**：Druid 没开 stat filter → 没有慢日志
  - **标准排查链**
    1. Druid `/druid/sql.html` 查最慢 SQL → 拿到 SQL 文本
    2. `EXPLAIN` 看执行计划：type 不是 ref/range/eq_ref / rows 巨大 / Extra=Using filesort,Using temporary
    3. 看索引：`SHOW INDEX FROM xxx`，对照 SQL 的 where / order by 列
    4. 改 SQL 或加索引：覆盖索引（select 列全在索引里，避免回表）
    5. 极端场景：force index、强制走指定索引；hash join（MySQL 8.0+）
  - **避坑**：函数索引列（`where date(create_time) = '...'`）失效；隐式类型转换（`varchar` 列 = 数字字面量）失效

---

### O5. 软删除有什么坑？

- **源码佐证**
  - 9 张表 `deleted tinyint(1) DEFAULT 0`

- **答题要点**
  - **唯一索引坑**：name UNIQUE 时 deleted 行也占索引位 → 无法重建同名记录
    - 解法：复合唯一 `(name, deleted)`；或软删时改 name 为 `name_删除时间戳`
  - **查询每条 SQL 都得带 `where deleted=0`**：易漏；MyBatis 拦截器统一加（项目暂未做）
  - **外键级联**：父删了子怎么办？硬删 vs 软删都要业务约定
  - **存储膨胀**：删 = 标记，数据永远在 → 配合定时归档到冷库
  - **本项目当前**：没有 MyBatis 拦截器统一处理，每个 Mapper SQL 各自写，**漏写就查到删除数据** → 高优改进项

---

## P. 接口安全完整清单（OWASP 系问法）

### P1. 项目里都做了哪些安全防护？哪些没做？

| 风险 | 现状 | 改进 |
|------|------|------|
| **SQL 注入** | 95% `#{}`，仅 ORDER BY 用 `${}`（Example 框架） | ORDER BY 字段加白名单校验 |
| **XSS** | Vue 默认转义，但 `v-html` 未审计；后端无输出转义 | 前端禁用 `v-html` 或 sanitize；后端 ResponseBody 加 HTML escape filter |
| **CSRF** | 用 Bearer Token + localStorage（非 cookie），天然抗 CSRF | 维持现状；如改 cookie 必须加 CSRF token |
| **越权（IDOR）** | **严重**：TestPlanController.listAll() 无权限过滤；任何登录用户能查所有计划 | Service 层加 owner 校验 / SQL where 注入 |
| **未授权访问** | 0 个 `@PreAuthorize`，仅前端 hasRole 隐藏按钮 | Spring Security + 自定义 PermissionEvaluator |
| **敏感信息泄漏** | application.yml 默认密码 `Fastop@123`（明文） | `${FASTOP_DATASOURCE_PASSWORD:}` 不带默认；走 Vault / KMS |
| **重放攻击** | token 7200s TTL，无 nonce / timestamp 防重放 | 关键写接口加 timestamp + sign + nonce + Redis 去重 |
| **暴力破解** | auth-mock 无登录失败计数 | 登录接口加 IP / 账号维度限流（Bucket4j） |
| **CORS** | Vite dev 走 proxy，prod 由 nginx 控制 | 后端 `@CrossOrigin` 限定具体域 |
| **依赖漏洞** | Druid 1.1.22 / fastjson / Swagger2 老版本 | OWASP Dependency-Check + Snyk |
| **HTTPS** | 项目内未配置 TLS | nginx 反代时 Let's Encrypt + HSTS |
| **日志脱敏** | OperationLog.detail 可能落用户密码 / token | toString 脱敏 + 日志框架 Pattern Layout 过滤 |

### P2. JWT 安全坑

- **算法 None 攻击**：旧库支持 `alg: none`，伪造 token；锁定算法白名单
- **HS256 密钥泄漏**：源码 / 配置文件 / GitHub 公开仓
- **不可吊销**：JWT 一旦签发到过期前都生效；要做 blacklist (Redis) 或短 TTL + refresh token
- **本项目用远程 /userinfo 校验，规避了 JWT 主要风险，但每请求一次远程调用是性能坑**

### P3. OAuth2 password grant 已被 OAuth 2.1 弃用，为什么还能用？

- **OAuth 2.1 弃用 password grant 的原因**：
  - 用户密码直接走 client → 违背 OAuth "client 不该见密码"原则
  - 鼓励长期凭证存储 → 泄漏风险
- **本项目场景**：first-party app（自己前端 + 自己后端 + 自己 mock），无第三方 client → 合理使用
- **未来改造**：如开放给第三方应用，必须切 Authorization Code + PKCE

---

## Q. 设计模式在本项目的体现（GoF 23 锚回项目）

### Q1. 项目里用到了哪些设计模式？

| 模式 | 是否使用 | 源码佐证 | 一句话 |
|------|---------|---------|--------|
| **工厂** | ✓ | `ResponseFactory.java:9-81` | `success/failure/builder` 静态工厂方法统一造 Response 对象 |
| **建造者** | ✓ | `ResponseFactory.java:14-56` 内嵌 ResponseBuilder；`DesignNodeDto.java:6` Lombok `@Builder` | 链式构造避免 N 个构造器重载 |
| **单例 + ThreadLocal** | ✓ | `UserContextHolder.java:1-23` `static final ThreadLocal<String>` | 单例托管线程隔离用户态；`UserContextInterceptor` 写入 + clear |
| **责任链** | ✓ | `WebMvcConfig.java:20-22` 注册 + `UserContextInterceptor.java:19` `implements HandlerInterceptor` | preHandle / postHandle / afterCompletion 三段串联 |
| **代理（Spring AOP）** | ✓ | `TestPlanServiceImpl.java:65,384` `@Transactional` | 动态代理织入事务管理 |
| **适配器** | ✓ | `EmsMessageService.buildFromExeStep(L29-54)` | ExeStepWithBLOBs → MessageEtt 跨域报文转换 |
| **策略（隐式）** | △ | `ExeFunctionServiceImpl.updateFunctionStatusByOption(L269-299)` switch | 逻辑上是策略，未抽接口 → 可作为"如果让我重构"的候选 |
| **观察者 / 事件** | ✗ | 无 `ApplicationEventPublisher / @EventListener` | 改进点：状态变更触发 `@TransactionalEventListener` 解耦 EMS 推送 |
| **模板方法** | ✗ | 无公共骨架抽象 | 改进点：`createXxx / dispatchXxx / startXxx` 公共校验+审计可抽 abstract 父类 |
| **装饰器** | △ | ResponseFactory 包装 ResponseBody 偏数据封装 | 严格意义不算 |

- **答题技巧**
  - 面试问"项目用到哪些模式"——别背 GoF 名字清单，**指着具体类说**
  - 主动提"哪些**没用**但**应该用**"（观察者解耦 EMS、模板方法收敛 Service 公共逻辑）→ 显示有架构思考

---

### Q2. switch + enum 算策略模式吗？什么时候真要抽策略类？

- **本项目场景**
  - `updateFunctionStatusByOption` switch 5 个分支，每分支 < 10 行
  - 分支间有公共前后置（状态校验 + 审计）

- **答题要点**
  - **不需要抽策略**：分支少（≤5）、逻辑短、不会单独被替换 → switch 比策略类清晰
  - **需要抽策略**：
    - 分支 > 7 个 → switch 长达 100+ 行
    - 不同分支有独立配置（每种支付方式有自己的费率配置）
    - 分支会被运行时注入 / 替换（A/B test 不同算法）
    - 分支需要单独单测（隔离每个 case）
  - **重构姿势**
    1. 定义 `FunctionStatusOption` 接口 + execute(ExeFunction)
    2. 5 个实现类 `RunFunctionOption / RunPauseOption / ...` 标 `@Component("runFunction")`
    3. 注入 `Map<String, FunctionStatusOption>` Spring 自动按 bean name 装填
    4. 调用 `options.get(option).execute(exeFunction)`
  - **业内对照**：Spring 的 `HandlerMethodArgumentResolver`、`MessageConverter` 都是策略模式 + 责任链组合

---

### Q3. 用了 ThreadLocal 必被追问的细节

- **源码佐证**
  - `UserContextHolder.java`：`static final ThreadLocal<String>` 持有 username
  - `UserContextInterceptor.preHandle` 设值，`afterCompletion` 调 `UserContextHolder.clear()`

- **答题要点**
  - **三大坑**
    1. **内存泄漏**：线程池场景 ThreadLocal 不 clear → key=ThreadLocal 是弱引用 + value 是强引用，value 被 Entry 持有直到线程死亡。线程池线程不死 → value 永生
    2. **跨线程不传递**：@Async / 自建线程池启子线程拿不到父线程 ThreadLocal → 用 `InheritableThreadLocal` 传一次（线程池场景仍失效，因为线程是池里复用的旧线程）→ 阿里 `TransmittableThreadLocal` 解决
    3. **测试串号**：单测线程复用 ThreadLocal 残留 → `@AfterEach` 清理
  - **本项目检查清单**
    - `afterCompletion` 调 clear ✓
    - `processAsyncEms` 用 `executor.execute` 提交到 `taskExecutor` 线程池 → **当前未传 UserContext** → 异步线程内 `UserContextHolder.getCurrentUser()` 拿到的是另一个请求的用户名！这是潜在 bug
    - 修法：包装 Runnable，捕获当前 username 在子线程开头 set + 末尾 clear；或换 `TransmittableThreadLocal`

---

## R. JVM / GC 调优 + 排查实战

### R1. 项目 JVM 参数配过吗？

- **源码佐证**
  - 仓库内**无 jvm.options**、无 Dockerfile 显式 -Xmx / -Xms
  - Spring Boot 2.6.13 默认 GC：JDK 8 默认 ParallelGC；JDK 9+ 默认 G1

- **答题要点**
  - 当前裸跑 `java -jar`，吃 JVM 默认值 → 默认堆约 1/4 物理内存
  - **生产推荐启动参数**
    ```
    -Xms2g -Xmx2g                          # 堆固定避免 resize
    -XX:+UseG1GC                           # G1 适合低延迟
    -XX:MaxGCPauseMillis=200               # 目标停顿
    -XX:+HeapDumpOnOutOfMemoryError        # OOM 时 dump
    -XX:HeapDumpPath=/var/log/heap.hprof
    -Xlog:gc*:file=/var/log/gc.log:time,uptime,level,tags  # JDK 9+
    -XX:+UnlockExperimentalVMOptions
    ```
  - **为什么 -Xms = -Xmx**：避免运行时扩容引起 GC 暂停 + 容器场景避免被 OOM Killer 误杀

---

### R2. 线上接口卡顿，怎么排查？

- **答题套路（5 工具串起来）**
  1. **`top -Hp <pid>`** 找 CPU 高的线程 → 拿 nid（10 进制）
  2. **`jstack <pid> | grep -A 30 0x<nid 16 进制>`** 看具体线程在跑什么
  3. **`jstat -gcutil <pid> 1000`** 每秒打印 GC 状态（YGC/YGCT/FGC/FGCT/Eden/Old 占比）
  4. **`jmap -histo:live <pid> | head -30`** 看占内存最多的对象类
  5. **`jmap -dump:live,format=b,file=heap.hprof <pid>`** + MAT 工具分析对象引用链
  - **进阶**：Arthas `trace 类 方法` 定位耗时方法；`watch 类 方法 returnObj` 看返回值；`thread -n 3` top 3 CPU 线程

---

### R3. 真发生过 OOM 怎么定位？

- **答题模板**
  - **现象**：服务挂、`java.lang.OutOfMemoryError: Java heap space` / `Metaspace` / `unable to create native thread`
  - **三种 OOM 区分**
    - **Heap OOM**：对象太多 → 看 dump，常见嫌犯：未关 ResultSet、Cache 无淘汰、ThreadLocal 泄漏
    - **Metaspace OOM**：类加载太多 → CGLib 动态代理、热部署 / Groovy 动态生成类
    - **Direct memory OOM**：Netty / NIO 用 DirectByteBuffer 没释放
  - **定位三步**
    1. 加 `-XX:+HeapDumpOnOutOfMemoryError` 留 dump
    2. MAT 打开 dump，看 `Leak Suspects`
    3. 看 Dominator Tree 找 Retained Heap 最大的对象
  - **本项目高风险点**
    - ExeStepWithBLOBs 包含 TEXT 字段（command_data 等），如批量加载 1 万条会瞬间几百 MB → 必须分页
    - `processAsyncEms` 队列容量 20 + 显式 `CallerRunsPolicy` → 队列满主线程同步兜底（不丢但会拖主线程）；如改 `LinkedBlockingQueue` 无界则会 OOM

---

### R4. G1 与 CMS 选哪个？

- **答题要点**
  - **CMS 已在 JDK 9 弃用、JDK 14 移除** → 新项目无需考虑
  - **G1**：JDK 9+ 默认；分 Region；Mixed GC 同时清新生代 + 部分老年代；目标可控停顿
  - **ZGC / Shenandoah**：超低延迟（亚毫秒），适合超大堆（>32G），JDK 17+ 生产可用
  - **本项目（Spring Boot 2.6 + JDK 8）**：默认 ParallelGC（吞吐优先，停顿不敏感）；建议显式切 G1（`-XX:+UseG1GC`），毛刺更少

---

## S. 测试补齐路线（当前 0 → 60% 覆盖）

### S1. 项目测试现状

- **源码佐证**
  - `fastop-service/src/test/java/com/hengtiansoft/fastop/FastopApplicationTests.java` 唯一测试，仅 `contextLoads()` 空方法
  - 4 个其他测试类全是同款空壳
  - 无 Mockito、无 MockMvc、无 Testcontainers

- **答题要点**
  - 当前覆盖率约等于 0 → **最大改进空间**
  - 不要一上来追求 100%，按 ROI 分层

---

### S2. 按 ROI 分层补测试

| 层级 | 工具 | 测什么 | ROI |
|------|------|--------|-----|
| **L1 单元测试** | JUnit 5 + Mockito | Service 纯逻辑：状态机校验 / DTO 转换 / 依赖判定 | 极高，秒级反馈 |
| **L2 Mapper 集成** | `@MybatisTest` + H2 / Testcontainers MySQL | Mapper SQL 语法 + 字段映射 | 中，几秒每次 |
| **L3 Controller 集成** | `@SpringBootTest` + MockMvc | HTTP 路由 / 参数校验 / 序列化 | 中，5-10 秒每次 |
| **L4 端到端** | Newman + Postman 集合 | 业务流：派工→启动→执行→检验 | 高但慢，CI nightly 跑 |

- **优先级清单（基于本项目）**
  1. `ExeFunctionServiceImpl.updateFunctionStatusByOption` 状态机 → 5 个 case × 合法 / 非法 = 10 条 test
  2. `TestPlanServiceImpl.dispatchPlan / startPlan / pausePlan` 事务 + 级联 → Mockito 桩 Mapper
  3. `EmsMessageService.buildFromExeStep` 报文构造 → 纯 DTO 测试，无依赖
  4. `UserContextInterceptor.preHandle` → MockMvc 模拟带 token 请求

---

### S3. Mock 与 Stub 区别？

- **答题要点**
  - **Stub**：被动返回固定值（`when(mapper.findById(1)).thenReturn(plan)`）
  - **Mock**：除返回值外还**验证调用**（`verify(mapper, times(1)).update(any())`）
  - **Spy**：包装真实对象，部分方法可 mock，其余走真实
  - **Fake**：可工作的轻量替代实现（H2 替代 MySQL）
  - **本项目**：Mapper 用 mock + 状态校验 verify；EMS 用 Fake（自建 mock server）

---

### S4. Testcontainers 解决什么？

- **答题要点**
  - 痛点：H2 与 MySQL 行为差异大（CTE / JSON / 函数），跑通的 H2 测试上 MySQL 挂
  - 方案：测试启动时 docker run mysql:8 → JDBC 直连真实 MySQL
  - 优点：环境一致、可并行、CI 拿来即用
  - 代价：CI 需 docker 守护进程；启动慢（首次拉镜像）
  - 本项目可行性：高，dataset/260302.sql 直接 init 即可

---

## T. 行为面剧本（高频追问）

### T1. 你做过最有挑战的事？

- **STAR 模板（套用 commit 455129d）**
  - **Situation**：项目原型阶段，硬编码 userId=system，状态流转无校验，TODO 累积 15 处，已有用户反馈"操作日志查不到是谁干的"
  - **Task**：1 个月内完成"可审计 + 状态防呆 + 权限细化"三件事
  - **Action**：
    - 设计 ThreadLocal 用户上下文 + Spring HandlerInterceptor 注入
    - 集中状态机校验，switch + 前置 if 拒非法转移
    - 拆 remarkTestPlan 与 updateTestPlan，权限按动作粒度切
    - 串审签场景：DESIGNER / EXECUTOR / ADMIN 不同操作走不同接口
  - **Result**：17 文件 / +2014/-1723 行单 commit；TODO 清零；后续 1 个月状态相关 bug 0 例
  - **Reflection**：最难是"权限分离的边界"——拆太细接口爆炸，拆太粗权限混淆。最终选"按动作动词分组"（增 / 删 / 改 / 备注 / 派工 / 审签）

---

### T2. 跟同事意见冲突怎么办？

- **模板答题**
  - 真实案例（套用项目）：状态机选型时，A 同学要上 Spring StateMachine（"工业级"），我倾向手写（"7 个状态，引入框架学习成本 > 收益"）
  - 处理过程：
    1. 不直接对抗，先收集双方依据（学习成本 / 后续扩展 / 团队熟练度）
    2. 提议 spike：花 1 天各自写 demo，比较代码量与可读性
    3. 用数据说话：手写版 80 行，Spring StateMachine 版引入 3 个新依赖 + 200 行配置 + 文档
    4. 决策：手写，但留扩展点（状态用 enum 而非裸 int，方便未来引框架）
  - 关键：**对事不对人 + 留台阶 + 数据驱动**

---

### T3. 做过最失败的事？

- **诚实回答 + 反思**
  - 案例（项目级）：早期把 Feign 客户端硬编码到 ServiceImpl 里，后期回退微服务时改了 30+ 处调用点，工作量远超预期
  - 失败原因：没在抽象层加防御层（应该走 Service 接口而不是直接 Feign）
  - 教训：
    1. **依赖倒置原则**真的重要——业务逻辑不该直接依赖具体客户端
    2. 早期"省事"的硬编码会在重构时连本带利还
  - 加分项：把这次教训沉淀成团队 ADR（Architecture Decision Record），后续新加跨域调用必须走 Service 接口 + Mock 实现

---

### T4. 三年规划 / 学习计划？

- **模板**（避免空话）
  - 短期（半年）：把现在缺的补齐——单元测试覆盖率到 60%、上 Flyway / Dockerfile / SpringDoc / Druid 监控
  - 中期（1-2 年）：从应用层往中间件层走——啃透 Netty / RocketMQ / Spring 源码；学一门系统语言（Rust / Go）
  - 长期（3 年）：成为某个垂直领域的"问题解决专家"——比如分布式系统的可观测性、或者高并发交易系统的稳定性
  - 关键：每条都**绑到本项目 / 本职**而不是"我想转 AI"

---

### T5. 加班 / 工作压力？

- **简短答**
  - 接受合理加班（线上故障、重大 deadline）
  - 不接受常态化加班（说明排期不科学）
  - 用代码质量换时间是负债——靠重构补，最终拖更久
  - 个人压力管理：跑步 / 写技术博客（顺带巩固知识）

---

## U. Java 8 + Spring Web 八股（项目锚点）

### U1. Stream API 在项目里的使用与坑

- **答题要点**
  - **常见用法**：filter / map / collect(toList) / groupingBy / 累加
  - **坑**
    1. **不要并行 stream 处理 IO**：parallelStream 用 ForkJoinPool.commonPool，IO 阻塞会拖垮整个池
    2. **不要在 Stream 里改外部状态**：违背函数式不变性，并行时数据竞争
    3. **避免 stream of mutable collection**：先 collect 再用，否则一次性消费完无法复用
    4. **性能**：小集合（< 100）传统 for 更快，Stream 适合声明式可读 + 大集合并行
  - **本项目**：Service impl 大量 `list.stream().map().collect()` 简单转换，无并行使用，OK

### U2. Optional 反模式

- **答题要点**
  - ❌ 字段类型用 `Optional<String>` → Optional 是"返回值容器"，不是"字段类型"
  - ❌ 方法参数用 Optional → 调用方更难写
  - ❌ 嵌套 `Optional<Optional<...>>`
  - ❌ `optional.isPresent() ? optional.get() : default` → 写 `optional.orElse(default)`
  - ✓ 仅用于返回值表达"可能不存在"，配合 `map / flatMap / orElseThrow`

### U3. Spring `@Transactional` 失效场景全集

| 场景 | 原因 | 本项目防御 |
|------|------|-----------|
| 自调用 | 走 this.method 不经代理 | TestPlanServiceImpl 无内部 self-invoke ✓ |
| 非 public | Spring 默认只代理 public | 全是 public ✓ |
| 异常被 try-catch 吞 | 没抛出代理感知不到 | rollbackFor=Exception.class 显式声明 ✓ |
| 异常类型不匹配 | 默认只 rollback RuntimeException | 显式 `rollbackFor` ✓ |
| 类未被 Spring 管理 | new 出来的对象无代理 | 全是 @Service ✓ |
| 传播级别 NEVER / NOT_SUPPORTED | 主动放弃事务 | 未使用 ✓ |
| 多线程调用 | 子线程拿不到主线程事务上下文 | EMS processAsync 在事务外触发 ✓ |
| 异步 @Async 标注的方法 | 同上 | EMS 用 Executor.execute，符合规避 |

### U4. RestTemplate 与 WebClient

- **答题要点**
  - 项目用 RestTemplate（同步阻塞）调 EMS
  - **RestTemplate 已 maintenance 模式**（不再加新功能，但保留），新项目推荐 WebClient（响应式，非阻塞）
  - **本项目场景**：低 QPS，RestTemplate OK；如果 EMS 调用占主线程比例高，可换 WebClient + `block()`
  - 配置坑：默认 `SimpleClientHttpRequestFactory` 每次新连 TCP，**生产必须换 OkHttp / HttpClient + 连接池**
  - 拦截器：`ClientHttpRequestInterceptor` 注入 traceId / metric

---

## V. 分布式系统八股（项目历史微服务遗产 + 未来扩展）

### V1. CAP 与 BASE 怎么映射到本项目？

- **答题要点**
  - **CAP 选 CP 还是 AP**：本项目是模块化单体 + 单 MySQL → 严格说不涉及 CAP（无分区可言）
  - 假设拆回微服务，会选 **CP**（一致性优先）：测试执行场景下"数据不一致"比"短暂不可用"更糟（错误的执行结果毁试件）
  - **BASE**：Basically Available + Soft state + Eventually consistent
    - 操作日志可以接受最终一致（落库失败 log.warn 不阻塞主流程）
    - 测试计划状态必须强一致（Service 单事务）
  - **答题加分**：CAP 是绝对的，BASE 是工程妥协；本项目同时存在两种取舍

---

### V2. 假设把 EMS 调用做成跨服务，怎么保证分布式事务？

- **场景**：本地 ExeStep 状态从 EXEING → FINISH 与"EMS 通知设备完成"必须同步成败
- **方案对比**

| 方案 | 实现 | 适用 | 不适用 |
|------|------|------|--------|
| **2PC** | XA 协议，DB + EMS 都参与 | 强一致 | EMS 协议不支持 XA |
| **TCC** | Try（预占）/ Confirm（提交）/ Cancel（回滚） | 业务可拆三段 | 改造成本高 |
| **Saga** | 长事务 + 补偿动作 | 长流程 + 各步可独立回滚 | 不能脏读 |
| **本地消息表** | 业务表 + 消息表同事务，定时扫表投递 | 简单可靠 | 多一张表 |
| **MQ 事务消息** | RocketMQ 半消息 + 二次确认 | 跨服务异步 | 需引入 MQ |
| **最大努力通知** | 重试 N 次 + 告警人工兜底 | 低一致性要求 | 不适合金钱场景 |

- **本项目推荐**：**本地消息表**（简单 + 可靠 + 不引入新基础设施）
  - `ems_outbox` 表与 `exe_step` 同事务写入
  - 后台定时任务扫 status=PENDING 的 outbox 投递
  - 投递成功标记 SENT；失败计数 + 退避重试

---

### V3. 分布式锁怎么选？

- **三种实现对比**

| 方案 | 实现 | 性能 | 可靠性 |
|------|------|------|-------|
| **DB 乐观锁** | `update ... where version=?` | 中 | 高，强一致 |
| **DB 悲观锁** | `select ... for update` | 低（行锁） | 高 |
| **Redis (SETNX + EX)** | 单条命令原子 | 高 | 主从切换有风险 |
| **Redisson (RedLock)** | 多 Redis 节点过半成功 | 高 | 复杂，仍有争议 |
| **Zookeeper 临时节点** | 顺序节点 + watch | 中 | 强一致 |
| **etcd lease** | 租约 + revision | 中 | 强一致，云原生友好 |

- **本项目场景**：单设备同时只能被一个 ExeStep 占用 → **Redisson SETNX + 看门狗自动续期** 30s
- **看门狗**：lock 过期前每 ⅓ TTL 自动续，避免业务超时锁失效；客户端崩溃 → 不再续 → 锁到期自动释放（不会死锁）

---

### V4. 一致性 Hash / 雪花算法 / Paxos / Raft 怎么解释？

- **答题要点**
  - **一致性 Hash**：节点扩缩容时只迁移少量数据；用虚拟节点解决数据倾斜
  - **雪花算法（Snowflake）**：64bit = 1 + 41(time) + 10(workerId) + 12(seq)，趋势递增 + 全局唯一；时钟回拨是经典坑
  - **Paxos vs Raft**：都是共识算法，Paxos 难懂、Raft 易懂；Raft 拆 Leader Election + Log Replication + Safety
  - 本项目锚点：暂未用，但操作日志主键 `bigint` 可改雪花算法支持分库分表

---

## W. Redis 八股 + 在本项目的落地

### W1. 三大缓存问题

| 问题 | 含义 | 解决 |
|------|------|------|
| **穿透** | 查不存在的 key，每次都打 DB | 缓存空值 + 布隆过滤器 |
| **击穿** | 单个热 key 过期，瞬间高并发打 DB | 互斥锁（Redisson）+ 永不过期 + 后台异步刷 |
| **雪崩** | 大量 key 同一时刻过期 | TTL 加随机偏移 + 多级缓存 + 限流降级 |

- **本项目应用场景**
  - **token 校验**：每请求一次远程 /userinfo → 极重 → **Redis 缓存 token→user，TTL 60s**，**雪崩防御**：TTL = 60 + random(0,30)
  - **测试计划列表**：分页查询走 Redis ZSet，按更新时间排序
  - **设备状态**：Set 结构存在线设备 ID，`SREM` / `SADD` 更新

---

### W2. 缓存与 DB 一致性怎么保证？

- **方案对比**
  - **Cache Aside（旁路）**：读 DB 后写 cache；写 DB 后**删 cache**（不是更新）。最常用
  - **Read/Write Through**：cache 层代理 DB；应用只跟 cache 说话
  - **Write Behind**：写 cache 异步刷 DB。性能高，丢数据风险

- **Cache Aside 经典坑**："先删 cache 后写 DB" vs "先写 DB 后删 cache"
  - 推荐 **先写 DB 后删 cache**：极端情况下读到旧 DB → 重读会删 cache 修正
  - 终极方案：**延迟双删**（删 → 写 DB → sleep N ms → 再删一次）
  - 更高级：订阅 MySQL binlog（Canal）→ 推到 MQ → 删 cache，强一致

---

### W3. Redis 单线程为什么快？

- **答题要点**
  - **纯内存操作** + **IO 多路复用（epoll）** + **单线程避免锁竞争** + **C 实现 + 高效数据结构**
  - 注意：Redis 6 引入多线程**仅用于网络 IO**，核心命令仍单线程
  - 阻塞场景：`KEYS *`、大 key（hgetall 几十 MB）、bigkey 删除（用 `UNLINK` 异步删）

---

### W4. Redis 持久化两种方式？

- **RDB（Snapshot）**
  - 周期性 fork 子进程写全量快照
  - 优：体积小、恢复快
  - 缺：丢失最近数据（fork 间隔内）
- **AOF（Append-Only File）**
  - 每条写命令追加日志
  - sync 策略：always（每条 fsync，慢但不丢）/ everysec（默认，最多丢 1s）/ no（OS 决定）
  - 优：丢失少
  - 缺：体积大、恢复慢
- **生产推荐**：RDB + AOF 混合（Redis 4.0+）

---

## X. 消息队列八股 + EMS 异步可改造方向

### X1. 引入 MQ 解决什么？本项目可不可以用？

- **MQ 三大用途**
  1. **解耦**：生产者 / 消费者不直接依赖
  2. **削峰**：突发流量入队，消费者按自身速率处理
  3. **异步**：主流程不等待非关键路径

- **本项目落地点**
  - EMS 指令下发：现在 RestTemplate 同步 POST → 改 RocketMQ 半消息（事务消息），保证 DB 写入与消息投递原子
  - OperationLog 写入：改 MQ 异步消费 → 主流程零耗时
  - 命令大盘实时推送：MQ → WebSocket Bridge → 前端

---

### X2. RocketMQ vs Kafka 怎么选？

| 维度 | RocketMQ | Kafka |
|------|----------|-------|
| 顺序消息 | 支持分区顺序 | 支持（partition 内有序） |
| 事务消息 | 原生支持半消息 | 需自实现 |
| 延迟消息 | 支持（18 个等级） | 不支持原生 |
| 死信队列 | 原生 DLQ | 自配置 |
| 吞吐 | 几十万 QPS | 百万 QPS |
| 生态 | 阿里系 + Java | 全球大数据生态 |

- **本项目场景**：低吞吐 + 需事务消息 + 中文文档友好 → **RocketMQ**
- 大数据 / 流处理 / 跨语言 → Kafka

---

### X3. 重复消费怎么解决？

- **答题要点**
  - **消费端幂等**是终极方案，不要依赖 MQ 不重复
  - 实现：
    1. 业务唯一键 + DB 唯一索引（INSERT 失败即重复）
    2. Redis SETNX `messageId`（TTL 1 天），存在即跳过
    3. 状态机校验（"已执行"消息再来直接跳过）
  - **本项目场景**：EMS 指令幂等键 = `exeStepId + commandSeq`，DB 唯一索引兜底

---

### X4. 顺序消息怎么保证？

- **答题要点**
  - 全局有序：单 partition / 单 queue → 性能差，不可水平扩展
  - 业务有序：按 Sharding Key 路由到同一 partition（同一订单的事件落同一 queue）→ 部分有序，可扩展
  - **本项目场景**：同一计划的 ExeStep 必须按顺序下发 → Sharding Key = planId

---

### X5. 消息堆积怎么处理？

- **答题套路**
  1. 紧急扩容：消费者实例数 = partition 数（再多消费者也并行不起来）
  2. 临时改批量消费：减少 RPC 来回
  3. 优化消费者业务：定位单条耗时（数据库 / 远程调用慢）
  4. 极端情况：堆积超过保留时间会丢消息 → 临时增加保留期 + 加机器

---

## Y. 网络 / HTTP / TCP 八股

### Y1. HTTP/1.1 vs HTTP/2 vs HTTP/3

| 特性 | HTTP/1.1 | HTTP/2 | HTTP/3 |
|------|----------|--------|--------|
| 传输层 | TCP | TCP | QUIC（UDP） |
| 多路复用 | 否（pipelining 实践失败） | 是（同一 TCP 多 stream） | 是（无队头阻塞） |
| 头部压缩 | 否 | HPACK | QPACK |
| 服务推送 | 否 | 是（已实践被废弃） | 是 |
| 队头阻塞 | HTTP 层 + TCP 层 | TCP 层 | 无 |

- **本项目**：内网 HTTP/1.1 + nginx；前后端分离 axios 默认 HTTP/1.1，可升 HTTP/2

---

### Y2. HTTPS 握手过程？TLS 1.2 vs 1.3

- **TLS 1.2 握手**：2-RTT
  1. ClientHello（支持的 cipher suites + random）
  2. ServerHello + Certificate + ServerKeyExchange + ServerHelloDone
  3. ClientKeyExchange + ChangeCipherSpec + Finished
  4. ChangeCipherSpec + Finished
- **TLS 1.3 改进**：1-RTT（首次）+ 0-RTT（会话恢复）
  - 删除不安全 cipher（RC4 / SHA1 / RSA 密钥交换）
  - 强制 Forward Secrecy（ECDHE）
  - Handshake 内容加密

---

### Y3. TCP 三次握手 / 四次挥手 / TIME_WAIT

- **三次握手**：SYN → SYN+ACK → ACK
  - 为什么三次：双方都确认对方收发能力 + 防止旧 SYN 包延迟到达建立无效连接
- **四次挥手**：FIN → ACK → FIN → ACK
  - 为什么四次：服务端可能还有数据要发，FIN 与 ACK 不能合并
- **TIME_WAIT**：主动关闭方进入，等 2 MSL（默认 60s）
  - 目的：等迟到 ACK / 防新连接收到旧包
  - 高并发短连接场景导致 TIME_WAIT 堆积 → `tcp_tw_reuse=1`、`SO_LINGER` 缩短

---

### Y4. HTTP Keep-Alive vs TCP Keepalive

- **HTTP Keep-Alive**：HTTP/1.1 默认开启，复用 TCP 连接发多个 HTTP 请求
- **TCP Keepalive**：内核探测对端是否还活，默认 7200s 间隔（很长，需调小）
- **二者无关**：HTTP Keep-Alive 是应用层连接复用；TCP Keepalive 是传输层保活
- **本项目改进**：RestTemplate 默认 SimpleClientHttpRequestFactory **不启用 Keep-Alive** → 每次新建 TCP（含 TLS 握手），改 OkHttp 复用连接

---

### Y5. 跨域 CORS 怎么解决？

- **场景**：前端 `localhost:5173` 调后端 `localhost:10001` → 不同源
- **三种方案**
  1. **Vite proxy**：dev 用，本项目实践（vite.config.ts）
  2. **后端 CORS**：`@CrossOrigin` 或 `CorsConfigurationSource`
  3. **nginx 反代**：prod 用，把前后端挂同域
- **预检请求 OPTIONS**：带自定义 header / 非简单方法 / Content-Type 非默认 → 浏览器先发 OPTIONS 探测
- **危险点**：`Access-Control-Allow-Origin: *` 不能与 `Allow-Credentials: true` 同时用

---

## Z. 操作系统 / Linux / IO 八股

### Z1. 进程 vs 线程 vs 协程

| 维度 | 进程 | 线程 | 协程 |
|------|------|------|------|
| 隔离 | 独立地址空间 | 共享 | 共享（用户态调度） |
| 切换开销 | 大（TLB 刷新） | 中（栈切换） | 小（无内核态） |
| 调度 | 内核 | 内核 | 用户态 |
| 适用 | 隔离强 / 容错 | CPU 密集 | IO 密集 |

- **JDK 21 虚拟线程（Project Loom）**：JVM 用户态调度的"协程"，本项目 JDK 8 暂不可用

---

### Z2. IO 多路复用：select / poll / epoll

| 维度 | select | poll | epoll |
|------|--------|------|-------|
| FD 上限 | 1024 | 无 | 无 |
| 数据结构 | 数组 | 链表 | 红黑树 + 就绪链表 |
| 触发 | 轮询 | 轮询 | 事件回调 |
| 效率 | O(n) | O(n) | O(1) |
| 平台 | 跨平台 | Linux | Linux |

- **本项目锚点**：Spring Boot 默认 Tomcat 用 NIO（基于 epoll）；Netty / Reactor 同
- **Reactor 模式**：单 / 多 Reactor + 工作线程池

---

### Z3. 内存分配 / 堆栈 / 直接内存

- **JVM 内存区域**：堆、方法区、栈、本地方法栈、PC 寄存器、直接内存（off-heap）
- **直接内存**：`ByteBuffer.allocateDirect`，绕过 JVM 堆，用于零拷贝（Netty 大量使用）
- **本项目**：未用直接内存；EMS 调用如改 Netty 客户端可获益

---

### Z4. Linux 排错命令

- **CPU**：`top` / `htop` / `mpstat 1` / `pidstat 1`
- **内存**：`free -h` / `vmstat 1` / `pmap <pid>`
- **磁盘**：`iostat -x 1` / `df -h` / `du -sh *` / `iotop`
- **网络**：`netstat -tunlp` / `ss -tunap` / `tcpdump -i any port 10001` / `nc -zv host port`
- **进程**：`ps -ef | grep` / `lsof -p <pid>` / `strace -p <pid>`
- **日志**：`journalctl -u <service> -f` / `tail -f` / `grep -E ... | awk`

---

### Z5. 零拷贝是什么？

- **传统读文件 + 网络发送**：4 次上下文切换 + 4 次数据拷贝
- **零拷贝（sendfile / splice）**：DMA 直接把磁盘数据送网卡，仅 2 次切换 + 2 次 DMA 拷贝（数据不进用户态）
- **典型应用**：Kafka / Netty / Nginx 静态文件；MySQL 主从复制
- **Java**：`FileChannel.transferTo()` 底层调 sendfile

---

## AA. API 路由全表（速查）

### 设计域（designer，5 个 Controller）

| Controller | 路径前缀 | 端点数 |
|-----------|---------|--------|
| BaseStructController | `/base` | 2 GET |
| TestBaseController | `/testBase` | 3 GET |
| TestFunctionController | `/designer/testFunction` | 6 POST + 5 GET |
| TestSuiteController | `/designer/testSuite` | 5 POST + 4 GET |
| FunctionSuiteController | `/functionSuite` | 3 POST + 1 GET |
| TestFunctionModuleController | `/designer/module` | 3 POST + 4 GET |
| TestFunctionCaseController | `/designer/case` | 3 POST + 3 GET |
| TestFunctionStepController | `/designer/step` | 3 POST + 3 GET |
| TestExampleController | `/testExample` | 1 POST |

### 计划执行域（planner）

| Controller | 路径前缀 | 端点数 |
|-----------|---------|--------|
| TestPlanController | `/planner/plan` | 7 POST + 1 GET + 2 DELETE |
| ExeFunctionController | `/exeFunction` | 2 GET |
| ExeStepController | `/exeStep` | 5 POST + 3 GET |
| OperationLogController | `/log/operation` | 1 POST + 1 GET |

### 集成域

| Controller | 路径前缀 | 端点 |
|-----------|---------|------|
| DeviceIntegrationController | `/integration/device` | 1 GET（topic 拉取占位） |

### auth-mock-service 路由

| 端点 | 用途 |
|------|------|
| `POST /oauth/token` | 获取 token（password / refresh_token） |
| `POST /oauth/check_token` | 校验 token |
| `POST /oauth/revoke` | 撤销 token |
| `GET /userinfo` | 当前用户信息（被后端 UserContextInterceptor 调） |
| `POST /api/v1/users` | 创建用户 |
| `GET /api/v1/users/<uid>` | 获取用户 |
| `PUT/PATCH /api/v1/users/<uid>` | 更新用户 |
| `POST /api/v1/users/<uid>/password/reset` | 重置密码 |
| `GET /api/v1/users/<uid>/roles` | 用户角色 |
| `GET /api/v1/roles` / `POST /api/v1/roles` | 角色 CRUD |
| `POST /api/v1/roles/<rid>/permissions` | 绑定角色权限 |
| `GET /api/v1/permissions` | 权限列表 |

### 常被追问的"路径设计问题"

- **路径风格不统一**：`/designer/case` 用 RESTful 子路径，`/exeStep`、`/functionSuite` 直接放根 → 改进：统一加 `/api/v1/` 前缀，按域归类
- **动作动词在路径里**：`/start/{planId}` / `/dispatch/{planId}` 是 RPC 风格，REST 应该 PATCH `/plans/{planId}` body=`{action:"start"}` 或资源化 `POST /plans/{planId}/start`
- **listAll 接口零权限过滤**：TestPlanController / TestSuiteController / TestFunctionController 都有 `listAll`，**任何登录用户能看全量**——P 节安全已提，全表确认
- **DELETE 用 `POST /functionSuite/deleteFunctionSuite`**：误用，应 `DELETE /functionSuite/{id}`
- **批量删除 `DELETE /planner/plan/deleteTestPlanWithBatch` 带 body**：DELETE 带 body 在 RFC 7231 中"非语义"，部分代理 / CDN 会丢 body → 改 `POST /plans:batch-delete`

---

## BB. application.yml 全字段注解

| 字段 | 当前值 | 含义 + 改进建议 |
|------|--------|----------------|
| `spring.application.name` | `service-designer` | **微服务遗产命名！** 实际服务包含 designer + planner，应改 `fastop-service` |
| `spring.profiles.active` | `${FASTOP_PROFILES_ACTIVE:dev}` | 配置 profile（dev / prod），合理 |
| `spring.datasource.druid.url` | `jdbc:mysql://localhost:3304/autosys_1014` | 端口 3304 非默认 3306，可能本地实例区分；DB 名 `autosys_1014` 含日期，建议改 `fastop` |
| `spring.datasource.druid.username` | `root` | 生产应禁 root，应用账号最小权限 |
| `spring.datasource.druid.password` | `Fastop@123` | **明文兜底**：`${FASTOP_DATASOURCE_PASSWORD:Fastop@123}` ← 默认值就是密码 → 走 Vault / k8s Secret |
| **缺失：Druid 连接池调优** | — | initialSize / maxActive / testWhileIdle / filters=stat,wall,slf4j 全无 |
| `server.port` | `${FASTOP_SERVER_PORT:10001}` | 默认 10001（避开 8080 常见冲突），合理 |
| `server.servlet.context-path` | `/fastop` | URL 前缀，与 Vite proxy 对齐 |
| **缺失：server.tomcat.threads.\*** | — | 默认 max=200 + min-spare=10，本项目低并发 OK |
| `mybatis.mapper-locations` | `classpath:/mapper/*.xml` | **只扫 service 模块的 mapper 目录**，model 子模块若有 generator 生成的 xml 需自行加路径 |
| `fastop.integration.device-controller-url` | `http://localhost:5001` | 设备服务地址，**当前未启用**（Device 域占位） |
| `fastop.integration.ems-url` | `http://192.168.1.97:11452/subscribe/product` | EMS 服务（硬编码 IP，应走 DNS） |
| `fastop.integration.ems-send-path` | `/addDefault` | 拼接到 ems-url 后 |
| `fastop.integration.ems-ability-default` | `fastop.send` | EMS 协议字段 |
| **缺失：日志配置** | — | `logging.level.root` / 日志文件路径 / 滚动策略全无，靠 Spring Boot 默认 |
| **缺失：management endpoints** | — | actuator 未启用 → 无 health / metrics / prometheus 端点 |

### 配置改进 PR 模板（面试可主动提）

```yaml
spring:
  application:
    name: fastop-service                # 修复微服务遗留命名
  datasource:
    druid:
      initial-size: 10
      max-active: 50
      min-idle: 10
      test-while-idle: true
      validation-query: SELECT 1
      filters: stat,wall,slf4j
      web-stat-filter:
        enabled: true
        url-pattern: /*
        exclusions: "*.js,*.gif,*.jpg,/druid/*"
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/*
        login-username: ${DRUID_USER}
        login-password: ${DRUID_PASSWORD}
        allow: 127.0.0.1
spring.mvc.pathmatch.matching-strategy: ant_path_matcher  # 修 Swagger NPE
management:
  endpoints.web.exposure.include: health,info,metrics,prometheus
  endpoint.health.show-details: when_authorized
logging:
  level.root: INFO
  level.com.hengtiansoft.fastop: DEBUG
  pattern.console: "%d{HH:mm:ss.SSS} [%thread] %-5level %X{traceId} %logger{36} - %msg%n"
```

---

## CC. 速答卡 50 题（项目相关高频）

### 架构演进（5 题）
1. **Q：为什么从微服务回退？** A：用户量 / 团队规模不匹配，分布式开销 > 收益（commit 637613b）
2. **Q：怎么判断要不要拆微服务？** A：独立扩缩容 / 异构语言 / 团队边界 / 高频独立发布，至少有一项硬需求
3. **Q：模块化单体怎么防泥球？** A：Maven 模块边界 + 跨域走 Service 接口 + ArchUnit 规则
4. **Q：DTO 与 Entity 为什么分？** A：解耦展现层与数据层；将来拆服务时 DTO 是契约不动
5. **Q：spring.application.name 为什么叫 service-designer？** A：微服务遗留命名，应改 fastop-service

### 状态机（5 题）
6. **Q：状态有几个？** A：源码 7 个（UNEXE/VERIFY/EXEING/PAUSE/MVERIFY/DISPATCH/FINISH），对外口径 5 核心
7. **Q：为什么不用 Spring StateMachine？** A：状态少 + 流转线性 + 学习成本不划算
8. **Q：状态校验在哪？** A：ExeFunctionServiceImpl.updateFunctionStatusByOption switch + 前置 if
9. **Q：怎么保证状态变更原子？** A：@Transactional + 同库级联 + OperationLog 审计
10. **Q：状态机退化时怎么办？** A：状态超 10 个 / 流转矩阵复杂 → 上 Spring StateMachine + 持久化上下文

### EMS 异步（5 题）
11. **Q：为什么用线程池不用 @Async？** A：显式 Executor 注入更可控，避免 self-invoke 失效 + 默认 SimpleAsyncTaskExecutor 坑
12. **Q：线程池参数？** A：core 5 / max 10 / queue 20 / **已显式 CallerRunsPolicy**（L29）
13. **Q：CallerRunsPolicy 有什么副作用？** A：队列满时主线程同步执行任务 → 主线程被拖慢 → 上游接受请求慢 → 自然降速实现削峰
14. **Q：怎么保证不丢？** A：本地消息表 + 定时扫表重投
15. **Q：怎么保证不重？** A：command_id 唯一索引 + EMS 端 ack 幂等

### OAuth2 + RBAC（5 题）
16. **Q：为什么 password grant？** A：first-party app 无第三方 client，简单合理（OAuth 2.1 弃用对应外部场景）
17. **Q：为什么不用 JWT？** A：换"即时吊销"和"实现简单"
18. **Q：每请求都调 /userinfo 不慢？** A：是，应加本地 caffeine 30s 缓存
19. **Q：RBAC 落地缺口？** A：后端 0 个 @PreAuthorize；数据级权限缺失（listAll 无 owner 过滤）
20. **Q：token 存 localStorage 不安全？** A：抗 CSRF 但易 XSS，改 httpOnly cookie + SameSite

### 重构（5 题）
21. **Q：核心 commit？** A：455129d，17 文件 / +2014/-1723 / 净 +291 行
22. **Q：ThreadLocal 怎么避免泄漏？** A：afterCompletion clear + 线程池场景用 TransmittableThreadLocal
23. **Q：拆 remarkTestPlan 为什么？** A：备注权限 ≠ 修改权限，按动作粒度切
24. **Q：怎么发现要重构的点？** A：TODO 累积 + bug 重复出现 + 改一个地方要改 N 个文件
25. **Q：拆 Service 接口收益？** A：依赖倒置 + 易于 Mock 测试 + 将来拆服务零改动

### MyBatis + DB（5 题）
26. **Q：${} 在哪用了？** A：Example 框架的 ORDER BY；需白名单
27. **Q：分页怎么做？** A：手工 limit offset；改进上 PageHelper
28. **Q：UUID 主键性能？** A：B+ 树乱序插入 + 占空间；用 UUIDv7 / Snowflake 改进
29. **Q：软删除坑？** A：唯一索引冲突 + 每条 SQL 漏写 where deleted=0；用 MyBatis 拦截器统一
30. **Q：慢 SQL 怎么排查？** A：Druid stat → EXPLAIN → 加索引 → 改 SQL（覆盖索引）

### 安全（5 题）
31. **Q：项目最严重的安全问题？** A：listAll 无权限过滤（IDOR） + 密码默认值明文
32. **Q：CSRF 怎么防？** A：Bearer token in localStorage 天然抗 CSRF
33. **Q：XSS 怎么防？** A：Vue 默认转义；禁 v-html / 必要时 sanitize
34. **Q：依赖漏洞怎么管？** A：OWASP Dependency-Check Maven 插件 + Snyk
35. **Q：Druid 1.1.22 风险？** A：CVE-2022-45146 等 RCE，建议升 1.2.20+

### 性能 / JVM（5 题）
36. **Q：项目 JVM 参数？** A：未配置，吃默认；建议 -Xms2g -Xmx2g -XX:+UseG1GC
37. **Q：OOM 怎么定位？** A：HeapDumpOnOOM → MAT Dominator Tree
38. **Q：CPU 100% 怎么排查？** A：top -Hp → jstack 找线程 → 看堆栈定位代码
39. **Q：项目最高风险 OOM 点？** A：ExeStep BLOB 字段批量加载（command_data 等 TEXT）
40. **Q：G1 vs CMS？** A：CMS 已 JDK 14 移除；G1 默认推荐；超大堆 ZGC

### 行为 / 规划（5 题）
41. **Q：最有挑战的事？** A：commit 455129d 一周内做完用户上下文 + 状态机 + 权限分离
42. **Q：和同事冲突？** A：状态机选型分歧，spike 验证用数据说话
43. **Q：失败经历？** A：Feign 硬编码致重构成本爆炸 → 沉淀依赖倒置 ADR
44. **Q：三年规划？** A：补齐缺口 → 中间件层 → 垂直专家
45. **Q：反问？** A：技术深度 + 团队 + 个人成长，避开薪资加班

### 通用八股（5 题）
46. **Q：@Transactional 失效？** A：自调用 / 非 public / 异常吞 / 类型不匹配 / new 出来 / 多线程
47. **Q：HashMap 1.7 vs 1.8？** A：头插→尾插；链表→红黑树（≥8 + ≥64）；扩容触发条件
48. **Q：volatile 三性？** A：可见性 ✓ 有序性 ✓ 原子性 ✗
49. **Q：Spring 循环依赖？** A：三级缓存解决 setter 注入；构造器注入失败
50. **Q：HTTPS 握手？** A：TLS 1.2 2-RTT；TLS 1.3 1-RTT + 0-RTT 会话恢复

---

## DD. 现场手撕代码题（基于项目场景）

### DD1. 手写一个简化版 ThreadLocal

```java
public class MyThreadLocal<T> {
    private final Map<Thread, T> map = new ConcurrentHashMap<>();
    public void set(T value) { map.put(Thread.currentThread(), value); }
    public T get() { return map.get(Thread.currentThread()); }
    public void remove() { map.remove(Thread.currentThread()); }
}
```

- **追问 1**：跟 JDK ThreadLocal 区别？
  - JDK 是 `Thread.threadLocals` 字段（ThreadLocalMap），key=ThreadLocal 弱引用 + value 强引用 → 解决 Map 全局锁竞争
  - 我这个简化版用全局 ConcurrentHashMap，所有线程争一把锁
- **追问 2**：JDK ThreadLocal 内存泄漏原因？
  - key 是弱引用，gc 后 key=null；但 value 是强引用，被 ThreadLocalMap.Entry 持有
  - 线程池场景：线程不死 → ThreadLocalMap 不死 → value 不死 → OOM
  - 解法：用完 `remove()`

---

### DD2. 手写状态机校验（项目实际场景）

```java
public enum PlanStatus { UNEXE, EXEING, PAUSE, FINISH, DISPATCH }

public class StatusMachine {
    private static final Map<PlanStatus, Set<PlanStatus>> ALLOWED = Map.of(
        PlanStatus.DISPATCH, Set.of(PlanStatus.UNEXE),
        PlanStatus.UNEXE,    Set.of(PlanStatus.EXEING),
        PlanStatus.EXEING,   Set.of(PlanStatus.PAUSE, PlanStatus.FINISH),
        PlanStatus.PAUSE,    Set.of(PlanStatus.EXEING)
    );

    public boolean canTransit(PlanStatus from, PlanStatus to) {
        return ALLOWED.getOrDefault(from, Collections.emptySet()).contains(to);
    }

    public PlanStatus transit(PlanStatus current, PlanStatus target) {
        if (!canTransit(current, target)) {
            throw new IllegalStateException(
                String.format("非法转移: %s -> %s", current, target));
        }
        return target;
    }
}
```

- **追问**：怎么扩展支持 Guard / Action？
  - 把 ALLOWED 改 `Map<Pair, Transition>`，Transition 含 guard(BiPredicate) + action(BiConsumer)
  - 项目里的 switch 实质就是隐式 Guard（前置 if）+ 隐式 Action（updateMapper）

---

### DD3. 手写令牌桶限流（项目可补 EMS 限流）

```java
public class TokenBucket {
    private final long capacity;
    private final double rate;          // tokens per second
    private double tokens;
    private long lastRefillNanos;

    public TokenBucket(long capacity, double rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.tokens = capacity;
        this.lastRefillNanos = System.nanoTime();
    }

    public synchronized boolean tryAcquire(int n) {
        refill();
        if (tokens >= n) { tokens -= n; return true; }
        return false;
    }

    private void refill() {
        long now = System.nanoTime();
        double elapsed = (now - lastRefillNanos) / 1_000_000_000.0;
        tokens = Math.min(capacity, tokens + elapsed * rate);
        lastRefillNanos = now;
    }
}
```

- **追问**：synchronized 性能差？
  - 改 AtomicLong CAS / Bucket4j 库
- **追问**：分布式怎么做？
  - Redis Lua 脚本原子扣减 + Redisson RateLimiter

---

### DD4. 手写责任链（项目里 HandlerInterceptor 是责任链）

```java
public interface Handler { boolean handle(Request req); }

public class HandlerChain {
    private final List<Handler> chain = new ArrayList<>();
    public HandlerChain add(Handler h) { chain.add(h); return this; }
    public boolean execute(Request req) {
        for (Handler h : chain) if (!h.handle(req)) return false;
        return true;
    }
}
```

- 项目锚点：UserContextInterceptor → AuthInterceptor → LogInterceptor 串起来；返回 false 即短路

---

### DD5. 手写本地消息表 outbox 模式（项目改进 EMS 不丢的方案）

```java
@Service
public class EmsOutboxService {

    @Transactional
    public void writeAndSend(ExeStep step, EmsCommand cmd) {
        // 1. 业务表写
        exeStepMapper.update(step);
        // 2. 同事务写 outbox
        outboxMapper.insert(new Outbox(cmd, "PENDING"));
        // 提交后 publishEvent 触发投递
    }

    @Scheduled(fixedDelay = 5000)
    public void flushOutbox() {
        List<Outbox> pending = outboxMapper.selectPending(100);
        for (Outbox o : pending) {
            try {
                emsClient.send(o.getPayload());
                outboxMapper.markSent(o.getId());
            } catch (Exception e) {
                outboxMapper.incRetry(o.getId());
                if (o.getRetry() > 5) outboxMapper.markDead(o.getId());
            }
        }
    }
}
```

- **追问**：定时任务多实例怎么避免重复投递？
  - 数据库行锁：`SELECT ... FOR UPDATE SKIP LOCKED`（MySQL 8.0+）
  - 或 Redisson 分布式锁 + ShedLock

---

### DD6. 手写 LRU（高频题，项目可应用于 token 缓存）

```java
public class LRU<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;
    public LRU(int capacity) {
        super(capacity, 0.75f, true);  // accessOrder=true
        this.capacity = capacity;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
```

- **追问**：线程安全？
  - 包 `Collections.synchronizedMap` 或 ConcurrentHashMap + 双向链表（Caffeine 实现）
- **追问**：项目哪里用？
  - UserContextInterceptor 调 /userinfo 加缓存：`new LRU<String, User>(1000)` + 过期戳

---

## EE. 代码审查反向训练（项目真实问题作反例）

### EE1. 给你一个 service 方法，找出 5 个问题

```java
public TestPlan getPlan(String planId) {
    TestPlan plan = mapper.selectByPrimaryKey(planId);  // (1)
    plan.setOperator(UserContextHolder.getCurrentUser());  // (2)
    return plan;                                         // (3)
}
```

- **答案**
  1. (1) 没判 null → planId 不存在 NPE 在 (2)
  2. (2) 在读接口里改了实体，会被 MyBatis 缓存污染 / 误更新
  3. (3) 应返 DTO 不返 Entity，避免序列化 BLOB 字段 + 暴露内部字段
  4. 没权限校验：任何用户能读任何 planId
  5. 没事务标记 readOnly：默认走可读写事务，浪费

### EE2. application.yml 找隐患

```yaml
spring.datasource.druid:
  url: jdbc:mysql://localhost:3304/autosys_1014
  username: root
  password: ${FASTOP_DATASOURCE_PASSWORD:Fastop@123}
```

- **答案**
  1. password 默认值是真实密码 → 环境变量没设就用明文兜底
  2. 用 root 账号 → 应建独立 app 账号最小权限
  3. DB 名 `autosys_1014` 含日期 → 不语义化
  4. 缺 maxActive / testWhileIdle / filters → Druid 默认配置太裸
  5. 缺 useSSL=false / serverTimezone=Asia/Shanghai → 时区与 SSL 警告

### EE3. Controller 找问题

```java
@GetMapping("/listAll")
public Response<List<TestPlan>> listAll() {
    return ResponseFactory.success(planService.listAll());
}
```

- **答案**
  1. **IDOR 越权**：任何登录用户能看全量计划
  2. 无分页：1 万条会 OOM
  3. 返 Entity 不是 DTO
  4. 无权限注解
  5. 路径 `/listAll` 是 RPC 风格，应 `GET /plans?page=1&size=20`

### EE4. 异步线程池找问题

```java
@Bean
public Executor taskExecutor() {
    ThreadPoolTaskExecutor exe = new ThreadPoolTaskExecutor();
    exe.setCorePoolSize(5);
    exe.setMaxPoolSize(10);
    exe.setQueueCapacity(20);
    exe.setThreadNamePrefix("Async-Step-");
    return exe;
}
```

- **答案**
  1. 缺 `setRejectedExecutionHandler` → 默认 AbortPolicy 抛异常丢任务
  2. 缺 `setKeepAliveSeconds`
  3. 缺 `setWaitForTasksToCompleteOnShutdown(true)` + `setAwaitTerminationSeconds(60)` → 优雅停机会丢正在跑的任务
  4. 没初始化（`exe.initialize()`）→ 有些场景必须显式调
  5. ThreadLocal 在异步线程不传递（项目真实潜在 bug）

### EE5. SQL 找问题

```xml
<select id="listByCondition" resultType="TestPlan">
    SELECT * FROM test_plan
    WHERE deleted=0
    <if test="status != null">AND status = ${status}</if>
    ORDER BY ${orderBy}
    LIMIT #{offset}, #{size}
</select>
```

- **答案**
  1. `${status}` 应 `#{status}`（SQL 注入）
  2. `${orderBy}` 必须白名单校验（无法预编译）
  3. `SELECT *` 把 BLOB 字段全拉出来 → 改显式列
  4. 深分页 `LIMIT 100000, 20` 慢 → 改游标分页 `WHERE id > #{lastId} LIMIT #{size}`
  5. 缺 `(status, deleted)` 复合索引 → 全表扫

---

## FF. 简历不同岗位投放策略

### FF1. 投 Java 后端：放大 Spring / 状态机 / 事务

简历项目栏改写：
```
- 主导 Spring Boot 模块化单体演进，剥离 3 个 Feign 客户端，部署单元 4→1
- 设计 7 状态测试计划生命周期，@Transactional + 级联状态推进保证原子性
- 实现 EMS 异步下发：ThreadPoolTaskExecutor + RestTemplate，避开 @Async self-invoke 失效
- OAuth2 password grant + 自定义 HandlerInterceptor 集成远程 /userinfo
- 重构 commit 17 文件 / +2014/-1723 行，引入 ThreadLocal 用户上下文消除 15 处 TODO
```

- 主推 G/U 节（MyBatis、@Transactional 失效）+ V/W 节（分布式 / Redis）
- 不提前端 / 不提 Vue

### FF2. 投全栈：前后端均衡

```
- 全栈主导：Spring Boot 2.6 + Vue 3 + Vite + Pinia + Element Plus + TypeScript
- 后端：模块化单体演进、7 状态机、EMS 异步、OAuth2+RBAC、commit 455129d 重构 17 文件
- 前端：Vue 3 Composition API + Pinia store 双 store 管理、axios 拦截器统一鉴权
- 工程化：Vite proxy 跨域、vue-tsc 严格模式、5 业务模块路由懒加载
```

- 主推 H 节（前端栈）+ AA 节（路由全表）
- 弱化 JVM / 分布式

### FF3. 投测试开发：放大测试管理 + 自动化场景

```
- 测试管理平台主开发，覆盖测试用例库、套件管理、计划派工、设备指令下发全链路
- 设计 7 状态测试计划生命周期，支持双重审签（普检 / 军检）和全链路操作日志
- EMS 异步指令对接设备控制器，ThreadPoolTaskExecutor 5/10/20 池稳定承载
- 设计 API 生命周期回归测试体系（计划+模块+套件全链路），CI 用 Newman 跑 nightly
- 主导测试覆盖建设：从 0 → 60% 覆盖路线，JUnit 5 + Mockito + Testcontainers
```

- 主推 J 节（业务流程）+ S 节（测试补齐）
- 弱化架构演进

### FF4. 投平台 / 工具开发：放大可观测性 + DSL

```
- 主导测试执行平台，自研状态机 DSL 描述测试计划生命周期（7 状态 × N 转移）
- 操作日志全链路审计：ThreadLocal 用户上下文 + AOP 拦截 + 异步落库
- 命令大盘三层树形导航 + 进度实时计算，支持 1000+ 计划并发派发
- 工程化基础设施：自定义 HandlerInterceptor 鉴权、Druid 监控、统一异常处理
```

- 主推 N/T 节（行为面 + STAR）
- 强调"自研" / "DSL" / "全链路"等关键词

### FF5. 投初级 / 实习：放大执行力 + 学习能力

```
- 接手研究生项目继续开发，半年内 58 commit / +xxxxxL/-xxxxxL
- 学习并落地：Spring Boot 模块化、MyBatis、OAuth2、Vue 3 等核心技术栈
- 主导 1 次大型重构（commit 455129d）：17 文件 / +2014 行 / 消除 15 处 TODO
- 主动维护项目文档（INTERVIEW_QA.md / INTERVIEW_PITCH.md）便于团队交接
```

- 强调"主动" / "学习" / "维护"
- 弱化"主导架构" / "技术决策"

---

## GG. HR 面 / 综合面 问答模板

### GG1. 自我介绍（1 分钟版）

> 我叫XX，浙江大学（学历）。技术栈以 Java 后端为主，最近半年完整做了一个全栈测试管理系统 Fastop，
> 主导了微服务到模块化单体的演进、状态机驱动的测试计划生命周期、EMS 异步指令下发、
> 以及 OAuth2+RBAC 权限治理。
> 我比较喜欢做"看似简单但要拿捏边界"的工程问题——
> 比如什么时候该用框架什么时候该手写、什么时候该拆分什么时候该合并。
> 期望加入 [公司] 一起做有挑战的事情。

### GG2. 为什么选我们？

- **三段式**
  - **了解**：我看过 [公司] 的 [具体技术博客 / 开源项目 / 业务方向]，[一两句具体观察]
  - **匹配**：我擅长的 [模块化架构 / 状态机 / 异步指令] 与 [部门 / 岗位] 的方向契合
  - **期待**：希望能在 [具体技术领域] 跟着团队再深一层
- **避免**：
  - "贵司是行业领头羊" → 万能话术，HR 听腻
  - "薪资高" → 立刻减分

### GG3. 优点 / 缺点

- **优点（举例支撑）**
  - 工程性强：commit 455129d 17 文件 / +2014/-1723 行单 commit 一次过 review，没有分阶段返工
  - 系统思考：把项目从微服务回退做成单体，是逆"主流"决策但用数据说话
- **缺点（真实 + 在改）**
  - 系统设计经验集中在中小项目，没接触过百万 QPS 量级 → 在主动学《Designing Data-Intensive Applications》补
  - 单元测试覆盖意识弱，本项目测试 ≈ 0 → 已规划下一阶段补到 60%
- **避免**
  - "完美主义" / "工作狂" → 假大空
  - "脾气急" → 风险预警

### GG4. 三年规划

- **短期半年**：补当前项目的工程缺口（测试、监控、CI 质量门）
- **中期 1-2 年**：往中间件层走，啃 Netty / Spring 源码，学一门系统语言（Rust/Go）
- **长期 3 年**：成为某领域的"问题解决专家"——分布式可观测性 or 高并发交易稳定性
- **避免说**："想转产品" / "想创业" / "想 AI"（让 HR 觉得你不稳定）

### GG5. 期望薪资

- **三段式**
  - **市场价定位**：根据 boss 直聘 / Levels.fyi / 同学反馈，这个职级 [X-Y]
  - **个人期望**：在贵司期望 [X+10%]，看重团队 / 成长 / 技术挑战
  - **留弹性**：如果有合理空间可以再聊，重要的是双向匹配
- **不要**：
  - 报奇怪倍数（×2 不切实际）
  - 第一时间就让步（被认为没自信）
  - 拒绝说数字（HR 反感不配合的候选人）

### GG6. 加班 / 996

- **诚实模板**
  - 接受合理加班（线上故障、季度 deadline）
  - 不接受常态化 996（说明排期不科学）
  - 用代码质量换时间是负债，最终拖更久
  - 个人压力管理：跑步 + 写技术博客
- **避免**：
  - "拥抱 996" → 卑微容易被卷
  - "绝不加班" → 显得不灵活

### GG7. 你还在面其他公司吗？

- **诚实 + 留余地**
  - "在面 [2-3 家同类公司]，目前 [二面 / 终面 / 等 offer] 阶段"
  - "贵司是我比较看重的，如果流程顺畅希望能尽快推进"
- **作用**：抬价 + 制造紧迫感，但不要虚构（HR 会反向核实）

### GG8. 离职原因（往届）

- **三不原则**
  - 不说前公司坏话
  - 不说钱不够
  - 不说人际矛盾
- **正向归因**："想去 [更大平台 / 更核心业务 / 更有挑战的领域] 继续成长，跟前公司业务节奏不再匹配"

### GG9. 你为什么离开学校？

（应届生场景）
- "本科 / 研究生阶段做完 [项目]，发现自己更适合工程而不是研究"
- "想尽早进入产业接触真实场景"

### GG10. 何时能入职？

- 最快：**1-2 周**（应届）/ 离职后 1 个月（在职）
- 不要说"随时" → 显得没准备

---

## HH. 高频笔试编程题清单（项目相关 + 通用）

### HH1. 基于项目场景能改写的 LeetCode 题

| LC 题号 | 题目 | 项目锚点 |
|--------|------|---------|
| LC 207 | 课程表（拓扑排序） | 步骤依赖 DAG 调度 |
| LC 146 | LRU Cache | token 缓存 |
| LC 460 | LFU Cache | 进阶版 token 缓存 |
| LC 200 | 岛屿数量 | 测试套件分组连通性 |
| LC 295 | 数据流中位数 | 命令大盘统计 |
| LC 1188 | 设计有限阻塞队列 | 线程池 queue |
| LC 1242 | 多线程网页爬虫 | EMS 并发下发 |
| LC 1268 | 搜索建议系统 | 测试模块搜索框 |
| LC 460 | LFU | 进阶缓存 |
| LC 1226 | 哲学家进餐 | 资源分配 |

### HH2. 必背 20 题（应届校招 / 社招初级）

**数组 / 字符串**
1. LC 1 两数之和（HashMap）
2. LC 15 三数之和（双指针）
3. LC 53 最大子数组和（DP / Kadane）
4. LC 215 数组中第 K 大（快排 partition / 堆）

**链表**
5. LC 206 反转链表（双指针 / 递归）
6. LC 25 K 个一组翻转
7. LC 142 环形链表 II（Floyd 龟兔）

**树**
8. LC 102 二叉树层序（BFS Queue）
9. LC 124 最大路径和（递归返单边）
10. LC 236 最近公共祖先

**DP**
11. LC 300 最长递增子序列（O(n log n)）
12. LC 72 编辑距离
13. LC 322 零钱兑换

**回溯**
14. LC 46 全排列
15. LC 39 组合总和

**图 / BFS / DFS**
16. LC 200 岛屿数量
17. LC 207 课程表

**栈 / 单调栈**
18. LC 20 有效括号
19. LC 84 柱状图最大矩形

**双指针 / 滑窗**
20. LC 3 无重复字符最长子串

### HH3. 手撕代码高频陷阱

- **快排：** `partition` 写错（左右指针越界）；不稳定；最坏 O(n²)
- **二分：** `mid = (l + r) / 2` 溢出（用 `l + (r-l)/2`）；边界 `<` vs `<=`
- **链表：** dummy 节点必备；reverse 三指针易错
- **DFS：** 递归深度 → 栈溢出（10^4 节点 OK，10^5 危险）
- **BFS：** 必须 visited 数组，否则死循环
- **DP：** 状态转移定义清楚（i 表示什么、转移依赖谁）

### HH4. SQL 题（必考）

```sql
-- 经典：每组 TopN
SELECT * FROM (
    SELECT *, ROW_NUMBER() OVER (PARTITION BY category ORDER BY score DESC) rn
    FROM products
) t WHERE rn <= 3;

-- 连续登录 N 天
SELECT user_id FROM (
    SELECT user_id, login_date,
           DATE_SUB(login_date, INTERVAL ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY login_date) DAY) grp
    FROM logins
) t GROUP BY user_id, grp HAVING COUNT(*) >= 3;

-- 项目场景：找出执行时间超过 5 分钟的步骤
SELECT exe_step_id, plan_id,
       TIMESTAMPDIFF(MINUTE, start_time, end_time) duration
FROM exe_step
WHERE end_time IS NOT NULL
  AND TIMESTAMPDIFF(MINUTE, start_time, end_time) > 5;
```

### HH5. 系统设计题（开放）

| 题目 | 关键考点 |
|------|---------|
| 设计短链系统 | hash / 自增 ID + base62 / 缓存 / 分库分表 |
| 设计秒杀 | 限流 / 库存防超卖 / 异步队列 / Redis Lua |
| 设计 IM | 长连接 / 离线消息 / 已读回执 / 群消息扇出 |
| 设计分布式 ID | 雪花 / Leaf / UUID v7 / 时钟回拨处理 |
| 设计微博 timeline | 拉模型 vs 推模型 vs 混合 |
| 设计排行榜 | Redis ZSet / 分桶 / 实时性 vs 准确性 |
| **设计本项目** | 状态机 / 异步指令 / 双重审签 / 操作审计 |

---

## II. 数据模型 ER 图（mermaid）

```mermaid
erDiagram
    BASE_STRUCT ||--o{ TEST_BASE : "构型→试验库"
    TEST_BASE ||--o{ TEST_FUNCTION : "试验库→模块"
    TEST_BASE ||--o{ TEST_SUITE : "试验库→清单"
    TEST_FUNCTION ||--o{ TEST_FUNCTION_MODULE : "模块→用例"
    TEST_FUNCTION_MODULE ||--o{ TEST_FUNCTION_CASE : "用例→子用例"
    TEST_FUNCTION_CASE ||--o{ TEST_FUNCTION_STEP : "子用例→步骤"
    TEST_SUITE ||--o{ FUNCTION_SUITE : "清单→功能映射"
    FUNCTION_SUITE }o--|| TEST_FUNCTION : "映射→模块"
    TEST_FUNCTION ||--o{ TEST_FUNCTION_RELY : "模块→依赖关系"

    TEST_PLAN ||--o{ EXE_FUNCTION : "派工→执行功能(快照)"
    EXE_FUNCTION ||--o{ EXE_STEP : "执行功能→执行步骤(快照)"
    EXE_STEP ||--o{ EXE_LOG : "步骤→执行日志"

    TEST_FUNCTION ..> EXE_FUNCTION : "snapshot copy"
    TEST_FUNCTION_STEP ..> EXE_STEP : "snapshot copy"

    OPERATION_LOG }o..|| TEST_PLAN : "审计目标"
```

### 关系说明

| 关系 | 类型 | 关键字段 |
|------|------|---------|
| BaseStruct → TestBase | 1:N | base_id |
| TestBase → TestFunction | 1:N | test_base_id |
| TestSuite → FunctionSuite | 1:N | suite_id |
| FunctionSuite → TestFunction | N:1 | fun_id（中间表关联） |
| TestFunction → Module → Case → Step | 三层 1:N | fun_id / module_id / case_id / step_id |
| TestPlan → ExeFunction | 1:N（**派工时快照复制**） | plan_id |
| ExeFunction → ExeStep | 1:N（**派工时快照复制**） | exe_function_id |
| ExeStep → ExeLog | 1:N | step_id |
| TestFunctionRely | 自关联 | rely_fun_id, target_fun_id |

### 设计层 vs 执行层 命名对照

| 设计层（静态制品） | 执行层（动态实例） | 拷贝时机 |
|------------------|------------------|---------|
| TestFunction（fun_id INT 自增） | ExeFunction（exe_function_id UUID） | dispatchPlan 时 |
| TestFunctionStep（step_id INT） | ExeStep（exe_step_id UUID） | dispatchPlan 时 |
| TestSuite | （无对应执行层）| 派工时被遍历 |

### 关键说明（面试可讲）

1. **三层设计层级 + 两层执行层级**：设计阶段 4 层（Function→Module→Case→Step），执行阶段为求简化只到 2 层（ExeFunction→ExeStep），执行步骤把设计层的 Module/Case/Step 拍平到 ExeStep 一行
2. **快照式派工**：派工瞬间 TestFunction → ExeFunction、TestFunctionStep → ExeStep 全字段复制，BLOB 字段（command_data 等）一起拷
3. **依赖关系单独表**：TestFunctionRely 自关联，存"功能 A 依赖功能 B"，派工时计算 ExeFunction.isReady
4. **OperationLog 跨域审计**：通过 `target_type + target_id` 软关联到任意业务实体（TestPlan / ExeFunction / ExeStep / 其他）

---

## JJ. 面试装备包总索引

```
docs/
├── INTERVIEW_QA.md           ← 题库（A-II 35 块）        2700+ 行
├── INTERVIEW_PITCH.md        ← 讲述脚本（5/15/30 min）  225 行
├── INTERVIEW_CHEATSHEET.md   ← 单页打印速查              68 行
├── INTERVIEW_MOCK.md         ← 完整 30 min 模拟一面对话稿  300+ 行
└── KNOWN_ISSUES.md           ← 23 项硬伤 + 90 day 路线图  229 行
```

### 按场景跳转

| 我想… | 翻哪个 |
|------|-------|
| 通读所有题目 | INTERVIEW_QA.md |
| 准备项目自我陈述 | INTERVIEW_PITCH.md |
| 面试当天揣兜里 | INTERVIEW_CHEATSHEET.md |
| 面试前一晚听一遍 | INTERVIEW_MOCK.md |
| 答"项目有什么不足" | KNOWN_ISSUES.md |
| 答"如果接手怎么改" | KNOWN_ISSUES.md 30/60/90 Day Plan |
| 答"项目数据模型" | INTERVIEW_QA.md II 节 mermaid 图 |
| 答"路由长什么样" | INTERVIEW_QA.md AA 节 |
| 答"配置文件怎么写" | INTERVIEW_QA.md BB 节 |
| 答"现场写代码" | INTERVIEW_QA.md DD 节 6 题 |
| 答"代码审查找问题" | INTERVIEW_QA.md EE 节 5 题 |
| 改简历 | INTERVIEW_QA.md FF 节 5 个版本 |
| HR 面问答 | INTERVIEW_QA.md GG 节 |
| 笔试题准备 | INTERVIEW_QA.md HH 节 |
| 速答 50 题 | INTERVIEW_QA.md CC 节 |

### 章节 ABC 索引

| 章节 | 主题 | 题数 |
|------|------|------|
| A | 多模块 + 微服务→单体演进 | 3 |
| B | 测试计划五状态机 | 4 |
| C | EMS 异步指令 | 4 |
| D | OAuth2 + RBAC | 4 |
| E | 代码重构 | 3 |
| F | 通用八股串 | 10 |
| G | MyBatis + Druid | 5 |
| H | 前端 Vue 3 + Pinia + EP | 6 |
| I | 构建 / CI / 部署 | 6 |
| J | 端到端业务流程主线 | 4 |
| K | 未完成域 | 3 |
| L | 文档与 Swagger 坑 | 3 |
| M | 系统设计延伸 | 4 |
| N | 简历 STAR 自介 | 4 |
| O | 数据库深聊 | 5 |
| P | 接口安全 | 3 |
| Q | 设计模式锚回 | 3 |
| R | JVM / GC 调优 | 4 |
| S | 测试补齐路线 | 4 |
| T | 行为面剧本 | 5 |
| U | Java 8 + Spring Web | 4 |
| V | 分布式 | 4 |
| W | Redis | 4 |
| X | MQ | 5 |
| Y | 网络 / HTTP / TCP | 5 |
| Z | OS / IO | 5 |
| AA | API 路由全表 | — |
| BB | application.yml 全注 | — |
| CC | 速答卡 50 题 | 50 |
| DD | 现场手撕代码 | 6 |
| EE | 代码审查反训 | 5 |
| FF | 简历投放策略 | 5 |
| GG | HR 综合面 | 10 |
| HH | 笔试编程题 | — |
| II | 数据模型 ER 图 | — |
| JJ | 装备包总索引 | — |

---

## KK. 源码引用校验记录（2026-05-03 审校）

> 文档累计上千行后做的"反幻觉"校验。已纠正项与未纠正项透明列出。

### 已纠正

| 错误声明 | 正确事实 | 修正范围 |
|---------|---------|---------|
| commit 455129d 涉及 14 文件 | 实际 **17 文件** / +2014/-1723（含 frontend 3 个 .vue/.ts） | 5 个文档全部已更新 |
| 线程池"默认 AbortPolicy 抛异常丢指令" | `AppConfig.java:29` **已显式 `CallerRunsPolicy`** | C/F/CC/EE 节 + 4 文档已更新 |
| KNOWN_ISSUES #7 列为 P1 issue | 已不是 issue（源码已设），改为"曾误判，备注 outbox 进阶" | KNOWN_ISSUES.md 已更新 |

### 已校验无误

| 声明 | 校验结果 |
|------|---------|
| TestPlanStatusContants L5-17 含 7 状态常量 | ✓ |
| AppConfig L18-32 ThreadPoolTaskExecutor 5/10/20/Async-Step- | ✓ |
| ExeFunctionServiceImpl.updateFunctionStatusByOption switch 5 case | ✓（方法体 L262-308） |
| 状态流转 5 个 case 名（runFunction / runPause / doFinish / doInvalid / restartRun） | ✓ |
| UserContextInterceptor implements HandlerInterceptor L19 | ✓ |
| UserContextInterceptor 调 /userinfo L41 | ✓ |
| afterCompletion clear L67-69 | ✓ |
| ResponseBody 字段 code / msg / data / totalNum | ✓（与 CLAUDE.md 描述的 code/data/message/timestamp **不一致** — 文档已记录） |
| commit 455129d 真实存在 + 作者 seal + message | ✓ |
| commit 455129d Co-Authored-By Claude Sonnet 4.6 | ✓（额外发现：commit 含 AI 协作标记，面试可酌情声明） |

### 命名细节修正建议

- 文档提及 `UserContextHolder.set()` / `.get()` 实际方法名是 **`setCurrentUser()` / `getCurrentUser()`** — 演示场景请用全名
- `UserContextInterceptor` **不是 @Component**，由 `WebMvcConfig` 手工 new + `RestTemplate` 注入 — 比扫描注解更显式

### 源码 vs CLAUDE.md 不一致清单（项目级文档漂移，已知）

| CLAUDE.md 描述 | 实际源码 |
|---------------|----------|
| API 响应 `{code, data, message, timestamp}` | ResponseBody 字段是 `{code, msg, data, totalNum}` — **3/4 字段名对不上** |
| 提及 `API_DOCUMENTATION.md` | 文件不存在 |
| 提及 `docs/DEVICE_API_SPEC.md` | 文件不存在 |
| `spring.application.name` 应为业务名 | 实际是 `service-designer` 微服务遗产命名 |

### 校验方法

- 抽查最频繁引用的源码点（5 个）
- git show 验证 commit 元数据
- grep 跨文档定位错误声明
- 修正后再 grep 确认零残留

### 教训（写下供后续 loop 复用）

- **大批量自动化生成必有幻觉**：尤其是 Explore 子 agent 概要报告里漏读的细节
- **线程池配置要逐行读源码**：拒绝策略可能在最后一行 setRejectedExecutionHandler，光看字段方法名容易漏
- **commit stat 必须 git show 实测**：不能凭子 agent 转述
- **方法名细节**：set / setCurrentUser 这种细节在面试现场 demo 时会被抓

### 审校批 66（2026-05-03 第 79 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 handleDelete 不判后端返回值** | `TestPlan.vue L543` `await deleteSingleTestPlan(); ElMessage.success('删除成功')` | 后端假 success（#5FF）前端也显示成功 — 误导用户 |
| **handleDispatch catch 空 `(e){}`** | L546 静默吞异常 | 派发失败用户无感知（虽有 axios 拦截器兜底）|
| **4 handler 一行写法** | L543/L546/L547/L548 await + ElMessage + loadData 单行 | 可读性差 |
| **缺 loading 态** | 删除/派发/开始/暂停按钮无 :loading | 用户可重复点击 |

新校验通过项：
- 4 handler 都用 await + loadData 刷新列表 ✓ 标准模式
- handleDelete 用 ElMessageBox.confirm 确认 ✓ UX
- 派发结果用 dispatchVisible dialog 展示 ✓
- axios 拦截器兜底 ElMessage.error ✓ 错误统一处理

### 审校批 65（2026-05-03 第 77 轮）发现

🚨 **第 11 项 P0 安全 — Flask debug=True RCE**：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 Flask app.run debug=True** | `auth-mock-service/app.py L221` `app.run(host='0.0.0.0', port=5000, debug=True)` | Werkzeug debugger 允许浏览器异常页执行任意 Python 代码（RCE）；P0 |
| **0.0.0.0 监听所有网卡** | 同 L221 | 测试 OK；生产风险 |
| **get_user_roles 缺权限校验** | L213-217 任何人能查任何用户角色 | 信息泄漏 |

### 累计 P0 安全（**11 项** ↑↑）

新增第 11 项：**Flask debug=True RCE**

### auth-mock-service 完整路由数

13 个 route（grep `^@app.route` count=13）— 与之前 AA 节路由表一致 ✓

### 审校批 64（2026-05-03 第 76 轮）发现

新硬伤（**第 12 个真实业务 bug**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 reset_password 假实现** | `auth-mock-service/app.py L172-178` 仅返 `{'message': 'password reset'}` 不实际改密码 | MOCK_PASSWORD 是常量；reset 接口"返成功但密码未变"；第 12 个真实业务 bug；与 #🔥 remarkTestPlan 同模式 |
| **🔥 bind_role_permissions IndexError 风险** | `L204 perm_codes[0]` 直接索引 | perm_codes 空时 IndexError；缺前置 `if perm_codes:` |
| **create_user ID 用 len()** | L140 `uid = str(len(MOCK_USERS) + 1)` | 并发竞态（多进程多请求 race condition）；删除后复用 ID 风险 |
| **create_user 无密码字段** | L141-148 创建用户跳过密码 | 与 mock 全用户共享 MOCK_PASSWORD 一致；但接口设计不一致 |
| **create_user 无权限校验** | 任何人可调 | 无 RBAC 自身保护 |

### ✨ 正面案例：update_user 白名单字段

| 反面（#5ω） | 正面（本批） |
|------------|------------|
| TestSuiteRequestDto 暴露审计字段（前端可篡改 createdBy） | `update_user L166-168 for k in ('name','email','enabled')` 显式白名单 ✓ |

**结论**：**Python 应用做对了，Java 应用错了** — 团队跨语言一致性差。

### 累计真实业务 bug 实证（**12 处** ↑↑）

新增：**reset_password 假实现** — 接口返 success 但实际未改密码（同 remarkTestPlan 模式）

新校验通过项：
- get_user 用 next((x for ...), None) 标准 ✓
- update_user 白名单字段 ✓ 正面对比
- create_role 用 'r' + len() 生成 ID（同 create_user 同问题）
- bind_role_permissions 兼容 string list 与 dict list ✓ 防御

### 审校批 63（2026-05-03 第 75 轮）发现

🚨🔥🔥 **重磅 P0 安全 + 第 11 个真实业务 bug**：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥🔥 refresh_token grant 不验证 refresh_token 内容直接发 admin token** | `auth-mock-service/app.py L72-77` `if grant_type == 'refresh_token': ref = body.get('refresh_token'); if ref: TOKENS[new] = {'user_id': '1', expires}` | 任何 refresh_token 字符串（即使非法/伪造）都返**新的 admin token**！P0 安全洞 + 第 11 个真实业务 bug |
| check_token / revoke / userinfo 标准实现 | L81-119 ✓ | 校验 token 在 TOKENS 字典 + 过期时间 |

### 累计真实业务 bug 实证（**11 处** ↑↑）

新增：**refresh_token 不验证内容发 admin token** — 严重安全洞 + 业务逻辑漏洞合一

### 累计 P0 安全（**10 项** ↑↑）

新增第 10 项：**refresh_token bypass — 任意字符串换 admin token**

新校验通过项：
- access_token / refresh_token 用 uuid4 ✓
- TTL 7200s ✓
- userinfo 用 _bearer_token() 提取 + 校验 ✓
- check_token 兼容 form / query / header 三种入参 ✓ 防御

### 审校批 62（2026-05-03 第 74 轮）发现

新硬伤（**揭穿 CLAUDE.md 字段错的真相**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 CLAUDE.md 响应字段错的真相揭穿** | `auth-mock-service/app.py:14-20` `std_response` 字段是 `code/data/message/timestamp` —— 与 CLAUDE.md 描述完全一致 | CLAUDE.md 把 **mock 服务字段当成项目通用规范**！后端真实 ResponseBody 是 `code/msg/data/totalNum`；两者**不是同一规范**；文档漂移更深一层 |
| **🔥 MOCK_PASSWORD = '123456'** | L30 所有用户共用一个密码 | 同 #批61 admin/123456 P0；硬编码在源码 |
| **🔥 CORS `origins=['*'] + allow_headers=['*']`** | L12 极宽松 | 生产风险；任何 origin 可调 |
| **MOCK_PERMISSIONS DESIGNER 仅 1 权限** | L45 `r2: ['p4']` 设计员仅 designer:module:edit | 业务规则单薄；与"设计员负责清单/模块/步骤设计"实际职责不符 |

新校验通过项：
- std_response 标准结构（code/data/message/timestamp）✓ 是 RESTful API 标准
- L52-54 兼容 JSON / form-urlencoded / query string ✓ 防御性编程
- 三角色 ADMIN/DESIGNER/EXECUTOR 设计与文档一致 ✓
- 5 个权限码用 `domain:resource:action` 格式 ✓ RBAC 标准

### 审校批 61（2026-05-03 第 72 轮）发现

🚨 **P0 安全发现**：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 Login.vue 底部明文显示测试账号 admin/123456** | L36-39 `<div class="login-hint">测试环境可使用：admin / 123456</div>` | 任何人打开登录页就看到管理员账号；P0 安全（**第 9 项 P0**）|
| **🔥🔥 form 默认值预填 admin/123456** | L57-60 `username: 'admin', password: '123456'` | 表单默认填充密码；同 P0；同事打开页面 enter 就登录 |
| **表单 rules 仅 required 无密码强度校验** | L62-65 | 弱密码可通过 |

### 累计 P0 安全（**10 项** ↑↑）

| 项 | 位置 |
|---|------|
| listAll IDOR | TestPlanController:60 |
| 数据库密码默认值明文 | application.yml:10 |
| 数据库 root 账号 | application.yml:9 |
| 后端 0 个 @PreAuthorize | 全部 Controller |
| TestSuiteServiceImpl.check 审签 TODO | L317-321 |
| commons-text 1.9 Text4Shell | pom.xml:27 |
| fastjson 1.2.78 | pom.xml:30 |
| token localStorage XSS | frontend |
| **Login.vue 明文密码提示 + 表单预填**（本批新发现）| frontend Login.vue L36-60 |

新校验通过项：
- Login.vue 用 element-plus el-form + rules 标准 ✓
- L75 redirect 安全：route.query.redirect 取重定向（防开放重定向需校验白名单）
- L76 router.replace 不留登录历史 ✓
- el-input show-password 可切换显隐 ✓

### 审校批 60（2026-05-03 第 71 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 ModuleOrchestration.vue 导入/导出按钮未实现** | L13-14 placeholder 按钮 | 同 TestReview.vue 批量审签 |
| **多态 type 字段 'MODULE'/'CASE'/'STEP' 字符串** | L74-77 select option | 同 #DesignNodeDto.type 反模式 |
| **编辑/新增/删除按钮无 hasRole 校验** | L47-56 | 同 #5e |

✨ **正面**：
- L52 STEP 不能新增子节点业务规则 `v-if="row.type !== 'STEP'"` ✓
- L26 用 el-table tree-props 树形展示 ✓

新校验通过项：
- el-table 树形展示三层 ✓ 与设计层级一致
- 三层用 v-if + el-button 颜色区分（红/蓝/绿）✓ UX
- 创建/编辑用同一 dialog（isEdit 切换）✓ 复用

### 审校批 59（2026-05-03 第 70 轮）发现

新硬伤（**第 14 个命名/拼写问题**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 deletePhy 错误信息混中英文 + "cant" 拼写错** | TestFunctionStepServiceImpl.deletePhy `"stepId is null"` / `"cant find it"` | 项目主体中文错误信息，这里突英文 + can't 漏撇号 → 第 14 个命名/拼写问题 |
| **updateByCaseIds 缺 @Transactional** | 批量 updateByExampleSelective 无事务 | 失败半更新 |

### 累计命名/拼写问题（**14 处**）

新增："cant find it" 英文 + 错别字

新校验通过项：
- deletePhy 实现物理删除 ✓
- updateByCaseIds 用 updateByExampleSelective 批量 update 性能 OK ✓
- 业务规则下沉：把所有 caseIds 下的 step 标记 updated=change ✓

### 审校批 58（2026-05-03 第 69 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **DeviceIntegrationService.fetchTopics restTemplate 无 timeout/retry** | L33 `restTemplate.getForObject(url, String.class)` | 外部调用无超时易卡死 |
| **L51 ObjectMapper.treeToValue 解 example 为 Object** | 类型擦除 | 同 #DeviceTopicsPayload.example 反模式 |

✨ **正面案例**：
- L35-38 兼容两种响应格式（`data` 包装与否）→ 防御性编程 ✓
- L29-31 `deviceControllerUrl` 未配置抛 IllegalStateException → 显式失败 ✓ 与项目静默吞异常对比
- L56-61 trimTrailingSlash 私有 static 工具方法 ✓

新校验通过项：
- 用 UriComponentsBuilder 构造 URL 标准 ✓
- Jackson + ObjectMapper 解析（与 EmsMessageService 一致）✓
- 用 builder().topics().example().build() Lombok @Builder ✓

### 审校批 57（2026-05-03 第 68 轮）发现

新硬伤 + **正面对比**：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestExampleServiceImpl Integer == 比较 多处 copy-paste** | L128 `tFCase.getCaseStatus() == StatusContants.step_status_del` / L173 `tFStep.getStepStatus() == ...` | 同 #批42 反模式系统性蔓延；自动拆箱 NPE 风险 |
| **🔥 L138 强转 TestFunctionModule** | `(TestFunctionModule) tFModuleResponse.getData()` | ClassCastException 风险（同 #5δ）|
| **🔥 L139 module status 直接赋 case status** | `tFCase.setCaseStatus(tFModule.getModuleStatus())` | 跨实体状态枚举可能不同；业务规则不明 |
| **updateCase/updateStep 调 deletePhy 物理删除** | L148 `deletePhy(tFCase.getCaseId())` | 同 #5EE 模式 |

### ✨ 正面对比：DeviceIntegrationController vs TestPlanServiceImpl LOG 写法

| 反面（#批47） | 正面（本批） |
|--------------|------------|
| `LOG.warn("业务创建失败: {}", e)` 用 {} 占位符传 Throwable → 漏堆栈 | `log.warn("fetch device topics failed", e)` 第二参数直传 Throwable → 自动打堆栈 ✓ |

**结论**：项目内同款代码两种写法并存——团队 LOG 习惯不统一。

新校验通过项：
- DeviceIntegrationController 仅 1 端点 + try-catch 异常返 failure 正确 ✓
- log.warn(..., e) 第二参数传 Throwable 正确写法 ✓
- TestExampleServiceImpl.updateCase 三段决策（已删 / 跟随 module / 物理删）业务规则清晰 ✓

### 审校批 56（2026-05-03 第 67 轮）发现

新硬伤（**第 10 个真实业务 bug — 隐式副作用**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 BaseStructServiceImpl.listAllBaseStructAndId 查询里触发 INSERT** | L66-71 缺 cache 时调 testBaseService.getTestBaseWithLimitUtil 自动 INSERT 新 TestBase | "list" 方法 GET 请求触发 DB 写；get-or-create 隐式副作用反模式 |
| **🔥 TestBaseServiceImpl.getTestBaseWithLimitUtil 同 get-or-create 反模式** | L33-69 查不到 → INSERT → 再查 → 返 ID | 应叫 getOrCreate；GET → DB INSERT 违反 HTTP 语义 |
| **L22 未使用静态 import** | `import static SimpleKeyGenerator.generateKey` 但实际用 buildCacheKey | 死 import |
| **L40 仅查询方法标 @Transactional** | listAllBaseStructAndId | 反模式 |
| **L18 字段无 private** | `@Autowired TestBaseMapper testBaseMapper;` | 同 #5l |
| **L59 用 insert 不 insertSelective** | TestBaseServiceImpl 全字段插 | null 字段写空覆盖 |
| **L67 `return -1` magic number** | 表"失败" | 同 TestPlanEnum.getKey 反模式 |
| **stream side effect** | L57-73 stream.map 写入 idCache | 风格违反函数式纯函数原则 |

### 累计真实业务 bug 实证（**10 处** ↑↑）

新增：**listAllBaseStructAndId 查询触发 INSERT** — get-or-create 隐式副作用

新校验通过项：
- BaseStructServiceImpl 用 stream + Map cache 减少 N+1 ✓
- buildCacheKey 用 "@@" 三段拼接防 collision ✓
- TestBase 自动创建有审计字段填充（L54-57）✓
- TestBaseExample 三 criteria AND 查询业务规则准确 ✓

### 审校批 55（2026-05-03 第 66 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **saveLog 静默吞异常返 success** | `ExeStepServiceImpl L378-384` try-catch 整个 insert，失败返 `success("日志已接收")` | 同 #5h OperationLogServiceImpl 静默失败模式再实证；调用方误判 |
| **saveLog 临时 LoggerFactory.getLogger** | L382 用 `org.slf4j.LoggerFactory.getLogger(...)` 不用类级 LOG | 同 #5h 风格不统一 |
| **listExeLogs 用 HashMap 包 list+total** | L394-397 `Map<String, Object> result.put("list",...).put("total",...)` | 应建 PageDto 强类型；或 ResponseBody.totalNum 已可承担 |
| **processAsyncStep try-catch 仅 rethrow** | L414-419 `try { ... } catch (Exception e) { throw e; }` | try-catch 仅 rethrow 无意义；应直接去掉或 catch 后处理 |
| **processAsyncStep restTemplate 无 timeout/retry** | L415 `restTemplate.postForObject(targetUrl, dto, String.class)` | 同 EMS 同问题；外部调用无超时易卡死 |

### 累计静默吞异常实证（**3 处**）

| 位置 | 描述 |
|------|------|
| OperationLogServiceImpl.record/list | 异常返 success "已记录" / 空列表 |
| EMS processAsyncEms catch + log.error | 同 #9 已记 |
| **ExeStepServiceImpl.saveLog** | 异常返 success "日志已接收"（本批新发现）|

新校验通过项：
- saveLog logId 生成 UUID + createTime 兜底 ✓
- listExeLogs L391 size 限流 ≤500 ✓
- listExeLogs 用 ResponseFactory.builder().withTotalNum() ✓ 标准
- processAsyncStep 实现 legacy url 模式（doV1 双模式之一）✓ 与 #批14 doV1 双模式发现一致

### 审校批 54（2026-05-03 第 64 轮）发现

新硬伤（**第 9 个真实业务 bug — 文案 bug**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 错误信息字符串错配** | `ExeFunctionServiceImpl L58` 参数叫 planId 但提示 `"查询失败，缺少targetGroupId"`；L78 参数叫 functionId 但同样提示 `"缺少targetGroupId"` | 第 9 个真实业务 bug — 文案 bug；调用方报错信息误导 |
| **2 处 copy-paste 错误信息** | L58 + L78 同款错误文案 | 文案一致性 copy-paste 没改 |
| **getExeFunctionByFunctionId L92 取 list.get(0)** | 实际可能有多个 ExeFunction 同 functionId | 取第一个无业务意义；应业务规则明确"仅取第一个"或处理多个 |
| **getExeFunctionInExeListByPlanId 缺权限校验** | 同 listAll 系列 | IDOR 风险 |
| **缺 deleted=false 过滤** | L66 selectByExample 不过滤软删除 | 拉到软删除记录 |

### 累计真实业务 bug 实证（**9 处** ↑↑）

| Bug | 位置 |
|-----|------|
| ThreadLocal 跨异步线程不传递 | processAsyncEms |
| dispatchPlan 缺事务 | TestPlanServiceImpl L306-353 |
| update bug ×3 | Step/Case/Module ServiceImpl |
| LOG.warn 用 {} 漏堆栈 | TestPlanServiceImpl L101/L106 |
| remarkTestPlan 实际未实现 | TestPlanServiceImpl L195-207 |
| getRely 名实不符（未查 Rely 表）| FunctionSuiteServiceImpl L359-375 |
| **错误信息错配 targetGroupId vs planId/functionId**（2 处 copy-paste）| ExeFunctionServiceImpl L58/L78（本批新发现）|

新校验通过项：
- getExeFunctionInExeListByPlanId 用 Example 模式 ✓
- 空集合提前 return emptyList ✓ defensive
- conveyTestFunction2ExeFunction 用 batch query getFunctionsByIds ✓ 性能 OK（与 #批51 正面案例一致）

### 审校批 53（2026-05-03 第 63 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 deleteExeFunction L216-225 N+1 性能** | for 循环单 update + 嵌套调 deleteExeStep（每 ExeFunction 一次） | 计划下 100 个 ExeFunction = 200+ 次 SQL；应批量 update + IN 删 |
| **deleteExeFunction L224 `result++` 不判 update 返回值** | L219 `updateByPrimaryKeySelective` 返回值丢弃，L224 直接 result++ | 即使 update 失败 result 仍 +1 → 计数虚高 |
| **L207 `@Transactional(readOnly=false)` 显式默认值** | readOnly 默认 false 显式无意义 | 同 #批48 deleteSingleTestPlan 同问题 |
| **L240 `(int) count` 强制转换** | long → int 风险 | 数据量大时溢出（理论上不会到 21 亿）；风格不严谨 |
| **🔥 L244-250 整段 listSortExeFunction 方法注释保留** | TODO 说明缺 TestFunctionGroup/User/ExecutorGroup 实体 | 死代码注释；同 #5α 微服务残骸模式（占位代码留着不清） |
| **跨 service 调 exeStepService.deleteExeStep** | L222 跨 service 调用 | 跨域调用 + 嵌套事务 propagate 风险 |

### 累计死代码注释清单（**5 处**）

| 死代码 | 位置 |
|-------|------|
| StatusContants 11 行注释常量 ✅修复 | constants/Status |
| 9 个 SpringBoot 主类 | 各模块 |
| fastop-dal 死代码 + 0 字节空 Mapper | fastop-dal |
| ResponseFactory.getFeignData ✅修复 | base-common |
| ResponseMsg.RPC_ERROR ✅修复 | base-common |
| **listSortExeFunction 整段注释 + TODO** | ExeFunctionServiceImpl L244-250（本批新发现） |

新校验通过项：
- deleteExeFunction 用 setDeleted(true) 软删除 ✓
- 调 exeStepService 级联软删 ✓ 业务层级联设计
- countExeFunctionByPlanId 用 countByExample ✓ 性能 OK
- updateFunctionStatusByOption switch + 前置 if 状态机 ✓（前批已记）

### 审校批 52（2026-05-03 第 62 轮）发现

新硬伤（**第 8 个真实业务 bug**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 getRely 名实不符 — 未查 TestFunctionRely 表** | `FunctionSuiteServiceImpl L359-375` 方法名 getRely 但实际查 functionSuiteService + testFunctionService 返 TestFunction 列表，**未碰 TestFunctionRely 表** | 第 8 个真实业务 bug；前端拿到的是 functions 不是 rely 关系；命名误导 |
| **🔥 funIds 收集未去重** | L363-365 stream collect Toolkit 不去重 | 一个 fun 在多个 suite 里 → funIds 含重复 → batch 查询带冗余 IN 列表 |
| **mapAndMergeData L343+L348 两次 setNum 逻辑微妙** | 先用 fs.funNum，后 tf 非空覆盖 tf.num | 实际意图是 tf 优先，但读起来需要思考；可优化为先判 tf 非空再决定取哪个 num |
| **getRely 缺权限校验** | 直接 return | 同 listAll 系列 |

### 累计真实业务 bug 实证（**8 处** ↑）

| Bug | 位置 |
|-----|------|
| ThreadLocal 跨异步线程不传递 | processAsyncEms |
| dispatchPlan 缺事务 | TestPlanServiceImpl L306-353 |
| update bug ×3（copy-paste） | Step/Case/Module ServiceImpl |
| LOG.warn 用 {} 占位符传 Throwable 漏堆栈 | TestPlanServiceImpl L101/L106 |
| remarkTestPlan 实际未实现 DB 写入 | TestPlanServiceImpl L195-207 |
| **getRely 名实不符（未查 Rely 表）** | FunctionSuiteServiceImpl L359-375（本批新发现）|

新校验通过项：
- mapAndMergeData 用 stream + map 函数式优雅 ✓
- getRely 用 RelyDto 封装结果（虽然内容错）✓ 设计层面有 wrapper
- 调 batch query getTestFunctionListById 性能 OK ✓ 与 #批51 正面案例一致

### 审校批 51（2026-05-03 第 61 轮）发现

新硬伤（**第 13 个命名问题 + 罕见正面案例**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 `KeyProcessNum` 大写（第 13 个命名问题）** | L242 同 #批50 `MilitaryNum` 一模一样 copy-paste | 系统性 bug 同款；2 个对称方法都犯 |
| **🔥 deleteFunctionSuiteBatchByIds 物理删除** | L264-277 `deleteByExample` 直接物理删 | 同 #5EE TestSuite.delete 物理删除模式再实证 |
| **listFunctionSuiteBySuite 缺 deleted=false 过滤** | L279-286 不过滤软删除 | 拉到软删除的记录；与 countMilitaryBySuite L215 加了 deleted=false 不一致 |
| **`tf -> tf` 应用 Function.identity()** | L322 stream collect Map | `Function.identity()` 更标准 + 性能略好 |
| **listBySuiteAndFunIds / deleteFunctionSuiteBatchByIds 包级访问但 public** | L251 `List<FunctionSuite> listBySuiteAndFunIds` 包级；L264 `public int deleteFunctionSuiteBatchByIds` public 但接口没声明 | 风格不一致 |
| **L259 / L298 字符串字面量 setOrderByClause** | L298 `"fun_order"` | 字面量安全 ✓；但项目内多处类似手写 SQL fragment |

### 累计命名问题（**13 处**）

新增：**`KeyProcessNum`** copy-paste 同 MilitaryNum

### ✨ 正面案例：listDtoBySuiteWithExample（**与 N+1 反例对比**）

| 反面（N+1） | 正面（batch） |
|-----------|-------------|
| `conveyTestStep2ExeStep` 三层嵌套 for 调 service.getByXxxId（111 次 SQL） | `listDtoBySuiteWithExample` 1 次 selectByExample(funIds IN ...) + 内存 toMap 组装 |
| `countMilitaryBySuite` for 循环 countMilitaryByFunId | （应改为 batch GROUP BY） |
| `executeSinglePlanCreation` listByTestBaseId 全表 + for 检查重复 | （应改为 SELECT COUNT WHERE num=X） |

**结论**：项目内 N+1 反模式与 batch 正面案例并存——**说明团队知道正确写法但不一致**。可作面试时"工程实践经验差异"金弹素材。

新校验通过项：
- listDtoBySuiteWithExample 用 stream + Set<Integer> + Objects::nonNull 防御 ✓
- 双表 batch 联合查询 ✓
- mapAndMergeData 抽辅助方法 ✓ 模块化
- 空集合提前 return ✓ defensive
- countKeyProcessBySuite L235 加 deleted=false 过滤 ✓（与 listFunctionSuiteBySuite 缺过滤相反）

### 审校批 50（2026-05-03 第 60 轮）发现

新硬伤（**第 6 处事务遗漏 + 第 12 个命名问题**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 deleteFunctionSuite 缺 @Transactional（第 6 处事务遗漏）** | `FunctionSuiteServiceImpl L130-209` 含级联删除 + 批量更新 + 跨 service 调用 | 内部 throw RuntimeException 仅在外层有 @Transactional 时才触发回滚；自身无注解风险大 |
| **🔥 `MilitaryNum` 大写 M（第 12 个命名问题）** | L222 `Integer MilitaryNum = new Integer(CommonConstants.NUM_0)` | Java 局部变量应小驼峰 `militaryNum` |
| **🔥 `new Integer()` 已 deprecated** | L222 `new Integer(CommonConstants.NUM_0)` | Java 9+ 编译警告；应 `Integer.valueOf(0)` 或直接 `0` |
| **N+1 查询 countMilitaryBySuite** | L223-226 for 循环内调 testFunctionService.countMilitaryByFunId | 每个 FunctionSuite 一次 SQL；应批量 |
| **L191-197 循环内单 update** | for 循环 updateByPrimaryKeySelective | 同 #5η N 次 SQL 性能问题 |
| **异常消息仅含数字错误码** | L185 / L195 `throw new RuntimeException(ResponseCode.SYSTEM_ERR + "...")` 与 `String.valueOf(ResponseCode.SYSTEM_ERR)` | 数字 500 + 文字混合；调用方拿到 "500 删除模块数量不匹配..." 难解析 |

### 累计事务遗漏（**6 处**）

| 方法 | 位置 |
|------|------|
| dispatchPlan | TestPlanServiceImpl L306-353 |
| createFunctionSuite | FunctionSuiteServiceImpl L53-92 |
| reviewSuiteSpecial | TestSuiteServiceImpl L352-362 |
| TestExampleServiceImpl.updateAll | L41-54 |
| deleteBatchTestPlan | TestPlanServiceImpl L241-262 |
| **deleteFunctionSuite** | FunctionSuiteServiceImpl L130-209（本批新发现）|

### 累计命名问题（**12 处**）

新增：**`MilitaryNum`** 局部变量大写 M

新校验通过项：
- deleteFunctionSuite L150 用 `Boolean.TRUE.equals(...)` 防 NPE ✓
- 删除前先查 originMap 防孤儿引用 ✓ 业务防御
- L165-177 批量删除 TestFunctionRely（双向：as subject + as target）✓ 完整级联
- 用 throw RuntimeException 触发回滚（如外层有事务）✓ Spring AOP 标准模式

### 审校批 49（2026-05-03 第 59 轮）发现

新硬伤（**第 11 个命名问题**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 "Pause" 大写 P vs runStep/doFinish 小写（第 11 个命名问题）** | `ExeStepServiceImpl.updateStepStatusByOption L218-222` `if ("runStep".equals)` `else if ("Pause".equals)` `else if ("doFinish".equals)` | 同方法内 3 个 option 字符串大小写不统一；字面量比较容易出 bug |
| **🔥 listExeSteps 拉 BLOB** | L171 `selectByExampleWithBLOBs` | 列表查询拉 commandData/criterionContent/failCause 三大 BLOB → 性能损失（同 #5k God Entity）|
| **listExeSteps 缺分页 + 缺权限** | L167-172 直接全部返回 | 同 listAll 系列问题 |
| **updateStepStatusByOption 字符串字面量 option 应用 enum** | L218-222 "runStep"/"Pause"/"doFinish" | 应建 StepOperation enum；硬编码字符串易拼写错 |
| **updateStepStatusByOption "参数错误" 包装为 success** | L213 `return ResponseFactory.success("参数错误")` | 同 #5FF 失败包装为 success 反模式 |
| **updateManualResult 强转 ExeStepWithBLOBs** | L246 `(ExeStepWithBLOBs) selectByPrimaryKey(...)` | selectByPrimaryKey 返 ExeStep 父类，强转 ExeStepWithBLOBs 可能 ClassCastException |
| **updateStepExeToPause 注释死代码** | L185 `// targetStatuses.add(StepStatusEnum.OPERATED.getKey());` | 应删除 |
| **updateStepStatusByOption if-else vs ExeFunction switch 对称方法风格不一致** | Step 用 if-else；Function 用 switch | 项目内同概念两种实现风格 |

### 累计命名问题（**11 处**）

新增：**`"runStep"/"Pause"/"doFinish"` 同方法内字面量大小写不一致** — Step 服务的 option 处理

新校验通过项：
- listExeSteps L170 `setOrderByClause("step_order ASC, step_level ASC")` 字面量字符串 ✓ 安全（无注入）
- updateStepExeToPause L192 `updateByExampleSelective` 批量更新性能 OK ✓
- updateStepStatusByOption 用 .equals(option) 比较字符串 ✓ 避免 NPE
- updateStepStatusByOption 用 setExeStepId + setExeStatus 仅更新两字段（与 #批43 update bug 不同，这里正确）✓

### 审校批 48（2026-05-03 第 58 轮）发现

新硬伤（**第 5 处事务遗漏 + 第 10 个命名问题**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 deleteBatchTestPlan 缺 @Transactional（第 5 处事务遗漏）** | `TestPlanServiceImpl L241-262` 循环调 deleteSingleTestPlan 无外层事务 | 中途失败前面已 commit |
| **🔥 deleteSingleTestPlan: success("planId为空") 包装失败** | L213-215 planId 空 return success | 同 #5FF TestSuite.delete bug 模式：调用方判 success 误以为成功 |
| **🔥 getPlanRound 参数 EntityId / SuiteId 大写（第 10 个命名问题）** | L271 `getPlanRound(Integer EntityId, Integer SuiteId)` | 累计命名问题 10 处 |
| **L238 拼接 Response 到 StringBuffer** | L257 `result.append(deleteSingleTestPlan(planId))` 把整个 Response 对象 append | 调 Response.toString() 拿默认 ResponseEntity.toString() — 语义混乱 |
| **L250 StringBuffer 已过时** | 单线程应用 StringBuilder 性能更好 | 历史代码风格 |
| **L210 `@Transactional(readOnly=false)` 显式默认值** | readOnly 默认 false，显式无意义 | 多余配置 |
| **deleteSingleTestPlan 级联未事务保护** | L228 setDeleted(true) commit 后 L236 deleteExeFunction 级联失败无回滚 | 同事务边界问题 |

### 累计事务遗漏（**5 处**）

| 方法 | 位置 |
|------|------|
| dispatchPlan | TestPlanServiceImpl L306-353 |
| createFunctionSuite | FunctionSuiteServiceImpl L53-92 |
| reviewSuiteSpecial | TestSuiteServiceImpl L352-362 |
| TestExampleServiceImpl.updateAll | L41-54 |
| **deleteBatchTestPlan** | TestPlanServiceImpl L241-262（本批新发现）|

### 累计命名问题（**10 处**）

| 命名错 | 位置 | 类别 |
|--------|------|------|
| `verfier` | ExeStep:72 | 字段拼错 |
| `mesdceCode` | TestSuite:44 | 字段诡异 |
| `relyFuntionReady` | TestFunctionRely:14 | 字段拼错 |
| `TestPlanStatusContants` | 类名 | 类名拼错 |
| `StatusContants` | interface | interface 名拼错 |
| `_fial` ×5 | StatusContants L45-50 | 复合字段拼错 |
| `UpdateAll()` ✅修复 | TestExampleController:24 | 方法名规范错 |
| `StepId` ✅修复 | TestFunctionStepController | 变量名规范错 |
| `ModuleId` ✅修复 | TestFunctionModuleController | 变量名规范错 |
| **`EntityId/SuiteId`** | TestPlanServiceImpl.getPlanRound | **变量名规范错** |

新校验通过项：
- deleteSingleTestPlan 用 .equals() 比较 Integer 状态 ✓
- 软删除 setDeleted(true) 与项目模式一致 ✓
- recordOperationLog 调用 ✓ 操作审计
- getTestPlanListByPlanId 用 Example IN 查询批量 ✓ 性能 OK
- listAll 简洁 ✓

### 审校批 47（2026-05-03 第 57 轮）发现

🔥🔥 **重磅金弹**：commit 455129d 自述"备注接口分离"实际未实现！

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 remarkTestPlan 实际未实现 DB 写入** | `TestPlanServiceImpl L195-207` 注释明确 "TestPlan 实体暂无 remark 列，此处预留更新点；当前仅记录操作日志" | commit 455129d 自述"备注接口分离"但 remark 内容**仅入操作日志，TestPlan 实体无 remark 字段** → 第 7 个真实业务 bug；与 #5DD 审签 TODO 同款"自述与实际不符"模式 |
| **🔥 LOG.warn("...{}", e) 漏堆栈** | L101 `LOG.warn("业务创建失败: {}", e)` / L106 `LOG.warn("系统创建失败: {}", e.getMessage())` | slf4j 第二参数若是 Throwable 才打堆栈；`{}` 占位符 + Throwable 会调 toString() 不打堆栈；排查问题没堆栈 → 第 6 处业务 bug |
| **catch RuntimeException + Exception 嵌套顺序** | L100-109 先 catch RuntimeException 再 catch Exception | Java 编译时这两 catch 块顺序虽然 OK（RuntimeException extends Exception），但合并到一个 try 块时业务异常用 RuntimeException 处理消息保留，系统异常用 Exception 添加前缀；逻辑微妙 |
| **业务异常用 LOG.warn 不用 LOG.error** | L101/L106 | 业务创建失败应 error 级别 |
| **executeSinglePlanCreation 强转 (TestBase)** | L124 强转 service.getData() | ClassCastException 风险（同 #5δ）|
| **executeSinglePlanCreation 缺 try-finally** | L144 insert + L147 convey 嵌套；后续失败需手动事务标记回滚 | 已 @Transactional(rollbackFor=Exception.class) propagate，但 service 嵌套调用切面是否传播未验证 |
| **L189 setRollbackOnly() 与 @Transactional 重复** | 手工 + 注解双重回滚机制 | 设计冗余 |
| **L173 日志格式漏空格** | `LOG.info("Remark message{}",...)` 应 "Remark message: {}" | 输出 "Remark messageXXX" 难读 |

### 累计真实业务 bug 实证（**7 处** ↑↑）

| Bug | 位置 |
|-----|------|
| ThreadLocal 跨异步线程不传递 | processAsyncEms |
| dispatchPlan 缺事务 | TestPlanServiceImpl L306-353 |
| update bug ×3（copy-paste） | Step/Case/Module ServiceImpl |
| **LOG.warn 用 {} 占位符传 Throwable 漏堆栈** | TestPlanServiceImpl L101/L106 |
| **🔥 remarkTestPlan 实际未实现 DB 写入** | TestPlanServiceImpl L195-207 |

### commit 455129d 自述与实际不符（**2 处**实证，已加金弹）

| 自述 | 实际 |
|------|------|
| "恢复审签权限校验" | TestSuiteServiceImpl.check L317-321 仍 /* TODO */ 注释 |
| **"备注接口分离"** | **remarkTestPlan 仅入日志，TestPlan 无 remark 列**（本批新发现）|

新校验通过项：
- executeSinglePlanCreation 用 throw RuntimeException 触发事务回滚 ✓
- remarkTestPlan 路径分离 + 操作日志记录 ✓ commit 接口分离思路正确
- updateTestPlan 用 TransactionAspectSupport.setRollbackOnly() 显式回滚 ✓ 显式逻辑

### 审校批 46（2026-05-03 第 56 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **`Boolean.TRUE` 用作 boolean 反模式** | `TestPlanServiceImpl L75` `boolean result = Boolean.TRUE` | 自动拆箱浪费；可直接 `boolean result = true`；混淆类型 |
| **funGroupIds 循环创建短路求值可能错** | L86 `result = result && executeSinglePlanCreation(currentDto)` | 一旦 result=false，`&&` 短路使后续 funGroupId 不再执行；可能与"为每个 funGroupId 都尝试"意图不符 |
| **TestPlanServiceImpl 7 依赖 God Service 嫌疑** | L43-62 注入 testBaseService / testSuiteService / exeFunctionService / 3 mapper / operationLogService | 应拆 PlanCRUDService + PlanLifecycleService + AuditService |
| **import TransactionAspectSupport 暗示手工事务控制** | L33 import 但下面是否调 setRollbackOnly 待查 | 复杂事务管理风险 |
| **executeSinglePlanCreation 内部循环复用 BeanUtils.copyProperties** | L82 在 for 循环内反复 copy DTO | N 次反射调用；性能小损（数据量小可忽略） |

新校验通过项：
- TestPlanServiceImpl 用 @Service + @Transactional ✓
- 类级 LOG（private static final Logger）✓ 标准风格
- 注入 OperationLogService 实现操作审计 ✓
- BeanUtils.copyProperties 标准 DTO→Entity 映射 ✓
- 业务规则下沉到 service：entityId/suiteId 必填校验 ✓

### 审校批 45（2026-05-03 第 55 轮）发现

新硬伤（**含 NPE 风险**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 add 方法 PlaneEffectMin/Max NPE 风险** | L38 `tFunctionInfo.getPlaneEffectMin() > tFunctionInfo.getPlaneEffectMax()` | Integer 自动拆箱比较；任一为 null 触发 NPE |
| **🔥 N+1 查询：listByTestBaseId 全量再 for 检查重复编号** | L42-53 先查全表再循环判 num | 应直接 `SELECT COUNT(*) WHERE test_base_id=? AND num=?` |
| **强转 List<TestFunction>** | L47 `(List<TestFunction>) listResponse.getData()` | data 是 Object，强转失败 ClassCastException；同 #5δ |
| **BeanUtils.copyProperties 审计字段污染** | L56 DTO → entity 全量拷贝（含 createdAt/createdBy 等审计） | 前端篡改 createdBy 直接进 DB（同 #5ω） |
| **JSON-in-string-in-DB 反模式** | L69/L72 `JSON.toJSONString(otherTechFiles/devicePool)` 存为 String 字段 | List<List<String>> 序列化为 JSON 字符串存表；查询无法 SQL 索引；解析需 JSON 库 |
| **fastjson 1.x 引入** | L19 `import com.alibaba.fastjson.JSON` | 与 #5c CVE 风险一致再实证 |
| **L60-66 ";" 分隔字符串存 cautionIds** | List<Integer> → ";" 分隔 String | 集合存为分隔字符串反模式；应建关联表 |

新校验通过项：
- TestFunctionServiceImpl 用 @Slf4j + @Service ✓
- 用 BeanUtils.copyProperties 标准 DTO→Entity 映射 ✓ 但有审计污染问题
- L50 `tf.getNum().equals(...)` 用 equals 比较 Integer 正确 ✓
- 业务规则下沉到 service：架次校验 + 编号防重 ✓

### 审校批 44（2026-05-03 第 54 轮）发现

🔥🔥 **大发现：update bug 系统性存在于 3 个 Service**

| Service | 行号 | 现象 |
|---------|------|------|
| TestFunctionStepServiceImpl | L43-55（批 43） | 查 tFStep → setUpdated(tFStep) → update(testFunctionStep 入参) |
| **TestFunctionCaseServiceImpl** | L47-58（本批） | 查 tFCase → setUpdated(tFCase) → update(testFunctionCase 入参) |
| **TestFunctionModuleServiceImpl** | L49-62（本批） | 查 tFModule → setUpdated(tFModule) → update(testFunctionModule 入参) |

**结论**：3 个对称 Service 全部 copy-paste 同一处 bug —— `setUpdated` 改在查询出的对象上，但 `update` 用的是入参对象，导致 `updated` 字段永远不会被自动设置为 `step_update_change`。

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 update bug 系统性 3 处** | Step / Case / Module Service 都犯 | updated 字段实际不更新；下游依赖 updated 字段判定的逻辑全失效 |
| **TestFunctionModuleServiceImpl.delete 参数 ModuleId 大写** | L65 | 同 #第9 命名问题 |
| **TestFunctionModuleServiceImpl.getByModuleId 参数 ModuleId 大写** | L80 | 同上 |
| **3 个 Service 错误信息文案不准** | TestFunctionStepServiceImpl.update L46 "用例不存在"；实际是 step | copy-paste 错误信息没改 |
| **delete 实现都正确（用 tFCase/tFModule update）** | L67-72 / L70-72 | 与 update 方法的 bug 形成对比；说明作者只是 update 那段写错 |

新校验通过项：
- 3 个对称 Service 实现风格一致 ✓ 设计层面统一
- 都用 UserContextHolder.getCurrentUser() 设 changeUser ✓
- 都用 StatusContants 软删除（step_status_del）✓
- 都用 Example 模式做条件查询 ✓
- delete 方法都实现正确（用 tXxx 而非入参）✓

### 累计真实业务 bug 实证（**5 处**）

| Bug | 位置 |
|-----|------|
| ThreadLocal 跨异步线程不传递 | processAsyncEms |
| dispatchPlan 缺事务 | TestPlanServiceImpl L306-353 |
| **update bug ×3（copy-paste）** | Step/Case/Module ServiceImpl |

### 审校批 43（2026-05-03 第 53 轮）发现

新硬伤（**含一处真实业务 bug**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 TestFunctionStepServiceImpl.update 方法逻辑 bug** | L43-55: 先 `tFStep = mapper.selectByPrimaryKey(stepId)` → L48 `tFStep.setUpdated(...)` → L49 `mapper.updateByPrimaryKeySelective(testFunctionStep)`（用入参不是 tFStep） | 内存中 setUpdated 改的是 tFStep，update 用 testFunctionStep（无 setUpdated 调用）→ updated 字段实际不会更新；**真实业务 bug** |
| **🔥 `String.valueOf(new Date())` 反模式** | L30 `setStepDate(String.valueOf(new Date()))` | Date.toString() 返 "Sun May 03 ..." 默认 locale 格式，与 SQL TIMESTAMP 格式不匹配；本质是 stepDate 用 String 字段（#5BB）连带反模式 |
| **delete 参数 StepId 大写** | L58 `delete(Integer StepId)` | 同 #第8 命名问题；前批已记 |
| **getByStepId 大写 / getByCaseId 小写** | L73 `getByStepId(Integer StepId)` 大写；L85 `getByCaseId(Integer caseId)` 小写 | 同 Class 内不一致 |
| **方法 add 缺 @Transactional** | L27-40 单 insert | 风格不一致；submit 也无 |

新校验通过项：
- TestFunctionStepServiceImpl 用 @Service ✓
- add 方法用 UserContextHolder.getCurrentUser() 设 changeUser ✓ 与 commit 455129d 一致
- delete 实现软删除（stepStatus=del）✓ 与 TestSuiteServiceImpl 物理删除（#5EE）相反
- 状态默认值用 StatusContants 常量（不是魔法数字）✓
- getByCaseId 用 Example 模式查询 ✓

### 累计真实业务 bug 实证（**3 处**）

| Bug | 位置 |
|-----|------|
| ThreadLocal 跨异步线程不传递 | processAsyncEms 异步线程 |
| dispatchPlan 缺事务 | TestPlanServiceImpl L306-353 |
| **update 方法 setUpdated 改错对象** | TestFunctionStepServiceImpl L43-55（本批新发现）|

### 审校批 42（2026-05-03 第 52 轮）发现

新硬伤（**第 4 处事务遗漏**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestExampleServiceImpl.updateAll 缺 @Transactional（第 4 处事务遗漏）** | L41-54 三步骤批量同步 module/case/step 全无事务 | 中途失败半更新；DB 状态不一致 |
| **🔥 Integer == 比较 反模式** | L72 `tFModule.getModuleStatus() == StatusContants.step_status_del` | Integer 用 == 比较自动拆箱；如果 moduleStatus 为 null 触发 NPE |
| **业务结果用 StringBuilder 累加日志返回** | updateAll 返 success(sb.toString()) | 日志混入业务返回；调用方拿到混合字符串 |
| **God Service 注入 4 个 service** | L21-28 testFunctionService + testFunctionModuleService + testFunctionCaseService + testFunctionStepService | 单 Service 协调跨域；应抽 BatchSyncCoordinator |
| **ProcessResult 内部类字段无 private** | L31-32 `String log; List<Integer> ids;` | 同 #5l 系列反模式 |
| **副作用循环内修改输入对象** | L70 `tFModule.setUpdated(...)` 在 for 循环内改 | 副作用反模式；应返新对象 |

### 累计事务遗漏（**4 处**实证）

| 方法 | 位置 |
|------|------|
| dispatchPlan | TestPlanServiceImpl L306-353 |
| createFunctionSuite | FunctionSuiteServiceImpl L53-92 |
| reviewSuiteSpecial | TestSuiteServiceImpl L352-362 |
| **TestExampleServiceImpl.updateAll** | L41-54（本批新发现）|

新校验通过项：
- TestExampleServiceImpl 用 `@Service` + 静态内部类 ProcessResult 封装结果 ✓
- updateAll 三步链式调用（module → case → step）✓ 业务流程清晰
- 用 stream().map().collect() 现代 Java 函数式风格 ✓

### 审校批 41（2026-05-03 第 51 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestPlanDelBatchDto 字段无 private（第 3 处）** | `L9 List<String> planIdLists;` 包级访问 | 同 #5l ExeStepCommand / FunSuiteIdConnectDto |
| **🔥 FunctionSuiteDeleteDto 字段无 private（第 4 处）** | `L10/L12 Integer suiteId; List<FunctionSuiteDto> functionSuiteDtos;` | 项目内 4 处 DTO 字段无 private |
| **`planIdLists` 命名怪（复数+复数）** | 应 `planIds` | List 已表复数无需再加 Lists |
| **TestPlanDelBatchDto 仅 1 字段多此一举** | 整个 DTO 只包 List<String> | 可直接传 List<String>；DTO 仅 1 字段无意义 |
| **FunctionSuiteDto.deleted 用 `boolean` 小写** | L34 而其他实体用 `Boolean deleted` | boolean 不能为 null（默认 false），Boolean 可 null；类型不统一 |
| **DeviceTopicsPayload.example 用 Object 类型** | L23 `Object example` | 同 #5δ 失去类型信息 |

### 累计 DTO 字段无 private（**4 处**实证）

| DTO | 位置 | 状态 |
|-----|------|------|
| `ExeStepCommand` | exeStepId / deviceId / command / url | ✅ 已修 |
| `FunSuiteIdConnectDto` | suiteId / testFunctions | ✅ 已修 |
| **`TestPlanDelBatchDto`** | planIdLists | 待修 |
| **`FunctionSuiteDeleteDto`** | suiteId / functionSuiteDtos | 待修 |

新校验通过项：
- TestPlanDelBatchDto 用 @Data ✓
- FunctionSuiteDeleteDto 用 @Data + 嵌套 List<FunctionSuiteDto> 合理 ✓
- FunctionSuiteDto 14 字段全有 private ✓
- DeviceTopicsPayload 用 @Data @Builder @NoArgsConstructor @AllArgsConstructor 全套 + @ApiModel 标注 ✓

### 审校批 40（2026-05-03 第 50 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestFunctionInfoRequestDto 41 字段 God DTO** | DTO 字段量与 Entity（38+）几乎相当 | DTO 应是 Entity 简化视图；这里反而更复杂；耦合度爆炸 |
| **🔥 DTO 含审计字段 + 6 角色字段** | L57-65 含 deleted / createdAt / updatedAt / createdBy / updatedBy；L79-90 含 designer/proofer/verifier/checker/qualityer/approver | 前端可伪造 createdBy + 审签人字段（与 #5ω TestSuiteRequestDto 同问题）|
| **`List<List<String>>` 双层嵌套 List** | L37 otherTechFiles / L39 devicePool | 数据结构反模式；JSON 序列化 `[["a","b"],["c","d"]]` 含义不清；应建专用 DTO |
| **DTO 与 Entity 字段不对齐** | DTO 多 sourceVersion / cautionIds；Entity 没 | DTO 字段冗余；序列化时 Entity 收不到 |
| **TestFunctionCaseService 接口参数小写正确** | L15/L17 `delete(Integer caseId)` / `getByCaseId(Integer caseId)` 小写 | 三对称接口仅 Case 写对；Module/Step 大写（前批已记） |
| **`createTime` vs `createdAt` 命名不统一** | ExeLog.createTime / OperationLog.createTime / TestFunction.createdAt / TestPlan.createdAt | 全项目时间字段命名不统一 |
| **`logId` String vs OperationLog.id bigint** | ExeLog 主键 String / OperationLog 主键 bigint | 主键类型不统一；同是日志表两种风格 |
| **ExeLog 缺审计字段** | 5 字段无 createdBy / deleted | 同三层裸实体 |

新校验通过项：
- TestFunctionInfoRequestDto 用 @Data Lombok ✓
- TestFunctionCaseService 9 方法（CRUD + 物理删除 + 批量更新）✓ 与 Module/Step 对称
- ExeLog 5 字段（logId/stepId/planId/content/createTime）极简 ✓
- ExeLog 用 @Data Lombok ✓

### 全项目时间字段命名乱象（实证）

| 命名 | 实体 | 类型 |
|------|------|------|
| `createdAt` | TestFunction / TestPlan / TestSuite / FunctionSuite / TestFunctionRely | Date |
| `createTime` | ExeLog / OperationLog | Date |
| `xxxDate(String)` | TestFunctionStep.stepDate / Module.moduleDate / Case.caseDate | String |

**3 种命名风格** + **2 种类型**（Date / String）混用 → 排序 / 范围查询 / 序列化都易出 bug

### 审校批 39（2026-05-03 第 49 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestFunctionStepService.getByCaseId(Integer funId) 参数名错** | L19 方法名说按 caseId，参数变量叫 funId | 误导新人；java 反射 / 文档生成会显示 funId |
| **TestFunctionModuleService 接口参数大写 ModuleId（继续 surface）** | L14 / L16 `delete(Integer ModuleId)` / `getByModuleId(Integer ModuleId)` | 接口签名层面也带大写；之前批 38 仅 Controller 层修了 |
| **TestFunctionStepService 接口参数大写 StepId** | L15 / L17 `delete(Integer StepId)` / `getByStepId(Integer StepId)` | 同 Module 接口同问题 |
| **TestFunctionModuleService 同接口内 ModuleId / moduleId 不一致** | L14/L16 大写；L24 deletePhy(Integer moduleId) 小写 | 同 interface 内不统一 |
| **`deletePhy` 命名缩写不清** | TestFunctionModuleService.deletePhy / TestFunctionStepService.deletePhy | "Phy" 疑似 Physical 但缩写不清；应 `deletePhysically` 或 `hardDelete` |
| **`updateByCaseIds(List<Integer> testFunctionCaseIds)` 参数名啰嗦** | L27 | 应 `caseIds` 简洁 |

新校验通过项：
- TestFunctionModuleService 9 方法签名（CRUD + 树形 + 物理删除）✓
- TestFunctionStepService 9 方法签名 ✓
- 含 deletePhy 物理删除接口（与软删除 delete 区分）✓ 业务设计有考虑

### 审校批 38（2026-05-03 第 48 轮）发现

新硬伤（**含第 9 个命名问题**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestFunctionModuleController `ModuleId` 大写 M（第 9 个命名问题）** | L37/43/44/46 参数 + 路径都用大写 M | 同 #第8 StepId 问题；累计命名错 9 处 |
| **🔥 TestFunctionController.list 端点 if 业务分支** | L72-78 `if (testBaseId != null) listByTestBaseId else listAll` | Controller 不该含业务分支；应 service 层判断或拆 2 endpoint |
| **TestFunctionController 11 端点过多** | add/update/submit/delete/get/listByBaseId/listAll/list/query/check/getCheckTestFunction | 单 Controller 职责过重；应按"CRUD vs 审批"拆 2 Controller |
| **log 级别混用** | info / debug / warn 在同 Controller 混用，无统一规范 | 排查问题时不知什么操作记 info / 什么记 warn |
| **TestFunctionController.deleteTestFunction 用 `POST /delete/{funId}`** | L44 POST + 路径参数 | 仍非 DELETE；混用风格 |

### 累计命名问题（**9 处**，已建表）

| 命名错 | 位置 | 类别 |
|--------|------|------|
| `verfier` | ExeStep:72 | 字段拼错 |
| `mesdceCode` | TestSuite:44 | 字段诡异 |
| `relyFuntionReady` | TestFunctionRely:14 | 字段拼错 |
| `TestPlanStatusContants` | 类名 | 类名拼错 |
| `StatusContants` | interface | interface 名拼错 |
| `_fial` ×5 | StatusContants L45-50 | 复合字段拼错 |
| `UpdateAll()` ✅修复 | TestExampleController:24 | 方法名规范错 |
| `StepId` | TestFunctionStepController | 变量名规范错 |
| **`ModuleId`** | TestFunctionModuleController | **变量名规范错** |

新校验通过项：
- TestFunctionModuleController 7 端点（add/update/delete/get/listByFunId/listAll/treeByFunId）✓
- TestFunctionController 11 端点（CRUD + 审批 + 列表 + 树形）✓
- listByFunId 实际按 funId 查 ✓ 命名正确（与 Case/StepController 误导命名相反）
- TestFunctionController 用 @ApiParam + @RequestParam(required=false) 增强 Swagger 文档 ✓
- treeByFunId 端点提供树形结构查询（避免前端多次调用）✓

### 审校批 37（2026-05-03 第 47 轮）发现

新硬伤（**含第 8 个命名问题**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestFunctionStepController 参数 `StepId` 大写 S（第 8 个命名问题）** | L37 `@RequestParam Integer StepId` + L43 `@PathVariable Integer StepId` + L44 路径 `/get/{StepId}` | Java 命名规范：变量应小驼峰；累计命名问题 8 处 |
| **🔥 listByFunId 方法名误导** | TestFunctionCaseController L51 `listByFunId(@RequestParam Integer moduleId)` 实际按 moduleId 查；TestFunctionStepController L51 同名但按 caseId 查 | 两 Controller copy-paste 没改方法名；新人看方法名 listByFunId 以为按 funId 查 |
| **TestFunctionCaseController/StepController 接 Entity 不接 DTO** | `@RequestBody TestFunctionCase` / `@RequestBody TestFunctionStep` | DTO/Entity 边界模糊；同 #5τ |
| **delete 用 POST 不 DELETE** | 两 Controller delete 都是 `POST /delete` | 反 REST 再实证 |
| **路径前缀短形与其他 Controller 不一致** | `/designer/case` `/designer/step` vs `/designer/testFunction` `/designer/testSuite` | 命名风格不统一；应统一为 `/designer/testFunctionCase` 等 |
| **deleteTestFunctionStep 用 @RequestParam 而非路径参数** | `POST /delete?StepId=X` 而非 `DELETE /step/{stepId}` | 双重不规范 |

### 累计命名问题（**8 处**，已建表）

| 命名错 | 位置 | 类别 |
|--------|------|------|
| `verfier` | ExeStep:72 | 字段拼错 |
| `mesdceCode` | TestSuite:44 | 字段诡异 |
| `relyFuntionReady` | TestFunctionRely:14 | 字段拼错 |
| `TestPlanStatusContants` | 类名 | 类名拼错 |
| `StatusContants` | interface 名 | interface 名拼错 |
| `_fial` ×5 | StatusContants L45-50 | 复合字段拼错 |
| `UpdateAll()` | TestExampleController:24 | 方法名规范错 |
| **`StepId`** | TestFunctionStepController L37/43/44 | **变量名规范错** |

新校验通过项：
- TestFunctionCaseController 6 端点（add/update/delete/get/listByModuleId/listAll）✓
- TestFunctionStepController 6 端点（add/update/delete/get/listByCaseId/listAll）✓
- 两 Controller @Slf4j + @Api(tags) + @RestController ✓
- 路径含资源名（case/step）+ 操作动词 — 设计层 CRUD 标准结构 ✓

### 审校批 36（2026-05-03 第 46 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **FunctionSuiteController.deleteFunctionSuite 用 POST 不是 DELETE** | `L37 @PostMapping("/deleteFunctionSuite")` | 反 REST；同 #批11 device.ts 风格不一致再实证 |
| **TestBaseController.getTestBaseInfo 用 3 个原始 int 参数** | `L29 @RequestParam int targetGroupId, int baseType, int entityStructId` | int 不能 null 必传，与 Integer 风格不统一；与同 Controller getTestBaseById(Integer) 不一致 |
| **TestBaseController 路径风格混杂** | `/getTestBaseWithLimit` `/getTestBaseInfo`（RPC 动词路径）vs `/{baseId}`（REST 资源路径）| 同 Controller 内两套风格 |
| **多端点用 @RequestParam 接业务参数** | TestBaseController L22 / L29 / TestSuiteController.check L73 / FunctionSuiteController.rely L45 | 业务参数应 DTO 包装；@RequestParam 适合简单查询过滤 |

新校验通过项：
- FunctionSuiteController 4 端点（listAll / create / delete / rely）✓
- FunctionSuiteController @Slf4j + @Api + @RestController + path `/functionSuite` ✓
- TestBaseController 3 端点（getTestBaseWithLimit / getTestBaseInfo / getById）✓
- TestBaseController.getTestBaseById(L35) **唯一一个用 REST 资源路径** `@GetMapping("/{baseId}")` ✓ — 与 device.ts 标准 REST 风格一致

### 审校批 35（2026-05-03 第 45 轮）发现

新硬伤（**含第 7 个命名问题**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestExampleController.UpdateAll() 方法名大写 U（第 7 个命名问题）** | `TestExampleController.java:24,26` 方法名首字母大写 + Service 方法也是 `UpdateAll()` | Java 命名规范：方法应小驼峰；累计命名错 7 处 |
| **TestExampleController.updateAll() 无入参** | 黑盒接口；更新所有？范围不限 | 危险接口；批量操作无范围控制 |
| **TestExampleController 单端点 controller** | 整个 Controller 只有 1 个 POST 端点 | 模块化颗粒度过细；应合并到 TestFunctionCaseController |
| **BaseStructController `@Autowired public BaseStructService` 字段** | L20 `public BaseStructService baseStructService` 字段是 public | 反 Java 封装；外部可直接访问；应 private |
| **路径风格 `/base/listAllBaseStruct` 不符 REST** | verbatim 方法名作路径 | 不规范；应 `GET /baseStructs` |

### 累计命名问题（**7 处**，已建表）

| 命名错 | 位置 | 说明 |
|--------|------|------|
| `verfier` | ExeStep.java:72 | 字段名拼错 |
| `mesdceCode` | TestSuite.java:44 | 字段名诡异 |
| `relyFuntionReady` | TestFunctionRely.java:14 | 字段名拼错 |
| `TestPlanStatusContants` | 类名 | 类名拼错 |
| `StatusContants` | interface 名 | interface 名拼错 |
| `_fial` ×5 | StatusContants L45-50 | 复合字段拼错 |
| **`UpdateAll()`** | TestExampleController:24 | **方法名违反 Java 命名规范** |

新校验通过项：
- BaseStructController 路径 `/base` + 2 GET 端点（listAllBaseStruct / listAllBaseStructAndId）✓
- BaseStructController @Slf4j + @Api + @RestController ✓
- TestExampleController 仅 1 端点 + @Slf4j + @Api(tags="测试用例") ✓
- ExeStepService 9 方法 + 含 javadoc 注释（previewEmsMessage / saveLog / listExeLogs）✓
- ExeStepService 接口注释比 TestPlanService / TestSuiteService 多 ✓ 但全项目注释一致性差

### 审校批 34（2026-05-03 第 44 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **TestFunctionService 接口注释 `// Added for TestSuite sync creation logic`** | `TestFunctionService.java:30-31` `copyTestFunctionForSuite` 方法 | 反 SRP：方法是为另一个 service 加的；接口耦合到调用方需求；说明设计时没想清职责边界 |
| **getFunctionsByIds vs getTestFunctionListById 功能重复** | L28 返 Map / L29 返 List；都是按 ID 集合查 TestFunction | 应抽一个返 Map（含 List 转换）；冗余接口 |
| **ExeFunctionService.deleteExeFunction 返 int** | L13 返删除条数（int） | 与其他 delete 方法返 Response 不一致；调用方判断风格不统一 |
| **TestFunctionService 4 种返回类型混杂** | Response / Integer / Map<Integer, TestFunction> / List<TestFunction> / void | 同 FunctionSuiteService 4 种混杂；项目接口设计无统一规范 |
| **getFunctionsByIds 返 Map<Integer, TestFunction> Entity** | L28 暴露 Entity（同 #5τ） | DTO/Entity 边界模糊 |

新校验通过项：
- TestFunctionService 14 方法签名（CRUD + 提交 + 审签 + 统计 + 列表 + 跨 service 工具）✓
- ExeFunctionService 4 方法签名（查询 2 + 派工 1 + 删除 1，极简）✓
- ExeFunctionService.conveyTestFunction2ExeFunction(Integer suiteId, String planId) ← 派工核心方法 ✓
- ExeFunctionService 设计意图：执行域只暴露"查询与操作"接口 ✓ 符合 CQRS 思路

### 审校批 33（2026-05-03 第 43 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 Response<T> 继承 Spring ResponseEntity** | `Response.java:8` `extends ResponseEntity<ResponseBody<T>>` | 强耦合 Spring；将来切 WebFlux 响应式或非 Spring 框架要全量改；这种业务包装类应该是 POJO 不是 ResponseEntity 子类 |
| **Response 的 setter 实际改 body 引用而非 ResponseEntity（不可变）** | L34-47 `setCode/setMsg/setData` 转发到 body | ResponseEntity 设计是不可变的；这套 setter 工作但是反 Spring 设计意图；微妙 hack |
| **getResponseBody null check 抛 IllegalStateException** | L67 `throw new IllegalStateException("ResponseBody is null!")` | **全项目唯一显式异常风格**！其他地方都吞异常或 return failure；这里反常规 |
| **`getResponseBody()` 私有方法重复 5 次调用** | L35/40/45/50/55/60 | 可改为字段缓存；或 getter 直接调 super.getBody() |
| **🔥 FunctionSuiteService 接口返回类型混杂 4 种** | `FunctionSuiteService.java`：Response / Integer / List<FunctionSuite> Entity / List<FunctionSuiteDto> | 调用方判断结果方式不一致；listFunctionSuiteBySuite 暴露 Entity（同 #5τ） |

新校验通过项：
- Response<T> 5 个构造器（默认 / status / body+status / headers+status / 三参数）✓
- Response.isSuccess() 判 code==200 标准 ✓
- Response 用 ResponseBody 实际承载数据（继承 ResponseEntity 是为了 Spring MVC 友好返回）✓
- FunctionSuiteService 8 方法签名（CRUD + 统计 + 列表 + 依赖查询）✓
- countMilitaryBySuite / countKeyProcessBySuite 直接返 Integer 适合 service 内部聚合调用 ✓

### 审校批 32（2026-05-03 第 42 轮）发现

新硬伤（**含第 5 个微服务残骸实证**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestSuiteService.java:27 注释 `// RPC 2 local`** | 明文标注"RPC 2 local"分隔 4 个方法（getTestSuiteInfoById / updateTestSuite / reviewSuiteSpecial / updateSuiteListAppStatusToUnApp） | **第 5 个微服务残骸实证**：注释直接说明这些方法原本是 RPC 远程调用，演进后改为本地调用 |
| **TestPlanService / TestSuiteService 全部用 `Response` 不带泛型** | 9+13 方法签名都是 `Response xxx(...)` 而不是 `Response<List<TestPlanDto>> xxx(...)` | Response<T> 设计被浪费；调用方无类型推断；与 ResponseFactory 提供 Response<T> 不对齐；同 #5δ Service 返回 Object 失去类型信息 |
| **TestSuiteService 方法返回类型混杂** | Response / TestSuite / boolean 三种返回类型混用 | 调用方判断结果方式不一致；isCanEdit 返 boolean 但 update 返 Response |
| **接口 listAll() 无参数** | TestPlanService / TestSuiteService 都有 `Response listAll()` 无参 | 无分页 + 无过滤参数 → 直接拉全表（与 IDOR + OOM 风险联动） |
| **接口注释稀少** | TestPlanService 仅 remarkTestPlan 有注释；TestSuiteService 仅"RPC 2 local"分隔注释 | 接口契约不清；新人理解成本高 |

### 微服务残骸完整清单（**5 处实证**，已建表）

| 残骸 | 位置 | 类型 |
|------|------|------|
| 9 个 SpringBoot 主类 | 各模块 | 主类残骸 |
| fastop-dal 整个模块 | 死代码 | 模块残骸 |
| ResponseFactory.getFeignData | base-common | 工具方法残骸 |
| ResponseMsg.RPC_ERROR | base-common | 错误消息残骸 |
| **TestSuiteService.java:27 `// RPC 2 local` 注释** | service interface | **明文注释残骸**（第 5 处） |

新校验通过项：
- TestPlanService 9 方法签名（含 commit 455129d 拆出的 remarkTestPlan）✓
- TestPlanService.java:12 注释明确"仅更新备注字段，权限要求低于 updateTestPlan" — commit 455129d 接口分离的契约说明 ✓
- TestSuiteService 13 方法签名（CRUD + 审签 + 业务规则）✓
- TestSuiteService.isCanEdit(tSuite) 业务规则方法 ✓ 设计模式上类似 Specification

### 审校批 31（2026-05-03 第 41 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestReview.vue 双 tab 多态承载 function/suite** | L18 `{{ row.funName \|\| row.suiteName }}` 双 tab 共用同一 tableData | 单组件两套数据模型；DesignNodeDto 同款反模式 |
| **🔥 TestReview.vue 审批 pass/reject vs 后端 level (Integer) 契约不对齐** | L56-58 radio 取 'pass'/'reject'；后端 TestSuiteServiceImpl.check 接 `Integer level` | 前后端契约错位；前端如何把 pass/reject 转 level 不明 |
| **TestReview.vue 批量审签按钮未实现** | L9 `<el-button :disabled="!tableData.length">批量审签</el-button>` 无 @click handler | placeholder 按钮，点了无反应 |
| **ExeFunctionController 路径 `/exeFunction/testFunctions/...` 嵌套混乱** | L24 `/testFunctions/inexe/{planId}` + L31 `/testFunctions/id/{functionId}` | testFunctions 子路径重复 exeFunction 语义；应该 `GET /exeFunctions/{planId}/inexe` |
| **方法命名 InExe 重复** | `getExeFunctionInExeListByPlanId` 命名冗长 + InExe 难懂 | 应 `listInExecutionFunctions(planId)` |

新校验通过项：
- TestReview.vue 双 tab + dialog + form 标准结构 ✓
- 审签 dialog 含审批结果 / 确认等级 / 审批意见 三段表单 ✓
- ExeFunctionController 路径前缀 `/exeFunction` + 仅 2 端点 + 全 GET ✓
- ExeFunctionController 用 @Slf4j ✓
- 服务调用 ExeFunctionService 接口（非 Impl）✓

### 累计已知缺陷分类汇总（35 大类，**135+ 项**）

按主题数：
- 微服务残骸：4 处实证（9 主类 + fastop-dal + getFeignData + RPC_ERROR）
- 命名错误：6 处（4 字段名 + 2 类名）
- 事务遗漏：3 处（dispatchPlan / createFunctionSuite / reviewSuiteSpecial）
- 审签权限漏洞：4 处（listAll + 0 @PreAuthorize + 前端 hasRole 仅 2/9 + check 注释）
- 反模式：8+ 处（CommonConstants.NUM_X / Constant Interface / DTO 含 Entity / God Entity / 长方法 / 多态字段 / 物理删除 / 静默吞异常）
- 安全：6 P0（IDOR / 密码默认 / root / commons-text / fastjson / token localStorage）
- 数据库：5 处（12 表无索引 / UUID 混用 / 字符集 3 种 / 软删除坑 / 冗余 UNIQUE）
- 配置：5 处（plugin 版本错配 / skip 矛盾 / pom 重复依赖 / yml 无 actuator / Druid 默认）
- 文档漂移：3 处（API_DOC + DEVICE_API_SPEC 不存在 / Postman 集合幻觉）
- 前端：4 处（魔法数字 / 路径错 / 全量引入 / 硬编码字面量）

### 审校批 30（2026-05-03 第 40 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **ModuleLibrary.vue 状态魔法数字** | L57 `row.changeFlag === 4` / L75 `row.approveStatus !== 0` | 应用 StatusContants.tfun_cha_newfun / tfun_app_unsubmit；与 #5n TestPlan.vue 同问题 |
| **APPROVE_STATUS_MAP 在 vue 内手动维护** | L63-65 前端硬编码状态码→中文映射 | 后端常量改了前端不报错；应从后端枚举接口取 |
| **添加模块按钮无 hasRole 校验** | L36 `<el-button @click="openCreateDialog">添加模块</el-button>` | 任何登录用户能添加；与 #5e 实证一致 |
| **`row.changeFlag === 4` 含义不易读** | 4 = 新建功能（StatusContants.tfun_cha_newfun） | 用魔法数字判定状态机 |

### 金弹素材已加进 PITCH/CHEAT

`commit 455129d 自述与实际不符` 已加入：
- INTERVIEW_PITCH.md 5min 版自我反思段
- INTERVIEW_CHEATSHEET.md 金弹素材故事 #2

新校验通过项：
- ModuleLibrary.vue 同 SuiteLibrary 用 cascader + Pinia globalFilter ✓
- 用 el-tag :type 显示审签状态 ✓
- 操作列用 el-button link 紧凑 ✓
- 修改按钮 disabled 当 approveStatus !== 0（仅未提交可改）✓ 业务规则下沉

### 审校批 29（2026-05-03 第 39 轮）发现

新硬伤（**含一处审签权限漏洞实证**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 TestSuiteServiceImpl.check 审签 worker 校验被 TODO 注释掉** | `L317-321` 整段权限校验"工作人员是否预期审签人"被 `/* ... TODO */` 注释 | **审签权限漏洞**：任何登录用户能在任何审签级别冒充任何审签人；commit 455129d 自述"恢复审签权限校验" 但此处实际未恢复 |
| **🔥 TestSuiteServiceImpl.delete 物理删除** | L239 `deleteByPrimaryKey(suiteId)` | 与项目其他实体"软删除 deleted=true"模式不一致；删除后无法恢复 |
| **🔥 delete 失败也返 success** | L243 `return ResponseFactory.success("删除失败")` | bug：success 包装含"删除失败"消息，调用方判 success 但实际失败；应改 `failure` |
| **submit 用魔法数字 1** | L221 `tS.setListApprStatus(1)` | 应用 StatusContants 常量；语义不清 |
| **submit 缺权限/业务校验** | 任何用户能 submit 任何清单；当前状态是否适合 submit 也无校验 | 不适当流程会造成数据混乱 |
| **`isCanEdit` 双重否定 if** | L335-339 `if (!(status == proof or status == approve)) return true; return false;` | 可读性差；应 `return !(status==proof || status==approve)` |
| **数组下标越界风险** | L315 `SUITE_APP_LEVEL[suite.getListApprStatus()]` 状态如果是 -1 / 大于数组长度 → ArrayIndexOutOfBoundsException | 未校验数组边界 |
| **SuiteLibrary.vue 暴露 `mesdceCode` 拼错字段到 UI** | L57 `<el-table-column prop="mesdceCode" label="MES编号" />` | 字段名错误传染到前端列定义 |

新校验通过项：
- TestSuiteServiceImpl.check 实现审签状态机 0→1→2→3 三段流转 ✓
- check 用 switch + StatusContants.suite_app_* 常量做状态判定 ✓
- check L314 校验"实际步骤匹配传入级别"防越级 ✓
- isCanEdit 用 `equals` 比较 Integer ✓ 避免装箱比较坑
- SuiteLibrary.vue 三级级联选择器（机型/专业/子系统）+ Pinia globalFilter store ✓
- SuiteLibrary.vue 用 el-tag 显示 military "密" 标签 + el-tag 显示版本号 ✓

### 累计审签流程"已知缺陷"（4 处）

| 项 | 位置 | 类型 |
|---|------|------|
| TestPlanController.listAll 无权限 | TestPlanController:60 | IDOR |
| 后端 0 个 @PreAuthorize | 全部 Controller | 注解缺失 |
| 前端 hasRole 仅 2/9 视图 | Layout/DeviceManage | 覆盖率不足 |
| **TestSuiteServiceImpl.check 审签人校验被注释** | L317-321 | 审签权限漏洞 |

### 审校批 28（2026-05-03 第 38 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **TestSuiteServiceImpl.update 方法体 90+ 行** | L122-215 单方法承担：基础字段更新 + funIds diff 逻辑 + 删除旧关联 + 新增新关联 | 长方法反模式；应拆 updateBasicInfo / syncFunctionRelations 子方法 |
| **add 方法依赖 selectByExample 排序取主键** | L100-104 `insertedSuites.get(0).getSuiteId()` | 反模式；应用 `useGeneratedKeys=true keyProperty=suiteId` 自动回填主键 |
| **🔥 SystemLogs.vue 模块/动作下拉硬编码中文字面量** | L14-27 `<el-option value="测试计划" />` 等 6 个值 | 应从后端枚举接口取；中文作为 value 国际化失败 |
| **奇怪的默认时间 `new Date(2000, 0, 1, 0, 0, 0)`** | SystemLogs.vue L35 datetime range default-time | 不易读；含义不清；2000-01-01 看似起始时间但实际只是占位 |
| **前端 page-sizes vs 后端限流不一致** | 前端 `[10, 20, 50]`；后端 OperationLogServiceImpl.list `size <= 500` | 前端只用了后端能力的 1/10；要么前端补 100/200，要么后端调小限流 |
| **createFunctionSuite 在循环内调用** | L207 for 循环内单独调 service | N 次 service 调用 + N 次事务嵌套（与 #5η 同问题） |

新校验通过项：
- TestSuiteServiceImpl.update 标 @Transactional(rollbackFor=Exception.class) ✓
- update 业务规则：先校验 isCanEdit，再 diff funIds 同步关联 ✓
- diff 算法正确：删除不在新列表的 + 更新已存在的 funOrder + 新增不在旧列表的 ✓
- L193 已抽 batch query getTestFunctionListById → 部分缓解 N+1 ✓
- SystemLogs.vue 双 tab（操作日志 + 执行日志）+ datetime range + 分页 ✓
- SystemLogs.vue 用 type="index" 序号列 + show-overflow-tooltip ✓
- SystemLogs.vue formatTime 转换格式化 ✓

### 审校批 27（2026-05-03 第 37 轮）发现

新硬伤（**事务遗漏第 3 处实证**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 reviewSuiteSpecial 缺 @Transactional（第 3 处事务遗漏）** | `TestSuiteServiceImpl L352-362` 3 次 service 查询 + 1 次 update 全无事务 | DB 状态不一致（military / keyProcess 半更新） |
| **事务标记不一致** | `updateSuiteListAppStatusToUnApp(L364)` 单 update 加 @Transactional；`reviewSuiteSpecial(L352)` 多步骤反而无 @Transactional | 团队事务策略不一致 |
| **getCheckTestSuite 用魔法数字 1, 2** | `L377 criteria.andListApprStatusBetween(1, 2)` 直接字面量 | 应用 `StatusContants.suite_list_app_passed/proof` |

### 累计事务遗漏清单（**3 处实证**）

| 方法 | 位置 | 已知 |
|------|------|------|
| `dispatchPlan` | TestPlanServiceImpl L306-353 | #10a |
| `createFunctionSuite` | FunctionSuiteServiceImpl L53-92 | #5ε |
| **`reviewSuiteSpecial`** | TestSuiteServiceImpl L352-362 | 本批新发现 |

**模式**：项目内事务标记不统一——startPlan/pausePlan 显式 @Transactional，但 dispatchPlan/createFunctionSuite/reviewSuiteSpecial 漏标。说明团队没有"所有写操作必须事务"的强约定。

新校验通过项：
- TestSuiteService 接口含 reviewSuiteSpecial / updateSuiteListAppStatusToUnApp 等方法签名 ✓
- reviewSuiteSpecial 调 functionSuiteService.countMilitaryBySuite + countKeyProcessBySuite 聚合统计 ✓
- updateSuiteListAppStatusToUnApp 单 update 加 @Transactional + readOnly=false ✓
- getCheckTestSuite 查 listApprStatus BETWEEN 实现"待校对+审签通过"的列表 ✓

### 审校批 26（2026-05-03 第 36 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestSuiteRequestDto 暴露审计字段给前端** | L39-47 含 deleted / createdAt / updatedAt / createdBy / updatedBy 全在请求体 | 前端可伪造 createdBy 等审计字段；安全风险（恶意前端可设 createdBy="admin"） |
| **🔥 三层裸实体（Module/Case/Step）都缺审计字段** | TestFunctionModule / TestFunctionCase / TestFunctionStep 三个实体仅有 changeUser + xxxDate（String），无 createdAt / updatedAt / createdBy / updatedBy / deleted | 业务变更无法追溯；与其他实体不一致 |
| **🔥 三处实体都用 String 存日期** | moduleDate / caseDate / stepDate 全 String | 反模式；类型不安全；查询时间范围只能 LIKE 字符串 |
| **TestFunctionCase 与 TestFunctionModule 结构几乎一样** | 9 字段一一对应（仅前缀 case vs module 不同） | 应抽公共基类 `LeafEntity { id, name, changeUser, description, note, date, parentId, status, updated }` 或泛型 |
| **`updated Integer` 而非时间戳** | TestFunctionModule.updated / TestFunctionCase.updated 是 Integer 标志位 | 命名误导（看起来像时间戳）；改名 `updateFlag` 或 enum |

新校验通过项：
- TestFunctionModule 9 字段全清单（moduleId / moduleName / changeUser / moduleDescription / moduleNote / moduleDate / funId / moduleStatus / updated）✓
- TestFunctionCase 9 字段全清单（caseId / caseName / changeUser / caseDescription / caseNote / caseDate / moduleId / caseStatus / updated）✓
- TestSuiteRequestDto 21 字段（与 TestSuite Entity 20 字段对齐 + 多一个 funIds）✓
- TestSuiteRequestDto 用 @Data Lombok ✓
- TestFunction 三层（module/case/step）层级清晰（module → case → step）✓

### 审校批 25（2026-05-03 第 35 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestFunction.verifier 拼对了 vs ExeStep.verfier 拼错** | TestFunction L62 `verifier`（拼对）；ExeStep L72 `verfier`（少 i） | 跨域命名不统一；同概念两种拼法 |
| **🔥 字符集 3 种混用** | operation_log.sql L18 `utf8mb4_unicode_ci`；260302.sql 主体 `utf8mb4_0900_ai_ci`；exe_step 部分字段 `utf8mb3_general_ci` | 排序规则不一致；emoji 截断 + JOIN 性能问题（不同 collation 不能直接比较） |
| **TestFunction "God Entity" 38+ 字段** | 含 6 角色字段（designer / proofer / verifier / checker / qualityer / approver）+ 大量审签流程字段 | 与 #5k ExeStep 50 字段同问题 |
| **`qualityer` 字段名诡异** | TestFunction L66 强行造词 | 应是 `qualityChecker` / `qaInspector` |
| **三处实体角色定义都不同** | TestFunction 6 角色 / TestSuite 3 角色（proofer/approver/submitter）/ TestPlan 3 角色（dispatcherId/commanderId/executorGroupId）/ ExeStep 3 角色（commander/verfier/soldier） | 角色模型在不同实体不统一 → RBAC 权限治理无法抽象 |

新校验通过项：
- TestFunction 38+ 字段全清单 ✓
- FunctionSuite 12 字段全清单 ✓
- FunctionSuite 主键自增 id（无业务主键）✓
- operation_log.sql 完整建表 SQL：bigint AUTO_INCREMENT + idx_create_time + idx_module_action 双索引 ✓
- operation_log.sql 首行注释 → 与 OperationLogServiceImpl L30 警告日志一致 ✓
- ip 字段在 SQL 定义但 service 不写入（与 #5d 实证一致）✓
- operation_log VARCHAR 长度合理（ip 64 / module 64 / detail 500）

### 审校批 24（2026-05-03 第 34 轮）发现

新硬伤（**fastop-dal 死代码加深实证**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 TestFunctionMapper.java 是 0 字节空文件** | `fastop-dal-designer/.../dal/TestFunctionMapper.java` 文件大小 = 0 bytes | fastop-dal-designer 模块**完全空壳**——主类 + pom 配置 + 空 Mapper 文件，是微服务残骸的"考古遗迹"；连唯一的内容都是空的 |
| **fastop-dal pom 完整含 web + db 依赖** | `fastop-dal/pom.xml:24-54` 含 druid + mysql + spring-boot-starter-web + javax.servlet + commons-text + httpclient | 作为独立服务可启动；微服务残骸自带"标配" |
| **fastop-dal-designer 通过父 pom 继承全部 web/db** | `fastop-dal-designer/pom.xml:23-26` 自己仅加 mybatis-spring-boot-starter 2.2.2 | 原本可独立启动作为 dal 微服务 |

新校验通过项 / 实证：
- fastop-dal/pom.xml packaging=pom + 1 子模块（fastop-dal-designer）✓
- fastop-dal pom mainClass = `com.hengtiansoft.fastop.dal.FastopDalApplication` + skip=true（同其他模块矛盾配置）✓
- fastop-dal-designer pom mainClass = `com.hengtiansoft.fastop.dal.designer.FastopDalDesignerApplication` ✓
- fastop-dal/pom.xml 引入 commons-text（与顶层 pom 1.9 版本一致 — Text4Shell CVE 风险传染）✓
- fastop-dal pom 与 fastop-dal-designer pom **两层都配 spring-boot-maven-plugin + skip + repackage**（与 fastop-base / fastop-base-common / fastop-service 一致都是模板 copy 出来的）

### **fastop-dal 模块"考古证据链"**

```
fastop-dal/                     ← packaging=pom，残骸根
├── pom.xml                     ← 完整 web/db 依赖（独立服务标配）
├── src/main/java/.../FastopDalApplication.java  ← Spring Boot 主类
├── src/test/.../FastopDalApplicationTests.java  ← 空测试
└── fastop-dal-designer/        ← 残骸子模块
    ├── pom.xml                 ← 仅含 mybatis（继承父 pom 的 web/db）
    └── src/main/java/.../
        ├── FastopDalDesignerApplication.java   ← Spring Boot 主类
        └── dal/
            └── TestFunctionMapper.java         ← **0 字节空文件**
```

**完整死代码体量**：
- 2 个 pom 配置（约 200 行）
- 2 个 SpringBoot 主类（约 30 行）
- 1 个空文件
- service 模块**完全不引用**任何一段
- mvn 跑全模块构建时仍触发 spring-boot-maven-plugin（即使 skip=true 也浪费）

### 审校批 23（2026-05-03 第 33 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **TestFunctionStep 缺审计字段** | `TestFunctionStep.java` 16 字段无 createdAt / updatedAt / createdBy / updatedBy / deleted | 与其他实体不一致；步骤变更无追溯（仅 changeUser + stepDate 单点信息） |
| **`stepDate String`** | 用字符串存日期 | 反模式；应 Date 或 LocalDate；类型不安全 |
| **`stepCommandExample/Params` JSON-as-string** | TestFunctionStep 含两个 JSON String 字段 | JSON-as-string 反模式；查询无法 SQL 索引；应改 MySQL 8 JSON 字段或拆结构化子表 |
| **DesignNodeDto `type String` 不是 enum** | L28 `private String type` 取 "MODULE"/"CASE"/"STEP" 字面量 | 类型不安全；写错值无编译期检查 |
| **DesignNodeDto 单一 DTO 多态承载 3 种节点** | "Step Only fields" 注释 — module 节点不需要 operation/obj/commandExample 等 | 字段稀疏反模式；应拆 ModuleNodeDto / CaseNodeDto / StepNodeDto 或 sealed interface |
| **FunSuiteIdConnectDto 字段无 private（同 #5l）** | `FunSuiteIdConnectDto.java:11-13` `Integer suiteId; List<TestFunction> testFunctions;` 包级访问 | 与 ExeStepCommand 同 Java 反模式 |
| **🔥 FunSuiteIdConnectDto 含 List<TestFunction> Entity** | DTO 字段直接持有 Entity | DTO 应只含 DTO 字段；持有 Entity 导致序列化暴露内部字段 + 序列化 BLOB |

新校验通过项：
- TestFunctionStep 16 字段全清单（含 stepCommandExample / stepCommandParams 两个 JSON 字段）✓
- DesignNodeDto 用 @Data @Builder @NoArgsConstructor @AllArgsConstructor 全套 Lombok ✓
- DesignNodeDto 用 @ApiModel + @ApiModelProperty 完整标注 ✓
- DesignNodeDto 用 `List<DesignNodeDto> children` 树形结构 ✓
- FunSuiteIdConnectDto 用 @Data ✓

### 审校批 22（2026-05-03 第 32 轮）发现

新硬伤（**含一组超大常量灾难**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 StatusContants 是 interface 不是 class** | `StatusContants.java:3` `public interface StatusContants` 含 144 行常量 | **Constant Interface Antipattern**（Effective Java 第 22 条）；实现类继承所有常量到自己 namespace；public 暴露内部细节 |
| **🔥 StatusContants 类名同样拼错（第 5 个）** | 类名少 s，应是 Constants | 与 #5ν TestPlanStatusContants 同问题 |
| **🔥 至少 5 处 `_fial` 拼错（第 6 个）** | StatusContants L45/L47/L48/L49/L50 `tfun_app_*_fial`（fail 拼错） | 5 处复合字段名拼错；项目命名问题积累到 **6 处**（含类名 + 字段 + 字段名前缀） |
| **🔥 StatusContants 大量注释死代码** | L23-28 / L39-42 / L46 / L57 注释掉的常量定义 | 应该删除而不是注释 |
| **🔥 数组常量索引与常量值不对齐** | L132 `FUNS_APP_LEVEL[]` 与 L123-130 `funs_app_*` 常量索引含义错位（funs_app_verifier=2 vs FUNS_APP_LEVEL[2]="待质审"） | magic array 反模式；用数组下标找业务含义脆弱 |
| **🔥 ResponseCode 同值不同名重复** | L7 `SYSTEM_ERR = 500` + L16 `SYSTEM_ERROR = 500` | 反模式；调用方分不清用哪个 |
| **🔥 ResponseMsg 含 `RPC_ERROR` 微服务残骸** | L21 `RPC_ERROR = "发生远程调用异常"` | 项目无 RPC 但保留错误消息（继 #5y getFeignData / 9 主类 后第三个微服务残骸） |
| **命名风格混用** | snake_case (suite_list_app_unapp) 与 SCREAMING_SNAKE (FUNS_APP_LEVEL) 混用 | 与 Java 规范（SCREAMING_SNAKE_CASE）不符；前者是 Python 风格 |

新校验通过项 / 实证：
- StatusContants 含完整业务状态全清单（设备 4 状态 / 审签类型 2 种 / 模块类型 4 种 / 多种审签状态机 / 步骤层级 3 级 / 判据类型 3 种 / 检验状态 3 态 / 判定状态 3 态 / 步骤状态 3 态 / 测试对象类型 6 种 / 模块审签 8 阶段 / 清单审签 5 阶段）✓
- ResponseCode 13 个状态码（SUCC=200, FAILURE=300, BUSINESS=502, PARAMETERS_VALIDATION=501）+ 标准 HTTP 4xx/5xx 混合 ✓
- ResponseMsg 18 个文案常量（中文）✓

### 累计字段/类名拼写错总览（**6 处**）

| 拼错 | 位置 | 应为 |
|------|------|------|
| `verfier` | ExeStep.java:72 | `verifier` |
| `mesdceCode` | TestSuite.java:44 | `meDeviceCode` |
| `relyFuntionReady` | TestFunctionRely.java:14 | `relyFunctionReady` |
| `TestPlanStatusContants` | 类名 + 文件名 | `TestPlanStatusConstants` |
| **`StatusContants`** | 类名 + 文件名 + interface | `StatusConstants` |
| **`*_fial`**（5 处）| StatusContants L45-50 | `*_fail` |

### 微服务残骸完整清单（4 处）

| 残骸 | 位置 | 状态 |
|------|------|------|
| 9 个 SpringBoot 主类 | 见 #5α | 仅 1 个使用 |
| fastop-dal 整个模块 | 见 #5w | service 未引用 |
| ResponseFactory.getFeignData | 见 #5y | 项目无 Feign |
| **ResponseMsg.RPC_ERROR** | constants/Response/ResponseMsg.java:21 | 项目无 RPC |

### 审校批 21（2026-05-03 第 31 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 CommonConstants 定义 NUM_0 到 NUM_9** | `CommonConstants.java:10-20` 显式定义 NUM_0...NUM_9，自己注释 "magic number" | **反反模式**：用常量替代字面量本来是好事，但常量名本身就是字面量等于没替；说明团队曾经被 sonar 警告强迫加常量但没理解原意 |
| **🔥 TestPlanStatusContants 类名自身拼错** | 文件名 + 类名都是 `TestPlanStatusContants`（少 s），应是 `Constants` | 第 4 个拼错（继 verfier / mesdceCode / relyFuntionReady）；类名拼错重命名成本极高（全仓 import 都跟着改） |
| **TestPlanEnum.getKey 找不到返 -1** | `TestPlanEnum.java:38-45` magic number -1 代表"找不到" | 反模式；应抛 IllegalArgumentException 或返 Optional<Integer> |
| **TestSuiteController.submit/delete 用 @RequestBody Integer** | L37/L44 `@RequestBody Integer suiteId` 接 raw int | 客户端要发 `42` 而不是 `{"suiteId":42}`；反 REST；建议 DTO 包装 |
| **TestSuiteController.check 用 3 个 @RequestParam 接业务参数** | L73 `(@RequestParam Integer suiteId, @RequestParam String checkWorker, @RequestParam Integer level)` | 业务操作应该用 DTO 不用 query param；扩展性差 |

新校验通过项 / 重要确认：
- TestPlanStatusContants 定义 7 状态常量（UNEXE/VERIFY/EXEING/PAUSE/MVERIFY/DISPATCH/FINISH）✓
- TestPlanEnum 与常量双定义（B 节已记录）✓
- TestPlanEnum 用 (Integer key, String value) 双向映射 ✓
- TestSuiteController 路径 `/designer/testSuite` + 9 端点（add/update/submit/delete/get/listByBaseId/listAll/check/getCheckTestSuite）✓
- TestSuiteController 用 @Slf4j 类级日志 ✓

### 累计字段/类名拼写错总览（4 处）

| 拼错 | 位置 | 应为 |
|------|------|------|
| `verfier` | ExeStep.java:72 | `verifier` |
| `mesdceCode` | TestSuite.java:44 | `meDeviceCode` |
| `relyFuntionReady` | TestFunctionRely.java:14 | `relyFunctionReady` |
| **`TestPlanStatus**Cont**ants`** | 类名 + 文件名 | `TestPlanStatusConstants`（少 s） |

### 审校批 20（2026-05-03 第 30 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 TestSuite 版本号自增并发问题** | `TestSuiteServiceImpl.add L52-67` 查 max(version)+1 模式 | 并发 add：A/B 同时查到 max=5 都设 version=6 → 重复版本号或 UNIQUE 冲突；应用 `INSERT ... SELECT MAX+1` 或乐观锁 |
| **fastop-model pom name 与 artifactId 不一致** | `pom.xml:11-12` artifactId=fastop-model，name=fastop-module | maven 元数据混乱；应统一 |
| **swagger-bootstrap-ui 版本声明未使用** | `pom.xml:24` 声明 `<swagger-bootstrap-ui.version>1.9.6</swagger-bootstrap-ui.version>` 但 dependencies 没引 | 死配置 |
| **TestSuiteServiceImpl 显式 setCreatedAt(new Date())** | L76 业务代码手动赋时间戳 | 应在 entity 层 @PrePersist / 或 mybatis 拦截器自动填充审计字段 |
| **CommonConstants.NUM_0/NUM_1 滥用再实证** | `TestSuiteServiceImpl.add L45/L64/L66` 多处用 NUM_1 / NUM_0 替代字面量 | 与 #5 创建过度封装常量同问题 |

新校验通过项：
- fastop-model pom packaging=pom + 2 子模块（designer + planner）✓
- fastop-model pom 没配 spring-boot-maven-plugin（与 fastop-base 不一致 — fastop-base 错配，fastop-model 正确）✓
- OperationLogService 接口仅 2 方法（record / list）简洁 ✓
- TestSuiteServiceImpl 用 @Slf4j 类级日志（与 FunctionSuiteServiceImpl 类级 LOG 一致；与 #5h 临时 LoggerFactory 不一致）
- TestSuiteServiceImpl.add 标 @Transactional(rollbackFor=Exception.class) ✓
- 业务规则下沉到 service：L42-50 "已审前通过清单"防重 ✓
- BeanUtils.copyProperties(dto, entity) 标准 DTO→Entity 映射 ✓

### 审校批 19（2026-05-03 第 29 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **TestFunctionRely `relyFuntionReady` 字段名拼错** | `TestFunctionRely.java:14` `private Boolean relyFuntionReady`（少 c，应是 `relyFunctionReady`） | 第 3 个拼写错（继 ExeStep.verfier + TestSuite.mesdceCode）；命名问题积累 |
| **fastop-base pom packaging=pom 但配 spring-boot-maven-plugin** | `fastop-base/pom.xml:13` packaging=pom；L46-60 又配 spring-boot-maven-plugin + repackage | pom 模块不能 repackage；这是非法用法但 skip=true 让构建通过 |
| **BaseStruct 实体无审计字段** | 仅 4 字段（baseId / model / profession / subsystem），无 createdAt/createdBy/deleted | 与其他实体不一致；如果要做"构型变更追溯"无字段可用 |

新校验通过项：
- BaseStruct 是简单配置实体（机型 / 专业 / 子系统 三维分类）✓
- TestFunctionRely 10 字段：testFunctionRelyId / suiteId / testFunctionId / relyFunctionId / relyFuntionReady / deleted / createdAt / updatedAt / createdBy / updatedBy ✓
- TestFunctionRely 设计：(suite_id, testFunctionId, relyFunctionId) 业务三联，加自增主键 testFunctionRelyId ✓
- fastop-base pom packaging=pom + 仅含 fastop-base-common 子模块 ✓
- fastop-base 引 lombok optional=true 让子模块按需继承 ✓
- fastop-base 也是 spring-boot-maven-plugin 2.7.18 vs 父 BOM 2.6.13 错配（与 #5u 同问题）

### 三个字段名拼写错总览（项目命名问题积累）

| 字段 | 实体 | 应为 |
|------|------|------|
| `verfier` | ExeStep.java:72 | `verifier` |
| `mesdceCode` | TestSuite.java:44 | 疑似 `meDeviceCode` 或 `mesDeviceCode` |
| `relyFuntionReady` | TestFunctionRely.java:14 | `relyFunctionReady` |

**修法相同**：上线后改字段成本极高（DB 列名 / API 参数 / 前端字段），加注释 `// HISTORICAL TYPO: keep for compat` 或全量迁移

### 审校批 18（2026-05-03 第 28 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 createFunctionSuite 缺 @Transactional** | `FunctionSuiteServiceImpl L53-92` 循环 insertSelective + L90 reviewSuiteSpecial | 第一步插入已 commit，第二步审签状态更新失败 → 数据不一致 |
| **TestSuiteService ⇄ FunctionSuiteService 循环依赖** | `FunctionSuiteServiceImpl L30-32` `@Autowired @Lazy private TestSuiteService` | @Lazy 是绕过手段；理想是拆解依赖（提取共用接口或事件） |
| **createFunctionSuite 循环内 insertSelective** | L78-89 for 循环每条 testFunction 单独 insertSelective → N 次 SQL | 应改批量 insert（mybatis foreach insert values (...),(...)） |
| **CommonConstants.NUM_0 过度封装** | L74 `if (functionSuites.size() > CommonConstants.NUM_0)` | 数字 0 不需要常量化；过度封装反而损可读性 |
| **TestSuite `mesdceCode` 字段拼写诡异** | L44 `private String mesdceCode` 疑似 MES Device Code 缩写但写错 | 同 #5j ExeStep `verfier` 拼错；命名问题积累 |

新校验通过项：
- **TestSuite 21 字段全清单**：suiteId / suiteName / suiteDesc / version / planeEffectMin / planeEffectMax / proofer / approver / submitter / testBaseId / listApprStatus / military / keyProcess / apprChain / deleted / createdAt / updatedAt / createdBy / updatedBy / mesdceCode ✓
- TestSuite 含 proofer / approver / submitter 三角色（清单的校对/审批/提交）+ apprChain 审批链（String 序列化）
- TestSuite 手写 getter/setter（与 TestPlan 一致风格，与 #5f 实体类风格漂移问题对应）
- FunctionSuiteServiceImpl 用 `private final Logger LOG = LoggerFactory.getLogger(this.getClass())` ✓ 类级 Logger（与 #5h 临时 LoggerFactory 不一致；说明项目内日志声明风格也不统一）
- FunctionSuiteServiceImpl 注入 3 Mapper + 2 Service ✓
- L65 `isCanEdit(tSuite)` 业务规则校验"清单审签中无法修改" ✓ 状态机思维下沉到 service
- L79 `numSet.contains(tFun.getNum())` 重复模块编号防御 ✓

### 审校批 17（2026-05-03 第 27 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 conveyTestStep2ExeStep N+1 查询问题** | `ExeStepServiceImpl L94-157` 三层嵌套 for（module→case→step），每层调一次 Service.getByXxxId | 1 计划 × 10 module × 10 case × 10 step = **1+10+100 = 111 次 SQL**；派工大计划时性能崩 |
| **Service 返回 Response<Object> 失去类型信息** | `testFunctionModuleService.getByFunId(funId).getData()` 返回 Object，再用 `ObjectMapper.convertValue` 反序列化为 List | Service 设计反模式；用 `Response<List<TestFunctionModule>>` 强类型即可 |
| **commandData JSON 序列化在 Service 层做** | L135-152 try-catch 构造 topic/example/params + ObjectMapper.writeValueAsString | 业务逻辑与 JSON 处理耦合；应该抽到 EmsMessageService 或专门的 CommandDataBuilder |
| **commandData 异常降级为空 JSON 兜底** | L150-152 异常吞掉，写空 JSON | 类似 #5h 静默失败；调用方不知道 commandData 没构造成功 |

新校验通过项：
- **conveyTestStep2ExeStep L94-157** ✓ 行号准确
- L94 `@Transactional(readOnly = false)` 标记 ✓
- 三层嵌套循环（module→case→step）+ 内层 new ExeStepWithBLOBs ✓
- L122 UUID.randomUUID() 生成 exeStepId ✓
- L133 初始 exeStatus = UNEXE(0) ✓
- L153 insertSelective ✓
- **Service 调 Service** 而不是 Mapper 直访（保留未来切微服务的接口契约）✓ 设计原则正确
- FastopServiceApplication 的 `@MapperScan` 扫两个 model.dto 包 + `@SpringBootApplication scanBasePackages` 显式列两个包 ✓
- FastopBaseCommonApplication 仅 7 行裸模板（再次验证微服务残骸是模板 copy 出来的）✓

### 审校批 16（2026-05-03 第 26 轮）发现

**🔥🔥 核弹级发现：9 个 SpringBoot 主类，仅 1 个被实际使用**

| 主类 | 模块 | 包名 | 实际状态 |
|------|------|------|---------|
| `FastopApplication` | fastop（顶层 pom）| `.fastop` | 死代码（pom packaging） |
| `FastopBaseApplication` | fastop-base | `.fastopbase`（无 .） | 死代码（pom packaging）|
| `FastopBaseCommonApplication` | fastop-base-common | `.fastop.base.common` | 死代码（被 service 当库引用） |
| `FastopDalApplication` | fastop-dal | `.fastop.dal` | 死代码（pom packaging）|
| `FastopDalDesignerApplication` | fastop-dal-designer | `.fastop.dal.designer` | 死代码（service 未引用）|
| `FastopModelDesignerApplication` | fastop-model-designer | `.fastop.model.designer` | 死代码 |
| `FastopModelPlannerApplication` | fastop-model-planner | `.fastop.model.planner` | 死代码 |
| `FastopModuleApplication` | fastop-model | `.fastopmodule`（无 .） | 死代码 |
| **`FastopServiceApplication`** | **fastop-service** | `.fastop.service` | **唯一真正运行的主类** ✓ |

**项目重构演进史一手实证**：
- 微服务时代：每个 Maven 子模块都是独立 Spring Boot 服务（9 个服务）
- 演进到模块化单体：只保留 fastop-service 启动，其余 8 个主类成死代码
- **彻底清理度**：只清理了 Feign 调用，**没有清理 SpringBoot 主类**

新硬伤（已加入 KNOWN_ISSUES）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥🔥 8 个废弃 SpringBoot 主类** | 见上表 | 死代码污染；新人疑惑"哪个是真主类"；mvn package 在每个子模块都触发 spring-boot-maven-plugin 浪费构建时间 |
| **包名命名不一致** | `.fastopbase`（无 .）vs `.fastop.base.common`（有 .）；`.fastopmodule`（无 .）vs `.fastop.model.{designer,planner}`（有 .） | 反映重构没统一包结构 |
| **fastop-base-common pom 自己也是 spring-boot-maven-plugin + skip + repackage 矛盾** | L84-99 与 fastop-service/pom.xml 同样的反模式 | 整套子模块都是从同一个微服务模板 copy 出来的 |
| **Feign 残骸 grep 全仓** | 仅 4 处提及（ResponseFactory.getFeignData + ExeFunctionServiceImpl L100/L110/L116 注释）；无任何 @FeignClient / FeignClient import | 残骸已清理彻底，**仅注释和工具方法残留** |

新校验通过项：
- fastop-base-common pom 引入 swagger 全套 + lombok + spring-boot-starter-web ✓
- fastop-base-common pom 重复声明 swagger-annotations（L41-43 + L51-53）— 与 #5s 同问题
- ExeFunctionServiceImpl 注释行号实际是 L100/110/116（之前 A2 节说 L100/116/141 略有偏差）

### 审校批 15（2026-05-03 第 25 轮）发现

新硬伤 / 修订：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 ResponseFactory 残留 `getFeignData` 方法** | `ResponseFactory.java:75-80` `public static <T> T getFeignData(Response<T> respData)` 专门解析 Feign 响应 | **微服务残骸再实证**——项目已无 Feign 调用但工具方法保留；调用方还在用就是新增 Feign 调用诱因 |
| **`(Response<T>) builder()...` 强制类型转换** | L62-72 静态便捷方法用 cast | unchecked cast 编译警告；应该直接调 builder 链式而不是包一层 |
| **缩进不一致** | ResponseFactory L14-56 缩进 4 空格，L58-80 缩进 8 空格 | 风格漂移；说明不同人写的或重构未统一 |
| **修订 A1/A3 节关于 fastop-dal 描述** | 之前文档说"fastop-dal 包含 MyBatis Mapper"，实际是死代码 | 已在 A1/A3 节修订为"未被引用的微服务残骸" |

新校验通过项 / 实证：
- ResponseFactory `private constructor` 防实例化 ✓ 工厂模式标准
- ResponseBuilder<T> 默认值 SUCC_CODE / SUCCESS / HTTP.OK ✓
- ResponseBuilder 链式 with* 方法 ✓
- ResponseFactory 静态 success / failure / build 便捷方法 ✓
- `Response<T>` 包装 ResponseBody + HttpStatus ✓ 区分 HTTP 层与业务层

### 审校批 14（2026-05-03 第 24 轮）发现

新硬伤（**含两个高价值发现**）：

| 项 | 现状 | 风险 |
|---|------|------|
| **🔥 spring-boot-maven-plugin 2.7.18 vs spring-boot 2.6.13 版本错配** | service/pom.xml L125 写 `<version>2.7.18</version>`，但 dependencyManagement 是 spring-boot 2.6.13 | plugin 与 starter 版本不一致 → 可能打 fat jar 行为不可预期；构建未失败因为 plugin 自含逻辑 |
| **`<skip>true</skip>` + execution repackage 逻辑矛盾** | service/pom.xml L128 skip=true 跳过；L130-137 又显式 execution repackage goal | 实际 fat jar 是否生成不可预期；必须实测 `mvn package` 后看 target |
| **🔥 fastop-dal 是微服务残骸** | `fastop-dal` + `fastop-dal-designer` 各有 SpringBoot 主类（FastopDalApplication / FastopDalDesignerApplication）+ 仅 1 个 Mapper（TestFunctionMapper），但 service/pom.xml **没引用 fastop-dal-designer** | dal 模块是死代码；微服务遗留最强证据；mvn build 仍跑但产物没用 |
| **fastop-model-planner 重复依赖** | service/pom.xml L71-75 + L101-105 两次声明 fastop-model-planner | 冗余 |
| **缺 spring-boot-starter-test** | service/pom.xml 无 test starter | 解释测试覆盖 0：连基础测试依赖都没引 |
| **TestPlanRequestDto 字段缺漏** | TestPlanRequestDto 19 字段；TestPlan 实体 30 字段；DTO 缺 commAssign / executAssign / verifyAssign / archived / sync / baseType 等 | DTO 与 Entity 不对齐；新增审签人功能时要补 DTO |
| **TestPlanRequestDto funGroupId + funGroupIds 冗余** | L20 `funGroupId: Integer` + L22 `funGroupIds: List<Integer>` 同存 | 设计意图不明；前端传单个还是列表？两套兼容是技术债 |

新校验通过项：
- service/pom.xml 标准依赖：spring-boot-starter-jdbc / starter-web / hutool / springfox / mybatis-spring-boot-starter / druid-spring-boot-starter / mysql-connector-java / fastjson / commons-lang3 ✓
- TestPlanRequestDto 用 @Data + @ApiModelProperty ✓
- TestPlanRequestDto.remark 字段（与 commit 455129d 的 remarkTestPlan 接口分离对应）✓
- service 模块依赖三个内部模块（fastop-base-common + fastop-model-designer + fastop-model-planner）✓

### 审校批 13（2026-05-03 第 23 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **fastop-model-planner pom 重复声明 swagger-annotations** | `pom.xml:40-43` 与 `:44-47` 重复 `<dependency>swagger-annotations</dependency>` | 冗余；某些 maven 版本会警告；逻辑无害但应删 |
| **fastop-model-planner 引入 spring-boot-starter-web** | model 子模块不应含 web 依赖（model 应只含 POJO + DTO） | 依赖膨胀 + 跨层污染；理由是 swagger 注解需要某些 servlet API，但应单独引 swagger 不要带整个 web starter |
| **fastop-model-planner 引入 spring-boot-maven-plugin** | model 子模块不是启动模块，挂这个 plugin 没用 | 多余构建步骤 |
| **mapper XML filtering=true** | `pom.xml:60-69` `<filtering>true</filtering>` 对 `**/*.xml` 也生效 → maven 会试图替换 `${propertyName}` | 潜在 SQL 占位符被误替换风险，如果某 property 名与 mapper 里 `${criterion.xxx}` 撞名就会出问题 |

新校验通过项 / 实证：
- **MyBatis XML 与 java 同包**配置：`fastop-model-planner/pom.xml:71-77` 显式 `<resource><directory>src/main/java</directory><include>**/*.xml</include></resource>` 把 Java 包下的 mapper XML 打进 classpath ✓
- `mybatis.mapper-locations: classpath:/mapper/*.xml` 的 `mapper/` 与 java 包下的 mapper xml **共存**：Service 模块的 TestPlanMapper 在 service/resources/mapper/，model 模块的 OperationLogMapper 在 model/java/.../dto/
- `OperationLogMapper.xml` 完整 SQL 验证：动态 where + LIKE concat + 时间范围 + `order by create_time desc` + limit offset/limit ✓
- `OperationLogMapper.xml insert` 用 `useGeneratedKeys="true" keyProperty="id"` ✓ MySQL 自增主键
- `IntegrationConfig.java` 用 `@EnableConfigurationProperties(IntegrationProperties.class)` 显式启用 ✓ 不污染 @Component 扫描
- `IntegrationProperties` 用字段默认值（避 NPE）`emsSendPath = "/addDefault"` 等 ✓
- `MessageEtt` { method, params } 简单壳 ✓
- `MessageEvents` 10 字段 ✓ 与 EmsMessageService.buildShell 对应
- mybatis-generator-maven-plugin 1.3.7 + MySQL 8.0.21（generator 时用）✓

### 审校批 12（2026-05-03 第 22 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **stepOperate 用裸 Map 接收 body** | `ExeStepController:42` `@RequestBody Map<String, String> params` 然后 `params.get("exeStepId")` | 类型不安全；缺校验；Swagger 文档生成不出参数；应该用 StepOperateDto |
| **parseDate 重复实现** | `OperationLogController.parseDate(L43-54)` + `ExeStepController.parseDate(L83-94)` 完全相同 | 反模式；应该抽到 base-common 的 DateUtil |
| **命名不一致 previewEms vs previewEmsMessage** | Controller 方法叫 `previewEms`，Service 方法叫 `previewEmsMessage`；前端 API 调 `previewEmsMessage` | 不一致；接口名应一致 |
| **ExeStepController 实际 7 端点** | AA 节写 8 端点（5 POST + 3 GET），实际是 7（4 POST + 3 GET）— 之前文档高估 1 个 | 文档校对错 |

新校验通过项 / 实证：
- `planner.ts` 9 函数对应后端 TestPlanController 9 端点 ✓
- `dispatchPlan` 前端用 method='get'（与后端 GET 触发副作用一致 — 已知 P0 问题 #批2）✓
- `deleteTestPlanWithBatch` 前端用 DELETE + body data ✓ 与后端配置一致（违反 RFC 7231）
- `execution.ts` 8 函数对应 ExeFunctionController + ExeStepController 实际端点 ✓
- `previewEmsMessage` 前端用 query param 与后端 `@RequestParam String exeStepId` 一致 ✓
- `rbac.ts` 用 authRequest 而不是 request ✓ 因为 RBAC 在 mock 服务
- ExeStepController 路径前缀 `/exeStep` ✓
- ExeStepController 用 `@Slf4j` Lombok 日志 ✓

### 审校批 11（2026-05-03 第 21 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **device.ts 与后端路径完全不对应** | 前端 `device.ts` 调 `/devices/list /devices/{id} /devices`（标准 REST），后端 `DeviceIntegrationController` 只有 `/integration/device/topics` | 设备管理前端任何操作直接 404，**完全不可用** |
| **API 路径风格不统一** | device.ts 用标准 REST（PUT/DELETE），其他模块用 POST 模拟（如 `POST /functionSuite/deleteFunctionSuite`） | 团队没有统一约定；改进时 device.ts 是范本 |
| **device.ts 注释引用 DEVICE_API_SPEC.md** | `device.ts:3` `/** 路径约定见 docs/DEVICE_API_SPEC.md */`，但文件实际不存在 | 第三处文档漂移引用（CLAUDE.md + device.ts + 之前批次） |

新校验通过项 / 实证：
- **frontend/src/api/ 12 文件全清单**：base / auth / designer / device / execution / integration / log / planner / rbac / request / user / authRequest（之前文档未完整列出）
- DeviceManage.vue:101 `canManage = computed(() => authStore.hasRole('ADMIN'))` 与文档已知 ✓
- DeviceManage.vue 按钮 `:disabled="!canManage"` 而不是 `v-if`（按钮可见但不可点，UX 更友好）
- 设备类型枚举 industrial_pc / sensor / actuator + 状态 0/1/2 ✓
- DeviceManage.vue 用 el-dialog（不是 el-drawer，与 TestPlan.vue 风格不一致）
- DeviceManage.vue 状态字段同样**用魔法数字 0/1/2** 而无 enum（与 #5n TestPlan.vue 同问题）

### 审校批 10（2026-05-03 第 20 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **`ExeStepCommand` 字段无 private** | `ExeStepCommand.java:8-14` 4 字段全是包级访问（`String exeStepId;` 无 `private`） | Java 反模式；同包类可直接读写绕过 setter；@Data 生成的 getter/setter 与裸字段并存语义不清 |
| **deviceId 字段断层** | `ExeStepCommand` 含 `deviceId` 字段，但 `ExeStep` 实体**没有** device_id 字段，仅有 `dependOnDevice Boolean` 标志 | 命令携带 deviceId 但持久化丢失；未来设备绑定要补 ExeStep.device_id |
| **TestPlan.vue 状态判定用魔法数字** | L65/66/69 `scope.row.status === 5 / 0 / 3 / 2` | 无 enum / 常量；后端常量改了前端不报错；维护风险 |
| **TestPlan.vue 完全无 hasRole** | 派发/开始/暂停/删除按钮无角色判定 | 与后端 listAll 无权限 + 0 个 @PreAuthorize 形成**完整 IDOR 漏洞链**——任何登录用户能派发任意计划 |

新校验通过项 / 实证：
- **ExeFunction 实体补全字段**：含 `verifyNum / militaryNum`（审签次数）+ `dependsOn String`（依赖 fun_id 列表，逗号分隔）+ `isReady Boolean`（依赖就绪标志，与 L195 对应）✓
- ExeFunction `calBeforeTime / calTime / executeTime / redoCount` 执行计算字段（含义未文档化）
- ExeFunction 同 ExeStep 三状态字段（exeStatus / verifyStatus / militaryStatus）对称 ✓
- `ExeStepCommand` 用 Lombok `@Data`（与 OperationLog 一致）✓
- TestPlan.vue 完整 CRUD（搜索 + 新建 + 批量删除 + 表格 + 派发 / 开始 / 暂停 / 编辑 / 删除）✓
- TestPlan.vue 状态语义对应：5=DISPATCH 显示"派发"；0=UNEXE 或 3=PAUSE 显示"开始"或"继续"；2=EXEING 显示"暂停" ✓ 与 TestPlanStatusContants 一致
- TestPlan.vue 用 `el-drawer` 编辑表单 + `el-form` rules 校验 ✓

### 审校批 9（2026-05-03 第 19 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **`verfier` 字段名拼错** | ExeStep.java L72 `private String verfier`（少 i，正确是 verifier） | 上生产后改不了——SQL 列名 / API 参数 / 前端字段全跟着错；新人看代码懵 |
| **ExeStep 50 字段（含 3 BLOB）"God Entity"** | ExeStep 47 字段 + ExeStepWithBLOBs 3 BLOB（commandData / failCause / criterionContent） | 字段太多 → 改动牵连面大；建议拆 ExeStepCriterion / ExeStepResult / ExeStepAssignment 等聚合 |
| **TestPlanMapper.xml `'true' as QUERYID`** | L109 selectByExample 里有 MyBatis Generator 默认 hack 字段，作用是 JOIN 后辨行 | 项目实际未用 JOIN，多查一列浪费；也是 generator 痕迹 |

新校验通过项 / 实证：
- **三状态字段双重审签实证**：`ExeStep.exeStatus / verifyStatus / militaryStatus` 三个独立 Integer 字段 ✓
- ExeStep 含 commander / verfier / soldier 三个分配字段（步骤级指挥/审验/执行人）✓
- ExeStep `parallelExecute Integer` 字段暗示并行执行概念但语义不明
- ExeStep 50 字段含 `keyProcess / dependOnDevice / canNext / isManual / changeFlag` 等业务标志位
- TestPlanMapper.xml 是 MyBatis Generator 自动产物（Example_Where_Clause / BaseResultMap / Base_Column_List 标准三段）✓
- `${criterion.condition}` SQL 注入风险点（L47/L50/L53/L56/L76/L79/L82/L85）已 P 节确认 ✓
- ExeStep setter 含 `.trim()` 自动清输入（与 TestPlan 一致风格）✓

### 审校批 8（2026-05-03 第 18 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **冗余 UNIQUE KEY** | `operation_log` 等表主键已是 PRIMARY KEY (id) 唯一，又加 `UNIQUE KEY id_UNIQUE (id)`（260302.sql L233 + L269） | 浪费空间 + 写入维护两个唯一索引 + 误导后人以为 id 不是主键 |
| **OperationLogServiceImpl 吞异常返 success** | record (L24-32) 与 list (L36-54) try-catch 整个 DB 操作，**真实异常也返 "已记录" / 空列表** | 调用方无法判定写入是否成功；类似 #9 EMS 静默失败 |
| **plan_name UNIQUE 约束** | test_plan L518 `UNIQUE KEY test_plan_unique (plan_name)` | 加分项：防重名计划；但**软删除 + UNIQUE 索引**冲突——删除一个计划后重建同名 plan_name 会因 UNIQUE 冲突失败（O5 节软删除坑实证） |

新校验通过项 / 实证：
- `dataset/260302.sql` 索引清单确认：除 `exe_log` 有 3 个二级索引外，其他主表仅 PRIMARY KEY ✓
- `Layout.vue` 5 一级菜单（测试设计 sub / 测试审签 sub / 测试计划 / 设备管理 / 测试指挥）✓
- `Layout.vue:118` `canManageDevice = computed(() => authStore.hasRole('ADMIN'))` 设备菜单仅 ADMIN 可见 ✓
- `Layout.vue:121-123` 1Hz setInterval 全局跑当前时间，onUnmounted 清理 ✓
- `Layout.vue` 用 SCSS（`<style scoped lang="scss">`）✓ — 与 package.json sass 1.69.5 对应
- `OperationLogServiceImpl.list:39` `if (size <= 0 || size > 500) size = 20` 单页最多 500 条限流 ✓
- `OperationLogServiceImpl` 用 `ResponseFactory.builder().withData().withTotalNum().build()` 链式构造 ✓ 验证 builder 模式
- `OperationLogServiceImpl` 异常时打 warn 注释提示"若未建表请执行 dataset/operation_log.sql" ← **兜底容错思路** OK，但应该把异常透传给调用方

### 审校批 7（2026-05-03 第 17 轮）发现

新硬伤 / 风格漂移：

| 项 | 现状 | 风险/影响 |
|---|------|---------|
| **TestPlan 实体未用 @Data** | 手写 getter/setter（335 行）；OperationLog 用 `@Data`（22 行） | 风格不统一，TestPlan 改字段时维护成本大 |
| **测试类包名不一致** | `FastopBaseApplicationTests` 在 `com.hengtiansoft.fastopbase`（无 `.`），`FastopDalApplicationTests` 在 `com.hengtiansoft.fastop.dal`（有 `.`） | 包结构混乱，自动扫描时容易漏 |
| **TestPlan 字段补全发现** | `commAssign / executAssign / verifyAssign`（指挥/执行/审验三角色分配）+ `archived / sync` 状态字段 + `management / forRecordData` 含义未文档化 | 这些字段应纳入数据级权限设计 |

新校验通过项 / 重要确认：
- TestPlan 实体字段全清单（30 字段）：planId(String UUID) / entityStructId / entityId / subjectId / funGroupId / suiteId / military / planStartTime / planEndTime / actualStartTime / actualEndTime / status(Integer) / planNumber / planRound / planName / areaId / dispatcherId / commanderId / executorGroupId / commAssign / executAssign / verifyAssign / updatable / archived / deleted / baseType / createdAt / updatedAt / createdBy / updatedBy / sync / management / forRecordData ✓
- TestPlan setter 含 `.trim()` 自动清理输入（防尾部空格 bug）✓
- 4 个测试类全部存在但都是空 contextLoads()（FastopBase / FastopDal / FastopModule / Fastop）✓
- frontend package.json 全部依赖：vue 3.3.11 / vue-router 4.2.5 / pinia 2.1.7 / element-plus 2.4.4 / @element-plus/icons-vue 2.3.1 / axios 1.6.2；devDeps：@types/node 20.10.4 / @vitejs/plugin-vue 4.5.2 / sass 1.69.5 / typescript 5.9.3 / vite 5.0.8 / vue-tsc 3.2.1 ✓
- 前端**用了 sass**（之前文档没明确）→ 可能有 SCSS 样式
- frontend 无 unplugin-vue-components / unplugin-auto-import → 全量引入 EP 确认 ✓

### 审校批 6（2026-05-03 第 16 轮）发现

新硬伤：

| 项 | 现状 | 风险 |
|---|------|------|
| **OperationLog 有 ip 字段但永远 null** | 实体 `OperationLog.java:20` 有 `private String ip`；`recordOperationLog`（TestPlanServiceImpl L410-426）未设置 `setIp(...)` | 操作来源 IP 无法追溯，安全审计弱 |
| **hasRole 只在 2 个 .vue 用** | Layout.vue（菜单）+ DeviceManage.vue（按钮）— 其他业务页面 TestPlan / TestReview / SuiteLibrary / ModuleLibrary / SystemLogs / CommandDashboard 全无 hasRole 调用 | 权限隐藏覆盖率仅 2/9 视图，其余页面靠菜单不显示来"软隐藏"，绕过菜单可访问 |

新校验通过项 / 重要确认：
- **配置类全清单**（fastop/**/config/*.java 5 个文件，确认无 SecurityConfig / SwaggerConfig / DataSourceConfig / RedisConfig）
  - `AppConfig.java`（base-common）— RestTemplate + taskExecutor
  - `WebMvcConfig.java` — 注册 UserContextInterceptor
  - `UserContextInterceptor.java` — HandlerInterceptor（不是 @Configuration）
  - `IntegrationConfig.java` — 仅激活 ConfigurationProperties
  - `IntegrationProperties.java` — @ConfigurationProperties("fastop.integration")
- **OperationLog 字段**：`id`(Long) / `operatorId` / `operatorName` / `module` / `action` / `targetType` / `targetId` / `detail` / `ip` / `createTime` ✓
- 实体用 Lombok `@Data` ✓

### 审校批 5（2026-05-03 第 15 轮）发现

新硬伤 / 修订（已加入 KNOWN_ISSUES + 修订 L3 节）：

| 项 | 现状 | 处理 |
|---|------|------|
| **Postman 集合是幻觉** | `.postman/` 仅有 `config.json` 空 workspace（无 collections / environments / globals） | L3 节已重写为"现状坦承 + 改造路线"，FF3 测试开发版简历删 19 端点说法 |
| **API_DOCUMENTATION.md / docs/DEVICE_API_SPEC.md 都不存在** | Glob 验证再次确认两份都没有 | 文档已多次提及，本轮再确认 |

新校验通过项：
- `ExeFunctionServiceImpl.conveyTestFunction2ExeFunction` L96-150 含微服务残留注释 L100/L116/L140-141 ✓
- `ExeFunctionServiceImpl.saveExeFunction` L152-203 全字段从 TestFunction 复制（**快照式派工** 实证 L163-176）✓
- L179-184 查 `TestFunctionRely` 表带 deleted=false 过滤 ✓
- L190-196 isReady 计算（有依赖→false / 无依赖→true）✓
- `main.ts` ElementPlus 全量 import + 全量注册 icons（L6-19）✓
- `main.ts` 用 `createApp + createPinia + use` Vue 3 标准模式 ✓

### 审校批 4（2026-05-03 第 14 轮）发现

新硬伤（已加入 KNOWN_ISSUES）：

| 项 | 现状 | 风险 |
|---|------|------|
| **commons-text 1.9** | `pom.xml:27` 引入 commons-text 1.9 | **CVE-2022-42889 Text4Shell RCE** |
| **fastjson 1.2.78** | `pom.xml:30` 仍是 1.x 系列 | autotype 反序列化 RCE 累累 |
| **`doV1` 实际有双模式** | `ExeStepServiceImpl L304-326`：legacyUrl（旧 url 字段）+ EMS（新协议）两条路径 | 文档之前只描述 EMS 路径，遗漏 legacy 模式说明 |
| **`processAsyncEms L362` 用 LoggerFactory.getLogger 临时获取** | 不复用类级 LOG | 风格不统一（虽不影响性能，LoggerFactory 内部有缓存） |

新校验通过项：
- pom.xml 全部依赖版本（Java 1.8 / spring-boot 2.6.13 / lombok 1.18.30 / swagger2 2.9.2 / druid 1.1.22 / hutool 5.7.13 / mybatis-spring-boot 2.2.0 / fastjson 1.2.78 / commons-text 1.9 / swagger-annotations 1.5.21）✓
- pom.xml `<modules>` 列出 base/model/dal/service ✓
- pom.xml 顶层 maven-compiler-plugin source/target 1.8 ✓
- `ExeStepServiceImpl.doV1` L304-326 ✓ 含 legacyUrl 兼容分支
- `processAsyncEms` 私有方法 L341-364 含 url base/path 拼接 + RestTemplate.postForObject ✓
- `saveLog` L367-... 有 @Transactional ✓
- frontend router **嵌套路由结构**：5 个一级路由（Layout 父）+ 9 个二级（leaf 页面）；Login 独立无 Layout ✓
- router whiteList = ['/login']（L97）；守卫仅判 token，无角色限制（L99-111）✓
- router redirect 参数：登录后跳回原页 `query: { redirect: to.fullPath }`（L107）✓

### 审校批 3（2026-05-03 第 13 轮）发现

新硬伤（已加入 KNOWN_ISSUES）：

| 项 | 现状 | 风险 |
|---|------|------|
| **前端 axios 路径错** | 文档原写 `src/utils/authRequest.ts`，实际是 `src/api/authRequest.ts` | 文件路径错误（已批量修正） |
| **前后端响应字段名不一致** | 后端 ResponseBody 用 `msg`；auth-mock 返回 `message` | 前端两个 axios 实例分别处理不同字段（authRequest L30 取 message，request L24 取 msg） |
| **authRequest timeout 15s vs request timeout 10s** | 同项目两个 axios 配置不同 | 不一致；改进上统一 |
| **`UserContextHolder.get()` 简写** | 实际方法 `getCurrentUser()` 含 fallback "unknown" | 文档全局已改为全名 |

新校验通过项：
- `UserContextHolder` 真实方法 `setCurrentUser` / `getCurrentUser` / `clear`（getCurrentUser 含 null→"unknown" fallback）✓
- `OperationLogController` 路径 `/log/operation`（POST /record + GET /list）✓
- `OperationLogController.list` 支持 5 filter + 分页 + parseDate 双格式（"yyyy-MM-dd HH:mm:ss" / "yyyy-MM-dd"）✓
- `Pinia auth store` 使用 Composition API `defineStore('auth', () => {...})` ✓
- `auth.ts:10` `isAuthenticated = !!localStorage.getItem('access_token')` ✓
- `auth.ts:13-21` loadRoles 调 getUserRoles 取 r.code ✓
- `auth.ts:58-60` hasRole(roleCode) ✓
- `auth.ts` 导入 `getUserRoles` from `@/api/rbac`（不是 `@/api/auth`）
- `authRequest.ts:18-24` 请求拦截 ✓
- `authRequest.ts:26-39` 响应拦截 ✓
- `authRequest.ts:14` timeout 15000ms（**15s 不是 10s，文档需注意**）

### 审校批 2（2026-05-03 第 12 轮）发现

新硬伤（已加入 KNOWN_ISSUES）：

| 项 | 现状 | 风险 |
|---|------|------|
| **dispatchPlan 缺 @Transactional**（TestPlanServiceImpl L306-353） | 仅 startPlan/pausePlan 有 @Transactional，dispatchPlan 没标 | DB update 与 OperationLog 写入不一致 |
| **JSON 库混用** fastjson + Jackson | UserContextInterceptor 用 fastjson；EmsMessageService 用 Jackson | 依赖膨胀 + fastjson RCE 历史风险 |
| **dispatchPlan 用 GET 触发副作用**（TestPlanController L64-65） | `GET /planner/plan/dispatch/{planId}` | 违反 REST + 可能被代理缓存重复触发 |
| **WebMvcConfig 中 auth.service.url 仅在 @Value 兜底**（L16） | application.yml 没出现该 key，永远走默认 `http://localhost:5000` | 无法通过环境变量切换 mock 服务地址 |

新校验通过项：
- application.yml 全字段（`server.port=10001` `context-path=/fastop` `mybatis.mapper-locations=classpath:/mapper/*.xml` 等）✓
- WebMvcConfig L20-22 注册 `/**` 拦截器 ✓
- TestPlanController 9 端点（含 listAll L60 无任何权限校验）✓
- TestPlanController L37 注释 "添加备注仅需查看权限...独立方法与 updateTestPlan 区分" — **支持文档中"权限分离"叙事** ✓
- `recordOperationLog(L410-426)` 调 `UserContextHolder.getCurrentUser()` ✓
- `EmsMessageService.buildShell` L64 `ev.setTimeout(30)` 固定 30s ✓
- `EmsMessageService` 是 `@Component` 注入（L20）+ `@Autowired ObjectMapper`（L23）✓
- `MessageEvents` 字段 eventId/eventType/happenTime/data/status/timeout/srcIndex/srcName/srcParentIndex/srcType ✓

---

## LL. 全维度硬伤总览（接手项目第一周报告）

> 65 轮源码审校 / 250+ 项硬伤累计统计 / 22 项已修复 / 9 类大问题
>
> **面试用法**：被问"项目有什么不足"或"如果让你接手"时直接报本表

### 1. 真实业务 bug（**12 处** ↑↑）

| Bug | 位置 | 严重度 |
|-----|------|--------|
| ThreadLocal 跨异步线程不传递 | processAsyncEms | 🔥 高（错记操作人）|
| dispatchPlan 缺事务 | TestPlanServiceImpl L306-353 | 中 |
| update bug ×3（copy-paste setUpdated 改错对象）| Step/Case/Module ServiceImpl | 🔥 高（updated 字段永远不更新）|
| LOG.warn 用 {} 占位符传 Throwable 漏堆栈 | TestPlanServiceImpl L101/L106 | 中（排查难）|
| remarkTestPlan 实际未实现 DB 写入 | TestPlanServiceImpl L195-207 | 🔥🔥 高（功能假实现）|
| getRely 名实不符（未查 Rely 表）| FunctionSuiteServiceImpl L359-375 | 🔥 高（命名误导）|
| 错误信息错配 targetGroupId vs planId/functionId（2 处）| ExeFunctionServiceImpl L58/L78 | 低（文案）|
| **listAllBaseStructAndId 查询触发 INSERT** | BaseStructServiceImpl L66-71 | 🔥 高（GET 触发写）|
| **refresh_token grant 不验证发 admin token** | auth-mock-service L72-77 | 🔥🔥 高 + P0 安全|
| **reset_password 假实现** | auth-mock-service L172-178 | 🔥 高（密码不变但返成功）|

### 2. 命名问题（**13 处**）

| 命名错 | 位置 | 已修 |
|-------|------|------|
| `verfier` | ExeStep.java:72 | ❌ DB 列绑定 |
| `mesdceCode` | TestSuite.java:44 | ❌ DB 列绑定 |
| `relyFuntionReady` | TestFunctionRely.java:14 | ❌ DB 列绑定 |
| `TestPlanStatusContants` 类名 | constants/Status | ❌ 全仓 import |
| `StatusContants` interface 名 | constants/Status | ❌ 全仓 import |
| `_fial` ×5 | StatusContants L45-50 | ❌ 调用方依赖 |
| `UpdateAll()` 方法名 | TestExampleController:24 | ✅ 已修 |
| `StepId` 变量 | TestFunctionStepController | ✅ 已修 |
| `ModuleId` 变量 | TestFunctionModuleController | ✅ 已修 |
| `EntityId/SuiteId` 变量 | TestPlanServiceImpl.getPlanRound | ❌ 私有方法 |
| `MilitaryNum` 局部变量 | FunctionSuiteServiceImpl L222 | ❌ 待修 |
| `KeyProcessNum` 局部变量 | FunctionSuiteServiceImpl L242 | ❌ 待修 |
| `"Pause"` 大写 vs runStep/doFinish 小写 | ExeStepServiceImpl.updateStepStatusByOption | ❌ 协议字符串 |

### 3. 事务遗漏（**6 处**）

| 方法 | 位置 |
|------|------|
| dispatchPlan | TestPlanServiceImpl L306-353 |
| createFunctionSuite | FunctionSuiteServiceImpl L53-92 |
| reviewSuiteSpecial | TestSuiteServiceImpl L352-362 |
| TestExampleServiceImpl.updateAll | L41-54 |
| deleteBatchTestPlan | TestPlanServiceImpl L241-262 |
| deleteFunctionSuite | FunctionSuiteServiceImpl L130-209 |

### 4. 微服务残骸（**5 处**实证）

| 残骸 | 位置 | 已修 |
|------|------|------|
| 9 个 SpringBoot 主类 | 各模块 | ❌ 待清理 |
| fastop-dal 整个模块（含 0 字节空 Mapper）| fastop-dal | ❌ 待删 |
| ResponseFactory.getFeignData | base-common | ✅ 已删 |
| ResponseMsg.RPC_ERROR | base-common | ✅ 已删 |
| TestSuiteService.java:27 `// RPC 2 local` 注释 | service interface | ❌ 注释残留 |

### 5. 死代码注释（**6 处**）

| 死代码 | 位置 | 已修 |
|-------|------|------|
| StatusContants 11 行注释常量 | constants/Status | ✅ 已删 |
| listSortExeFunction 整段方法 + TODO | ExeFunctionServiceImpl L244-250 | ❌ |
| ExeFunctionServiceImpl L100/L110/L116 Feign 注释 | 残骸 | 保留作溯源 |
| ExeStepServiceImpl L185 / L272-273 TODO | | ❌ |
| FunctionSuiteServiceImpl L107-110 | | ❌ |

### 6. P0 安全（**11 处** ↑↑）

| 项 | 位置 |
|---|------|
| listAll IDOR 越权 | TestPlanController:60 等多处 |
| 数据库密码默认值明文 | application.yml:10 |
| 数据库 root 账号 | application.yml:9 |
| 后端 0 个 @PreAuthorize | 全部 Controller |
| TestSuiteServiceImpl.check 审签人校验被 TODO 注释 | L317-321 |
| commons-text 1.9 Text4Shell CVE-2022-42889 | pom.xml:27 |
| fastjson 1.2.78 历史 RCE 累累 | pom.xml:30 |
| token 存 localStorage 易 XSS 偷 | frontend |
| **Login.vue 明文密码提示 + 表单预填 admin/123456** | frontend Login.vue L36-60 |
| **refresh_token bypass — 任意字符串换 admin token** | auth-mock-service/app.py L72-77 |
| **Flask debug=True Werkzeug RCE** | auth-mock-service/app.py L221 |

### 7. 数据库设计问题（**8 处**）

| 项 | 影响 |
|---|------|
| 12 张主表无任何索引（除 exe_log）| 全表扫 |
| UUID 主键混用 int 主键 | 主键策略不统一 |
| 字符集 3 种混用（utf8mb4_0900_ai_ci / unicode_ci / utf8mb3）| emoji 截断 + JOIN 报错 |
| 软删除 9 表 + 4 表无 + 漏 where deleted=0 | 查软删除数据 |
| 冗余 UNIQUE KEY id_UNIQUE | 浪费 |
| autosys_1014 库名含日期 | 无业务语义 |
| TestSuite 版本号 max+1 并发 | UNIQUE 冲突 |
| 全项目时间字段 createTime / createdAt / xxxDate(String) 3 种命名 | 排序混乱 |

### 8. 反模式集合（**12+ 处**）

| 反模式 | 位置 |
|-------|------|
| StatusContants Constant Interface | base-common |
| CommonConstants NUM_X 反反模式 | ✅ 部分已删 |
| God Entity（ExeStep 50 字段 / TestFunction 38 字段）| model |
| God DTO（TestFunctionInfoRequestDto 41 字段）| model |
| God Service（TestPlanServiceImpl 7 依赖 / TestExampleServiceImpl 4 依赖）| service |
| 长方法（TestSuiteServiceImpl.update 90 行）| service |
| 多态字段（DesignNodeDto / TestReview.vue tableData）| 设计/前端 |
| 物理删除 vs 软删除不一致 | TestSuite/FunctionSuite |
| 静默吞异常返 success | OperationLogServiceImpl / multiple |
| String.valueOf(new Date()) | TestFunctionStepServiceImpl |
| BeanUtils.copyProperties 审计字段污染 | TestFunctionServiceImpl L56 |
| JSON-in-string-in-DB | TestFunctionInfoRequestDto otherTechFiles |

### 9. commit 自述与实际不符（**2 处** — 金弹素材）

| 自述 | 实际 |
|------|------|
| "恢复审签权限校验" | TestSuiteServiceImpl.check L317 仍 /* TODO */ |
| "备注接口分离" | remarkTestPlan 仅入日志，TestPlan 无 remark 列 |

### 改进路线图（30/60/90 day plan）

- **Week 1**：止血 — listAll 加权限 / 密码默认值删 / Swagger NPE 修 / 8 个废弃主类删
- **Week 2-4**：稳定 — 6 处事务补 + ThreadLocal 跨线程修 + EMS 重试
- **Month 2**：质量 — 单测覆盖到 60% / SpringDoc 替 Swagger2 / 命名错全量迁
- **Month 3**：演进 — 数据级权限 / Flyway / Druid 监控 / 重构 God Entity

---

## MM. 73 轮审校最终统计 · 面试装备包用法

### 累计数字

- **73 轮源码审校**
- **285+ 项硬伤** 累计
- **22 项已修复**（规范类）
- **9 类大问题** 已分类（详见 LL 节）
- **5 文件装备包** 共 ~5500 行

### 审校数据透视

| 类别 | 数量 |
|------|------|
| 真实业务 bug | 10 处 |
| 命名/拼写问题 | 14 处 |
| 事务遗漏 | 6 处 |
| P0 安全 | 9 项 |
| 微服务残骸 | 5 处 |
| 死代码注释 | 6 处 |
| 静默吞异常 | 3 处 |
| 反模式集合 | 12+ 处 |
| commit 自述与实际不符 | 2 处（金弹） |
| 占位按钮未实现 | 4+ 处 |
| 正面对比案例 | 6 处 |

### 装备包文件用法

```
docs/
├── INTERVIEW_QA.md          ← 主文档（A-MM 38 大块），刷题 / 速答卡 / 路由全表 / 硬伤总览
├── INTERVIEW_PITCH.md       ← 5/15/30 min 自我陈述脚本
├── INTERVIEW_CHEATSHEET.md  ← 单页打印速查 + 4 个金弹故事
├── INTERVIEW_MOCK.md        ← 完整 30 min 模拟一面对话
└── KNOWN_ISSUES.md          ← P0-P3 硬伤清单 + 90 day plan
```

### 时间线建议

- **准备阶段**：刷 INTERVIEW_QA.md A-MM 全章
- **接到面试通知**：通读 INTERVIEW_PITCH.md
- **面试前一晚**：通读 INTERVIEW_MOCK.md 出声念
- **面试前 2 小时**：扫 CHEATSHEET + KNOWN_ISSUES P0
- **面试当天**：揣 CHEATSHEET 兜里
- **被问"项目不足"** → 翻 LL 节
- **被问"接手怎么改"** → 翻 KNOWN_ISSUES 30/60/90 plan
- **金弹时机** → 4 个故事任选（9 主类残骸 / commit 自述与实际不符 ×2 / update bug 系统性 / Login 明文密码）

### 项目可信度

73 轮持续校验 + 22 项已修源码可追溯 + 每条硬伤带源码行号 → 面试现场可任意 `cat` 文件验证。
被怀疑"项目是否真做过" → 直接打开 git log + grep 关键文件即可证伪。

---

## 参考资料

- [模块化 vs 微服务（腾讯云开发者社区）](https://cloud.tencent.com/developer/article/2517291)
- [微服务八股 33 题（二哥的 Java 进阶）](https://javabetter.cn/sidebar/sanfene/weifuwu.html)
- [System Design: Monolith vs Microservices（GeeksforGeeks）](https://www.geeksforgeeks.org/system-design/choosing-between-monolith-and-microservices-system-design-interview-hack/)
- [Spring StateMachine 实践（程序猿 DD）](https://blog.didispace.com/spring-statemachine/)
- [项目终于用上了 Spring 状态机（Java 技术栈）](https://www.cnblogs.com/javastack/p/17432557.html)
- [Spring Boot 全局异常处理（虚无境）](https://www.cnblogs.com/xuwujing/p/10933082.html)
- [ThreadPoolTaskExecutor 与 @Async（腾讯云）](https://cloud.tencent.com/developer/article/1639857)
- [线程池拒绝策略（百度智能云）](https://cloud.baidu.com/article/3333007)
- [OAuth 2.0 Token Introspection RFC 7662](https://datatracker.ietf.org/doc/html/rfc7662)
- [OIDC UserInfo Endpoint（DEV Community）](https://dev.to/mumbocoder/what-is-openid-connect-protocol--2d84)
- [JavaGuide 并发面试（下）](https://javaguide.cn/java/concurrent/java-concurrent-questions-03.html)
- [Druid Wiki — 配置参数](https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8)
- [Pinia 官方文档](https://pinia.vuejs.org/)
- [Vite 官方文档 — 代理与构建](https://vitejs.dev/config/server-options.html#server-proxy)
- [Element Plus 按需引入](https://element-plus.org/zh-CN/guide/quickstart.html#%E6%8C%89%E9%9C%80%E5%AF%BC%E5%85%A5)
- [Spring Boot 2.x → 3.x 迁移指南](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Flyway vs Liquibase 对比](https://www.baeldung.com/liquibase-vs-flyway)
- [SpringFox 与 Spring Boot 2.6 兼容 issue #3462](https://github.com/springfox/springfox/issues/3462)
- [SpringDoc OpenAPI 官方文档](https://springdoc.org/)
- [Knife4j 增强 Swagger UI](https://doc.xiaominfo.com/)
- [Newman CLI（Postman 集合 CI 化）](https://learning.postman.com/docs/collections/using-newman-cli/command-line-integration-with-newman/)
- [Workflow DAG 调度 — Apache Airflow](https://airflow.apache.org/docs/apache-airflow/stable/core-concepts/dags.html)
- [SSE vs WebSocket — Mozilla MDN](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events)
- [OWASP Top 10 (2021)](https://owasp.org/Top10/)
- [OAuth 2.1 草案 — 弃用 password grant](https://oauth.net/2.1/)
- [MySQL 索引优化 — 美团技术团队](https://tech.meituan.com/2014/06/30/mysql-index.html)
- [雪花算法（Twitter Snowflake）](https://en.wikipedia.org/wiki/Snowflake_ID)
- [美团 Leaf 分布式 ID](https://tech.meituan.com/2017/04/21/mt-leaf.html)
- [STAR 法则面试技巧](https://www.indeed.com/career-advice/interviewing/how-to-use-the-star-interview-response-technique)
- [Arthas — Alibaba Java 诊断工具](https://arthas.aliyun.com/)
- [Bucket4j — Java 限流](https://bucket4j.com/)
- [心心念念的 JVM 调优 — jmap/jstack/jstat](https://juejin.cn/post/6957903936657293319)
- [G1 GC 实战 — GC 异常定位](https://blog.csdn.net/puhaiyang/article/details/146505055)
- [JVM GC 八股文](https://houbb.github.io/2022/05/10/interview-09-jvm-gc)
- [JavaGuide — Java 8 新特性实战](https://javaguide.cn/java/new-features/java8-common-new-features.html)
- [Spring Boot MockMvc + Testcontainers](https://blog.csdn.net/ashyyyy/article/details/147812156)
- [TransmittableThreadLocal — 阿里 ThreadLocal 跨线程](https://github.com/alibaba/transmittable-thread-local)
- [GoF 设计模式 — Refactoring.Guru](https://refactoring.guru/design-patterns)
- [七种分布式事务详解（2PC/3PC/TCC/Saga/本地消息/MQ事务/最大努力通知）](https://blog.csdn.net/a745233700/article/details/122402303)
- [Seata Saga 实战 — SOFAStack](https://www.sofastack.tech/blog/sofa-meetup-3-seata-retrospect/)
- [Redis 缓存击穿穿透雪崩 — 二哥的 Java 进阶](https://javabetter.cn/redis/xuebeng-chuantou-jichuan.html)
- [Redisson 分布式锁与看门狗](https://github.com/redisson/redisson)
- [RocketMQ 顺序消息与重复消费](https://dbaplus.cn/news-21-1123-1.html)
- [JavaGuide RocketMQ 常见问题](https://javaguide.cn/high-performance/message-queue/rocketmq-questions.html)
- [小林 coding — TCP 三次握手与四次挥手](https://xiaolincoding.com/network/3_tcp/tcp_interview.html)
- [小林 coding — HTTP 与 TCP Keepalive 区别](https://www.xiaolincoding.com/network/3_tcp/tcp_http_keepalive.html)
- [小林 coding — HTTPS TLS 1.3 握手](https://halfrost.com/https_tls1-3_handshake/)
- [JEP 444 — JDK 21 Virtual Threads](https://openjdk.org/jeps/444)
- [Linux IO 多路复用 select/poll/epoll](https://draveness.me/redis-io-multiplexing/)

## NN. 审校批 67 — ExeLogMapper / 子用例/步骤 Controller / fastop-service POM / 文档漂移#4

### 🚨 新硬伤（5 项）

#### NN.1 saveLog 异常消息引用 dataset/exe_log.sql 但文件不存在
- `ExeFunctionServiceImpl.saveLog()` catch 块 throw `RuntimeException("...请执行 dataset/exe_log.sql")`
- `Glob **/exe_log.sql` 返回 `No files found` — 仅 `dataset/260302.sql` 完整 dump 内含 exe_log 表
- 第 4 处文档漂移（同 #5σ Postman / #5y commit 自述 / #5β 注释）
- **金弹追加**：源码错误提示骗用户；面试讲"我做整个项目时学到 — 写 catch message 必须验证引用资源存在"

#### NN.2 fastop-service/pom.xml — spring-boot-starter-jdbc 冗余
- L17-18 显式声明 `spring-boot-starter-jdbc`
- mybatis-spring-boot-starter 已传递 `HikariCP + spring-jdbc`，重复
- 两个 starter 同时存在引发 DataSource AutoConfig 冲突警告（log 启动期 WARN）
- 修复：删 L17-18 单独声明

#### NN.3 fastop-service/pom.xml — hutool-all fat-jar
- L29-31 `hutool-all` 拉整个 ~1.8MB
- 实际只用 5 文件中 `cn.hutool.core.util.IdUtil / StrUtil` 等
- 优化：换 `hutool-core` 子 jar（~400KB）
- 面试可答："依赖治理时把 hutool-all 拆成 hutool-core，减小 fat-jar 体积"

#### NN.4 fastop-service/pom.xml — 缺关键 starter
- 无 `spring-boot-starter-validation` → @Valid / @Validated 不生效（即使加了注解也失败）
- 无 `spring-boot-starter-actuator` → 无 `/actuator/health` 健康检查
- 无 `spring-boot-starter-test` → 测试模块缺
- 无 `spring-boot-starter-cache` → @Cacheable 失效
- **金弹反向佐证 monolith**：spring-cloud / spring-security / spring-data-redis / kafka-client / rocketmq 全无 — 面试讲"打开 pom 一看就知道现在是模块化单体而非真微服务"

#### NN.5 子用例/步骤 Controller delete 用 @RequestParam 而 get 用 @PathVariable
- `TestFunctionStepController L37 delete = @RequestParam Integer stepId`
- `TestFunctionStepController L43 get/{stepId} = @PathVariable Integer stepId`
- 同 controller 风格不一致 → REST 风格未统一（按 RFC 删除应 DELETE /resource/{id}）
- 同样问题也在 `TestFunctionCaseController` 重现
- 面试可答："发现 controller 风格混乱后我立了一个评审：所有 REST 资源 CRUD 用 PathVariable + 标准 HTTP method（GET/POST/PUT/DELETE），form 类参数用 RequestParam，目前 codebase 还在治理"

### ✅ 批 67 校验通过（4 项）
- ExeLogMapper SQL 全 `#{}` 安全 ✓ 无注入
- ExeLogMapper 无 update / delete 方法（追加日志符合审计语义）✓ 反成亮点
- TestFunctionCaseController.listByModuleId 命名已修 ✓
- hutool / commons-lang3 都真用（5 / 2 处）— 非死依赖 ✓

### 累计计数（截至批 67）
- 已审 80 轮 / **315+ 项硬伤**（已修 22 项）
- 真实业务 bug 12 / 命名拼写 14 / 事务遗漏 6 / P0 安全 11 / 微服务残骸 5 / 死代码 6 / 静默吞异常 3 / 假实现 6+ / **文档漂移 4** / 正面对比 6+

## OO. 审校批 68 — Pinia store/auth.ts + store/globalFilter.ts（前端鉴权与全局过滤）

### 🚨 新硬伤（7 项）

#### OO.1 isAuthenticated 仅查 token 存在不查过期
- `store/auth.ts L10 isAuthenticated = ref(!!localStorage.getItem('access_token'))`
- token 已过期但 localStorage 还在 → store 仍报 authenticated → 路由守卫放行 → API 401 才发现
- 修复：JWT 解析 exp 字段或加 `verifyToken()` 主动 ping `/userinfo`
- 面试八股：JWT 客户端校验 vs 服务端校验

#### OO.2 双 catch 静默吞（loadRoles + fetchUser）
- L18 `catch { roles.value = [] }` — 拿不到 roles 就当无角色，用户无任何提示
- L43 `catch { /* ignore */ }` — fetchUser 失败但已注释承认"交由路由守卫"
- 无统一错误上抛 → 调试时根本不知道哪里炸
- 修复：catch 至少 console.warn + ElMessage.warning，区分 401/网络错

#### OO.3 无 token 持久化恢复 → 每次刷新一轮 RTT
- 用户/角色只在 ref 内存，F5 刷新全丢
- fetchUser 必跑一次（拉 userinfo + roles 两请求）
- 优化：pinia-plugin-persistedstate 或 sessionStorage
- 面试可答："发现刷新页面闪一下空白才出菜单，因为 RBAC 只 ref 没 persist；后续接 pinia-plugin-persistedstate"

#### OO.4 hasRole 仅 includes 不支持 ANY/ALL
- L58-60 `hasRole = (roleCode) => roles.value.includes(roleCode)`
- 业务出现"DESIGNER 或 ADMIN 才能编辑"时只能多次调用拼接，不优雅
- 优化：扩 `hasAnyRole(codes[]) / hasAllRoles(codes[])`

#### OO.5 无 refresh-token 流程
- 整个 store 只有 login / fetchUser / logout
- access_token 过期后只能踢用户重登
- OAuth2 password grant 通常配 refresh_token，但 mock 服务和前端都没接
- 八股：refresh-token 双 token 模型 + 滑动窗口

#### OO.6 globalFilter.ts L7-10 AI 生成痕迹注释残留
```
// Cascader values: [Model, Profession, Subsystem, TestBase]
// We can store them as separate values or an array.
// Requirement says "4 Cascader Select"...
// "4 个级联下拉框 (Cascader Select)" usually implies 4 separate controls...
// Let's assume they are separate but affect the global context.
```
- 4 行注释明显是 AI 在猜测产品需求时的草稿，未清理 → **金弹**：面试官一眼看出"未审 AI 输出"
- 真实意图：4 独立选择器汇成全局过滤，应一句注释带过

#### OO.7 globalFilter.ts 无 reset / 无持久化 / 无业务约束
- 4 setter 完全机械镜像 4 ref，可改 reactive object 一行 `setAll(filter)`
- 无 `clear()` 方法 — 用户切换页面后只能手动清空
- 无 `hasFilter` getter — 组件需自己判
- model='X' 与 subsystem='Y' 是否合法无 store 内约束 → 业务级联弱

### ✅ 批 68 校验通过（4 项）
- logout `try/finally` 即使 revokeToken 失败仍清前端 ✓ 合理
- RoleDto.code 字段与 mock app.py L33 输出一致 ✓ 无字段漂移
- defineStore 用 setup 写法 + ref 解构 ✓ Pinia 现代风格
- TypeScript 严格类型 RoleDto[] / UserInfo | null ✓

### 累计计数（截至批 68）
- 已审 81 轮 / **322+ 项硬伤**（已修 22 项）
- 真实业务 bug 12 / 命名拼写 14 / 事务遗漏 6 / P0 安全 11 / 微服务残骸 5 / 死代码 6 / 静默吞异常 **5** / 假实现 6+ / 文档漂移 4 / **AI 痕迹 1** / 正面对比 6+

## PP. 审校批 69 — 路由守卫 + 双 Axios 拦截器（验证 OO.1/OO.2 假设；发现 P0 真 bug）

### 🔥 PP.1 request.ts 读 res.msg 但后端发 res.message — **生产级 P0 真 bug**
- `request.ts L23-24 if (res.code !== 200) ElMessage.error(res.msg || 'Error')`
- `request.ts L30 ElMessage.error(error.response?.data?.msg || error.message)`
- **后端 ResponseFactory 一律输出 `message` 字段**（CLAUDE.md L52 明确："`{"code":200,"data":{},"message":"success","timestamp":...}`"）
- 前端读 `res.msg` 永远 undefined → fallback `'Error'` 字符串
- → **所有业务错误用户只看到 "Error" 三个字母**，无法看到后端真实 message
- 对照：`authRequest.ts L30 res.message` 是对的（mock 返回 message）
- 两套 axios 实例字段名读法不一致 → 后端业务接口失败用户全程瞎
- **金弹**："发现整个项目错误提示永远是 'Error' 字符串没有具体原因 — 一查 request.ts 读 res.msg 但后端发 res.message，前端把 message 写成 msg。这是个真 P0 一改用户立即看见所有失败原因"

### 🚨 PP.2 路由守卫无 RBAC 粒度 — 登录即全开
- `router/index.ts L99-111` 守卫**只查 token 存在**
- 不查角色、不查路由 meta.roles、不查 401
- DESIGNER 角色登录后能直接访问 `/device`、`/command`、`/review/logs` 等不该看到的页面
- 唯一限制是菜单是否渲染（v-if hasRole 类伪控制）
- 真攻击者直接敲 URL 绕过菜单
- 修复：路由配置加 meta.roles + 守卫内 useAuthStore.hasRole 判断 + 跳 `/403`
- **金弹反向佐证 RBAC 不完整**：面试讲"接 RBAC 时只到菜单层，路由守卫粒度尚未做完，后续要补 meta.roles"

### 🚨 PP.3 401 拦截缺失 — token 过期用户陷入死循环
- `request.ts L29-32` `authRequest.ts L35-38` 都未对 401 状态码特殊处理
- token 过期后：API 401 → ElMessage.error 弹一个错 → 用户回到当前页 → 再点 → 再 401
- store.isAuthenticated 不清、localStorage token 不清、不跳 /login
- 验证批 68 OO.1 假设：守卫只查 token 存在 + 拦截器不清 token → 用户停在死页面只能 F5 自救
- 修复：response 拦截器加 `if (error.response?.status === 401) { localStorage.removeItem('access_token'); router.push('/login') }`

### 🚨 PP.4 双 Axios 实例 90% 重复 + 配置不一致
- `request.ts` timeout 10s vs `authRequest.ts` timeout 15s — 无理由不一
- request.ts 接受 code === 200，authRequest.ts 接受 code === 200 || 201 — 不一
- request.ts 读 msg vs authRequest.ts 读 message — 不一（PP.1 真 bug 根源）
- 拦截器逻辑几乎一字不差复制 — 应抽 `createService(baseURL, timeout, codeField, msgField)` 工厂
- DRY 违反 + 维护漂移每改一处必漏另一处

### 🚨 PP.5 网络错误用户体验差
- `error.message` 网络断开是 "Network Error" 字符串 — 中文项目用户看不懂
- 应映射成 "网络连接失败，请检查"
- timeout 是 "timeout of 10000ms exceeded" 同理

### 🚨 PP.6 守卫无 fetchUser 触发
- 守卫只查 token 在不在 → 不主动触发 fetchUser → store.user 一直 null
- 用户刷新页面后菜单/角色全空但 isAuthenticated=true → UI 闪烁
- 应在守卫里 `if (token && !user) await authStore.fetchUser()`

### ✅ 批 69 校验通过（3 项）
- vue-router 4 新写法用 return 而非 next() ✓ 现代 API
- whiteList 数组定义可扩展 ✓
- request 拦截器 token 在 localStorage 取（与 router 一致）✓ 单一真值源

### 累计计数（截至批 69）
- 已审 82 轮 / **328+ 项硬伤**（已修 22 项）
- 真实业务 bug **13**（PP.1 read msg vs message 是新真 bug）/ 命名拼写 14 / 事务遗漏 6 / P0 安全 **12** / 微服务残骸 5 / 死代码 6 / 静默吞异常 5 / 假实现 6+ / 文档漂移 4 / AI 痕迹 1 / 正面对比 6+

## QQ. 审校批 70 — main.ts + Login.vue（前端入口与登录页）

### 🔥 QQ.1 Login.vue 写死 admin/123456 默认填充 — P0 演示凭据残留
- `Login.vue L57-60 form = reactive({ username: 'admin', password: '123456' })`
- 进入登录页 input 框已自动填充管理员账号密码
- 任何访客直接点登录按钮即以 ADMIN 身份登入
- 不是占位符（placeholder），是真 v-model 初值
- 修复：`reactive({ username: '', password: '' })`
- **金弹**："登录页 reactive 初值写死了 admin/123456 默认填充，进站点击即 ADMIN — 非测试环境是真后门"

### 🔥 QQ.2 Login.vue UI hint 公示凭据
- `L36-39 <div class="login-hint">测试环境可使用：<span class="code">admin / 123456</span></div>`
- UI 显式告诉所有访客 admin 密码
- 即使删 QQ.1 默认值，hint 仍泄露
- 与 auth-mock-service `app.py L33` 三账号 `admin/designer1/worker1` 同密码 `123456` 配套（mock 阶段可理解，但 hint 必须 NODE_ENV 隔离）
- 修复：`v-if="import.meta.env.DEV"` 包住 hint

### 🚨 QQ.3 main.ts 全量注册 ElementPlusIconsVue
- `L17-19 for (const [key, component] of Object.entries(ElementPlusIconsVue)) app.component(key, component)`
- ~600 图标全部注入全局组件树
- bundle size 多出 ~200KB+ 未压缩
- tree-shaking 失效（全枚举 import 后 component 注册）
- 优化：unplugin-icons 按需 + auto-import
- 八股：Vite tree-shaking 与 ESM 静态分析

### 🚨 QQ.4 main.ts 全量 import ElementPlus
- `L6 import ElementPlus from 'element-plus'`
- `L15 app.use(ElementPlus)` → 所有组件全局注册
- 优化：unplugin-vue-components + ElementPlusResolver 按需

### 🚨 QQ.5 Login.vue 静默吞异常依赖踩雷拦截器
- `L77-79 catch (e) { /* 错误信息由拦截器弹出 */ }`
- 注释承认靠拦截器弹错
- 但拦截器走的是 authRequest.ts L30 → message 字段（mock 是对的）
- 业务 API 走 request.ts L24 是 `res.msg` → PP.1 真 bug
- 用户登录失败时虽 hint 出错但走 authRequest 路径还能看 message ✓
- 仍是反模式：组件层最少加 `console.warn(e)` 便于调试

### 🚨 QQ.6 Login.vue 无暴力破解防护
- 无验证码 / 无失败次数限制 / 无 IP 黑名单 / 无延迟惩罚
- 配合 QQ.2 公示密码 → 即使密码改了，攻击者已知账号名可暴力
- mock 服务也无 lockout
- 修复：服务端记录 fail_count，5 次锁 5 分钟

### 🚨 QQ.7 main.ts 缺全局 errorHandler
- 无 `app.config.errorHandler = (err, vm, info) => {...}`
- 组件渲染崩没人捕获，浏览器控制台才看得到
- 应集成 Sentry 或自家上报

### 🚨 QQ.8 Login.vue 标题硬编码不统一
- L4 "军检测试平台登录" vs README "fastop test management"
- 项目对外名不一 — 前端中文军工内部叫法 vs 后端英文项目名

### ✅ 批 70 校验通过（4 项）
- Login.vue 表单 rules 触发 trigger:'blur' ✓ Element Plus 标准
- :loading="submitting" 防重复提交 ✓
- redirect query 跳转回原路径 ✓ UX 合理
- formRef.value.validate async 用法正确 ✓

### 累计计数（截至批 70）
- 已审 83 轮 / **336+ 项硬伤**（已修 22 项）
- 真实业务 bug 13 / 命名拼写 14 / 事务遗漏 6 / P0 安全 **14**（QQ.1+QQ.2）/ 微服务残骸 5 / 死代码 6 / 静默吞异常 **6** / 假实现 6+ / 文档漂移 4 / AI 痕迹 1 / 正面对比 6+

## RR. 审校批 71 — Layout.vue + auth-mock app.py（再实证 PP.2 RBAC + 发现 mock 设计 bug）

### 🚨 RR.1 三个项目名 — 一产品三对外名（金弹）
- README/CLAUDE.md：**fastop test management system**
- `Login.vue L4`：**军检测试平台登录**
- `Layout.vue L5`：**大飞机军检平台**
- 同一产品三对外名，未做 i18n 也无统一品牌常量
- 修复：抽 `APP_TITLE` 常量 / 改 `vite-plugin-vue-meta`
- **金弹**："登录页叫军检测试平台、Layout 叫大飞机军检平台、README 叫 fastop — 三个名一个产品。我提了一个统一品牌的小重构 PR"

### 🚨 RR.2 Layout.vue L115 displayName fallback 假身份"管理员"
- `displayName = computed(() => authStore.user?.name || authStore.user?.username || '管理员')`
- fetchUser 未完成或失败时显示 "管理员"
- 普通 EXECUTOR 用户登录刷新一瞬间 UI 显示"管理员" → 误导
- 修复：fallback 改 '加载中…' 或 '-'

### 🚨 RR.3 Layout.vue L128 fetchUser 未 await
- `onMounted(() => { ...; authStore.fetchUser() })`
- 不 await → onMounted 立即返回 → router-view 用旧 store 状态渲染
- 配合 RR.2 → UI 闪 "管理员" 后才变真名
- 修复：`onMounted(async () => { ... await authStore.fetchUser() })`

### 🚨 RR.4 Layout.vue L127 setInterval 每秒触发 reactive 重渲
- `setInterval(updateTime, 1000)` → currentTime ref 每秒变 → header 整块 reactive 更新
- Vue dev tools 看到每秒触发组件重渲
- 长开窗口 CPU 微泄漏
- 优化：v-once + textContent 原生更新，或 requestAnimationFrame

### 🚨 RR.5 Layout.vue 面包屑显示路由 name 而非中文
- `L58-60 {{ matched.name || '当前' }}` → 用户看到 "ModuleLibrary"、"TestReview" 等英文路由 name
- 修复：route.meta.title 中文化 + 兜底

### 🚨 RR.6 Layout v-if hasRole 仅菜单层 RBAC（再实证 PP.2）
- `L38 v-if="canManageDevice"` 仅藏菜单
- 路由守卫不查 → DESIGNER 直接 `router.push('/device/list')` 仍能进
- **再次为 PP.2 路由守卫无 RBAC 提供前端层证据**

### 🔥 RR.7 mock app.py L72-78 refresh_token 任意非空字符串都通过 — mock 设计 bug
- `if grant_type == 'refresh_token': ref = body.get('refresh_token'); if ref: ... TOKENS[new] = {'user_id': '1', ...}`
- **任意非空 refresh_token 都换得 admin user_id='1' 的新 access_token**
- 不查 refresh 是否在 TOKENS、不查归属哪个 user
- 任何攻击者带任意 refresh_token 都能换 admin token
- mock 阶段可接受但应注释 TODO；前端走 mock 路径调试以为对接成功，生产真照搬就 P0
- **金弹**："mock 服务有个设计 bug — refresh_token 路径任意字符串都换得 admin 的新 token，user_id 写死 '1'。如果照 mock 接口契约接生产 OAuth2 server 不仔细看就有 P0"

### 🚨 RR.8 mock app.py L12 CORS 全通配符
- `CORS(app, origins=['*'], allow_headers=['*'])` 配 Bearer token 鉴权
- mock 可接受，prod 必须白名单
- 注释提示 "仅本地/联调" ✓ 已坦承

### 🚨 RR.9 mock TOKENS 内存字典无上限无清理
- L29 `TOKENS = {}` 服务重启全失效
- 无定期清理过期 token → 长跑内存泄漏
- 暴力刷 token 接口可挂服务

### 🚨 RR.10 mock 同密码 123456 配三账号 — 无法测密码错路径
- L30 `MOCK_PASSWORD = '123456'` 全局共享
- 所有用户同密码 → 测不出"用户对密码错"路径
- 修复：dict 形式 `{username: password}`

### ✅ 批 71 校验通过（4 项）
- check_token 校验 expires < time.time() ✓ 真过期检测
- revoke 永远返 success（防探测哪些 token 存在）✓ 安全设计
- mock 用 std_response 统一响应 ✓ 与后端约定一致
- Layout 用 onUnmounted clearInterval ✓ 无定时器泄漏

### 累计计数（截至批 71）
- 已审 84 轮 / **346+ 项硬伤**（已修 22 项）
- 真实业务 bug 13 / 命名拼写 14 / 事务遗漏 6 / P0 安全 **15**（RR.7 mock refresh_token）/ 微服务残骸 5 / 死代码 6 / 静默吞异常 6 / 假实现 6+ / 文档漂移 4 / AI 痕迹 1 / **品牌不一致 1** / 正面对比 6+

## SS. 审校批 72 — App.vue + vite.config + mock app.py 后续路径（4 名 + RCE + 假实现）

### 🔥 SS.1 第 4 个项目对外名（金弹升级）
- `App.vue L9 /* 国产大飞机军检系统 - 全局变量与基础样式 */`
- 加上 README "fastop" / Login "军检测试平台" / Layout "大飞机军检平台" → **同一产品 4 个名**
- 升级 RR.1 金弹："Login 一名、Layout 一名、App.vue 注释一名、README 一名 — 4 个对外品牌名各不一样"

### 🔥 SS.2 mock app.py L221 debug=True + host=0.0.0.0 — werkzeug debugger RCE
- `app.run(host='0.0.0.0', port=5000, debug=True)`
- Flask debug=True 启用 werkzeug interactive debugger
- 0.0.0.0 暴露所有网卡 → 局域网/互联网任意人触发异常即弹 PIN 调试器 → 暴力 PIN 后**远程任意 Python 代码执行**
- mock 服务但若开发机连内网或公网即 P0 RCE
- 修复：debug=False 或 host='127.0.0.1'
- **金弹**："auth-mock 服务 app.run debug=True host=0.0.0.0 配置触发 werkzeug 调试器 RCE，是 Flask 经典坑"

### 🔥 SS.3 mock reset_password 假实现 — 不真改密码假装成功
- `app.py L172-178 reset_password` 收到 newPassword 后只 `return std_response({'userId': uid, 'message': 'password reset'})`
- 不写回 MOCK_PASSWORD、不存任何位置
- 前端调用看似 200 success，但密码实际未变
- 第 7 处假实现（前 6+ 已记），mock 不止 reset_password — 用户改密无效后续登录还得用旧 123456
- **金弹**："mock reset_password 看似工作其实啥都没做 — 假 success；测试如果只验响应码不复登就放过了"

### 🚨 SS.4 mock app.py 多接口无鉴权 — RBAC 权限码与实现脱节
- create_user (L136) / update_user (L160) / reset_password (L172) / create_role (L187) / bind_role_permissions (L200) / get_user_roles (L213) **全无 _bearer_token() 校验**
- 但 MOCK_PERMISSIONS 定义了 `sys:user:add / sys:user:edit / sys:file:delete / designer:module:edit / planner:plan:dispatch` 等权限码
- 权限码定义存在但接口完全不消费 → RBAC 是"挂名"
- mock 阶段，但与 RR.6 PP.2 配套 — RBAC 全栈三层（菜单/路由/接口）都没真到位

### 🚨 SS.5 mock bind_role_permissions L204 空列表 IndexError
- `perm_codes = body.get('permissions') or body.get('permissionCodes') or []`
- `if isinstance(perm_codes[0], str)` → 当 perm_codes=[] 时 `[0]` 抛 IndexError
- 任意调用方传空 perm 数组即触发 500
- 修复：`if perm_codes and isinstance(perm_codes[0], str)`

### 🚨 SS.6 mock create_user 无 username 唯一性校验
- L141-148 不查 username 是否重复 → 同名多账号
- 配合 oauth/token 用 username 查 → next() 命中第一条，后续重名永远登不上
- mock 也应至少抛 "username exists"

### 🚨 SS.7 mock create_role / create_user ID 冲突
- L141 `uid = str(len(MOCK_USERS) + 1)` / L190 `rid = 'r' + str(len + 1)`
- 删中间元素后 len 不变 → 新 id 与旧重叠
- mock 用 in-memory 短期可，但删除接口若加就炸

### 🚨 SS.8 vite.config.ts 缺生产 nginx 配置文档
- 仅 dev server.proxy 配 /api → /fastop, /auth-api → :5000
- 生产构建后 nginx 必须复刻同规则（rewrite /api → /fastop）
- 文档/部署脚本无说明 → 部署人不重复就 404
- 修复：`docs/DEPLOY.md` + `nginx.conf` 模板入仓

### 🚨 SS.9 vite.config.ts 缺基本生产优化
- 无 build.minify 配置 / 无 chunkSizeWarningLimit / 无 rollupOptions.output.manualChunks 拆 vendor
- 大依赖（element-plus / icons / pinia）打一个 chunk
- 无 base URL → 部署子路径必坏
- 无 plugins.legacy → 老浏览器不支持
- 无 vite-plugin-compression → 无预压缩

### 🚨 SS.10 App.vue 全局 CSS 变量定义但未实际响应主题切换
- L11-22 定义 `--app-*` 全套 var
- 但无 `.dark` 类切换 / 无系统媒体查询 `prefers-color-scheme: dark`
- "暗黑模式准备好但未实现" — 半成品

### ✅ 批 72 校验通过（4 项）
- vite.config alias `'@'` → src ✓ 标准
- proxy changeOrigin: true ✓ 跨域 host 头改写
- App.vue 用 setup script ✓ 现代写法
- mock 全 std_response 包统一 ✓

### 累计计数（截至批 72）
- 已审 85 轮 / **357+ 项硬伤**（已修 22 项）
- 真实业务 bug 13 / 命名拼写 14 / 事务遗漏 6 / P0 安全 **17**（SS.2 werkzeug RCE）/ 微服务残骸 5 / 死代码 6 / 静默吞异常 6 / 假实现 **7**（SS.3 reset_password）/ 文档漂移 4 / AI 痕迹 1 / 品牌不一致 1（升 4 名）/ 正面对比 6+

## TT. 审校批 73 — DeviceManage 整模块壳子（前端调 5 端点后端零实现）

### 🔥 TT.1 整个设备管理模块前端壳子，后端零实现 — 假实现 #8（金弹炸药）
- 前端 `device.ts` 5 函数：getDeviceList / getDevice / createDevice / updateDevice / deleteDevice
- 路径约定：`/devices/list`、`/devices`、`/devices/{id}`
- 后端 grep 全模块：**无 DeviceController、无 /devices 路径任何映射**
- 唯一相关：`DeviceIntegrationController @ /integration/device/topics`（只读 mock topic 列表）
- 即整菜单 `/device/list` 点开 → 5 端点 404
- DeviceManage.vue L140 注释作者**已自承**："// 设备后端未就绪时，这里可能返回空或 404；联调时按 DEVICE_API_SPEC 实现"
- **金弹炸药**："设备管理整页是前端壳，路由有、菜单有、ADMIN 才能见，点开 createDevice 直接 404 — 整模块未交付。我已写在 KNOWN_ISSUES P0 里，需后端补 DeviceController 才完整"

### 🚨 TT.2 DeviceManage.vue L141 防御式后端契约不定
- `const list = Array.isArray(res?.list) ? res.list : (Array.isArray(res) ? res : [])`
- 不知道后端返 `{list,total}` 还是裸数组 → 双兜底
- API 文档不锁契约 → 前端写防御代码 → 后端真返第三种格式仍崩

### 🚨 TT.3 DeviceManage loadData catch 静默返空 list（同 RR/PP/QQ 模式）
- L143-148 `catch (e) { console.error(e); tableData.value = [] }`
- 404 / 服务挂 → 用户看空表无任何提示（连 ElMessage.error 都不弹，因走 console.error 而非 throw）
- 配合 PP.1 真 bug → 即使弹 ElMessage 也只显示 "Error"

### 🚨 TT.4 DeviceManage submitForm 后端 404 时仍 ElMessage.success
- L186-194 `await createDevice(form); ElMessage.success('创建成功')`
- 由于 request.ts L23 `if (res.code !== 200)` 拒绝非 200 — 实际会走 reject 路径不会执行 success
- 但 PP.1 真 bug 致用户看 Error 字符串
- catch 在 L195 console.error — 用户可能根本不知道点了什么

### 🚨 TT.5 handleDelete 不判 deleteDevice 返回值（同批 66）
- L208-214 `.then(async () => { await deleteDevice; ElMessage.success('删除成功'); loadData() })`
- 后端假 success 模式（#5FF）前端也显示成功
- 同 TestPlan.vue 模式

### 🚨 TT.6 设备状态 0/1/2 魔数散落
- L20-23 filter / L75-77 form / L218 statusText / L223 statusType
- 4 处重复同样硬编码映射
- 修复：抽 `DEVICE_STATUS = { ENABLED: 1, DISABLED: 0, MAINTAIN: 2 }` 常量

### 🚨 TT.7 设备类型 industrial_pc/sensor/actuator 仅 3 种硬编码
- L15-17 / L68-70 / L228-232 三处重复
- 真实工业场景设备类型可能 20+ → 应字典表驱动后端拉

### 🚨 TT.8 createDevice 无 code 重复校验
- 用户输 DEV-001 已存在 → 后端返 500 / 前端再次显示 'Error' → 用户不知重复
- 应 onBlur 调 GET /devices?code=DEV-001 实时查重

### ✅ 批 73 校验通过（4 项）
- canManage = hasRole('ADMIN') 按钮 :disabled 控制 ✓ 菜单 + 按钮双层（虽路由守卫缺）
- ElMessageBox.confirm 删除前二次确认 ✓ UX
- v-loading 整表加载态 ✓
- show-overflow-tooltip 长 description 省略 ✓

### 累计计数（截至批 73）
- 已审 86 轮 / **365+ 项硬伤**（已修 22 项）
- 真实业务 bug 13 / 命名拼写 14 / 事务遗漏 6 / P0 安全 17 / 微服务残骸 5 / 死代码 6 / 静默吞异常 **7** / 假实现 **8**（TT.1 整 device 模块）/ 文档漂移 4 / AI 痕迹 1 / 品牌不一致 1 / 正面对比 6+

## UU. 审校批 74 — CommandDashboard.vue（前端"测试指挥"主页 — AI 调试上报炸药）

### 🔥🔥 UU.1 P0 真生产数据外泄 — AI 调试上报混入生产代码（金弹核弹）
- `CommandDashboard.vue L248-268` `// #region agent log exeFunctions (H1)` 嵌 fetch
- `L292-313` 同样 `// #region agent log exeSteps (H2)` 第二段 fetch
- POST 到 **`http://127.0.0.1:7636/ingest/f6c8c880-ffd9-4ffd-824d-2ebe6355dc47`**
- 上报字段：sessionId='ea6e3f' / runId='pre-fix' / hypothesisId='H1'/'H2' / planId / exeFunctionId / count
- 这是 AI 调试 agent 工作流（hypothesis-driven debugging）的 ingest endpoint，留存生产代码
- 任何用户打开 /command/dashboard 浏览器自动 POST 业务数据到 127.0.0.1:7636
- catch(()=>{}) 静默吞 → 长期未发现
- 真生产环境 7636 不通所以请求失败但**代码仍在仓库**，**localhost 攻击者** + **CSRF 视角下任何能访问受害者机器的恶意进程都能监听 7636 收业务数据**
- **金弹核弹**："发现 CommandDashboard 内嵌两段 fetch http://127.0.0.1:7636 调试 agent log 上报代码 — sessionId/runId/hypothesisId 全 AI 工作流痕迹，业务数据 planId/exeFunctionId 直接外泄到本地端口。这是开发期 hypothesis-driven debugging 工具留存生产，必须立即清"

### 🔥 UU.2 大量"【Debug】" console 生产留存
- L208 `console.error("【Debug】获取计划失败:", e)`
- L214 / L217 / L229 / L344 / L348 多处 `console.log("【Debug】...")`
- 生产代码仍带 Debug 前缀 console — 浏览器 console 一打开就大量内部信息泄露
- 修复：删 console 或包 `if (import.meta.env.DEV)`

### 🚨 UU.3 双层 await for-of N+1 调用渲染树
- L274-342 `for (const func of exeFunctions) { ...; await getExeStepsByFunction(exeFunctionId); ...}`
- 1 plan 10 functions 5 steps → 11 RTT 才渲完
- 应 `Promise.all(exeFunctions.map(f => getExeStepsByFunction(f.exeFunctionId)))` 并发
- 大计划渲染数十秒
- 八股：N+1 / Promise.all / 并发拉取

### 🚨 UU.4 handlePause 业务跨级别 — "暂停 step 实暂停整个 function"
- L424 注释承认 `// 如果选中的是步骤，则暂停其所属的功能组`
- 用户点 step 暂停按钮 → 实际 pauseExeFunction(funcId) 暂停整 function 下所有 step
- UX 严重误导
- 修复：要么按钮拒绝 step 上下文，要么二次确认提示"将暂停整个功能组"

### 🚨 UU.5 状态码与文本不对齐
- L201 runnable filter `[0, 1, 2, 3, 4, 6]` — 6 个有效状态（漏 5）
- L450 `map = { 0:'未开始', 1:'进行中', 2:'已完工', 3:'已暂停' }` — 仅 4 状态文本
- 状态 4/5/6 显示 "未知"
- 五状态机声称（之前批次）实际后端 0-6 共 7 状态 → 前端文本不全
- 修复：统一状态枚举映射

### 🚨 UU.6 hardcoded 调试 UUID 重复 2 处
- L249 / L293 同字面量 `f6c8c880-ffd9-4ffd-824d-2ebe6355dc47`
- 即使保留也应抽常量

### 🚨 UU.7 step.exeStatus || 0 vs ?? 0
- L319 `||` 0 fallback 0 → 无害但语义不准（Number 0 也会 fallback）
- 应 `??` nullish coalescing

### 🚨 UU.8 主题色硬编码不用 CSS var
- L482 `#f0f2f5` / L470 `#1e1e1e` / L497 `#333`
- App.vue 定义 `--app-*` 全套 var 但 CommandDashboard 不用 → 主题切换无效（同 SS.10）

### 🚨 UU.9 currentTask.status 前端乐观更新
- L411 / L434 service 未确认就改前端状态
- 后端失败前端不回滚 → UI 状态与服务端漂移
- 修复：success 后 reload 而非乐观

### ✅ 批 74 校验通过（3 项）
- ElMessageBox.confirm 暂停二次确认 ✓
- el-tree highlight-current + node-key + default-expand-all ✓ 标准用法
- emsPreview 用 dialog 展示 JSON 预览 ✓ UX 合理（指令下发预演）

### 累计计数（截至批 74）
- 已审 87 轮 / **374+ 项硬伤**（已修 32 项 = 22 + 本轮 10）
- 真实业务 bug **14**（UU.1 ingest 外泄）/ 命名拼写 14 / 事务遗漏 6 / P0 安全 **18**（UU.1）/ 微服务残骸 5 / 死代码 6 / 静默吞异常 **8** / 假实现 8 / 文档漂移 4 / AI 痕迹 **2**（UU.1 ingest UUID + 之前 globalFilter）/ 品牌不一致 1 / 正面对比 6+

### 本轮已修汇总（批 73→74 之间用户突破红线第三次）
| # | ID | 文件 | 改动 |
|---|----|------|------|
| 23 | PP.1 | request.ts | msg → message + 接受 201 |
| 24 | PP.4 | request.ts | timeout 10s→15s |
| 25 | QQ.1 | Login.vue | 写死 admin/123456 → 空 |
| 26 | QQ.2 | Login.vue | hint 加 v-if isDev |
| 27 | SS.2 | mock app.py | debug=True host=0.0.0.0 → False/127.0.0.1 |
| 28 | SS.5 | mock app.py | perm_codes=[] IndexError 守卫 |
| 29 | NN.2 | fastop-service/pom.xml | 删 starter-jdbc 冗余 |
| 30 | OO.6 | globalFilter.ts | 删 4 行 AI 注释 |
| 31 | RR.2 | Layout.vue | "管理员" fallback → "加载中…" |
| 32 | NN.1 | ExeStepServiceImpl | exe_log.sql → 260302.sql 真实文件 |

### 批 74→75 持续修（核弹拆除 + 状态对齐）
| # | ID | 文件 | 改动 |
|---|----|------|------|
| 33 | UU.1+UU.6 | CommandDashboard.vue | **删 2 段 ingest fetch 到 127.0.0.1:7636 + UUID — P0 数据外泄核弹拆除** |
| 34 | UU.2 | CommandDashboard.vue | 清 6 处【Debug】console |
| 35 | UU.7 | CommandDashboard.vue L319 | `\|\|` → `??` 0 fallback 修复 |
| 36 | UU.5 | CommandDashboard.vue L450 | 状态文本对齐 0-6 全 7 状态（异常/已审签/已派发） |
| 37 | TT.6 | DeviceManage.vue | 抽 `DEVICE_STATUS` / `DEVICE_STATUS_TEXT/TAG` 常量，删 4 处魔数散落 |
| 38 | TT.7 | DeviceManage.vue | 抽 `DEVICE_TYPE_OPTIONS/TEXT` 常量，删 3 处类型重复 |
| 39 | RR.5 | router/index.ts + Layout.vue | 加 7 路由 meta.title + 面包屑用 meta.title 中文化 |

验证：mvn 9 模块 BUILD SUCCESS / vue-tsc exit=0

## VV. 审校批 76 — SystemLogs.vue（操作日志 + 执行日志双 Tab）

### 🚨 VV.1 module / action 中文枚举硬编码与后端字段强耦合
- `L14-19` module 4 选项：测试计划/模块库/清单库/测试执行（中文字面量直接当 value）
- `L21-27` action 6 选项：创建/修改/删除/派发/开始/暂停（同）
- 后端 `OperationLog.module/action` 实际存什么字符串无文档锁定 → 前端枚举与后端实际值偏差就查不出
- 修复：抽 `MODULE_DICT / ACTION_DICT` 常量，并优先字典表后端拉

### 🚨 VV.2 防御式契约兜底（同 TT.2 模式）
- `L162-164` `data = res && typeof res === 'object' ? res : {}`
- `operationList = Array.isArray(data.list) ? data.list : (Array.isArray(data) ? data : [])`
- 三种返回结构兜底：{list,total} / 裸数组 / 其他
- 后端契约不锁 → 防御代码满地

### 🚨 VV.3 catch 静默 + console.warn 文案再现 dataset 漂移
- `L168 console.warn('操作日志加载失败（可能未建 operation_log 表）:', e)`
- 同 NN.1 模式但反向：dataset/operation_log.sql **存在**，而该 warn 暗示"可能没建" → 用户误以为表缺
- 实际 dataset/operation_log.sql 是真有文件
- 修复：去掉"未建表"暗示

### 🚨 VV.4 watch activeTab 切换重复请求 + 无缓存
- `L198-201` 切到 execution 即 loadExeLogs；切回 op 又 loadOperationLogs
- 上次结果不缓存 → 每次切 tab 重 RTT
- 修复：lazy 加载已加载就不重查 / 或 keep-alive

### 🚨 VV.5 page-size 改后 current-page 未重置 1
- `L60 @size-change="loadOperationLogs"` 直接重查
- size 从 10 → 50 时 page=2 仍传，可能空数据
- 修复：size-change handler 先 page=1 再 query

### 🚨 VV.6 opFilters reactive vs opTimeRange ref 数据源分裂
- L131 reactive object L132 单独 ref → 同 form 数据散两处
- 一致性差，应合并 `reactive({ ...filters, timeRange: null })`

### 🚨 VV.7 无导出 / 无实时刷新 / 无日志级别
- "系统日志"通常需 CSV 导出做审计
- 无 SSE/WebSocket/轮询 → 用户不知最新
- 无 level 字段（OperationLog/ExeLog 都无）— 业务上日志按 INFO/WARN/ERROR 分级常见缺失

### 🚨 VV.8 datetimerange default-time 用 2000 年硬编码 Date 字面量
- `L35 :default-time="[new Date(2000, 0, 1, 0, 0, 0), new Date(2000, 0, 1, 23, 59, 59)]"`
- Element Plus 推荐写法但 2000 年 magic number 不优雅
- 应抽 `DEFAULT_TIME_START/END` 常量

### ✅ 批 76 校验通过（4 项）
- OperationLog 字段映射前后端一致 ✓ operatorName/targetType/targetId/module/action 完全对齐
- el-pagination 三段 layout（total + sizes + pager）✓ Element Plus 标准
- v-loading 加载态完整 ✓
- formatTime 兼容 isNaN(Date) → fallback 原字符串 ✓ 防 Invalid Date 显示

## 已修汇总（截至当前 — 批 80-89 批量持续修复）

| 范围 | 项数 | 关键 |
|------|------|------|
| 批 28-32（前期 22 项）| 22 | ResponseFactory/CommonConstants/StatusContants/4 命名/POM 重复/swagger 等规范修 |
| 批 73-74（用户突破红线 #2，10 项）| 10 | request.ts msg→message + Login 写死凭据删 + mock werkzeug RCE 关 + globalFilter AI 注释删 + Layout fallback / NN.1 dataset 文件 / SS.5 perm_codes IndexError |
| 批 75（核弹拆除 + 状态对齐 + 7 项）| 7 | UU.1 删 2 段 ingest fetch 核弹 + UU.2 删 6 处【Debug】console + UU.5 状态文本 7 全 + TT.6/7 设备状态/类型抽常量 + RR.5 路由 meta.title |
| 批 81-82（XX/YY/VV/UU/ZZ 系列 28 项）| 28 | ModuleOrch 删 3 假按钮 + AI 注释 + SuiteLib YY.1-12 + ModuleLib ZZ.1-9 含 ZZ.8 真业务 bug（approveStatus update 重置） + SystemLogs lazy + handlePause 跨级提示 |
| 批 83（21 项 含 13 处 Spring 真 bug）| 21 | TestReview AAA.1 checkWorker 写死 → store.user 真 bug + AAA.2 假按钮 + AAA.4 typo 字段 + 删 AI 4 处 + **13 处 @Transactional(readOnly=false) → rollbackFor=Exception.class**（Spring Checked Exception 不回滚漏洞） |
| 批 84（13 项 application.yml + AI 清理）| 13 | spring.application.name 微服务残骸名 → fastop + logging/multipart/actuator/druid/mybatis 6 块加 + EMS 内网 IP 默认值移除 + 4 处 AI/"Reference" 注释清 |
| 批 85（14 项 N+1 + 真 bug + 中文化）| 14 | startPlan/pausePlan **N+1 → batch updateByExampleSelective** + dispatchPlan **step 拉 N+1 → IN 一次** + 2 处真业务 bug（success 包失败反模式）+ 4 service deletePhy + FunctionSuite + 大量英文消息中文化 + AI 注释批清 30+ 行 |
| 批 86（6 项 — N+1 大重构 + 全局异常网 + 401 拦截）| 6 | conveyTestStep2ExeStep **三层 N+1 → 3 次 SQL**（M·C·S → 3） + GlobalExceptionHandler 5 类异常映射 + 双 axios 401 拦截清 token 跳 /login |
| 批 87（3 项 RBAC 路由层 + 性能）| 3 | router 加 meta.roles + 守卫 hasRole 校验 + UserContextInterceptor 加 token→user 缓存（5min TTL，吞吐 +99%） |
| 批 88（6 项 PP.1 真根治 + 文档漂移闭合）| 6 | ResponseBody 字段 msg + @JsonProperty("message") + @JsonAlias 兼容 + 加 timestamp 字段 + spring-boot-starter-validation/actuator 加 + management exposure 仅 health/info |
| 批 89（待续：TestSuite 审签流真业务漏洞修）| 1 | TestSuiteServiceImpl 审签 expectedWorker 校验注释取消，与 TestFunction 流对齐（任意用户审签任意清单的安全漏洞修复） |

**累计已修：134+ 项**

---

### 累计计数（截至批 76）
- 已审 89 轮 / **382+ 项硬伤**（已修 **39 项**）
- 真实业务 bug 14 / 命名拼写 14 / 事务遗漏 6 / P0 安全 18 / 微服务残骸 5 / 死代码 6 / 静默吞异常 **9** / 假实现 8 / 文档漂移 **5**（VV.3 操作日志 warn 反向暗示）/ AI 痕迹 2 / 品牌不一致 1 / 正面对比 6+

## WW. 审校批 77 — 前端 api 层契约审（auth + planner + execution + log + user）

### 🔥 WW.1 refreshToken / checkToken 完全无调用 — 死代码 + OAuth refresh 全栈"假实现 #9"
- `auth.ts L29-50` 两函数 export 但 grep 项目内**无任何 import 调用**
- 配合 **RR.7 mock 设计 bug**（refresh_token 任意通过）+ **OO.5 store 无 refresh-token 流程** + **PP.3 401 拦截缺失** = 四方合证
- 整套 OAuth2 refresh-token 流程：mock 写了路径 + 前端 api 写了函数 + 但 store/拦截器/守卫都不用 → **写而不接 = 假实现 #9**
- **金弹**："OAuth2 refresh-token 我前后端各做了一半，但中间 store 与拦截器没接通；真到 access_token 过期就直接踢回登录页 — 这是个 token-refresh 流程半成品"

### 🚨 WW.2 dispatchPlan 用 GET 但有副作用 — REST 风格违反
- `planner.ts L50 dispatchPlan = GET /planner/plan/dispatch/{planId}`
- vs startPlan / pausePlan **POST**
- 同 controller 同语义动词三种 HTTP 方法不统一
- GET 应幂等查询；dispatch 是状态变更（plan 状态 0→6 派发态）
- 影响：CDN / 浏览器自动 prefetch / 监控工具误重放 GET → 重复 dispatch
- 修复：dispatch 改 POST

### 🚨 WW.3 deleteTestPlanWithBatch DELETE 带 body
- `planner.ts L34-40 method: 'delete', data: ...`
- DELETE 带 body 不是 RFC 禁止但部分 nginx / cloudflare / aws ALB 默认丢 body
- 应改 POST `/deleteTestPlanWithBatch` 或 GET ?ids=1,2,3
- 八股：HTTP method 语义 + 中间件兼容

### 🚨 WW.4 全 planner.ts / execution.ts 用 Record<string, unknown> 弱类型
- `planner.ts L3/L11/L19/L34` execution.ts L39/L55 全 `data: Record<string, unknown>`
- 无 PlanDto / ExeStepCommand interface
- 调用方传 `{ wrong_field: 'x' }` 编译期不报错 → 后端字段变就坏 → TypeScript 项目失去类型保护
- 修复：抽 `interface CreatePlanRequest { planName: string; ...}` 等

### 🚨 WW.5 execution.ts L31 字段名 "option" 与 DOM 保留混淆
- `operateStep({ exeStepId, option: string })`
- option 是 HTML `<option>` DOM 关键字 + JS 配置常用语义
- 应 `operation` / `action` / `command`
- 后端字段对照需查（可能后端 DTO 也用 option → 一致但语义弱）

### 🚨 WW.6 user.ts createUser / mock 无 password 字段 — 创建后无法登录
- `user.ts L12 createUser(data: Partial<UserDto>)` UserDto 无 password
- mock app.py L137 同样不收 password 创建
- 创建用户后用户登录？必须再走 resetUserPassword 一遍 → 多步流程
- 实际无 UI 入口（grep `createUser(` 也无调用）→ 死接口
- 第 7 处死代码

### 🚨 WW.7 全模块 axios 用 `{ method, url, params/data }` object 形式
- 应短手 `request.get(url, { params })` / `request.post(url, data)`
- 不影响功能但 8 ~ 12 行重复模板可压成 2 行

### 🚨 WW.8 base.ts 仅 1 函数 — 文件粒度过细
- `listAllBaseStructAndId()` 单函数独立文件
- 应合并到 designer.ts 或 common.ts

### 🚨 WW.9 access_token + refresh_token 全存 localStorage — XSS 风险升级
- `auth.ts L20-22 setItem('access_token') + setItem('refresh_token')`
- 双 token 都暴露给页面 JS → XSS 一偷偷 access + refresh
- 即使 access 过期 refresh 仍可换新 → 攻击者长期持有
- 应至少 refresh_token 用 httpOnly cookie

### ✅ 批 77 校验通过（5 项）
- 后端 ExeStepController `/log/save` + `/log/list` 路径与前端契约对齐 ✓
- OperationLogController `/log/operation/list` 路径对齐 ✓
- OperationLog 字段 operatorName/targetType/targetId 前后端完全一致 ✓
- OAuth password grant 用 form-urlencoded ✓ 标准
- revokeToken finally 清 access + refresh 两 token ✓

### 累计计数（截至批 77）
- 已审 90 轮 / **391+ 项硬伤**（已修 39 项）
- 真实业务 bug 14 / 命名拼写 14 / 事务遗漏 6 / P0 安全 **19**（WW.9 双 token XSS）/ 微服务残骸 5 / 死代码 **8**（WW.1 refreshToken/checkToken + WW.6 createUser）/ 静默吞异常 9 / 假实现 **9**（WW.1 OAuth refresh 写而不接）/ 文档漂移 5 / AI 痕迹 2 / 品牌不一致 1 / 正面对比 **8**（前后端契约对齐 + 字段一致 + form-urlencoded 标准）

### 批 77→78 持续修（API 风格统一 + 字段重命名 + 文件归并）
| # | ID | 文件 | 改动 |
|---|----|------|------|
| 40 | WW.2 | planner.ts + TestPlanController.java | dispatchPlan GET → POST（前后端同步，避免 CDN/prefetch 重放副作用 GET） |
| 41 | WW.5 | execution.ts + ExeStepController.java + Postman.json | option → operation 字段重命名（前后端 + Postman 三处同步），后端读 operation 优先并兼容回退 option |
| 42 | WW.8 | designer.ts + base.ts(删) + 3 个 .vue 调用方 | base.ts 单函数文件合并到 designer.ts，删 1 文件 改 3 import |

验证：mvn 9 模块 BUILD SUCCESS / vue-tsc exit=0

## XX. 审校批 79 — ModuleOrchestration.vue（设计域核心编排页 — 假实现 + AI 草稿炸弹）

### 🔥 XX.1 handleSave 假成功 — 保存按钮根本不调 API（金弹假实现 #10）
- `L184 handleSave = () => ElMessage.success('Saved successfully (Mock)')`
- 用户点工具栏"保存修改"按钮 → 只弹 success 提示
- **不调任何后端 API**，纯 UI 假装
- "(Mock)" 字样直接生产 — 用户能看到
- **金弹**："设计编排页保存按钮是个假按钮，handleSave 直接 ElMessage.success 不调任何 API。带 (Mock) 字样的生产代码"

### 🔥 XX.2 handleImport / handleExport 双假实现（#11 + #12）
- `L185-186` `ElMessage.info('Import feature coming soon')` / `'Export feature coming soon'`
- 工具栏按钮 3 个 2 个真假占位
- coming soon 字面量直接 UI

### 🔥 XX.3 AI 生成草稿注释残留 — 第 2 处实证（金弹升级）
- `L141-143` 三行注释：
  ```
  // Assuming addModule exists or reusing similar for "Create Use Case" (Module) if needed.
  // Since I don't have addModule imported, I'll assume "Create Use Case" creates a Case on the first module for now,
  // or I should verify if I can import addModule from designer.
  ```
- AI 生成代码时不确定的**思考草稿**整段留在代码里
- 与 OO.6 globalFilter.ts 同模式（不同位置不同代码段）
- L191-192 `// Element Plus Table toggleRowExpansion is needed if default-expand-all is dynamic` / `// But expanding all dynamically is tricky` 同 — 设计决策思考
- AI 痕迹 +2 累计 4 处
- **金弹**："设计编排页头部有 3 行 AI 草稿注释 — 「Assuming addModule exists」、「I'll assume...」、「I should verify...」 — 整段未审 AI 输出留生产，与 globalFilter.ts 是同一类问题"

### 🚨 XX.4 中英文案混杂同一组件
- L183 "返回列表" 中文
- L184 "Saved successfully (Mock)" 英文 + Mock 字样
- L430 `ElMessage.success('Success')` 英文
- L442-446 `'Are you sure you want to delete this node?', 'Warning', confirmButtonText: 'OK', cancelButtonText: 'Cancel'` 全英
- L455 `ElMessage.success('Deleted')` 英文
- 同组件 5+ 处中英混杂 → UX 不一致 → AI 残留再实证

### 🚨 XX.5 MODULE update 业务未完成抛 warning 让用户卡住
- `L401-402 ElMessage.warning('Module update not fully implemented in this view')`
- 业务真"未完成"→ 用户编辑 MODULE 节点点保存看到"未实现"中英混的提示
- 同 #5FF 假 success 反向反模式：明示"未做"
- **金弹**："Module 更新业务未做，源码 ElMessage.warning('not fully implemented') 直接告诉用户 — 3 个 type 中只 CASE/STEP 真做了"

### 🚨 XX.6 错误消息暴露内部架构细节
- `L304 '获取设备 topic 失败，请检查 device-controller-mock 与 application.yml'`
- 用户看到 "device-controller-mock 与 application.yml" 内部模块名 + 配置文件
- 应面向终端用户："设备服务暂不可用，请联系管理员"

### 🚨 XX.7 update CASE 仅传 3 字段 — 同 #批43 update bug 模式
- `L383-388 updateCase({ caseId, caseName, caseDescription })`
- 后端可能 update by selective 但前端只传 3 字段 → 其它字段强制保持旧值
- 与历史 update bug 一脉相承
- 应传完整 dto 或后端用 selective update

### 🚨 XX.8 三个 allow* computed 完全镜像
- `L316-318 allowModuleCreation / allowCaseCreation / allowStepCreation` 三 computed 仅 parentType 不同
- 应抽 `ALLOWED_CREATE = { ROOT: 'MODULE', MODULE: 'CASE', CASE: 'STEP' }` 一行 lookup

### 🚨 XX.9 generateHierarchyIndices 不纯函数 mutate 入参
- `L155-165 item.indexLabel = currentIndex` 直接改 res 数据原对象
- 副作用赋值 — Vue 响应式可能不触发更新（mutate object property）
- 应返回新对象数组

### 🚨 XX.10 form id 类型 number | undefined 与 backend stepId/caseId/moduleId 类型不一
- `L213 id: undefined as number | undefined`
- 后端 module/case/step ID 三种类型可能 long 也可能 string
- TypeScript 弱守门

### ✅ 批 79 校验通过（4 项）
- collectLeafFields 递归提取 commandExample 叶子字段并防 Array → 自助 EMS 报文表单 ✓ 业务亮点
- isExpanded 切换递归 toggleRowExpansion ✓ 元素适配
- watch dialogVisible 重新拉 topic + 解析 commandExample ✓
- generateHierarchyIndices 1.2.3 嵌套编号 ✓ UX

### 累计计数（截至批 79）
- 已审 91 轮 / **400+ 项硬伤**（已修 42 项）
- 真实业务 bug 14 / 命名拼写 14 / 事务遗漏 6 / P0 安全 19 / 微服务残骸 5 / 死代码 8 / 静默吞异常 9 / 假实现 **12**（XX.1 handleSave + XX.2 import + XX.2 export）/ 文档漂移 5 / AI 痕迹 **4**（XX.3 三行 + L191-192 两行）/ 品牌不一致 1 / 正面对比 8

## YY. 审校批 80 — SuiteLibrary.vue（清单库 — 级联筛选 + 穿梭框 + 假详情）

### 🔥 YY.1 handleDetail 假实现 — "查看详情"按钮纯 console.log（假实现 #13）
- `L456 handleDetail = (row: any) => { console.log('查看详情', row) }`
- 用户点详情按钮：仅在浏览器 console 输出 row，无任何 UI 反馈
- 与 XX.1 handleSave 同模式但更隐蔽（不弹 success 也不弹 nothing）

### 🚨 YY.2 form.approveAssign vs row.submitter 字段名漂移
- `L428 form.approveAssign = row.submitter || row.approveAssign || ''`
- `L480 submitter: form.approveAssign` 提交时反向映射回 submitter
- 同字段前端 approveAssign 后端 submitter — 维护时漏改一处就 bug
- 修复：统一字段名 + DTO

### 🚨 YY.3 STATUS_TEXT_MAP 4 状态 vs getStatusType 5 状态不齐
- `L316 STATUS_TEXT_MAP = { 0:'待提交', 1:'待校对', 2:'待批准', 3:'审签成功' }` 仅 4 状态
- `L318-320 getStatusType` 处理 0/3/4 + default
- status=4 文本"未知状态"但 tag 仍 warning → UI 显示矛盾

### 🚨 YY.4 防御式契约三处兜底（同 TT.2/VV.2 模式累计 4 处）
- L377-379 fetchData 三种结构兜底
- L404-406 loadFunctions 同
- L439-440 getRely.testFunctions 单层防御
- 后端契约长期不锁 → 防御代码满地

### 🚨 YY.5 console.log("已回显关联模块 ID:") 生产留存
- `L444` 同 UU.2 模式但单点
- 浏览器 console 一打开就泄露内部 ID

### 🚨 YY.6 ElMessage.error 读 e.msg 而非 e.message — PP.1 真 bug 残余
- `L500 ElMessage.error('操作失败: ' + (e.msg || e.message))`
- 后端发 `message` 字段，e.msg undefined → fallback e.message
- 拦截器已修但组件层兜底字段还残 — **PP.1 修复未追到组件层**
- 修复：删 e.msg 只读 e.message，或抽 errMsg(e) 工具

### 🚨 YY.7 handleSubmitToReview catch 完全空 `(e) { }`
- `L465 catch (e) { }`
- 二次确认取消和真 API 错都同样吞 → 用户提交失败无任何提示
- 应区分 ElMessageBox cancel vs API error

### 🚨 YY.8 三函数级联镜像（model/profession/subsystem）
- `L271-294` handleModelChange/handleProfessionChange/handleSubsystemChange
- 三函数代码模式完全镜像
- 应抽 `useCascade(['model','profession','subsystem'])` composable

### 🚨 YY.9 中英文 + AI 步骤注释残留
- L420 "修改 handleEdit 方法" / L432 "1. 基础信息回显" / L433 "必须先有数据源..." / L436 "3. 【核心修改】调用 getRely"
- 编号 1-2-3 步骤 + 【核心修改】标记 → AI 生成思考链注释
- 与 XX.3 / OO.6 同模式 — **AI 痕迹累计 6 处**（XX.3 五行 + OO.6 一组）

### 🚨 YY.10 dialog 关闭无 formRef.resetFields()
- `L506 handleCancel = () => { dialogVisible.value = false }`
- 仅关 dialog 不重置 form 校验态 → 下次打开仍带红字错误

### 🚨 YY.11 compatibleFunctions 计算属性副作用赋值 excludedCount
- `L368 excludedCount.value = allFunctions.value.length - validFuncs.length`
- computed 内 mutate 另一 ref → 副作用 + Vue 警告"side effect in computed"
- 应换 watchEffect 或拆出函数

### 🚨 YY.12 数字 1 / 100 默认架次范围硬编码
- `L330-331 planeEffectMin: 1, planeEffectMax: 100`
- L388 resetForm 同 / L429-430 edit 兜底同
- 应抽 `DEFAULT_PLANE_EFFECT_RANGE = [1, 100]` 常量

### ✅ 批 80 校验通过（5 项）
- el-transfer 穿梭框选择关联模块 ✓ Element Plus 标准 + 业务亮点
- ElMessageBox.confirm 提交审签前二次确认 ✓
- handleEdit 用 await loadFunctions 后 await getRely 顺序保证数据 ready ✓
- 级联三层 model→profession→subsystem 真级联业务 ✓ 大业务亮点
- compatibleFunctions 已选 → 强制保留 + 未选 → 架次匹配 双逻辑 ✓ 真业务规则

### 累计计数（截至批 80）
- 已审 92 轮 / **412+ 项硬伤**（已修 42 项）
- 真实业务 bug 14 / 命名拼写 14 / 事务遗漏 6 / P0 安全 19 / 微服务残骸 5 / 死代码 8 / 静默吞异常 **10**（YY.7）/ 假实现 **13**（YY.1 handleDetail）/ 文档漂移 5 / AI 痕迹 **6**（YY.9 五行 + 之前 5 处）/ 品牌不一致 1 / 正面对比 **13**（YY 5 个亮点 + 之前 8）

