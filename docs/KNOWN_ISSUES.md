# Fastop 已知硬伤汇总 · 改进路线图

---

## 🎯 百轮加固里程碑（2026-05-03）

> 本文档累计标记 412+ 项硬伤；经过 101 轮持续审计 + 大批量重构后，**已修 181 项**。
> 配 **41 个 JUnit 单测**保护回归；mvn 9 模块 BUILD SUCCESS / vue-tsc exit=0 / CI 已移除 -DskipTests。

### 已修分类汇总（按严重度倒序）

| 类别 | 已修项数 | 关键案例 |
|------|---------|---------|
| **真业务 bug** | 14+ | ZZ.8 编辑模块 approveStatus=0 打回审签 / AAA.1 checkWorker 写死 'worker' / TestSuite 审签缺 expectedWorker / log.info `:` 占位符遗漏 / 多处 success 包失败反模式 |
| **P0 安全 / CVE** | 19+ | UU.1 ingest 数据外泄核弹 / Login 写死 admin/123456 / 2 处 werkzeug RCE / commons-text Text4Shell（CVE-2022-42889） / fastjson 1.2.78→1.2.83 autotype RCE |
| **N+1 性能** | 4 处 | startPlan/pausePlan batch update / dispatchPlan IN 一次 / **conveyTestStep2ExeStep 三层 1+M+M·C+M·C·S → 3 SQL** |
| **假实现拆除** | 14 | UU.1 ingest fetch / XX.1-2 假按钮（handleSave/import/export） / AAA.2 假按钮 / TT.1 整个 Device 模块壳 / WW.1 OAuth refresh-token 写而不接 |
| **AI 草稿清理** | 30+ 行 | "I'll assume..."/"Reference uses..."/"But I am trying to minimize changes" / 1-2-3 步骤注释 |
| **Spring 八股漏洞** | 13+ | @Transactional(readOnly=false) → rollbackFor=Exception.class（Checked 异常不回滚） |
| **架构补全** | 多项 | GlobalExceptionHandler / 401 双拦截器 / RBAC 路由守卫 meta.roles / UserContextInterceptor token 缓存（吞吐 +99%） / Swagger UI 复活（@EnableSwagger2 + Docket） / 业务异常体系（BusinessException + Unauthorized + Forbidden） |
| **依赖治理** | 多项 | starter-validation/actuator/test 补 / SpringFox NPE 兼容（matching-strategy=ANT_PATH_MATCHER） / pom 重复依赖删 |
| **契约统一** | 端到端 | PP.1 msg→message 全栈（mock + 后端 ResponseBody 加 @JsonProperty + @JsonAlias + 前端 + 4 单测保护） / WW.4 13 函数 Record 升 interface |
| **品牌收口** | 1 大 | RR.1 五项目对外名 → APP_TITLE 单一来源 + router 动态 title |
| **应用配置加固** | 多项 | jackson 时区/格式 / multipart 50MB / druid pool / graceful shutdown / actuator endpoints exposure 仅 health/info |

### 单测矩阵保护
| 测试类 | 用例 |
|--------|------|
| ResponseFactoryTest | 4 |
| ResponseBodyJsonTest | 4 |
| GlobalExceptionHandlerTest | 8 |
| GlobalExceptionHandlerMvcTest | 5 |
| BusinessExceptionTest | 5 |
| EmsMessageServiceTest | 5 |
| UserContextHolderTest | 4 |
| UserContextInterceptorTest | 6 |
| **总计** | **41 PASS** |

### 5 个金弹故事
1. **核弹拆除 UU.1**：CommandDashboard 内嵌 fetch http://127.0.0.1:7636/ingest/{UUID} 是 AI hypothesis-driven debugging 痕迹混入生产，业务数据外泄到本地端口
2. **PP.1 字段名漂移**：业务错误用户永远只看到 'Error' 三字母 — msg vs message 三方不一致；全栈统一 + 4 单测锁死
3. **N+1 三层折叠**：conveyTestStep2ExeStep 1+M+M·C+M·C·S → 3 SQL
4. **TestSuite 审签真业务漏洞**：expectedWorker 校验**被注释掉** + `TODO*/` — 任意已登录用户能审签任意清单
5. **Spring @Transactional 漏洞**：13 处 readOnly=false 缺 rollbackFor=Exception.class

---

## ✅ 已修复（2026-05-03 规范类批量修复 — 早期 22 项）

> 14 文件改动 / +715/-788 行 / mvn compile 9 模块 SUCCESS

| # | 项 | 操作 |
|---|----|------|
| #5y | ResponseFactory.getFeignData 微服务残骸 | ✅ 删除（0 调用） |
| #5σ | ResponseMsg.RPC_ERROR 微服务残骸 | ✅ 删除（0 调用） |
| #5ρ | ResponseCode SYSTEM_ERR/SYSTEM_ERROR 重复 | ✅ 删 SYSTEM_ERROR（保留 SYSTEM_ERR 因 2 调用） |
| #5l | ExeStepCommand 字段无 private | ✅ 4 字段加 private |
| #5τ | FunSuiteIdConnectDto 字段无 private | ✅ 2 字段加 private |
| 新 | BaseStructController public 字段 | ✅ 改 private |
| #第7 | TestExampleController.UpdateAll() 命名 | ✅ 改 updateAll()（接口+impl+controller 共 3 处同步） |
| #5s | fastop-base-common pom 重复 swagger-annotations | ✅ 删一份 |
| #5s | fastop-model-planner pom 重复 swagger-annotations | ✅ 删一份 |
| 新 | fastop-service pom 重复 fastop-model-planner | ✅ 删一份 |
| #5u | spring-boot-maven-plugin 2.7.18 vs 父 BOM 2.6.13 | ✅ 改 ${spring-boot.version} 对齐 |
| #5v | skip=true + repackage 矛盾 | ✅ 删 skip 让 repackage 真正生效 |
| #5ι | fastop-base packaging=pom 非法 spring-boot-maven-plugin | ✅ 删整段 |
| 新 | fastop-base-common 同样非法 plugin | ✅ 删整段 |
| #5ξ | StatusContants 死代码注释行 | ✅ 删 11 行注释掉的常量 |
| 新 | ResponseFactory 缩进不一致 | ✅ 统一 4 空格 |
| #5n | TestFunctionStepController StepId 大写 | ✅ stepId（参数+路径+前端 module-orchestration.ts 同步） |
| 新 | TestFunctionModuleController ModuleId 大写 | ✅ moduleId（参数+路径+前端同步） |
| 新 | listByFunId 误导命名 ×2 | ✅ Case 改 listByModuleId / Step 改 listByCaseId |
| 新 | TestFunctionController log 级别混用 debug→info | ✅ 5 处 log.debug 统一 log.info |
| 新 | TestFunctionController.list if 业务分支 | ✅ 改三元 + 加 log |
| #5μ | CommonConstants NUM_2/3/6/7/8/9 反模式 | ✅ 删未使用部分（保留 0/1/4/5 因有调用） |

