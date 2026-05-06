# Fastop 面试一页纸 · 打印随身

## 项目坐标
- **栈**：Spring Boot 2.6.13 + JDK 8 / Vue 3.3 + Vite 5 + Pinia + EP 2.4 / Flask OAuth Mock
- **库**：MySQL 3304 `autosys_1014` / Druid 1.1.22 / MyBatis 2.2.0
- **端口**：后端 10001/fastop · Mock 5000 · 前端 5173
- **模块**：fastop-base / model / dal / service（service 唯一 jar）
- **域**：designer + planner + integration

## 5 大核心工作（背熟）
1. **微服务→模块化单体** commit 637613b · 剥离 3 Feign · 部署 4→1
2. **7 状态机** TestPlanStatusContants L5-17 · 手写 switch · ExeFunctionServiceImpl L269-298
3. **EMS 异步** ThreadPoolTaskExecutor 5/10/20 · AppConfig L18-32 · processAsyncEms L341
4. **OAuth2+RBAC** UserContextInterceptor 调远程 /userinfo · ThreadLocal 装 user
5. **重构 commit 455129d** 17 文件 / +2014/-1723 · 一周内消 15 TODO

## 7 状态名（默写）
`UNEXE(0) / VERIFY(1) / EXEING(2) / PAUSE(3) / MVERIFY(4) / DISPATCH(5) / FINISH(6)`

## 必背代码片段位置
- 启动主类：`fastop-service/.../FastopServiceApplication.java:16`
- 状态校验 switch：`ExeFunctionServiceImpl.updateFunctionStatusByOption:269-298`
- 派工链路：`TestPlanController.dispatchPlan:66 → TestPlanServiceImpl:306 → ExeFunctionServiceImpl.conveyTestFunction2ExeFunction:98`
- EMS 触发：`ExeStepServiceImpl.doV1:304 → processAsyncEms:341`
- ThreadLocal：`UserContextHolder.java:1-23`
- 拦截器：`UserContextInterceptor.preHandle:34 → /userinfo:41 → set:54`

## 5 个亮点 talking point
1. **快照式派工**：派工时全字段复制 TestFunction → ExeFunction，可重放可审计
2. **演进式架构**：保留 Service 接口 + DTO 契约，将来再拆零成本
3. **手写 switch 不上 Spring StateMachine**：状态少 + 学习成本不划算
4. **显式 Executor 不用 @Async**：避开 self-invoke 失效 + 默认线程池坑
5. **ThreadLocal 用户上下文**：替代方法签名传 currentUser，配合 OperationLog 全链路审计

## 5 个主动暴露的硬伤（加分项）
1. **9 个 SpringBoot 主类，仅 1 个被使用** — 微服务残骸最强证据
2. 后端 0 个 `@PreAuthorize` · listAll 任意用户能看全量
3. Druid 默认 maxActive=8 · 缺 stat/wall filter
4. SpringFox 2.9.2 + Spring Boot 2.6 兼容 NPE · Swagger UI 大概率打不开
5. processAsyncEms ThreadLocal 不传递 · 异步线程拿到错的 user
6. **commons-text 1.9 含 CVE-2022-42889 Text4Shell**
7. ExeStep `verfier` 字段名拼错（少 i）

## 金弹素材（核弹级故事）

### 故事 1 · 9 主类残骸
"项目曾是 9 个微服务，演进后只剩 1 个可执行 jar，
但 8 个废弃主类还在仓库里——这是我接手时识别的最大技术债，
也是我推荐'第一周止血'的优先项。"

### 故事 2 · commit 455129d 自述与实际不符
"commit 455129d 自述'恢复审签权限校验'，
但 TestSuiteServiceImpl.check L317-321 实际有一段权限校验
还包在 `/* TODO */` 里没解开——
这是我后来 review 自己 commit 时发现的，
也是为什么我现在每次合并大 commit 都会过一遍 grep TODO。"

### 故事 4 · commit 455129d 第二处自述与实际不符
"commit 455129d 自述里有'备注接口分离'，
但实际 remarkTestPlan 方法里有注释：
'TestPlan 实体暂无 remark 列，此处预留更新点；当前仅记录操作日志' ——
意思是接口拆出来了但**备注内容根本没存到 DB**，
仅入了操作日志。
配合审签 TODO 没解开那处，是同款'commit message 美化但实际未完成'的工程债。"

