# 0723 / 1014 / V2.5 schema diff

Source files:
- `data/0723.sql` — V1 (db `autosys_xf_0723`), 191MB, 80+ tables
- `data/1014.sql` — V2 (db `autosys_1014`), 228KB, 12 tables
- `fastop/dataset/260302.sql` — V2.5 target DDL, 13 tables

## V2.5 target (13 tables)

| Table                | V2.5 cols | 0723 cols | 1014 cols | column-order aligned | row counts (0723 / 1014) |
|----------------------|-----------|-----------|-----------|----------------------|---------------------------|
| `base_struct`        | 4         | -         | -         | V2.5 new            | 0 / 0 (V2.5 seed = 4)    |
| `exe_function`       | 38        | 38        | -         | identical, aligned ✓ | 656 / 0                  |
| `exe_log`            | 5         | -         | -         | V2.5 new            | 0 / 0                    |
| `exe_step`           | 47        | 47        | -         | identical, aligned ✓ | **62498** / 0            |
| `function_suite`     | 12        | 12        | -         | identical, aligned ✓ | 876 / 0                  |
| `test_base`          | 13        | 10        | -         | 0723 missing 3 trailing cols (`model`,`profession`,`subsystem`) | 304 / 0 |
| `test_function`      | 42        | 42        | 42        | identical, aligned ✓ (all three) | 561 / 420       |
| `test_function_case` | 9         | -         | 8         | 1014 missing trailing `updated` | 0 / 3              |
| `test_function_module`| 9        | -         | 8         | 1014 missing trailing `updated` | 0 / 1              |
| `test_function_rely` | 10        | 10        | -         | identical, aligned ✓ | 0 / 0                    |
| `test_function_step` | 14        | -         | 13        | 1014 missing trailing `updated` | 0 / 2              |
| `test_plan`          | 33        | 34        | 33        | 0723 has extra `mesdce_code` | 184 / 1                  |
| `test_suite`         | 20        | 20        | 20        | identical, aligned ✓ (all three) | 139 / 1          |

## Conclusion

Column-level conversion is light:

| Source | Table | Action |
|--------|-------|--------|
| 0723 | `test_base` | Append `model=''`, `profession=''`, `subsystem=''` |
| 0723 | `test_plan` | Drop trailing `mesdce_code` column |
| 1014 | `test_function_case` | Append `updated=0` |
| 1014 | `test_function_module` | Append `updated=0` |
| 1014 | `test_function_step` | Append `updated=0` |
| V2.5 | `base_struct` | Seed 4 rows from 260302.sql |

## ID-space collisions

0723 vs 1014 int PK ranges:

| Table | 0723 min..max | 1014 min..max | Collision |
|-------|---------------|---------------|-----------|
| `test_function` | 1..619 | 1..1205 | **YES** (overlap 1..619) |
| `test_suite`    | 256..432 | 345 | **YES** |
| `test_function_module` | - | 1 | n/a (1014 only) |
| `test_function_case`   | - | 5001..5003 | n/a (1014 only) |
| `test_function_step`   | - | 1..2 | n/a (1014 only) |
| `test_base` | 1..304 | - | n/a (0723 only) |
| `function_suite` | 1..1010 | - | n/a (0723 only) |
| `test_plan` | UUID | UUID | UUID — no collision |
| `exe_function` | UUID | - | UUID |
| `exe_step` | UUID | - | UUID |

## Resolution: shift 1014 int PKs by `+100000`

Cascading FK updates inside 1014:
- `test_function.fun_id += 100000`
- `test_function_module.module_id += 100000` AND `test_function_module.fun_id += 100000`
- `test_function_case.case_id += 100000` AND `test_function_case.module_id += 100000`
- `test_function_step.step_id += 100000` AND `test_function_step.case_id += 100000`
- `test_suite.suite_id += 100000`
- (1014 `test_plan.suite_id` if non-null also += 100000 — only 1 row, manual check)

0723 IDs unchanged. UUIDs unchanged.

## V1-only tables in 0723 (dropped — not in V2.5)

`access_token`, `approve_function_history`, `approve_history`, `approve_suite_history`, `area`, `arealocation`, `buttonlocation`, `buttonlocation1`, `c30ptest`, `check_device`, `default_assign`, `designer_config`, `detect_result`, `device`, `device_category`, `device_command`, `device_command_back`, `device_data`, `device_ep_map`, `device_model`, `device_monitor`, `device_order`, `device_type`, `device_unit`, `device_usage_info`, `driver`, `employee`, `entity_model`, `entity_structure`, `entity_target`, `event`, `exe_cable_dwg`, `exe_cable_step`, `exe_function_archive`, `exe_step_archive`, `exe_step_command`, `exe_step_command_result`, `exe_step_img`, `exe_step_jugement`, `exe_step_speech`, `exe_step_video`, `executor_group`, `ft010_in_v`, `ft010_out_v`, `input_technical_status`, `manufacture_analysis`, `operation_log`, `paint_model`, `privilege`, `privilege_action`, `privilege_role_target`, `privilege_target`, `privilege_target_action`, `pump_address`, `pump_video`, `role`, `role_privilege`, `software_version`, `spec_input_technical_status`, `staff`, `subject_source`, `support_device_map`, `support_device_newmap`, `system_config`, `tech_management`, `tech_status`, `test_cable_dwg`, `test_cable_plan`, `test_caution`, `test_data_standard`, `test_function_group`, `test_step_substep`, `test_steps`, `test_subject`, `user`, `user_role_link`, `year_plan`.

## V2-only tables in 1014 (dropped — auth-side, V2.5 uses Flask mock)

`access_token`, `privilege_target_action`, `role`, `role_privilege_link`, `user`, `user_role_link`.