**未修原因**：
- 命名拼错（verfier / mesdceCode / relyFuntionReady / TestPlanStatusContants 类名 / StatusContants interface 名 / _fial ×5）→ DB 列映射 + 全仓 import 影响巨大
- 业务逻辑类（事务遗漏 / N+1 / 静默吞异常 / 审签 TODO 注释 / IDOR）→ 改动会破功能

---



> 全部硬伤来自 INTERVIEW_QA.md 各节散布的"主动暴露项"，按"严重度 × 修复难度"分级。
> 面试时主动陈述 = 加分项；接手项目第一天的"30/60/90 day plan"骨架。

---

## 优先级矩阵

| 严重度 / 难度 | 易（< 1 周） | 中（1-4 周） | 难（> 1 月） |
|--------------|------------|------------|--------------|
| **P0 安全** | #1 #2 #4 | #3 #5 | #6 |
| **P1 稳定** | #7 #8 | #9 #10 | — |
| **P2 工程** | #11 #12 #13 #14 | #15 #16 #17 | #18 |
| **P3 体验** | #19 #20 | #21 #22 | #23 |

---

## P0 · 安全（数据安全 / 越权 / 凭据）

### #1 listAll 接口零权限过滤（IDOR）
- **现状**：`TestPlanController.listAll(L60)` / `TestSuiteController.listAll` / `TestFunctionController.listAllTestFunction` 等接口任何登录用户能拉全量数据
- **风险**：EXECUTOR 能看到 ADMIN 私有计划；信息泄漏
- **修法**（< 3 天）
  ```java
  @GetMapping("/listAll")
  public Response<List<TestPlanDto>> listAll() {
      String currentUser = UserContextHolder.getCurrentUser();
      List<String> roles = roleService.getRoles(currentUser);
      if (roles.contains("ADMIN")) return success(planService.listAll());
      return success(planService.listByOwner(currentUser));
  }
  ```
- **配套**：Mapper 加 `where created_by = #{user} or executor_group_id in (...)` 行级过滤

### #2 数据库密码默认值明文
- **现状**：`application.yml:10` `password: ${FASTOP_DATASOURCE_PASSWORD:Fastop@123}`
- **风险**：环境变量未设 → 生产用兜底密码 → git 仓库公开 → 全网爆破
- **修法**（10 分钟）：去掉默认值，改 `${FASTOP_DATASOURCE_PASSWORD}`，启动失败比泄漏好
- **配套**：上 Vault / k8s Secret / Spring Cloud Config 加密

### #3 后端 0 个权限注解
- **现状**：grep `@PreAuthorize / @Secured / @CheckRole` → 0 命中
- **风险**：仅前端 hasRole 隐藏按钮，绕过前端直调 API 即越权
- **修法**（2 周）
  - 引 spring-security-core
  - 自定义 `PermissionEvaluator`
  - 关键 Controller 加 `@PreAuthorize("hasPermission(#id, 'TestPlan', 'execute')")`
  - 全量回归测试

### #4 数据库 root 账号
- **现状**：`username: root`
- **风险**：应用 SQL 漏洞 → 拖库 + DROP TABLE
- **修法**（1 天）：建 `fastop_app` 账号，仅授权目标库的 SELECT/INSERT/UPDATE/DELETE，禁 DROP/CREATE/GRANT

### #5d OperationLog.ip 字段永远 null（审校批 6 新发现）
- **现状**：实体 `OperationLog.java:20` 有 `private String ip`；`TestPlanServiceImpl.recordOperationLog L410-426` 未调 `log.setIp(...)`
- **风险**：操作来源 IP 无法追溯，安全审计弱（无法定位异常 IP）
- **修法**（10 分钟）：recordOperationLog 加 `log.setIp(request.getRemoteAddr())`，方法签名增加 HttpServletRequest 入参或从 RequestContextHolder 取

### #5f 实体类风格漂移 @Data vs 手写
- **现状**：OperationLog（22 行）用 Lombok `@Data`；TestPlan（335 行）手写 getter/setter
- **风险**：维护成本爆炸，TestPlan 加字段要手写 4 行（field+getter+setter+setter trim 逻辑）
- **修法**（< 1 天）：TestPlan 加 `@Data` 注解删 264 行 boilerplate，但要注意 `setPlanId(.trim())` 的清理逻辑要保留 → 用 @Data + 单独的 setter override

