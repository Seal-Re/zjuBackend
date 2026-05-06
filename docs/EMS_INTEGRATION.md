# EMS 集成关系说明

> Fastop 与 EMS（Event Management System）保持**独立部署**关系，不进行代码合并。
> 本文显式记录两者职责分工、调用链路、字段约定与运维边界，避免后续误把它当成一个仓库或一个进程。

## 1. 仓库与服务边界

| 项目      | 仓库 / 路径                                 | 技术栈                                                                  | 默认对外端口         |
|-----------|---------------------------------------------|-------------------------------------------------------------------------|----------------------|
| Fastop    | 当前仓库 `D:/AgentWorkStation/zjuBackend`   | Java 8, Spring Boot 2.6.13, MyBatis, Druid, MySQL                       | `20004` (容器 8080)  |
| EMS       | https://github.com/Seal-Re/ems<br/>本地: `D:/AgentWorkStation/workspace/ems-backend` | Java 17, Spring Boot 2.5, Spring Cloud Alibaba, RocketMQ, Nacos, Redis, MySQL | `11451` (affair-center), `11452` (subscribe-center) |

两套技术栈差异显著（Java 版本 / MQ / 服务发现 / ORM 选型）。Fastop 是模块化单体，EMS 是事件驱动微服务集群。**领域边界清晰**：

- Fastop 负责 **测试管理**（设计 + 计划 + 执行 + 审签）。
- EMS 负责 **事件总线 / 异步消息分发**（接收事务 → MQ → 推送给订阅者）。

合并会强迫 Fastop 引入 RocketMQ + Nacos + Redis 整套基础设施，弱化领域分离，且 Java 版本无法对齐。**不合并是基于架构差异 + 领域分离的明确决定**。

## 2. 端到端调用链

```
[ Fastop ]                                           [ EMS ]                                  [ 设备控制器 ]
                                                                                              (device-controller)
ExeStepServiceImpl
   │
   │ 1. EmsMessageService.buildFromExeStep(step)
   │    → MessageEtt JSON
   │
   │ 2. POST {fastop.integration.ems-url}{ems-send-path}
   │    默认 path = /addDefault                      ┌──────────────────┐
   ├──────────────────────────────────────────────►  │ affair-center    │
                                                     │ AffairController │
                                                     └────────┬─────────┘
                                                              │ 3. RocketMQ.send(topic="affair", msg)
                                                              ▼
                                                     ┌──────────────────┐
                                                     │ RocketMQ Broker  │
                                                     └────────┬─────────┘
                                                              │ 4. Consumer 拉取
                                                              ▼
                                                     ┌──────────────────┐
                                                     │ subscribe-center │
                                                     │ BaseMQHandler    │
                                                     └────────┬─────────┘
                                                              │ 5. 查 Subscribe.eventDest，HTTP POST
                                                              ▼
                                                                                              ┌──────────────────┐
                                                                                              │ device-controller│
                                                                                              │ (实机 / mock)    │
                                                                                              └──────────────────┘
```

**注意：当前未实现回调链路**（device-controller → EMS → Fastop）。EMS 是 **fire-and-forget**：fastop 推送指令后不等结果。如需结果回查，需在 device-controller 侧主动 POST 一条新事件回 EMS（topic 自定），并由 fastop 注册一个 HTTP 回调端点订阅。

## 3. 报文契约（MessageEtt）

由 `EmsMessageService.buildFromExeStep` 拼装，POST 给 EMS。结构：

```json
{
  "method": "send",
  "params": {
    "ability": "fastop.send",
    "sendTime": "2026-05-03T12:00:00+08:00",
    "events": [
      {
        "eventId": "<uuid>",
        "eventType": "<topic>",
        "happenTime": "2026-05-03T12:00:00+08:00",
        "data": { /* 由 ExeStep.commandData 的 example + params 合并而成 */ },
        "status": 0,
        "timeout": 30,
        "srcType": "",
        "srcName": "",
        "srcIndex": "",
        "srcParentIndex": ""
      }
    ]
  }
}
```

字段来源：

- `eventType` ← `ExeStep.operationObject` 或 `commandData.topic`（后者优先）
- `data` ← `commandData.example`（基底）+ `commandData.params`（dot-path 覆盖，例如 `meta.channel: 1`）
- `ability` ← `fastop.integration.ems-ability-default`（默认 `fastop.send`）
- `eventId` 每次发送随机 UUID — 用作幂等键（EMS 侧用其 MD5 做 RocketMQ 二阶段提交的事务键）

## 4. Fastop 侧配置

`application.yml`：

```yaml
fastop:
  integration:
    device-controller-url: ${FASTOP_DEVICE_CONTROLLER_URL:http://localhost:5001}
    ems-url: ${FASTOP_EMS_URL:}
    ems-send-path: ${FASTOP_EMS_SEND_PATH:/addDefault}
    ems-ability-default: ${FASTOP_EMS_ABILITY_DEFAULT:fastop.send}
```

`ems-url` **默认空**（不可改默认）— 历史曾默认含真实内网 IP，已修复，回归测试见 `IntegrationPropertiesTest:21`。生产部署必须显式注入 `FASTOP_EMS_URL`。

空 URL 行为：`ExeStepServiceImpl.doV1` 直接返回 `failure("未配置 fastop.integration.ems-url 且请求未携带 url")`，不会假装成功。

## 5. 兼容路径：旧版直连模式

`ExeStepCommand.url` 字段非空时走旧版逻辑（`processAsyncStep`，直接 POST 给传入的 url），跳过 EMS。这是 **过渡兼容**，不是常规路径。新代码只走 EMS。

## 6. 部署独立性约束

- Fastop 升级**不要求** EMS 升级（除非新增了字段 / 新 ability）。
- EMS 升级**不要求** Fastop 升级（payload schema 向后兼容时）。
- 两边任一停机：Fastop 侧 step 执行接口失败但其他业务（设计 / 计划 CRUD）不受影响 — `ems-url` 空和 EMS 网络不可达走同一个失败分支。
- 测试库 / 联调环境可以使用任意一个 EMS 实例。`infra/docker-compose.yml` 已**预留 20011-20016 端口位**给 EMS + RocketMQ + Nacos + Redis（注释中），需要本地起 EMS 时把对应仓库 clone 下来单独 `docker compose up`，并把 `FASTOP_EMS_URL=http://host.docker.internal:20011` 注入 fastop。

## 7. 测试

- `EmsMessageServiceTest` — 拼装层 7 个测试用例（默认值、example/params 合并、dot-path、空字段降级）。
- `IntegrationPropertiesTest:21` — 守住 `ems-url` 默认空。

## 8. 何时考虑合并

仅当满足全部以下条件再讨论合并：

1. EMS 与 Fastop 的部署、升级节奏强绑定（共同停机窗口）。
2. EMS 没有除 fastop 之外的其他订阅者。
3. Fastop 团队接管 EMS 的运维。
4. 双方 Java 版本能统一（fastop 升至 17 或 EMS 降至 8）。

否则保持当前关系：**两个仓库、两个进程、HTTP + MQ 异步耦合**。
