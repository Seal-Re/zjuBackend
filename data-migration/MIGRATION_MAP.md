# 0723 → V2.5 migration classification

Every 0723 table is classified as **migrated** (rows carried over), **inlined** (rows resolved into a column of another table), or **dropped** (no V2.5 home, with reason).

## Migrated — same-name, schema-aligned (8 tables)

| 0723 table | rows | V2.5 target | notes |
|---|---|---|---|
| `exe_function` | 656 | `exe_function` | 38 cols identical, order-aligned, UUID PK |
| `exe_step` | 62498 | `exe_step` | 47 cols identical, UUID PK; columns `command_data`/`fail_cause`/`criterion_*` already absorbed v1 details |
| `function_suite` | 876 | `function_suite` | 12 cols identical |
| `test_base` | 304 | `test_base` | 0723 missing 3 cols `model`,`profession`,`subsystem` → fill `''` |
| `test_function` | 561 | `test_function` | 42 cols identical |
| `test_function_rely` | 0 | `test_function_rely` | empty (table existed in V1, never populated) |
| `test_plan` | 184 | `test_plan` | drop trailing `mesdce_code` (V2.5 absent), UUID PK |
| `test_suite` | 139 | `test_suite` | 20 cols identical |

## Migrated — cross-table business-logic remap (3 tables)

| 0723 table | rows | V2.5 target | mapping |
|---|---|---|---|
| `operation_log` | 51517 | `operation_log` | 14 cols → 10 cols. `operation_name` split on `-` into `module/action`. `user`→`operator_name`, `user_ip`→`ip`, `timestamp`→`create_time`, `operation_object_id`→`target_id`, `operation_desc`→`detail`. |
| `device` | 77 | `device` | int PK → UUID via uuid5(deterministic). `device_serial`→`code` (fallback `V1-DEV-<id>`). `device_name`→`name` (fallback to code). `dev_cat_id`→`type` as `cat-<id>`. status remap V1→V2: `0→0,1→1,2→1,3→2`. |
| `test_steps` | 58497 | `test_function_step` (+ synth `test_function_module`/`test_function_case`) | V1 has flat fun_id→steps; V2.5 needs module→case→step layer. Synth 1 module + 1 case per fun_id (offset 200000+fun_id). step_id offset 200000. Field map: `operation→step_operation`, `operation_object→step_obj`, `operation_content→step_purpose`, `step_description→step_description`. Truncate to 100 chars per V2.5 varchar limit. |

## Dropped — V2.5 has no semantic home (rationale per group)

### Auth/RBAC (V2.5 uses Flask mock, no DB tables)
`access_token`, `privilege`, `privilege_action`, `privilege_role_target`, `privilege_target`, `privilege_target_action`, `role`, `role_privilege`, `user`, `user_role_link`

### V1 archive shadow tables (live tables already migrated)
`exe_function_archive`, `exe_step_archive`

### V1 sub-detail of exe_step
**Verified by `CONSOLIDATION_AUDIT.md`:**
- `exe_step.criterion_content` 90.7% non-empty JSON (criteria definitions consolidated). ✓
- `exe_step.judge_result` 100.0% populated (final pass/fail). ✓
- `exe_step.command_data` 31.1% non-empty JSON ≈ `exe_step_command` 32.2% step coverage — overlap suggests V1 consolidated PLAN data into `command_data`.
- `exe_step_command` (30288 rows, 20126 distinct step refs) and `exe_step_jugement` (62093 rows, 61697 step refs) hold **runtime per-batch execution audit** (success, error_msg, dev_result, value, evidence) — V2.5 simplified the model, granular per-eval audit was dropped. `exe_step.fail_cause` is V2-only (only 2288 non-NULL rows in 0723).
- `exe_step_img/video/speech` are media artifacts; V2.5 has no media tables → drop.

`exe_step_command` (30288), `exe_step_command_result`, `exe_step_jugement` (62093), `exe_step_img`, `exe_step_video`, `exe_step_speech`

### V1 device sub-tables (V2.5 has single flat `device`; sub-detail not needed for V2.5 frontend)
`device_category`, `device_command`, `device_command_back`, `device_data`, `device_ep_map`, `device_model`, `device_monitor`, `device_order`, `device_type`, `device_unit`, `device_usage_info`, `check_device`, `support_device_map`, `support_device_newmap`, `driver`

