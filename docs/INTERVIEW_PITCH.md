# 面试讲述脚本 · Fastop 项目

> 三种长度可切：5 min（电话初筛）/ 15 min（技术一面）/ 30 min（终面深聊）。
> 配套："API 路由全表"在 INTERVIEW_QA.md AA 节，速答卡 50 题在 CC 节。
>
> **百轮加固里程碑**（2026-05-03）：项目质量改造做了 101 轮源码审计 / 412+ 项硬伤定位 / **已修 181 项** / **41 单测 PASS**。
> 这是面试时讲"对项目主动加固"的核心论据——不是"我做了项目"而是"我对自己的项目做了 100 轮 review 还修了 180+ bug"。

---

## 一、5 分钟版（电话初筛 / 自我介绍）

### 0:00–0:30 自我定位
> 我叫XX，浙江大学（学历）。最近半年主导了 Fastop 飞机检测试运管理系统的全栈开发，
> 项目是 Spring Boot 2.6 + Vue 3 + Flask Auth Mock 三服务架构。

### 0:30–1:30 项目目标
> 这套系统服务于飞机检测的工程现场，核心场景是：
> 设计阶段做测试模块 / 套件 / 步骤的库管理，
> 计划阶段把套件派工成可执行实例，
> 执行阶段把指令异步下发到设备控制器（EMS），
> 全程保留双重审签（普检 / 军检）和操作日志。

### 1:00 自我反思加分句
> 比如我自己曾在 commit 455129d 自述"恢复审签权限校验"，
> 但事后 review 发现 TestSuiteServiceImpl.check 里有一段权限校验
> 还包在 `/* TODO */` 里没解开——
> 这件事让我现在每次合大 commit 都会过一遍 grep TODO。

### 1:30–3:30 我的核心工作
> 我负责后端的几个核心模块：
>
> **第一**，把项目从早期的微服务架构反向演进到模块化单体——剥离了 3 个 Feign 客户端，
> 部署单元从 4 个降到 1 个，理由是用户量和团队规模不匹配，分布式开销 > 收益；
>
> **第二**，设计了 7 状态测试计划生命周期（对外口径 5 个核心状态）：
> 状态校验集中在 ExeFunctionServiceImpl 的 switch 里，配合 @Transactional 保证级联原子性；
>
> **第三**，实现了 EMS 异步指令下发：用 Spring 的 ThreadPoolTaskExecutor + RestTemplate，
> 不走 @Async 是为了避开 self-invoke 失效的坑；
>
> **第四**，落地了 OAuth2 password grant + RBAC：自定义 HandlerInterceptor 调远程 /userinfo 校验，
> 用 ThreadLocal 装当前用户，记录到 OperationLog 做全链路审计；
>
> **第五**，主导了一次 17 文件 / +2014/-1723 行的重构（commit 455129d），
> 一周内消除 15 处 TODO，做了用户上下文 + 状态机优化 + 接口按动作粒度切。

### 3:30–4:30 反思与个人风格
> 这个项目让我反复练习"什么时候该过度设计、什么时候该克制"——
> 比如状态机我选手写而不是 Spring StateMachine，
> 异步选线程池而不是 MQ，
> 都是基于"当前规模"做的取舍。
>
> 我个人比较喜欢做"看似简单但要拿捏边界"的工程问题。

### 4:30–5:00 钩子
> 这次复盘下来我也清楚识别了一些已知缺口——
> 比如后端 0 个 @PreAuthorize、Druid 默认配置太裸、Swagger 与 Spring Boot 2.6 的兼容 bug——
> 后续如果有机会一定都要补上。

---

## 二、15 分钟版（技术一面 / 项目讲述环节）

### 段落 1：背景 + 架构（3 min）

讲：
- 业务背景：飞机检测试运现场，三个 actor（设计员 DESIGNER / 执行员 EXECUTOR / 管理员 ADMIN）
- 三服务：Spring Boot 后端 + Vue 3 前端 + Flask OAuth Mock
- 后端 Maven 4 模块：base / model / dal / service，service 是唯一启动 jar
- 双维度切分：水平按依赖方向（base→model→dal→service），垂直按域（designer / planner）

