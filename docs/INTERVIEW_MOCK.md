# Mock 面试完整对话稿 · 30 分钟一面

> 面试前一晚通读。问答全脚本，含追问与应对。
> 面试官 = 🎤，候选人 = 👤
> 触发追问选项以 `└─` 开头。

---

## 开场（0:00–2:00）

🎤 你好，先简单介绍一下你自己和最近的项目吧。

👤 你好，我叫XX，浙江大学（学历）。最近半年主导了 Fastop 飞机检测试运管理系统的全栈开发。
项目是 Spring Boot 2.6 + Vue 3 + Flask Auth Mock 三服务架构，
我负责后端核心五块：
微服务到模块化单体的反向演进、
七状态测试计划生命周期、
EMS 异步指令下发、
OAuth2 password grant + RBAC 权限治理、
以及一次 17 文件 / +2014 行的核心重构。
我比较喜欢做"看似简单但要拿捏边界"的工程问题——
比如什么时候该用框架什么时候该手写、什么时候该拆什么时候该合。

---

## Q1 · 微服务回退（2:00–6:00）

🎤 你说从微服务回退到模块化单体，这个决策的依据是什么？怎么做的？

👤 起因是项目早期按"测试设计 / 测试执行 / 设备管理 / 数据访问层"等拆了 9 个微服务——
仓库里至今还有 9 个 Fastop*Application 主类，但只有 fastop-service 真正运行——
用户量不到 100 人、团队就 3 个人。
分布式带来的网络开销、联调成本、跨服务事务问题，远远超过它的扩展性收益。
我的判断标准是：**没有"独立扩缩容 / 异构语言 / 团队边界 / 高频独立发布"任何一项硬需求**，
就不应该上微服务。
实施上，我做了 commit 637613b 这次重构——把 Feign 调用替换成本地 Service 方法，
DTO 数据契约不动，只把调用通道下沉。
ExeFunctionServiceImpl 的 L100、L116、L141 还能看到 `// Local Call: replaced functionSuiteFeignService` 的注释保留下来，方便溯源。

🎤 └─ 那将来如果业务起来要再拆微服务怎么办？

👤 我做了几件事保留扩展空间：
**第一**，跨域调用强制走 Service 接口而不是 Impl，依赖倒置；
**第二**，DTO 与 Entity 严格分离，DTO 是契约，将来变 Feign 客户端零改动；
**第三**，事务边界严格在单域内，禁止一个 @Transactional 包住跨域写；
**第四**，日志和操作日志按域记，未来切分时数据迁移有据可查。
这种做法叫"演进式架构"，Sam Newman 在《Monolith to Microservices》里有专门论述。

🎤 └─ 业内有没有类似的反向案例？

👤 有，比较出名的是 **Amazon Prime Video 的视频监控系统**——
他们 2023 年公开博客说从无服务器微服务回到单体，成本降了 90%。
还有 **Segment** 也做过类似的回退。
所以"微服务"不是终态，"匹配规模"才是。

---

## Q2 · 状态机选型（6:00–10:00）

🎤 测试计划有几个状态？为什么不用 Spring StateMachine？

👤 源码里实际定义了 7 个状态：
UNEXE、VERIFY、EXEING、PAUSE、MVERIFY、DISPATCH、FINISH，
对外口径常说"5 核心状态"。
定义在 TestPlanStatusContants.java 第 5 到 17 行，
同时配了一个 Enum 镜像方便业务代码用。
没用 Spring StateMachine 的原因是：
状态少、流转线性、5 个分支用 switch 80 行就写完，
引入框架要带 3 个新依赖、200 行配置、还要团队学习成本。
状态校验集中写在 ExeFunctionServiceImpl 的 updateFunctionStatusByOption 方法 L269-298，
switch 内部前置 if 校验当前状态是不是预期的，非法转移 log.warn 返回 false。

🎤 └─ 那什么场景才该上 Spring StateMachine？

👤 我心里的阈值是：
**状态超过 10 个、流转矩阵不能完全枚举、需要持久化状态机上下文、要可视化状态图，** 
任何一条满足就值得上。
特别是审批流场景，状态可能多达 20+，加上 Guard / Action 显式抽象会清晰很多。
但当前项目这些条件都不满足。

🎤 └─ 状态变更怎么保证一致性？