### V1 cabling-test (separate test path not in V2.5 scope)
`exe_cable_dwg`, `exe_cable_step`, `test_cable_dwg`, `test_cable_plan`

### V1 lookup tables — **3 inlined** into V2.5 varchar columns; rest dropped

**Inlined** (output: `output/supplemental_inlines.sql`):
- `test_caution` (14 rows) → resolved into `test_function.caution` for 7 fun_ids whose `test_caution_id` referenced it
- `subject_source` (43 rows) → resolved into `test_function.comment` for **557/561** fun_ids — high coverage
- `test_function_group` (35 rows) → resolved into `test_base.name` for **283/304** test_base rows whose `name` was empty

**Not inlined** (rationale: V2.5 layout has no free varchar without overwriting business field):
- `executor_group` (27 rows) — `test_plan.executor_group_id` is varchar id, but `executor_group` text would conflict with V2.5 group dispatch logic
- `test_subject` (9 rows), `entity_model` (2), `entity_structure` (4), `entity_target` (77) — all referenced by `test_plan.{subject_id,entity_struct_id,entity_id}` int FKs. V2.5 `test_plan` varchar fields (`plan_name`, `plan_number`, `comm_assign`, `execut_assign`, `verify_assign`, `management`) all hold business semantics — no free slot. Frontend doesn't expose lookup text.
- `default_assign`, `paint_model` (41), `tech_management` (1), `tech_status`, `system_config`, `software_version` (6) — config / model / version shadow tables, not in V2.5 frontend

### V1 layout / UI / misc (no V2.5 frontend equivalent)
`area`, `arealocation`, `buttonlocation`, `buttonlocation1`, `designer_config`, `pump_address`, `pump_video`, `c30ptest`, `ft010_in_v`, `ft010_out_v`, `staff`, `employee`

### V1 approval workflow history (V2.5 simplified approval, no history retention)
`approve_history` (2056), `approve_function_history` (2281), `approve_suite_history` (689)

### V1 imaging / detection feed (V2.5 references via `detect_id` varchar but has no DB-side handling)
`detect_result` (5412 rows)

### V1 misc events (no V2.5 audit equivalent — `operation_log` covers user actions, this is system events)
`event` (125844 rows), `manufacture_analysis`, `input_technical_status`, `spec_input_technical_status`, `tech_status`, `year_plan`

## Final row counts — bundle

| V2.5 table | Final | Source breakdown |
|---|---|---|
| `base_struct` | 4 | V2.5 seed |
| `exe_function` | 656 | 0723 |
| `exe_log` | 0 | (empty in V2.5) |
| `exe_step` | 62498 | 0723 |
| `function_suite` | 876 | 0723 |
| `test_base` | 304 | 0723 |
| `test_function` | 981 | 0723 (561) + 1014 shifted (420) |
| `test_function_case` | 601 | 1014 shifted (3) + V1 synth (598) |
| `test_function_module` | 599 | 1014 shifted (1) + V1 synth (598) |
| `test_function_rely` | 0 | empty |
| `test_function_step` | 58499 | 1014 shifted (2) + V1 synth (58497) |
| `test_plan` | 185 | 0723 (184) + 1014 shifted (1) |
| `test_suite` | 140 | 0723 (139) + 1014 shifted (1) |
| `operation_log` | 51517 | 0723 remapped |
| `device` | 77 | 0723 remapped |

**Total: 177,237 rows across 13 V2.5 tables.**

## ID-space discipline

| Table | 0723 PK range | 1014 shift | V1 synth range | Target PK type |
|---|---|---|---|---|
| `test_function.fun_id` | 1..619 | +100000 → 100001..101205 | — | int |
| `test_suite.suite_id` | 256..432 | +100000 → 100345 | — | int |
| `test_function_module.module_id` | — | +100000 (1014) | 200000+fun_id (synth) | int |
| `test_function_case.case_id` | — | +100000 (1014) | 200000+fun_id (synth) | int |
| `test_function_step.step_id` | — | +100000 (1014) | 200000+v1.step_id (synth) | int |
| `test_base.id` | 1..304 | — | — | int |
| `function_suite.id` | 1..1010 | — | — | int |
| `device.id` | uuid5(V1-DEV-<n>) | — | — | char(36) |
| `operation_log.id` | 68440..119956 | — | — | bigint |
| UUID PKs (test_plan/exe_function/exe_step) | random | random | — | char(36) |