钩子句："这是模块化单体，不是微服务——这是我做的一个反向演进。"

### 段落 2：微服务→单体演进（3 min）

讲：
- 起因：项目早期按"测试设计 / 测试执行 / 设备管理 / 数据访问层"拆 9 个微服务（**仓库里至今还有 9 个 SpringBoot 主类，只有 fastop-service 真正运行**）
- 痛点：用户量不到 100 + 团队 3 人 → 网络开销 + 联调成本 + 分布式事务问题爆炸
- 决策依据：业内有 Sam Newman《Monolith to Microservices》、Amazon Prime Video 的反向案例
- 实施：把 Feign 调用替换为本地 Service 方法（commit 637613b），DTO 不动，调用通道下沉
- 残留痕迹：ExeFunctionServiceImpl L100/L116/L141 的 `// Local Call: replaced functionSuiteFeignService...` 注释保留方便溯源
- 防止退化：Maven 模块边界做物理隔离，跨域走 Service 接口（依赖倒置）

钩子句："这次演进让我学到——架构不是越分布式越好，是越匹配规模越好。"

### 段落 3：状态机驱动测试计划生命周期（3 min）

讲：
- 7 个状态：UNEXE / VERIFY / EXEING / PAUSE / MVERIFY / DISPATCH / FINISH，对外口径"5 核心状态"
- 双定义：常量类（int 给 SQL 用）+ Enum（给业务代码用）
- 集中校验：ExeFunctionServiceImpl.updateFunctionStatusByOption 的 switch + 前置 if，非法转移 log.warn 返回 false
- 选型理由（必被追问）：
  - 状态少 + 流转线性 + 学习成本——不上 Spring StateMachine
  - 改进空间：状态超 10 个 / 需要持久化上下文时再上框架
- 副作用管理：
  - 内部级联（TestPlan→ExeFunction）走同事务
  - 外部 EMS 推送走异步线程池

亮点句："**快照式派工**是这块的核心设计——派工时把 TestFunction 全字段复制为 ExeFunction，可重放可审计，对应电商订单快照商品价格的模式。"

### 段落 4：EMS 异步指令下发（3 min）

讲：
- 触发：POST /exeStep/do → ExeStepServiceImpl.doV1 → processAsyncEms → RestTemplate POST EMS
- 异步实现：ThreadPoolTaskExecutor（core 5 / max 10 / queue 20，前缀 Async-Step-）
- 显式注入 Executor 而不是 @Async：避开 self-invoke 失效 + 默认 SimpleAsyncTaskExecutor 每次新线程的坑
- 当前现状（坦诚说）：
  - 失败仅 log.error，无重试、无幂等键
  - 拒绝策略已显式 CallerRunsPolicy（L29），队列满主线程兜底（不丢但拖主线程）
- 改进路线（演进性思考）：
  - 加本地消息表（ems_outbox）+ 定时扫表重投
  - command_id 唯一索引保证幂等
  - 高并发上 Redisson 分布式锁限制同设备并发

### 段落 5：OAuth2 + RBAC（2 min）

讲：
- OAuth2 password grant：first-party app 简单合理（如开放第三方再上 Authorization Code + PKCE）
- 后端校验：自定义 UserContextInterceptor，调 mock 的 /userinfo 远程校验（不是 JWT 本地解析，换"即时吊销"和"实现简单"）
- ThreadLocal 用户上下文：UserContextHolder + Interceptor.preHandle set / afterCompletion clear
- RBAC：3 角色 × 权限码二级映射，前端 Pinia hasRole 判定
- 已知缺口（主动暴露）：
  - 后端 0 个 @PreAuthorize 注解，仅前端隐藏按钮
  - listAll 接口无权限过滤，IDOR 风险

### 段落 6：核心重构 commit 455129d（1 min）