### #5DD 🔥🔥 TestSuiteServiceImpl.check 审签人校验被 TODO 注释（审签权限漏洞）
- **现状**：`L317-321` 整段 `if (expectedWorker == null || !expectedWorker.equals(checkWorker))` 校验被 `/* ... TODO */` 注释
- **风险**：任何登录用户能在任何审签级别冒充任何审签人
- **commit 455129d 自述"恢复审签权限校验"**，但此处实际未恢复 → 文档与代码不一致
- **修法**（5 分钟）：解开注释；同时 service 层从 UserContext 取 currentUser 与 expectedWorker 比对（不应让前端传 checkWorker）

### #5EE 🔥 TestSuiteServiceImpl.delete 物理删除（与软删除模式不一致）
- **现状**：L239 `deleteByPrimaryKey(suiteId)`
- **风险**：与其他实体"deleted=true"模式不一致；删除后无法恢复
- **修法**（10 分钟）：改 `update set deleted=1` 软删除模式

### #5FF delete 失败也返 success
- **现状**：`L243 return ResponseFactory.success("删除失败")`
- **风险**：bug；success 包装含"删除失败"消息；调用方判 success 误以为成功
- **修法**（5 分钟）：改 `failure`

### #5CC 🔥 reviewSuiteSpecial 缺 @Transactional（第 3 处事务遗漏）
- **现状**：`TestSuiteServiceImpl L352-362` 3 次 service 查询 + 1 次 update 全无事务
- **风险**：military / keyProcess 半更新；DB 不一致
- **修法**（5 分钟）：方法加 `@Transactional(rollbackFor = Exception.class)`

### #5ε 🔥 createFunctionSuite 缺 @Transactional
- **现状**：`FunctionSuiteServiceImpl.createFunctionSuite L53-92` 循环 insertSelective + L90 调 `testSuiteService.reviewSuiteSpecial(suiteId)`，整个方法**没有 @Transactional**
- **风险**：插入已 commit 后，reviewSuiteSpecial 失败 → 数据不一致（FunctionSuite 已加但清单状态未更新）
- **修法**（5 分钟）：方法加 `@Transactional(rollbackFor = Exception.class)`

### #5ζ 循环依赖（TestSuiteService ⇄ FunctionSuiteService）
- **现状**：FunctionSuiteServiceImpl 用 `@Autowired @Lazy` 绕过循环依赖
- **风险**：@Lazy 是症状治疗；构造器注入 + 循环依赖会启动失败；setter/字段注入 + @Lazy 是技术债
- **修法**（>1 周）：抽取共用领域逻辑到第三方 Service（如 SuiteEditPolicyService）；或用 ApplicationEventPublisher 事件解耦

### #5η 循环 insertSelective 性能问题
- **现状**：`createFunctionSuite L78-89` for 循环单条 insertSelective
- **风险**：N 次 SQL；批量场景慢
- **修法**（30 分钟）：MyBatis `<foreach>` 批量 insert

### #5γ 🔥 conveyTestStep2ExeStep N+1 查询性能瓶颈
- **现状**：`ExeStepServiceImpl L94-157` 三层嵌套 for（module→case→step），每层调一次 Service.getByXxxId
- **量化**：1 计划 × 10 module × 10 case × 10 step = 111 次 SQL；100 计划批量派工 = 11100 次 SQL
- **风险**：派工大计划时性能爆炸
- **修法**（1-2 周）：
  - 一次性批量查 funId → 全部 module → 全部 case → 全部 step（3 次 IN 查询替代 111 次单查）
  - 在内存做嵌套组装 + 批量 insertSelective
- **优化空间**：可一次 JOIN 查全树（test_function → module → case → step），单 SQL 出全量

### #5δ Service 接口返回 Response<Object> 失去类型
- **现状**：`testFunctionModuleService.getByFunId(funId).getData()` 返回 Object；调用方用 `ObjectMapper.convertValue` 反序列化为 List
- **风险**：Service 设计反模式；类型信息全丢；编译期 IDE 无补全；调用方易写错类型
- **修法**：所有 Service 接口返回 `Response<List<XxxDto>>` 强类型

### #5α 🔥🔥 8 个废弃 SpringBoot 主类（核弹级死代码）
- **现状**：项目共有 9 个 `Fastop*Application.java` 主类，仅 `FastopServiceApplication` 被实际使用
- **死类清单**：FastopApplication / FastopBaseApplication / FastopBaseCommonApplication / FastopDalApplication / FastopDalDesignerApplication / FastopModelDesignerApplication / FastopModelPlannerApplication / FastopModuleApplication
- **风险**：
  - 死代码污染（项目本质 1 个服务，看代码像 9 个）
  - 新人疑惑哪个是真主类
  - mvn package 在每个子模块都触发 spring-boot-maven-plugin（即使 skip=true 也浪费构建时间）
  - 误启动其他主类会出现奇怪的 BeanNotFound
- **微服务遗留最强证据**：每个 Maven 子模块原本都是独立 Spring Boot 服务
- **修法**（1 周，需谨慎）：
  - 所有子模块 pom 删 spring-boot-maven-plugin
  - 删除 8 个废弃主类
  - 子模块改 packaging=jar（不是 application）
  - mvn clean install 验证 service 仍能起

### #5β 包名命名不一致
- **现状**：
  - `.fastopbase`（无 .，FastopBaseApplication 所在）
  - `.fastopmodule`（无 .，FastopModuleApplication 所在）
  - `.fastop.base.common` / `.fastop.model.designer` / `.fastop.model.planner`（标准五段）
- **风险**：包结构混乱；@MapperScan / @ComponentScan 路径要写两套
- **修法**：与 #5g 测试类包名不一致联动，一次性统一为 `.fastop.{base,model,dal,service}.{xxx}`