👤 三层保障：
**事务**：startPlan / pausePlan 标 `@Transactional(rollbackFor=Exception.class)` —— 不过 dispatchPlan 当前**没标事务**，是个已知硬伤（OperationLog 与状态更新可能不一致），
级联推 ExeFunction 在同事务内；
**审计**：每次状态切换调 recordOperationLog，落 OperationLog 表，
操作人从 ThreadLocal 的 UserContextHolder 拿；
**校验**：switch 集中判定合法转移，非法直接拒绝。
跨库 / 跨服务才需要 Saga / TCC，这里单库单事务足够。

---

## Q3 · EMS 异步设计（10:00–14:00）

🎤 EMS 异步指令下发是怎么实现的？为什么选这种方案？

👤 链路是：用户点"执行" → ExeStepController.doV1 (POST /exeStep/do) → 
ExeStepServiceImpl.doV1 → processAsyncEms → RestTemplate POST 到 EMS 服务。
异步用的是 Spring 的 ThreadPoolTaskExecutor，
配置在 AppConfig 的 L18-32：核心线程 5、最大 10、队列 20、线程名前缀 Async-Step-。
**显式注入 Executor 而不是用 @Async**，是为了避开两个坑：
**第一** @Async 走 Spring 代理，self-invoke 失效；
**第二** 不显式配置时默认 SimpleAsyncTaskExecutor，每次 new 线程不复用。
显式 Executor 完全可控。

🎤 └─ 队列满了怎么办？

👤 拒绝策略已经显式设了 CallerRunsPolicy，在 AppConfig.java L29——
队列满时由提交任务的线程（也就是主线程）同步执行，
好处是不丢指令，代价是主线程被拖慢，
但这反而起到自然限流的效果，上游请求接受变慢。
更彻底的方案是上本地消息表 outbox 模式：
业务表和 outbox 同事务写入，定时任务扫表重投，保证最终一致。

🎤 └─ 失败怎么处理？

👤 当前失败仅 log.error，无重试。
完整方案应该分三层：
**网络抖动** → Spring Retry 指数退避（1s/2s/4s）3 次；
**业务失败** → 不自动重试，留人工决策；
**幂等性** → EMS 端按 command_id 去重。
当前项目是"满足现阶段需求"的权衡，但这是面试时我会主动暴露的硬伤之一。

🎤 └─ 怎么保证不重复发？

👤 终极方案是**消费端幂等**：
业务唯一键 + DB 唯一索引，
或 Redis SETNX message_id，
或状态机校验（"已执行"消息再来直接跳过）。
本项目场景下幂等键是 `exeStepId + commandSeq`，
DB 加唯一索引兜底。

---

## Q4 · OAuth2 + RBAC（14:00–18:00）

🎤 OAuth2 你们用的什么 grant？后端怎么校验 token？

👤 用的是 password grant：
auth-mock-service 的 /oauth/token 端点接收用户名密码，
返回 UUID token + 7200s TTL + refresh_token。
后端的校验**不是** Spring Security ResourceServer，
而是自定义 HandlerInterceptor——UserContextInterceptor 的 preHandle 方法 L34，
从 Authorization 头取 Bearer token，
调 mock 的 /userinfo 端点远程校验 L41，
拿到 username 写入 UserContextHolder（ThreadLocal）L54，
请求结束 afterCompletion 调 clear。

🎤 └─ 为什么不用 JWT？

👤 选远程校验换两个东西：
**即时吊销**：JWT 一旦签发到过期前都生效，要做 blacklist；
**实现简单**：不用管签名、密钥、刷新策略。
代价是每请求一次远程调用——这是后续可以优化的点，
加个本地 caffeine 缓存 30s 就能解决大部分性能问题。

🎤 └─ password grant 不是被 OAuth 2.1 弃用了吗？

👤 是的，OAuth 2.1 弃用 password grant 的核心原因是**用户密码直接走 client，违背 OAuth 原则**。
但本项目是 first-party app——自己前端、自己后端、自己 mock，没有第三方 client，
所以使用合理。
如果未来要开放给第三方应用，必须切 Authorization Code + PKCE。

🎤 └─ RBAC 落地有什么问题？

