# V1→V2 exe_step column consolidation audit

Source: 0723.exe_step (62498 rows)

## exe_step column population

| col | total | non_null | non_empty | non_empty % | samples |
|---|---|---|---|---|---|
| `command_data` | 62498 | 19417 | 19417 | 31.1% | [] · [] · [{\"commandParam\":[],\"devCatId\":37,\"devUnitId\":47,\"deviceCommandId\":1755}] |
| `fail_cause` | 62498 | 2288 | 0 | 0.0% |  |
| `operation` | 62498 | 57076 | 56796 | 90.9% | 按压 · 检查 · 检查 |
| `operation_object` | 62498 | 57076 | 56796 | 90.9% | 防火系统检查按钮 · 防冰系统页 · 交通告警和机载防撞系统成品、零组件 |
| `operation_content` | 62498 | 57076 | 38823 | 62.1% | 3发防冰压力值显示为绿色 · 安装应齐全、完好 · 首翻期、使用期、平均故障间隔时间三项指标中至少有一项 |
| `judge_result` | 62498 | 62498 | 62498 | 100.0% | 0 · 0 · 0 |
| `criterion_standard` | 62498 | 23466 | 13361 | 21.4% | 其他 · 其他 · 其他 |
| `criterion_type` | 62498 | 62498 | 62498 | 100.0% | 0 · 0 · 0 |
| `criterion_content` | 62498 | 56913 | 56704 | 90.7% | [{\"sortOrder\":1,\"criterionType\":0,\"criterionValueLeft\":\"\",\"criterionValueRight\":\"\",\"criterionDesc\":\"防火系统检 · [{\"dependOnDevice\":true,\"criterionType\":1,\"criterionStandard\":1,\"criterionValueLeft\":\"\",\"criterionValueRight\ · [{\"sortOrder\":1,\"criterionType\":0,\"criterionValueLeft\":\"\",\"criterionValueRight\":\"\",\"criterionDesc\":\"交通告警和 |

## Subdetail tables

| table | rows | distinct exe_step_id refs | exe_step coverage % |
|---|---|---|---|
| `exe_step_command` | 30288 | 20126 | 32.20% |
| `exe_step_jugement` | 62093 | 61697 | 98.72% |

## Decision

- `exe_step.command_data` populated 31.1% rows; `exe_step_command` covers 32.2% rows.
  **Decision:** V1 did NOT consolidate; `exe_step_command` carries data not in `exe_step.command_data`. **FAN-IN required.**

- `exe_step.judge_result` populated 100.0% rows; `exe_step_jugement` covers 98.7% rows.
  **Decision:** V1 already consolidated judgement; **DROP exe_step_jugement** confirmed.