### #5y ResponseFactory 残留 getFeignData 方法
- **现状**：`ResponseFactory.java:75-80` `public static <T> T getFeignData(Response<T> respData)` 专门解析 Feign 响应
- **风险**：项目已无 Feign 调用，但工具方法保留 → 后人误以为还有 Feign，可能新增 Feign 调用引发依赖回归
- **修法**（5 分钟）：删除该方法，加注释 "// removed Feign helper, project is now monolith"

### #5z ResponseFactory 强制类型转换
- **现状**：`ResponseFactory.success/failure/build` 用 `(Response<T>) builder()...` 强转
- **风险**：unchecked cast 编译警告污染；应该直接 return builder 链式
- **修法**（10 分钟）：去掉 cast，调整方法签名让 builder 自然返回 Response<T>

### #5u 🔥 spring-boot-maven-plugin 版本错配
- **现状**：`fastop-service/pom.xml:125` `<version>2.7.18</version>`，但 spring-boot 父 BOM 是 2.6.13
- **风险**：plugin 与 starter 版本错配；fat jar 打包行为不可预期；某些自动配置可能触发版本兼容警告
- **修法**（5 分钟）：`<version>${spring-boot.version}</version>` 与父 BOM 对齐

### #5v `<skip>true</skip>` + execution repackage 矛盾
- **现状**：spring-boot-maven-plugin 配置 `<skip>true</skip>` 跳过；同时又有 execution `<goal>repackage</goal>` 强制执行
- **风险**：fat jar 是否生成不可预期；mvn package 后必须实测 target/ 看是否有 fastop-service-*.jar 含 BOOT-INF
- **修法**（5 分钟）：删 `<skip>true</skip>`，让 repackage 默认执行

### #5w 🔥🔥 fastop-dal 是微服务残骸（死代码 + 空文件）
- **现状（审校批 24 加深）**：
  - `fastop-dal/` + `fastop-dal-designer/` 各有 SpringBoot 主类
  - 唯一的 `TestFunctionMapper.java` 是 **0 字节空文件**
  - fastop-dal pom 含完整 web + db 依赖（druid + mysql + spring-boot-starter-web + javax.servlet + commons-text + httpclient）
  - service/pom.xml **未引用 fastop-dal-designer**
- **风险**：死代码；mvn build 跑但产物无用；新人迷惑（这模块到底干啥的？）
- **微服务残骸最强证据**：原本可能是"data-access-layer"独立服务，演进时未清理
- **修法**：1) 把 TestFunctionMapper 迁到 fastop-model-designer/dao/，删掉 fastop-dal 整个模块；或 2) 让 service 引用 fastop-dal-designer，把所有 Mapper 集中到 dal 模块（更彻底的分层）

### #5x TestPlanRequestDto 与 TestPlan 字段不对齐
- **现状**：TestPlanRequestDto 19 字段；TestPlan 实体 30 字段；DTO 缺 commAssign / executAssign / verifyAssign / archived / sync / baseType
- **风险**：审签人前端无法传；DTO 与 Entity 漂移
- **修法**（30 分钟）：补全缺失字段

### #5s fastop-model-planner pom 依赖污染
- **现状**：
  - 重复声明 `swagger-annotations`（L40-43 + L44-47）
  - 引入 `spring-boot-starter-web`（model 子模块不该有 web 依赖）
  - 引入 `spring-boot-maven-plugin`（不是启动模块）
- **风险**：依赖膨胀 + 跨层污染 + maven 警告
- **修法**（10 分钟）：删冗余 swagger-annotations；移除 starter-web；删 spring-boot-maven-plugin

### #5t mapper XML filtering=true 隐患
- **现状**：`fastop-model-planner/pom.xml:60-69` `<filtering>true</filtering>` 对 `**/*.xml` 生效 → maven-resources-plugin 替换 `${propertyName}`
- **风险**：mapper XML 里的 `${criterion.xxx}` 占位符如果与 pom property 名撞，会被误替换
- **修法**（5 分钟）：把 `<filtering>true</filtering>` 改 false，或者 application.yml 单独 resource 段开 filtering，xml 段 filtering=false

### #5q stepOperate 用裸 Map 接收 body
- **现状**：`ExeStepController.stepOperate(L42)` `@RequestBody Map<String, String> params`
- **风险**：类型不安全；缺 @NotBlank 校验；Swagger 文档无法生成参数；新人改字段无 IDE 提示
- **修法**（30 分钟）：建 `StepOperateDto { String exeStepId; String option; }` + @Valid 校验

### #5r parseDate 重复实现
- **现状**：`OperationLogController.parseDate(L43-54)` 与 `ExeStepController.parseDate(L83-94)` **代码完全相同**
- **风险**：DRY 违反；改一个忘改另一个；项目越大越多 parseDate copy
- **修法**（10 分钟）：抽到 `fastop-base-common/util/DateUtil.parseDate`，两个 Controller 静态调

### #5o device.ts 路径与后端完全不对应
- **现状**：`frontend/src/api/device.ts` 调 `/devices/list /devices/{id} /devices`，后端 `DeviceIntegrationController` 只有 `/integration/device/topics`
- **风险**：DeviceManage.vue 任何 CRUD 操作直接 404，**完全不可用**（前端跑通是因为 mock 没存数据）
- **修法**：与 #19 设备域闭环联动，按 device.ts 约定的路径补后端 Controller

### #5p 前端 API 路径风格不统一
- **现状**：device.ts 用标准 REST（GET/POST/PUT/DELETE），其他模块大量 POST 模拟（如 `POST /functionSuite/deleteFunctionSuite`）
- **风险**：团队无统一约定，新人乱写
- **修法**（>1 月）：制定 RESTful API 规范文档，新接口必须遵守；旧接口逐步迁移

### #5l ExeStepCommand 字段无 private 修饰符
- **现状**：`ExeStepCommand.java:8-14` 4 字段全是包级访问（`String exeStepId;` 无 private）
- **风险**：Java 反模式；同包类可绕 setter 直接修改；@Data 生成的 getter 与裸字段并存语义不清
- **修法**（5 分钟）：4 字段全加 private