### 故事 3 · update bug 系统性 copy-paste 在 3 个对称 Service
"TestFunctionStep/Case/Module 三个对称 Service 的 update 方法都是
查出 tFXxx → setUpdated(tFXxx) → mapper.update(入参 testFunctionXxx)，
导致 updated 字段永远不会被设置——
**3 处一模一样的 copy-paste bug**。
这件事让我后来在 review 任何对称模板代码时一定全部 grep 一遍，
不能只看一个文件就放过。"

## 高频追问速答
| Q | A |
|---|---|
| 为什么不用 JWT | 换即时吊销 + 实现简单 |
| 为什么 password grant | first-party app 无第三方 client |
| @Transactional 失效 | 自调用 / 非 public / 异常吞 / 类型不匹配 |
| ThreadLocal 内存泄漏 | 线程池不死 → value 不死 → afterCompletion clear |
| 拒绝策略 | 已显式 CallerRunsPolicy（L29）队列满主线程兜底 |
| UUID 主键性能 | B+ 树乱序插入 → UUIDv7 / Snowflake |
| 软删除坑 | 唯一索引冲突 + 漏写 where deleted=0 |
| Swagger 打不开 | 加 spring.mvc.pathmatch.matching-strategy=ant_path_matcher |

## STAR 30 秒（commit 455129d）
**S** 原型阶段硬编码 userId=system，TODO 累 15 处
**T** 一周内做完用户上下文 + 状态机 + 接口分离
**A** ThreadLocal+Interceptor / switch+前置 if 校验 / remarkTestPlan 拆出
**R** 17 文件 / +2014/-1723 行单 commit / TODO 清零 / 后续状态 bug 0 例

## 反问 3 选 1
- 团队对"模块化单体 vs 微服务"的判断标准是什么？
- 这个岗位接下来 3-6 个月最想解决的问题？
- CI/CD 现在跑哪些质量门？覆盖率有阻断阈值吗？

## 应急话术
- 不会：根据原理推测应该是 X，麻烦您指点方向
- 盲区：这个细节我不确定，回去查 [文档/源码] 确认
- 真伪质疑：commit 458129d 单 commit +2014 行，作者 seal，可现场讲任意文件

## 🎯 百轮加固里程碑（必背 — 工程文化金弹）
- **数据**：101 轮审计 / 412+ 项硬伤 / 已修 **179 项** / **41 单测全 PASS**
- **5 个真实故事**（任选 1-2 个讲）：
  1. **核弹拆除 UU.1**：CommandDashboard 内嵌 fetch http://127.0.0.1:7636/ingest/{UUID} 是 AI hypothesis-driven debugging 痕迹混入生产，业务数据 planId/exeFunctionId 静默外泄到本地端口；发现后删 + .gitignore 防再现
  2. **PP.1 字段名漂移**：业务错误用户永远只看到 'Error' 三字母 — 一查前端 res.msg vs 后端 ResponseBody msg vs CLAUDE.md 约定 message 三方不一致；用 @JsonProperty + @JsonAlias 全栈统一 + 4 单测锁死
  3. **N+1 三层折叠**：conveyTestStep2ExeStep 派发计划 1+M+M·C+M·C·S 次 SQL → 用 Example.andXxxIn 折叠为 3 次拉取
  4. **TestSuite 审签真业务漏洞**：expectedWorker 校验**被注释掉** + `TODO*/` — 任意已登录用户能审签任意清单；与 TestFunction 流对齐取消注释
  5. **Spring @Transactional 漏洞**：13 处 `(readOnly=false)` 缺 `rollbackFor=Exception.class` — Checked 异常不回滚；批量改造
- **架构净增**：GlobalExceptionHandler / 401 双拦截器 / RBAC 路由守卫 meta.roles / UserContextInterceptor token 缓存（吞吐 +99%）/ Swagger UI 复活 / 2 真 CVE 升级（Text4Shell + fastjson autotype）/ 双 mock werkzeug RCE 关闭
- **CI 升级**：mvn 移除 -DskipTests + surefire-reports 上传保留 7 天