STAR 浓缩：
- **S**：原型阶段，硬编码 userId=system，状态校验缺失，TODO 累积 15 处
- **T**：1 个月内完成"可审计 + 状态防呆 + 权限细化"
- **A**：ThreadLocal 用户上下文 + 状态机集中校验 + remarkTestPlan 接口分离
- **R**：17 文件 / +2014/-1723 行单 commit；TODO 清零；状态相关 bug 0 例

收尾："这次重构让我系统理解了 ThreadLocal 在线程池场景的传递问题——
当时如果 EMS 异步线程也要用 user，必须用 TransmittableThreadLocal——
这是项目当前还没补的一个潜在 bug 点。"

---

## 三、30 分钟版（终面深聊 / 全维度过一遍）

> **结构**：
> - 第 1 段（5 min）：5 分钟版的全部内容，让面试官知道全貌
> - 第 2 段（15 min）：15 分钟版的 6 段，按面试官兴趣顺序详讲
> - 第 3 段（10 min）：留给追问 + 反问

### 必准备的 5 个深挖话题（任意一个 5-8 min）

#### 话题 A：派工流程的"快照式" vs "引用式"
- 快照式实现：ExeFunctionServiceImpl.saveExeFunction 逐字段复制
- 三大优势：可重放 / 隔离设计变更 / 状态字段独立
- 代价：DB 行膨胀（1000 计划 × 100 步骤 = 10 万行）
- 业内对照：电商订单 / Activiti 部署版本 / Git 提交不可变
- 何时改引用式：步骤体量小 + 修订频繁 + 不需要回溯

#### 话题 B：状态变更的事务边界与副作用解耦
- 当前：startPlan 同事务级联 ExeFunction，EMS 推送走事务外异步
- 风险：事务内同步调外部服务会拖慢事务 + 增加锁持有
- 改进：领域事件 `ApplicationEventPublisher` + `@TransactionalEventListener(AFTER_COMMIT)`
- 进一步：本地消息表 + 定时投递保证最终一致

#### 话题 C：ThreadLocal 在 EMS 异步线程的潜在 bug
- 现状：UserContextInterceptor 设值，processAsyncEms 在另一线程取不到
- 真实风险：操作日志可能记到错的用户（其他请求的 ThreadLocal 残留）
- 修法：包装 Runnable 捕获当前 username + 子线程开头 set + 末尾 clear
- 升级：用 TransmittableThreadLocal（阿里），线程池场景透明传递

#### 话题 D：模块化单体如何为"未来再拆"留口
- 保留契约：DTO 与 entity 严格分离
- 保留接口：跨域走 xxxService 不走 xxxServiceImpl
- 保留事务边界：单事务只动当前域
- 保留可观测性：日志、操作日志按域记
- 这是 **演进式架构**（Evolutionary Architecture）的实践

#### 话题 E：项目已知缺陷清单 + 改进路线图
- 数据：12 张主表无任何索引；DB 名 autosys_1014 含日期；密码默认值明文
- 安全：listAll 0 权限过滤；后端 0 个 @PreAuthorize
- 工程：Druid 全默认（含 maxActive=8 瓶颈）；Swagger UI 大概率打不开（SpringFox + Spring Boot 2.6 兼容 bug）；测试覆盖 ≈ 0
- 文档：CLAUDE.md 提的 API_DOCUMENTATION.md 实际不存在（漂移）
- 路线图：Druid 调优 → 全局异常 → 单测覆盖到 60% → SpringDoc 替换 Swagger2 → 数据级权限 → Flyway 迁移

### 反问环节（终面收尾必备）

技术：
1. 团队对"模块化单体 vs 微服务"的判断标准是什么？
2. 状态机选型上你们更倾向框架还是手写？为什么？
3. CI/CD 现在跑哪些质量门？覆盖率有阻断阈值吗？

团队：
4. 团队多少人？前后端比例？code review 怎么走？
5. 新人入职第一周一般做什么？

个人：
6. 这个岗位接下来 3-6 个月最想解决的问题？
7. 团队有 [分布式 / 性能调优 / 架构设计] 的实战机会吗？

---