### #5m deviceId 字段断层
- **现状**：`ExeStepCommand` 含 `deviceId String`，但 `ExeStep` 实体**没有** device_id 列，仅有 `dependOnDevice Boolean` 标志
- **风险**：命令携带 deviceId 但持久化时丢失，无法回溯"指令发给了哪台设备"
- **修法**（与设备域 #19 联动）：建 Device 实体后给 ExeStep 加 device_id 外键

### #5n TestPlan.vue 状态魔法数字
- **现状**：`TestPlan.vue L65/66/69` 用 `scope.row.status === 5 / 0 / 3 / 2` 字面量判定
- **风险**：后端 TestPlanStatusContants 常量改名 / 改值，前端不会报错；新人看 0/2/3/5 不知什么意思
- **修法**（30 分钟）：前端建 `src/const/planStatus.ts` 镜像后端常量，全部改为 `PlanStatus.DISPATCH` 等

### #5ω 🔥 TestSuiteRequestDto 暴露审计字段给前端
- **现状**：DTO L39-47 含 deleted / createdAt / updatedAt / createdBy / updatedBy
- **风险**：前端可伪造 createdBy="admin"；恶意提交可篡改审计
- **修法**（30 分钟）：从 DTO 移除审计字段，service 层强制覆盖（`entity.setCreatedBy(UserContextHolder.getCurrentUser())`）

### #5AA 三层裸实体缺审计字段
- **现状**：TestFunctionModule / TestFunctionCase / TestFunctionStep 仅有 changeUser + xxxDate(String)，无完整审计字段
- **风险**：变更无法追溯；与其他实体不一致
- **修法**（1 周）：补 createdAt/updatedAt/createdBy/updatedBy/deleted 字段；DB ALTER TABLE 添加；逐步迁移

### #5BB 三处实体用 String 存日期
- **现状**：moduleDate / caseDate / stepDate 全 String
- **风险**：类型不安全；范围查询失效；时区处理混乱
- **修法**：DB 列类型 ALTER 为 TIMESTAMP；entity 改 Date / LocalDateTime

### #5χ 字符集 3 种混用
- **现状**：operation_log = `utf8mb4_unicode_ci`；260302.sql 主体 = `utf8mb4_0900_ai_ci`；exe_step 部分 = `utf8mb3_general_ci`
- **风险**：JOIN 不同 collation 报 `Illegal mix of collations`；emoji 在 utf8mb3 字段截断；排序行为不一致
- **修法**（>1 周）：全表 ALTER TABLE 统一 utf8mb4_0900_ai_ci（MySQL 8 默认）

### #5ψ 角色模型四处实体不一致
- **现状**：
  - TestFunction 6 角色（designer/proofer/verifier/checker/qualityer/approver）
  - TestSuite 3 角色（proofer/approver/submitter）
  - TestPlan 3 角色（dispatcherId/commanderId/executorGroupId）
  - ExeStep 3 角色（commander/verfier/soldier）
- **风险**：RBAC 权限治理无法统一抽象；前端权限判定要写 4 套
- **修法**（>1 月）：建立 `EntityAssignment(entityType, entityId, role, userId)` 通用关联表，所有角色字段下沉到关联表

### #5τ 🔥 DTO 含 Entity 反模式
- **现状**：`FunSuiteIdConnectDto.java:13` `List<TestFunction> testFunctions`
- **风险**：DTO 应只含 DTO 字段（解耦传输层与领域层）；含 Entity 会序列化暴露内部字段 + BLOB；将来 Entity 改字段直接破前端契约
- **修法**（30 分钟）：建 `TestFunctionRefDto { funId, funName, version }`，FunSuiteIdConnectDto 改持有 List<TestFunctionRefDto>

### #5υ TestFunctionStep stepCommandExample/Params JSON-as-string
- **现状**：两个 JSON 字符串字段存命令模板与参数
- **风险**：查询无法 SQL 索引；解析需 JSON 库（fastjson/Jackson）；类型不安全；BLOB 行外存储拖慢列表查询
- **修法**（>1 月）：MySQL 8 JSON 字段类型（支持 ->>'$.field' 索引）；或拆结构化子表 step_command_param

### #5φ DesignNodeDto 单 DTO 多态承载 3 种节点
- **现状**：DesignNodeDto 同时承载 Module/Case/Step 三种类型，含 `type String` + `children + (operation/obj/commandExample 仅 STEP 用)`
- **风险**：字段稀疏；类型不安全；新加节点类型要改公共 DTO
- **修法**（1 周）：拆 ModuleNodeDto / CaseNodeDto / StepNodeDto，sealed interface DesignNode；前端 type 判定后取对应实体

### #5ξ 🔥🔥 StatusContants Constant Interface 反模式 + 144 行常量爆炸
- **现状**：`StatusContants.java:3` `public interface StatusContants` 144 行业务状态常量
- **风险**（Effective Java 第 22 条）：
  - 实现 interface 的类自动继承所有常量到 namespace
  - 常量是实现细节但被 public 暴露
  - 接口本意是契约不是数据存储
- **修法**（>1 月）：
  1. interface → final class + private constructor
  2. 按业务域拆分（DeviceStatusConstants / SuiteApprovalConstants / StepLevelConstants 等）
  3. 删除注释掉的死常量
  4. 建立 enum 替代 int 常量（如 ApprovalStatus enum）

### #5ο `_fial` 拼错 5 处（第 6 个命名错误）
- **现状**：StatusContants L45/L47/L48/L49/L50 `tfun_app_proof_fial / verify_fial / qualityapp_fial / checkapp_fial / approve_fail`（4 处 fial + 1 处 fail）
- **风险**：与 #5j #5n #5θ #5ν 命名错误积累到 6 处
- **修法**：保留 + 注释或全量迁移