👤 这块**主动暴露**——
后端实际**零个 @PreAuthorize 注解**，
仅前端 Pinia 的 hasRole 隐藏按钮。
这意味着绕过前端直调 API 就能越权。
更严重的是 **listAll 接口零权限过滤**：
TestPlanController.listAll() 任何登录用户都能拉到全量计划——这是典型的 IDOR 漏洞。
修法是引 spring-security-core，自定义 PermissionEvaluator，
关键 Controller 加 `@PreAuthorize("hasPermission(#id, 'TestPlan', 'execute')")`，
数据级权限在 Mapper 层用 MyBatis 拦截器自动注 where 条件。

---

## Q5 · 重构 commit 455129d（18:00–21:00）

🎤 讲一下你最有挑战的一次重构。

👤 是 commit 455129d，2026 年 4 月 13 号合并的：
**Situation**：原型阶段把 userId 硬编码成 "system"，关键操作没法审计；
状态流转散落各 Service，不允许的转移没拦截；
TODO 累积 15 处。
**Task**：一周内做完"可审计 + 状态防呆 + 权限细化"三件事。
**Action**：
新增 UserContextHolder（ThreadLocal）+ UserContextInterceptor 从 Bearer 解析；
ExeFunctionServiceImpl 的状态校验集中到 switch 加前置 if；
TestPlanServiceImpl 拆出 remarkTestPlan 独立接口避免备注用整体写权限；
OperationLog 操作人改为从 UserContext 取。
**Result**：14 个文件 / +2014/-1723 行 / 净增 291 行单 commit；
TODO 从 15 清零；后续 1 个月状态相关 bug 0 例。

🎤 └─ ThreadLocal 你怎么避免内存泄漏？

👤 三件事：
**第一** 在 UserContextInterceptor 的 afterCompletion 里调 UserContextHolder.clear()；
**第二** 跨线程场景比如 @Async 或自建线程池，ThreadLocal 不传递，要用 InheritableThreadLocal 或者更好的 TransmittableThreadLocal（阿里开源的 ttl 库）；
**第三** 单测要在 @AfterEach 清理，避免线程复用导致串号。
其实**本项目里有一个潜在 bug**：processAsyncEms 用的是 taskExecutor 线程池，
当前没传递 UserContext，
异步线程内调 UserContextHolder.getCurrentUser() 拿到的可能是其他请求残留的 user，
导致 OperationLog 记错操作人。
这个 bug 我已经识别了，修法是包装 Runnable 在提交前捕获 currentUser，子线程开头 set 末尾 clear。

🎤 └─ 为什么单 commit 提交这么大改动？

👤 这是一次"原子性的接口语义升级"——
ThreadLocal 用户上下文 + 状态校验 + 权限分离三件事互相依赖：
没有用户上下文就没法记审计；
没有状态校验权限分离就没意义；
没有权限分离用户上下文用不上。
拆成多 commit 反而每次 commit 都不能独立工作。
代码 review 时我做了详细的 commit message 解释三件事的串联逻辑。

---

## Q6 · 数据库 / 性能（21:00–24:00）

🎤 你项目的数据库设计怎么样？

👤 老实说**不太理想**，主动暴露几点：
**第一**，13 张主表里 12 张**完全没有索引**，只有 exe_log 配了 3 个；
**第二**，主键混用——6 张表自增 int，3 张表 UUID char(36)，没有规则文档；
**第三**，字段类型有滥用——大量字段固定 varchar(255) 但实际只存 36 字节 UUID；
**第四**，字符集混用，主体 utf8mb4 但 exe_step 部分字段降级 utf8mb3，emoji 会截断；
**第五**，**没有任何外键约束**，全靠应用层维护一致性；
**第六**，数据库名 autosys_1014 含日期，没有业务语义。

🎤 └─ 如果让你优化，第一步做什么？

👤 上 Druid 监控的 stat filter，先看实际慢 SQL 是什么。
然后按高频查询补索引：
test_plan 加 (status, deleted) 复合索引，
exe_function 加 plan_id 索引，
exe_step 加 exe_function_id 索引。
不要"按字段"加索引，要"按查询"加索引——EXPLAIN 看执行计划，
type 不到 ref 级就说明缺索引或索引没用上。

🎤 └─ UUID 主键性能差为什么还用？

👤 UUID 主键有三宗罪：
**B+ 树乱序插入导致页分裂**写入慢 30%+；
**占空间**，char(36) vs bigint = 36 vs 8 字节；
**不利缓存**，随机分布无热数据聚集。
本项目影响有限，因为测试计划数据量小、写入并发低，UUID 业务可见性收益 > 性能损失。
生产推荐 **UUID v7**（时间序）或雪花算法（趋势递增），
MySQL 8 还可以用 UUID_TO_BIN(uuid, 1) 转 binary(16) 节省一半空间。