## 四、应急话术速查

### 不会的题怎么处理
> "这块我没在生产环境用过，但根据原理推测应该是 [X]——
> 如果 [X] 不对，麻烦您指点一下我应该往哪个方向想。"

### 被追到知识盲区
> "这个细节我不确定，回去我会查 [具体文档/源码] 确认——
> 不过相关的 [我熟悉的部分] 我可以展开聊聊。"

### 简历项目被怀疑"是不是你做的"
> "整个 commit 历史在 [git remote / 个人仓库] 都能看，
> 主分支的 commit 458129d 是单 commit +2014 行，作者是 seal，
> 您可以看其中任意一个文件让我现场讲改动逻辑。"

### 薪资被问起（HR 面）
> "我了解到这个职级 [行业范围 X-Y]——
> 在贵司期望是 [X+10%]，但我更看重 [团队 / 成长 / 技术挑战]，
> 这块如果有合理空间我可以再聊。"

---

## 五、自查清单（面试前 24 小时过一遍）

- [ ] 项目背景能 30 秒说清楚
- [ ] 5 个核心模块每个能 2 分钟独立讲
- [ ] commit 455129d 的 STAR 能背出来
- [ ] 7 个状态名能默写
- [ ] EMS 链路（Controller→Service→processAsyncEms→RestTemplate）能默写
- [ ] 5 个已知缺陷能主动说
- [ ] 5 个反问问题准备好
- [ ] application.yml 关键字段（端口 10001 / context-path /fastop / db 端口 3304）记牢
- [ ] 笔试题：把 LeetCode hot 100 / 剑指 Offer 高频题过一遍
- [ ] 准备一个失败 + 反思的故事
- [ ] 准备一个团队冲突 + 解决的故事

---

## 六、百轮加固叙事（**强烈建议讲**——区分"做项目"与"持续维护项目"的关键）

### 30 秒电梯版
> "项目交付后我做了一轮持续质量审计——101 轮 cron 触发 + AI 辅助源码审计，
> 累计定位 412+ 项硬伤，**已修 181 项**，并配 **41 个 JUnit 单测**做回归保护。
> 关键修复包括 1 个数据外泄核弹（CommandDashboard 嵌 fetch localhost ingest UUID）、
> 2 个 CVE（commons-text Text4Shell、fastjson autotype RCE）、
> 1 处审签流权限校验注释残留、3 处 N+1 查询折叠（最大一处 1+M+M·C+M·C·S → 3 SQL）、
> 13 处 @Transactional 缺 rollbackFor 的 Checked 异常不回滚漏洞。"

### 加分句（演示工程文化）
> "每个修复都加了单测保护——比如 PP.1 字段名漂移修复后 4 个 JUnit 锁定 ResponseBody JSON 输出必须是 message 不是 msg，
> 未来谁手滑改回去 CI 立刻红。这比靠 code review 信任未来开发者更可靠。"

### 反问机会
> "团队对'修旧 bug'与'冲新功能'的优先级怎么权衡？有没有专门的 tech debt sprint？"

### 答题转化
- 被问"项目最大挑战" → 讲百轮里程碑 + 5 金弹之一
- 被问"如何保证代码质量" → 讲单测矩阵 + CI mvn 移除 -DskipTests
- 被问"主动学习" → 讲发现核弹 / CVE 的过程
- 被问"团队协作" → 讲 KNOWN_ISSUES.md 累计文档化所有硬伤供后人查

### 数字硬指标（git stat 验证）
- **62 文件修改 / 1 文件删 / 6 文件新增**
- **+5611 / -5329 行 diff**（净 +282，多为重构等价替换）
- **新增 8 单测文件 + 3 异常类 + GlobalExceptionHandler + SwaggerConfig + APP 常量**
- **文档体系**：5 大文档共 6291 行（QA 5277 + KNOWN_ISSUES 644 + PITCH 259 + CHEATSHEET 111 + MOCK 362 + 各 README）
- 这些数字面试官可现场 `git log --stat` 复核——所有改动有 commit 留痕