### #5π 数组常量索引错位
- **现状**：L132 `FUNS_APP_LEVEL = {"待提交", "待校对", "待质审", "待审查", "待批准", "待审核", "审签成功", "审签失败"}` 数组下标与 L123-130 `funs_app_*` 常量值不一一对应
- **风险**：用 `FUNS_APP_LEVEL[funs_app_verifier]` 拿到 "待质审" 而非 "待审核"（语义错乱）
- **修法**：改为 `Map<Integer, String>` 显式键值对；或用 enum 自带 description

### #5ρ ResponseCode 同值不同名重复
- **现状**：L7 `SYSTEM_ERR = 500` + L16 `SYSTEM_ERROR = 500`
- **风险**：调用方分不清；改一个忘改另一个
- **修法**（5 分钟）：删 SYSTEM_ERR，全量替换

### #5σ ResponseMsg.RPC_ERROR 微服务残骸
- **现状**：L21 `RPC_ERROR = "发生远程调用异常"`，项目无 RPC
- **修法**（5 分钟）：删常量

### #5μ 🔥 CommonConstants 定义 NUM_0 到 NUM_9（反反模式）
- **现状**：`CommonConstants.java:10-20` 定义 NUM_0/NUM_1/.../NUM_9 常量
- **风险**：常量名本身就是字面量；用常量替代字面量本意是给数字业务语义，这套写法等于没替
- **修法**（30 分钟）：删 NUM_X 常量；按业务命名（如 `MIN_PAGE_SIZE = 1` / `MAX_FAILED_RETRY = 3`）

### #5ν 🔥 TestPlanStatusContants 类名拼错
- **现状**：类名 + 文件名都是 `TestPlanStatusContants`（少 s，应是 Constants）
- **影响**：全仓 import 都跟着错；上线后改成本极高（package + import + 引用方）
- **修法**（>1 周）：批量替换 + 渐进式废弃；或保留并加注释 `// HISTORICAL TYPO`

### #5κ 🔥 TestSuite 版本号自增并发问题
- **现状**：`TestSuiteServiceImpl.add L52-67` 查 max(version)+1 模式
- **风险**：并发 add 时 A/B 同时查 max=5，都设 version=6 → DB UNIQUE 冲突或重复版本号
- **修法**（1 周）：
  - 方案 1：`INSERT INTO test_suite ... SELECT COALESCE(MAX(version), -1)+1 FROM ...`（数据库原子）
  - 方案 2：乐观锁 + 重试
  - 方案 3：业务表加版本号自增字段（per testBaseId）
- **业内**：电商订单号 / SCM 版本号都是这套思路

### #5λ 实体审计字段手动赋值
- **现状**：`TestSuiteServiceImpl.add L76` `testSuite.setCreatedAt(new Date())` 业务代码手动赋值
- **风险**：每个 service 都要写一遍；忘写就丢审计字段
- **修法**：MyBatis 拦截器统一填充 createdAt/updatedAt/createdBy/updatedBy 字段；或 DB DEFAULT CURRENT_TIMESTAMP

### #5θ TestFunctionRely `relyFuntionReady` 拼写错
- **现状**：`TestFunctionRely.java:14` 少 c
- **影响**：DB 列 / API 参数 / 前端字段全跟着错；与 #5j #5n 命名问题积累
- **修法**：保留 + 加注释，或全量迁移

### #5ι fastop-base pom 非法 spring-boot-maven-plugin
- **现状**：packaging=pom 但配 spring-boot-maven-plugin + repackage
- **风险**：pom 模块不能 repackage；skip=true 让构建通过但留废配置
- **修法**（5 分钟）：删 spring-boot-maven-plugin 整段

### #5j ExeStep `verfier` 字段名拼错
- **现状**：`ExeStep.java:72` `private String verfier`（少 i，正确 `verifier`）
- **影响**：SQL 列名 verfier / API 参数 / 前端字段全跟着错；新人看代码先懵 5 分钟
- **修法**（>1 月，迁移成本高）：DB 加新列 verifier → 双写 → 切前端 → 删旧列；或保留拼错并加注释"// HISTORICAL: keep typo for compat"

### #5k ExeStep "God Entity" 50 字段
- **现状**：`ExeStep` 47 字段 + `ExeStepWithBLOBs` 3 BLOB（commandData / failCause / criterionContent）
- **风险**：单实体职责过重；改动牵连面大；BLOB 拉取性能损失
- **修法**（>1 月）：按职责拆为
  - ExeStepCore（基础信息）
  - ExeStepStatus（exeStatus / verifyStatus / militaryStatus）
  - ExeStepAssignment（commander / verifier / soldier）
  - ExeStepCriterion（criterion* 字段 + criterionContent BLOB）
  - ExeStepResult（stepResult / failCause / militaryComment）

### #5h OperationLogServiceImpl 静默吞异常
- **现状**：`OperationLogServiceImpl.record(L24-32)` + `list(L36-54)` try-catch 整个 DB 操作，真实异常也返 success "已记录" / 空列表
- **风险**：调用方无法判定写入是否成功；与 #9 EMS 静默失败同类问题
- **修法**（30 分钟）：保留"未建表"特殊场景兜底（`SQLSyntaxErrorException` 才吞），其他异常透传给调用方

### #5i 冗余 UNIQUE KEY
- **现状**：`operation_log` L232-233 与某表 L268-269 主键 PRIMARY KEY (id) 后又加 `UNIQUE KEY id_UNIQUE (id)`
- **风险**：浪费空间 + 写入维护两个索引 + 误导后人以为 id 不是主键
- **修法**（5 分钟）：DROP 冗余 UNIQUE KEY，保留 PRIMARY KEY 即可