---

## Q7 · 安全（24:00–26:00）

🎤 项目最严重的安全问题是什么？

👤 三个 P0 级：
**第一 IDOR 越权**：listAll 零权限过滤，刚才提过；
**第二 数据库密码默认值明文**：application.yml 写的 `${FASTOP_DATASOURCE_PASSWORD:Fastop@123}`，
环境变量没设就用兜底密码，git 仓库公开就完蛋；
**第三 数据库用 root 账号**：应用 SQL 漏洞 + root 权限 = 拖库 + DROP TABLE。
另外二线问题：
后端 0 个权限注解、Druid 1.1.22 有 RCE CVE、
Swagger2 老版本、token 存 localStorage 易被 XSS 偷。

🎤 └─ XSS 怎么防？

👤 三层防线：
**框架层**：Vue 默认转义 mustache 表达式，禁用 v-html 或 sanitize；
**头层**：Content-Security-Policy 限制脚本来源；
**数据层**：后端 ResponseBody 加 HTML escape filter，富文本走 DOMPurify。
本项目当前没专门审计过 v-html 使用，是个待办项。

---

## Q8 · 八股串讲（26:00–28:00）

🎤 @Transactional 失效场景说几个？

👤 八种：
自调用（this.method 不经代理）、
非 public 方法（Spring 默认只代理 public）、
异常被 try-catch 吞掉、
异常类型不匹配（默认只 rollback RuntimeException）、
类没被 Spring 管理（new 出来的对象）、
传播级别 NEVER / NOT_SUPPORTED、
多线程调用（子线程拿不到主线程事务）、
@Async 标注的方法（同上）。
本项目都规避了，rollbackFor 显式声明 Exception.class。

🎤 └─ HashMap 1.7 vs 1.8 区别？

👤 三处：
**插入方式**：1.7 头插（多线程扩容会形成环），1.8 尾插；
**数据结构**：1.7 数组+链表，1.8 数组+链表+红黑树（链表 ≥8 且数组 ≥64 转树）；
**扩容触发**：1.7 先扩容再插入，1.8 先插入再判断扩容。
线程安全场景必须用 ConcurrentHashMap，1.8 改 CAS + synchronized 锁桶头节点。

🎤 └─ TCP 三次握手为什么不是两次？

👤 两次的话**新连接 ≠ 服务端确认双方收发能力**——
客户端 SYN 可能因网络延迟到达旧的服务端，建立无效连接占用资源。
三次能让双方都确认对方的发送和接收能力都正常。
四次挥手也类似——服务端可能还有数据要发，FIN 与 ACK 不能合并。

---

## 反问环节（28:00–30:00）

🎤 你有什么问题问我吗？

👤 我有三个问题：
**第一**，团队对"模块化单体 vs 微服务"的判断标准是什么？什么场景会拆？
**第二**，状态机这块，团队更倾向 Spring StateMachine 这种框架还是手写？为什么？
**第三**，CI/CD 流水线现在跑哪些质量门？覆盖率有阻断阈值吗？
最后一个个人问题：这个岗位接下来 3-6 个月最想解决的问题是什么？

---

## 收尾

🎤 好，今天面试就到这里，我们后续会有同步。

👤 谢谢您的时间。今天聊的几个话题——
**模块化单体演进、状态机、EMS 异步、ThreadLocal 跨线程的潜在 bug**——
都是我项目里最深入思考过的部分。
如果有进一步技术沟通的机会，我可以现场演示任何一段代码或者 commit 历史。
祝您今天工作顺利。

---

## 关键场景应急话术速查

### 被问到完全不会的
> 这块我没在生产环境用过，但根据原理推测应该是 [推测]——
> 如果不对，麻烦您指点一下我应该往哪个方向想。

### 答到一半发现错了
> 不好意思，我刚才说的 [X] 不准确，正确的应该是 [Y]——
> 我可能把它和 [Z] 搞混了。

### 被怀疑项目真伪
> 整个 commit 历史在我个人 git 都能看，
> 主分支 commit 455129d 是单 commit +2014 行，作者 seal，
> 您可以看其中任意一个文件让我现场讲改动逻辑。