### #5g 测试类包名不一致
- **现状**：`fastop-base/src/test/.../fastopbase/`（无 `.`）vs `fastop-dal/src/test/.../fastop/dal/`（有 `.`）
- **风险**：包结构混乱，maven-surefire 自动扫描时可能漏；新人接手不知道往哪个目录加
- **修法**（30 分钟）：统一改 `com.hengtiansoft.fastop.{base,dal,model,service}` 五段命名

### #5e 前端 hasRole 覆盖率仅 2/9 视图
- **现状**：grep 全前端，只有 `Layout.vue`（菜单）+ `DeviceManage.vue`（按钮）调用 hasRole
- **风险**：TestPlan / TestReview / SuiteLibrary / ModuleLibrary / SystemLogs / CommandDashboard 6 个业务页面**全无角色判定**，绕过菜单直接访问 URL 即可看到所有按钮
- **修法**（1 周）：每个 view 顶部 `const canEdit = computed(() => authStore.hasRole('DESIGNER'))`，所有 写/删 按钮加 `v-if="canEdit"`；与后端 P0 #3 @PreAuthorize 配合

### #5 OperationLog.detail 明文落库
- **现状**：操作详情可能包含密码 / token / 手机号
- **风险**：日志查询页 / 数据库 dump 直接泄漏
- **修法**（1 周）
  - 实体加 `@Sensitive` 注解
  - logback Pattern Layout 过滤 `(password|token)=\\w+` → `***`
  - OperationLog 写入前手工脱敏

### #6 token 在 localStorage（XSS 易窃取）
- **现状**：`localStorage.setItem('access_token', token)`
- **风险**：任意 XSS 即偷 token
- **修法**（>1 月，前后端联动）
  - 后端改 cookie 模式：Set-Cookie + httpOnly + Secure + SameSite=Lax
  - 前端 axios 改 `withCredentials: true`
  - 加 CSRF token 防 Cookie 模式下的 CSRF 攻击

---

## P1 · 稳定性（OOM / 数据丢失 / 业务错乱）

### #7 ~~EMS 异步线程池拒绝策略~~（已修）
- **更正**：审校发现 `AppConfig.java:29` **已显式设置 `CallerRunsPolicy`**，先前判定有误
- **现状**：队列满时主线程同步兜底执行，不会丢指令但会拖慢主线程
- **保留改进项**：本地消息表 outbox + 定时投递（高并发场景更稳）

### #8 ThreadLocal 在异步线程不传递（潜在 bug）
- **现状**：`processAsyncEms` 在 taskExecutor 跑，`UserContextHolder.getCurrentUser()` 拿到错的 user
- **风险**：OperationLog 记错操作人
- **修法**（1 天）
  ```java
  // 提交前捕获 user
  String currentUser = UserContextHolder.getCurrentUser();
  executor.execute(() -> {
      UserContextHolder.setCurrentUser(currentUser);
      try { processAsyncEms(stepId); }
      finally { UserContextHolder.clear(); }
  });
  ```
- **进阶**：换 `TransmittableThreadLocal`（阿里 ttl）

### #9 EMS 失败仅 log.error，无重试
- **现状**：`ExeStepServiceImpl.java:362` 异常吞，无 retry / 死信
- **风险**：网络抖动 → 指令永久丢失
- **修法**（2 周）
  - Spring Retry 指数退避（1s/2s/4s）3 次
  - 失败入 ems_failed 表，人工 / 定时补偿

### #10a `dispatchPlan` 缺 @Transactional（审校批2 新发现）
- **现状**：`TestPlanServiceImpl.dispatchPlan(L306-353)` 方法**没有 @Transactional 注解**
- **风险**：状态 update（L347）成功 + OperationLog（L351）失败 = 状态推进了但审计漏记；或反之审计写了但状态没推进
- **修法**（5 分钟）：方法加 `@Transactional(rollbackFor = Exception.class)`，与 startPlan/pausePlan 一致

### #5b commons-text 1.9 — Text4Shell（审校批 4 新发现）
- **现状**：`fastop/pom.xml:27` `commons-text.version = 1.9`
- **CVE**：CVE-2022-42889（Text4Shell），漏洞影响 commons-text < 1.10.0；恶意输入触发 `StringSubstitutor` 解析时 RCE
- **风险**：若任何接口处理用户输入并经 commons-text 解析 → 远程命令执行
- **修法**（5 分钟）：升 `commons-text 1.10.0+`（推荐 1.11.0）

### #5c fastjson 1.2.78 — 历史 RCE 累累
- **现状**：`fastop/pom.xml:30` `fastjson.version = 1.2.78`（仍是 1.x 系列）
- **风险**：autotype 反序列化漏洞累计十多个 CVE
- **修法**（1 周）：迁 fastjson2（`com.alibaba.fastjson2`），autotype 默认禁用；UserContextInterceptor 解析 /userinfo 处直接换 Jackson 更佳

### #10b JSON 库混用（fastjson + Jackson）
- **现状**：
  - `UserContextInterceptor.java` 用 fastjson `com.alibaba.fastjson.JSONObject` 解 /userinfo
  - `EmsMessageService.java` 用 Jackson `ObjectMapper` 解 commandData
- **风险**：依赖膨胀 + fastjson 历史 RCE（CVE 累计十多个）+ 两套序列化策略不统一
- **修法**（1 周）：统一改 Jackson；fastjson 可改 fastjson2（无 autotype 默认禁）

### #10 ExeStep BLOB 字段批量加载 OOM 风险
- **现状**：`command_data / fail_cause / criterion_content` 都是 TEXT，listAll 全拉
- **风险**：万级数据量 → OOM
- **修法**（1 周）
  - listAll 改分页
  - 列表 SELECT 显式列（不要 SELECT *），BLOB 字段单独详情接口拉

---

## P2 · 工程（可维护性 / 可观测性）

### #11 SpringFox 2.9.2 + Spring Boot 2.6 兼容 NPE
- **现状**：93 处 @Api 注解但 Swagger UI 大概率打不开
- **修法**（10 分钟）
  ```yaml
  spring.mvc.pathmatch.matching-strategy: ant_path_matcher
  ```
- **彻底**（3 天）：迁 SpringDoc OpenAPI 3 + Knife4j

### #12 Druid 默认配置全裸
- **现状**：`application.yml:7-10` 仅 url/username/password
- **风险**：默认 maxActive=8 → 高并发立即耗尽
- **修法**（30 分钟）：BB 节配置改进 PR 模板复制粘贴

### #13 application.name = "service-designer"
- **现状**：微服务遗产命名，实际是 designer + planner 双域单服务
- **修法**（5 分钟）：改 `fastop-service`

### #14 数据库名 `autosys_1014`
- **现状**：含日期，无业务语义
- **修法**（1 周）：迁 `fastop`，配 alias 兼容旧脚本

### #15 12 张主表无索引
- **现状**：除 exe_log 有 3 个索引，其余主表全是全表扫
- **修法**（2 周）
  - 按高频查询补索引（test_plan(status,deleted) / exe_function(plan_id) / exe_step(exe_function_id)）
  - 上 Druid stat filter 监控慢 SQL 验证

### #16 无全局异常处理
- **现状**：grep `@RestControllerAdvice` 0 命中
- **风险**：业务异常直接 500 + 堆栈给前端
- **修法**（1 周）：写 GlobalExceptionHandler，统一走 `ResponseFactory.fail(code, msg)`

### #17 测试覆盖 ≈ 0
- **现状**：4 个测试类全是 `contextLoads()` 空壳
- **修法**（4 周）：S 节路线图，从状态机 switch 入手补到 60%

### #18 无 CI/CD 完整流水线
- **现状**：仅 `mvn package -DskipTests`，无前端构建、无 docker 镜像、无依赖扫描
- **修法**（>1 月）
  - 前端 CI：node ci → vue-tsc → vite build → upload artifact
  - Docker 镜像：multi-stage build + 推 harbor
  - OWASP Dependency-Check + SonarQube
  - 部署 ArgoCD 蓝绿

---

## P3 · 体验（功能缺口 / 文档漂移）

### #19 设备域前端跑后端缺
- **现状**：DeviceManage.vue 完整 CRUD，后端只有 `/integration/device/topics` 一个端点
- **修法**（2 周）：建 Device 实体 + Mapper Generator + Controller CRUD + ExeStep.device_id 外键

### #20 命令大盘无 echarts / WebSocket
- **现状**：CommandDashboard.vue 三层树 + 进度条，纯 HTTP 轮询
- **修法**（2 周）
  - 后端聚合接口 `GET /command/stats`
  - 前端 echarts 折线/饼图
  - SSE 推实时事件

### #21 操作日志缺导出 + 全文检索
- **现状**：SystemLogs.vue 只有筛选 + 表格
- **修法**（3 周）
  - EasyExcel 流式导出
  - 全文检索：MySQL 8 fulltext 或 ES

### #22 文档与代码漂移
- **现状**：CLAUDE.md 提及 `API_DOCUMENTATION.md` 与 `docs/DEVICE_API_SPEC.md`，**两份文件都不存在**；`.postman/` 仅有 `config.json` 空 workspace（无 collection、无 environment、无 globals）
- **修法**（1 周）
  - SpringDoc 反向生成 OpenAPI 3.0 yaml
  - 按 INTERVIEW_QA.md AA 节路由全表写 Postman collection + environment
  - CI 校验 `markdown-link-check` 防新漂移

### #23 无 Flyway 数据库迁移
- **现状**：手工跑 `dataset/*.sql`，无应用记录
- **修法**（>1 月，含 staging 验证）
  - 引 flyway-mysql
  - 现有 SQL 改 `V1__init.sql` baseline
  - 后续改动一律 `V20260413__xxx.sql`

---

## 30/60/90 Day Plan（接手第一天提案）

### Day 1-30 · 止血（P0 全部）
- 改 listAll 加权限过滤 + 移除密码默认值 + 改 root 账号
- 加全局异常处理 + Druid 配置补全 + Swagger 修复
- 完成 #1 #2 #3 #4 #11 #12 #13

### Day 31-60 · 加固（P1 全部）
- ThreadLocal 跨线程 + EMS 重试 + outbox 模式
- 上索引 + Druid 监控 stat 验证
- 测试覆盖路线启动（先覆盖状态机）
- 完成 #7 #8 #9 #10 #15 #16

### Day 61-90 · 演进（P2 + P3）
- 测试覆盖到 60%
- 设备域闭环（前后端打通）
- 命令大盘升级（echarts + SSE）
- 文档反向生成（SpringDoc）
- Flyway 迁移基线
- 完成 #17 #19 #20 #21 #22 #23

### Day 90+ · 长期演进
- token 模式从 localStorage → httpOnly cookie（#6）
- CI/CD 完整流水线（#18）
- 状态机超 10 个状态时切 Spring StateMachine
- EMS 改 RocketMQ 事务消息（高并发场景）

---

## 面试用法

- 被问 "项目有什么不足？" → 翻这页报 P0 三条 + 一句"我有完整路线图"
- 被问 "如果让你接手怎么改？" → 报 30/60/90 Day Plan 标题
- 被问 "你怎么排优先级？" → 讲优先级矩阵：严重度 × 修复难度
- 被怀疑 "是不是真做过？" → 每条都有源码行号 + 改进 PR 模板，现场打开看