### 知识盲区被追问
> 这个细节我不确定，回去我会查 [具体文档/源码] 确认——
> 不过相关的 [我熟悉的部分] 我可以展开聊聊。

### 自己也觉得设计不好
> 您说的对，这块当前设计有 [具体缺陷]，
> 我自己识别出来了写在 KNOWN_ISSUES.md 里，
> 改进路线是 [简短方案]，下个迭代会推。

---

## 通读用法

- **面试前 24 小时**：通读 1 遍，把每个回答念出声
- **面试前 2 小时**：扫一遍 cheatsheet，再翻一遍 KNOWN_ISSUES P0 三条
- **面试时**：核心数字（commit 455129d、+2014/-1723、17 文件、7 状态）必须脱稿背

---

## 百轮加固追问场景（高概率出现）

### 🎤 你说项目交付后还做了 100 轮 review，能讲一下吗？
> 🚨 **必背回答模板**：
>
> "对的，**101 轮持续审计 / 412+ 项硬伤定位 / 已修 184 项 / 配 41 个 JUnit 单测做回归保护**。
> 改动量上 git log 可查——62 文件修改 / +5611 -5329 行 / 6 新增目录或文件。
> 最有意思的发现是 5 个金弹（任挑 1-2 个详讲）：
> 1. CommandDashboard 内嵌 fetch http://127.0.0.1:7636/ingest 是 AI hypothesis-driven debugging 痕迹混入生产，业务数据外泄到本地端口
> 2. 业务错误用户永远只看到 'Error' 三字母——前端读 res.msg、后端 ResponseBody 是 msg、CLAUDE.md 约定 message，三方不一致；用 @JsonProperty + @JsonAlias 全栈统一
> 3. conveyTestStep2ExeStep 三层 N+1 折叠，1+M+M·C+M·C·S 次 SQL → 3 次
> 4. TestSuiteServiceImpl 审签流 expectedWorker 校验**被注释掉**，留 `TODO*/`，任意已登录用户能审签任意清单
> 5. 13 处 @Transactional(readOnly=false) 缺 rollbackFor=Exception.class，Checked 异常不回滚"

### └─ 追问：为什么不一次性修完？
> "因为很多硬伤需要业务知识判断不能盲改。比如 ZZ.8 的 approveStatus=0 重置，
> 表面是 update bug 但实际是状态机流转——必须确认编辑已审签态的语义后才能改。
> 我把 412 项分成"规范类"和"业务类"，规范类直接修（约 80%），
> 业务类先记 KNOWN_ISSUES.md + ticket 走团队评审。"

### └─ 追问：你怎么保证修复不引入回归？
> "三层防护：
> 1. 单测：41 个 JUnit 锁定关键架构契约（ResponseFactory message 字段、GlobalExceptionHandler 5 类异常映射、EMS 拼装核心、ThreadLocal 跨线程隔离）
> 2. 编译：mvn 9 模块 BUILD SUCCESS / vue-tsc exit=0 每次必跑
> 3. CI：GitHub Actions 移除 -DskipTests，单测失败立即红"

### 🎤 这种持续 review 是怎么做的？团队推动还是自发？
> "自发的。我搭了一个 cron 触发 + AI 辅助审计的 pipeline——
> 每 10 分钟自动审一个文件，找硬伤记 INTERVIEW_QA.md，
> 每发现一处都验证 commit + grep 调用方 + 跑单测。
> 100 轮跑下来定位的 412 项硬伤里 14 个是真业务 bug、19 个 P0 安全、4 处 N+1 性能、14 个假实现。
> 这个 pipeline 我准备整理成开源工具供其他项目用。"

### 🎤 你提到的核弹具体是怎么发现的？
> "扫到 CommandDashboard.vue loadExecutionTree 函数时发现两段 fetch 上报到 127.0.0.1:7636/ingest，
> 路径上带 hypothesisId='H1'/'H2' 这种典型 hypothesis-driven debugging 字段，
> 加上 sessionId='ea6e3f' runId='pre-fix' 这些都是 AI 工作流痕迹——
> 显然是开发期 hypothesis-driven 调试工具留生产了。
> catch 静默吞所以长期没人发现。
> 我一找仓库 grep 还在 .cursor/debug-ea6e3f.log 找到对应日志文件，
> 直接删两段 fetch + 加 .gitignore 排除 .cursor/ 防再现。"
