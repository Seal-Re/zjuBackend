# 0723 dropped-table evidence audit

Top 3 sample rows from every dropped 0723 table, plus DDL summary, to verify the drop classification is correct.


## Group: `auth`

### `access_token` — 857+ rows, 6 cols

Schema:
```
access_token_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
token: char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
user_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
expire_at: datetime NOT NULL
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
```

Sample (first 3):
```
  row0: ('007bb2a1-dd1d-4367-82a3-97013e702416', 'fb50591e-6b3b-498e-95d5-6cd00e5eb692', '002d0337-6c2d-4992-807c-a39f39206023', '2025-11-06 14:41:46', '2025-11-06 14:11:46', '2025-11-06 14:11:46')
  row1: ('00a21f11-e016-4da7-9b45-50945ba43d7b', 'ea1e466e-8104-4945-a684-2b26f25bc8f1', 'a9ea20f9-daed-4e51-9eba-c9c024ec6ae9', '2025-11-06 14:36:38', '2025-11-06 14:06:38', '2025-11-06 14:06:38')
  row2: ('00a96a0a-3e4a-4b93-aaee-aed6d98eea0b', '5dd26b26-3b20-4bb9-9ca1-d7ed4e453543', '35f918e4-a212-48ef-92e3-7359a6a199a2', '2026-09-09 17:05:23', '2025-09-09 15:12:37', '2025-09-09 16:35:23')
```

### `privilege` — 32+ rows, 9 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
icon: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
router_link: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
title: varchar(50) DEFAULT NULL
parent_id: int DEFAULT NULL
leaf: int DEFAULT '0' COMMENT '是否是叶子节点,1表示是，0表示不是'
create_time: datetime DEFAULT CURRENT_TIMESTAMP
redirect: varchar(200) DEFAULT NULL
sorted: int DEFAULT '0' COMMENT '排序，递增'
```

Sample (first 3):
```
  row0: (1, 'icon-yibiaopan', '/dashboard', '总装集成测试', 23, 1, '2023-10-27 03:14:37', NULL, 18)
  row1: (2, 'icon-yibiaopan', '/analysis-board', '多维分析', 23, 1, '2023-10-27 03:15:40', NULL, 19)
  row2: (3, 'icon-yibiaopan', '/video-fly', '现场监督', NULL, 0, '2023-10-27 03:17:30', NULL, 2)
```

### `privilege_action` — 5+ rows, 5 cols

Schema:
```
privilege_action_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
name: varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
action_value: int DEFAULT NULL
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
```

Sample (first 3):
```
  row0: ('FASTOP15-5cb3-4183-891e-573ba53862ab', 'NONE', 1, '2019-02-16 00:00:00', '2019-02-16 00:00:00')
  row1: ('FASTOP2b-9894-4eaa-8e7f-c7d5b03c9319', 'VIEW', 2, '2019-02-16 00:00:00', '2019-02-16 00:00:00')
  row2: ('FASTOP81-8135-44fe-ab64-acf1eb5afe42', 'ADD', 16, '2019-02-16 00:00:00', '2019-02-16 00:00:00')
```

### `privilege_role_target` — 141+ rows, 6 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
role_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
privilege_id: int NOT NULL
scope: varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
can_read: int NOT NULL DEFAULT '1' COMMENT '是否能读。0不能读，1可以读'
can_write: int NOT NULL DEFAULT '1' COMMENT '是否能写，0不能写，1可以写'
```

Sample (first 3):
```
  row0: (1, '01', 1, 'PARK', 1, 1)
  row1: (2, '01', 2, 'PARK', 1, 1)
  row2: (5, '01', 3, 'PARK', 1, 1)
```

### `privilege_target` — 35+ rows, 5 cols

Schema:
```
privilege_target_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
name: varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
number: int DEFAULT NULL
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
```

Sample (first 3):
```
  row0: ('02', 'MY_ACCOUNT', 1, '2019-02-16 00:00:00', '2019-02-16 00:00:00')
  row1: ('03', 'EVENT', 17, '2019-02-16 00:00:00', '2019-02-16 00:00:00')
  row2: ('04', 'HOME_PAGE', 0, '2019-02-16 00:00:00', '2019-02-16 00:00:00')
```

### `privilege_target_action` — 212+ rows, 5 cols

Schema:
```
privilege_target_action_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
target_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
action_value: int DEFAULT NULL
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
```

Sample (first 3):
```
  row0: ('0201', '02', 1, '2020-09-01 15:40:34', '2020-09-01 15:40:34')
  row1: ('0202', '02', 2, '2020-09-01 15:40:34', '2020-09-01 15:40:34')
  row2: ('0204', '02', 4, '2020-09-01 15:40:34', '2020-09-01 15:40:34')
```

### `role` — 16+ rows, 9 cols

Schema:
```
role_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
name: varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
comment: varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
priority: int DEFAULT '10'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
default_role: tinyint(1) NOT NULL DEFAULT '0'
role_level: tinyint(1) DEFAULT NULL
parent_role_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
```

Sample (first 3):
```
  row0: ('00', '系统管理', '系统管理', 1, '2019-02-16 00:00:00', '2019-02-16 00:00:00', 1, 0, NULL)
  row1: ('01', '管理', '管理', 1, '2019-02-16 00:00:00', '2019-02-16 00:00:00', 1, 1, '00')
  row2: ('02', '工艺', '负责设计测试用例', 1, '2019-02-16 00:00:00', '2019-02-16 00:00:00', 1, 2, '00')
```

### `role_privilege` — 397+ rows, 5 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
role_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
privilege_target_action_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, '00', '0230', '2020-09-01 16:01:48', '2020-09-01 16:01:48')
  row1: (2, '00', '0330', '2020-09-01 16:01:48', '2020-09-01 16:01:48')
  row2: (3, '00', '0402', '2020-09-01 16:01:48', '2020-09-01 16:01:48')
```

### `user` — 85+ rows, 20 cols

Schema:
```
user_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
name: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
stuff_name: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
title: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
salt: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
password_hash: char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
user_status: tinyint(1) DEFAULT NULL
failed_login_attempt_count: tinyint unsigned DEFAULT '0'
locked: bit(1) DEFAULT b'0'
auto_locked: bit(1) DEFAULT b'0'
locked_at: datetime DEFAULT NULL
last_login: datetime DEFAULT NULL
security_key: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
profile: varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
deleted: tinyint(1) NOT NULL DEFAULT '0'
management: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```

Sample (first 3):
```
  row0: ('002d0337-6c2d-4992-807c-a39f39206023', '18301648', '李浩', '执行', 'a9f16b47-075d-4383-89c3-d93b83a111b9', '0d79e67bf2034b72c184cbf22d37b2a8db84955d85bf793a4485b5c06ac, 1, 0, _binary '\0', _binary '\0', NULL, '2025-12-16 14:01:19', 'MTgzMDE2NDgsMjAyNS0xMi0xNlQxNDowMToxOS4zNjg0OTEyNzUsZmFzdG9, NULL, '2025-06-19 19:00:15', '2025-11-06 10:42:52', '28404121', NULL, 0, NULL)
  row1: ('0121be39-8961-41cd-b04b-1b8e5d83aae0', '19502943', '梁原瑞', '技术室副主任', '55345cc7-6b52-4ee3-ac56-e603a8f54457', 'acabe04dfd40c74a7224ecaa23884578904d4898264dc10d7181b3dd40f, 1, 0, _binary '\0', _binary '\0', NULL, '2025-11-21 21:11:54', 'MTk1MDI5NDMsMjAyNS0xMS0yMVQyMToxMTo1NC4zODgzMTgsZmFzdG9wU2V, NULL, '2025-11-05 20:29:34', '2025-11-05 20:29:34', '28404121', NULL, 0, NULL)
  row2: ('01a4baa6-593f-43f0-b917-61666b0dfc35', 'admin', '管理员', '默认平台管理员', 'd3fb567c-8581-4b0f-84a4-6c2fb83f58e6', 'd2ebcf3bd420872ec1d9bf4173f9961499ce964102c598b40ed36280ea4, 1, 0, _binary '\0', _binary '\0', NULL, '2025-11-21 09:24:34', 'YWRtaW4sMjAyNS0xMS0yMVQwOToyNDozMy43NTIxMzAsZmFzdG9wU2VjdXJ, NULL, '2019-02-16 00:00:00', '2020-10-21 19:12:52', NULL, NULL, 0, '0')
```

### `user_role_link` — 277+ rows, 8 cols

Schema:
```
user_role_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
user_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
role_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
deleted: tinyint(1) NOT NULL DEFAULT '0'
```

Sample (first 3):
```
  row0: ('00ae40df-94b3-4fa7-81e7-4687e4db723d', 'a7ba6280-51a2-4a5f-9f03-cdd3c08e117b', '13', '2023-08-15 10:29:20', '2023-08-15 10:29:20', 'qn001', 'qn001', 0)
  row1: ('01f96c84-597e-477e-a4f7-281ce19c062e', '41640e6c-63a3-496b-b611-56d2cf46520c', '07', '2023-12-07 15:54:15', '2023-12-07 15:54:15', NULL, NULL, 0)
  row2: ('02c3ae12-49bf-49d9-9aad-90971e99878f', 'f7082093-92d9-4db7-8020-da57696b61ad', '03', '2025-11-06 10:07:14', '2025-11-06 10:07:14', NULL, NULL, 0)
```


## Group: `exe_archive`

### `exe_function_archive` — 0+ rows, 26 cols

Schema:
```
exe_function_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
plan_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
function_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
fun_version: int DEFAULT NULL
function_name: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
category_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
category_name: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
current_step_num: int DEFAULT '0' COMMENT '当前执行到的步骤'
exe_status: int DEFAULT '0' COMMENT '状态'
verify_status: int DEFAULT '0' COMMENT '检验状态'
verify_num: int NOT NULL DEFAULT '0' COMMENT '表示模块下待检验单元的数量'
military_status: int DEFAULT '0' COMMENT '军检状态  0：不合格；1：合格'
military_num: int NOT NULL DEFAULT '0' COMMENT '表示模块下待军检单元的数量'
exe_function_order: int DEFAULT NULL COMMENT '执行功能的顺序'
result_comments: varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
depends_on: varchar(100) DEFAULT NULL
caution: varchar(255) DEFAULT NULL COMMENT '功能提示信息'
is_ready: tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否准备好：0-未；1-准备好'
start_time: datetime DEFAULT NULL COMMENT '开始时间'
end_time: datetime DEFAULT NULL COMMENT '完成时间'
change_flag: int DEFAULT NULL COMMENT '标记更改状态'
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```

### `exe_step_archive` — 0+ rows, 45 cols

Schema:
```
exe_step_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
step_id: int DEFAULT NULL
exe_function_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
step_level: int DEFAULT NULL COMMENT '级别：区分子模块、用例、步骤'
step_order: int DEFAULT NULL COMMENT '步骤顺序'
level_seq: varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
step_seq: varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
step_description: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
content_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
exe_status: int DEFAULT '0' COMMENT '执行状态'
verify_status: int DEFAULT '0' COMMENT '检验状态'
military_status: int DEFAULT '0' COMMENT '军检状态  0：不合格；1：合格'
result_comments: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
military_comment: varchar(255) DEFAULT NULL COMMENT '军检说明'
step_result: varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
is_manual: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-工业相机；1-人工'
level_one_id: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
judge_result: int DEFAULT '0' COMMENT '自动判定结果的状态：0-无 1-通过 2-不通过'
can_next: tinyint(1) DEFAULT '1' COMMENT '是否可以下一步：0-不可以；1-可以,默认为1'
command_data: text
fail_cause: text COMMENT '用于存放设备指令执行结果消息'
operation: varchar(100) DEFAULT NULL COMMENT '操作'
operation_object: varchar(100) DEFAULT NULL COMMENT '操作目标'
operation_content: varchar(100) DEFAULT NULL COMMENT '操作内容'
criterion_standard_id: int DEFAULT NULL COMMENT '判据规范Id'
criterion_standard: varchar(100) DEFAULT NULL COMMENT '判据规范'
criterion_type: int DEFAULT NULL COMMENT '判据类型'
criterion_value_unit: varchar(100) DEFAULT NULL
criterion_content: varchar(200) DEFAULT NULL
criterion_desc: varchar(255) DEFAULT NULL
guide_url: varchar(50) DEFAULT NULL
key_process: tinyint(1) NOT NULL DEFAULT '0' COMMENT '关键重要标识'
depend_on_device: tinyint(1) DEFAULT '0' COMMENT '0:不需要；1：需要'
caution: varchar(255) DEFAULT NULL COMMENT '步骤提示'
commander: varchar(45) DEFAULT NULL COMMENT '指挥人员'
verfier: varchar(45) DEFAULT NULL COMMENT '检验人员'
soldier: varchar(45) DEFAULT NULL COMMENT '军检人员'
start_time: datetime DEFAULT NULL COMMENT '开始时间'
end_time: datetime DEFAULT NULL COMMENT '结束时间'
change_flag: int DEFAULT NULL COMMENT '标记更改状态'
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```


## Group: `exe_subdetail`

### `exe_step_command` — 3775+ rows, 17 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT COMMENT '主键'
exe_step_judgement_id: char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NO
exe_step_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
dev_cat_id: int NOT NULL COMMENT '设备类型id'
dev_unit_id: int NOT NULL COMMENT '设备单元id'
dev_command_id: int NOT NULL COMMENT '设备指令id'
dev_result: text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMEN
create_time: datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
command_param: varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
serial_no: int DEFAULT '0' COMMENT '批次'
description: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
success: int DEFAULT '0' COMMENT '是否成功返回，1表示正常，2 表示有问题，操作未执行时为0'
error_msg: varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
dev_data: text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMEN
evidence: varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
dev_command_code: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
depend_no: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
```

Sample (first 3):
```
  row0: (12, '83b4e39baf504e3bab9bc185ba17bbf0', '4af2db1c-f926-4891-bf26-316c5a887865', 2, 1, 254, NULL, '2023-11-20 10:06:17', '', 0, NULL, 0, NULL, NULL, NULL, NULL, NULL)
  row1: (13, '83b4e39baf504e3bab9bc185ba17bbf0', '4af2db1c-f926-4891-bf26-316c5a887865', 37, 15, 1239, NULL, '2023-11-20 10:06:32', '', 0, NULL, 0, NULL, NULL, NULL, NULL, NULL)
  row2: (14, '83b4e39baf504e3bab9bc185ba17bbf0', '4af2db1c-f926-4891-bf26-316c5a887865', 37, 15, 649, NULL, '2023-11-20 10:06:33', '', 0, NULL, 0, NULL, NULL, NULL, NULL, NULL)
```

### `exe_step_command_result` — 3+ rows, 7 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
exe_step_id: char(36) NOT NULL
create_time: datetime DEFAULT CURRENT_TIMESTAMP
type: int DEFAULT NULL
status: int DEFAULT NULL
command_mes: varchar(500) DEFAULT NULL
batch: int DEFAULT '1' COMMENT '执行批次'
```

Sample (first 3):
```
  row0: (1, '6e8375a8-8fe3-443b-8d6d-28ef43b1bb76', '2023-08-25 11:16:08', 0, 1, '', 0)
  row1: (2, '6e8375a8-8fe3-443b-8d6d-28ef43b1bb76', '2023-08-25 11:18:34', 0, 1, '', 0)
  row2: (3, '6e8375a8-8fe3-443b-8d6d-28ef43b1bb76', '2023-08-25 11:28:42', 0, 1, '', 0)
```

### `exe_step_jugement` — 3710+ rows, 14 cols

Schema:
```
id: char(32) NOT NULL COMMENT '主键'
exe_step_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
sort_order: int DEFAULT '0' COMMENT '判据排序规则'
criterion_type: int DEFAULT '0' COMMENT '操作类型。0仅操作，1选值，2录值'
criterion_standard: int DEFAULT '0' COMMENT '输入设置，0表示其他，1表示判据关联'
criterion_content: varchar(100) DEFAULT NULL COMMENT '值'
criterion_value_unit: varchar(100) DEFAULT NULL COMMENT '单位'
depend_on_device: int DEFAULT '0' COMMENT '是否使用设备，0表示不用，1表示使用'
command_json: text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMEN
create_time: datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
description: varchar(200) DEFAULT NULL COMMENT '描述'
serial_no: int DEFAULT '1' COMMENT '执行次数'
value: varchar(200) DEFAULT NULL COMMENT '最终值'
success: int DEFAULT '0' COMMENT '显示成功还是失败,2表示失败，1表示成功,0表示未知'
```

Sample (first 3):
```
  row0: ('00045c47c4004105a2e89231ec43ec93', '91755be5-96a2-4ff5-8e0f-a5705f4484a5', 0, 0, 0, NULL, '', 0, '{\"description\":\"平显上电并显示字符画面\",\"dependOnDevice\":false,\, '2025-06-21 20:33:05', '平显上电并显示字符画面', 0, NULL, 0)
  row1: ('00081b97e0c44d3f9efac3d187e7cb7a', 'ab94d2db-8eaa-47d2-8b56-4aabad326a1a', 0, 2, 0, '18.5;21.5', '度', 1, '[{\"devUnitId\":64,\"deviceCommandId\":1954,\"devCatId\":44, '2025-11-21 09:43:45', '下偏极限角度', 1, '20.56', 1)
  row2: ('0008543f484c4fbd86d7107ee57cb0c2', 'a84fed80-adba-49d3-8471-1178dd26ec09', 0, 0, 0, NULL, '', 0, NULL, '2025-09-09 10:48:09', '', 0, NULL, 0)
```

### `exe_step_img` — 2817+ rows, 8 cols

Schema:
```
img_name: varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
exe_step_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
is_manual: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-工业相机；1-人工'
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```

Sample (first 3):
```
  row0: ('/机电系统/60-供电系统/1648060962885.jpg', '44bc9611-b84e-4b44-ba89-95d7248281ea', 0, 0, '2022-03-23 18:47:07', '2022-03-23 18:47:07', 'qn', 'qn')
  row1: ('/机电系统/60-供电系统/1648062231748.jpg', '2500a872-15ff-4f13-a187-dca66a98d6b9', 0, 0, '2022-03-23 19:08:15', '2022-03-23 19:08:15', 'qn', 'qn')
  row2: ('/机电系统/60-供电系统/1648062245660.jpg', 'f5108461-72df-42da-ac59-73954592917e', 0, 0, '2022-03-23 19:08:29', '2022-03-23 19:08:29', 'qn', 'qn')
```

### `exe_step_video` — 43+ rows, 8 cols

Schema:
```
video_name: varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT N
exe_step_id: char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL
is_manual: tinyint(1) NOT NULL DEFAULT '0'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAUL
updated_by: varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAUL
```

Sample (first 3):
```
  row0: ('/航电系统/20230425测试-仪表/2023-4-25-18:43:10.mp4', '9b2522f7-a740-4837-b91d-7b26e1c2d9cf', 0, 0, '2023-04-25 18:42:40', '2023-04-25 18:42:40', '28404121', '28404121')
  row1: ('/航电系统/20230425测试-仪表/2023-4-25-18:44:49.mp4', '9b2522f7-a740-4837-b91d-7b26e1c2d9cf', 0, 0, '2023-04-25 18:44:10', '2023-04-25 18:44:10', '28404121', '28404121')
  row2: ('/航电系统/20230425测试-仪表/2023-4-25-19:54:03.mp4', '9b2522f7-a740-4837-b91d-7b26e1c2d9cf', 0, 0, '2023-04-25 19:53:43', '2023-04-25 19:53:43', '28404121', '28404121')
```

### `exe_step_speech` — 118+ rows, 8 cols

Schema:
```
speech_name: varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT N
exe_step_id: char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL
is_manual: tinyint(1) NOT NULL DEFAULT '0'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAUL
updated_by: varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAUL
```

Sample (first 3):
```
  row0: ('/机电系统/yyy语音Test-环控/2022-6-26-13:22:51.wav', '439011c4-d524-4966-b93d-e70e1fc01e78', 0, 0, '2022-06-26 13:25:38', '2022-06-26 13:25:38', 'qn', 'qn')
  row1: ('/机电系统/yyy语音Test-环控/2022-6-26-13:23:46.wav', 'e2294cce-9480-4a2c-817b-1287fcd5bfb3', 0, 0, '2022-06-26 13:26:33', '2022-06-26 13:26:33', 'qn', 'qn')
  row2: ('/机电系统/yyy语音Test-环控/2022-6-26-13:27:39.wav', 'e2294cce-9480-4a2c-817b-1287fcd5bfb3', 0, 0, '2022-06-26 13:30:26', '2022-06-26 13:30:26', 'qn', 'qn')
```


## Group: `device_subdetail`

### `device_category` — 43+ rows, 12 cols

Schema:
```
dev_cat_id: int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键'
dev_cat_name: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
dev_cat_desc: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
subject_id: int DEFAULT NULL COMMENT '所属专业id'
dev_cat_code: varchar(255) DEFAULT NULL COMMENT '维护设备编码'
dev_cat_alias: varchar(255) DEFAULT NULL COMMENT '设备别名'
is_support_device: tinyint DEFAULT '0' COMMENT '是否维护设备'
```

Sample (first 3):
```
  row0: (2, '工业相机', '工业相机', 0, NULL, '2020-10-12 11:39:08', NULL, 'admin', NULL, 'camera', '工业相机', 0)
  row1: (3, '大气数据激励设备', '大气数据激励设备', 0, NULL, '2020-12-04 16:38:18', NULL, 'admin', -1, 'dqsjjl', '大气数据激励设备', 0)
  row2: (5, '外部测量设备', '测量舵面角度', 0, '2022-12-26 00:00:03', '2020-06-05 19:46:28', 'admin', 'zh001', 1, 'wbcl', '测量舵面角度', 0)
```

### `device_command` — 2253+ rows, 16 cols

Schema:
```
device_command_id: int NOT NULL AUTO_INCREMENT
dev_cat_id: int NOT NULL
command_desc: varchar(255) NOT NULL
command_code: varchar(255) DEFAULT NULL
param_type: int NOT NULL COMMENT '参数数量类型'
param_desc: varchar(100) DEFAULT NULL
test_data_standard_id: int DEFAULT NULL
res_type: int NOT NULL DEFAULT '0' COMMENT '指令返回类型，是有数据返回，还是无数据返回'
timeout: double NOT NULL DEFAULT '0' COMMENT '指令超时时间'
paint: tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是获取图数据的标志'
paint_model_id: int DEFAULT NULL COMMENT '模板图形关联字段'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (11, 2, '右副翼状态识别', 'fcsd2', 0, '[]', 1, 2, 10, 0, NULL, 0, '2020-01-07 16:16:44', '2020-11-02 16:11:27', 'zh001', 'admin')
  row1: (12, 3, '设置高度', 'STFE', 2, '[\"单位：英尺1/米2/百米3\",\"高度值\"]', 1, 2, 300, 0, NULL, 0, '2020-05-25 17:12:17', '2021-01-08 10:39:31', 'admin', 'qn001')
  row2: (26, 6, '转台：位置模式', 'location', 3, '[\"角度\",\"速度\",\"正向0/反向1\"]', 1, 1, 60, 0, NULL, 0, '2020-06-04 10:24:53', '2020-06-06 16:40:25', 'admin', 'zh001')
```

### `device_command_back` — 1723+ rows, 16 cols

Schema:
```
device_command_id: int NOT NULL AUTO_INCREMENT
dev_cat_id: int NOT NULL
command_desc: varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_c
command_code: varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_c
param_type: int NOT NULL COMMENT '参数数量类型'
param_desc: varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_c
test_data_standard_id: int DEFAULT NULL
res_type: int NOT NULL DEFAULT '0' COMMENT '指令返回类型，是有数据返回，还是无数据返回'
timeout: double NOT NULL DEFAULT '0' COMMENT '指令超时时间'
paint: tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是获取图数据的标志'
paint_model_id: int DEFAULT NULL COMMENT '模板图形关联字段'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci
updated_by: varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci
```

Sample (first 3):
```
  row0: (11, 2, '右副翼状态识别', 'fcsd2', 0, '[]', 1, 2, 10, 0, NULL, 0, '2020-01-07 16:16:44', '2020-11-02 16:11:27', 'zh001', 'admin')
  row1: (12, 3, '设置高度', 'STFE', 2, '[\"单位：英尺1/米2/百米3\",\"高度值\"]', 1, 2, 300, 0, NULL, 0, '2020-05-25 17:12:17', '2021-01-08 10:39:31', 'admin', 'qn001')
  row2: (26, 6, '转台：位置模式', 'location', 3, '[\"角度\",\"速度\",\"正向0/反向1\"]', 1, 1, 60, 0, NULL, 0, '2020-06-04 10:24:53', '2020-06-06 16:40:25', 'admin', 'zh001')
```

### `device_data` — 0+ rows, 6 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
data: text
command_code: varchar(255) NOT NULL
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
```

### `device_ep_map` — 2+ rows, 12 cols

Schema:
```
device_ep_map_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
area_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
device_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
ip_addr: varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
ip_port: varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
is_ready: tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可用：0-断开；1-连接'
status: int DEFAULT '0' COMMENT '0-无效,1-可用,2-故障'
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```

Sample (first 3):
```
  row0: ('82d3ac86-c6c5-4e99-94c0-d6fc62efbf69', '1', 'bfca1c35-1944-4ddb-8d86-748ed45176a2', '192.152.12.11', '12', 1, 1, 0, '2019-11-08 14:19:29', '2019-11-08 14:26:59', 'admin', 'admin')
  row1: ('e980ca38-27c8-49e4-9ea6-28a2fddd939f', '1', '13ec4020-30fb-4e8e-bf59-af4bf437618f', '192.157.2.123', '13', 0, 1, 0, '2019-11-08 14:16:54', '2019-11-08 14:27:14', 'admin', 'admin')
```

### `device_model` — 45+ rows, 10 cols

Schema:
```
dev_mod_id: int unsigned NOT NULL AUTO_INCREMENT
dev_cat_id: int NOT NULL
dev_cat_name: varchar(100) DEFAULT NULL
dev_mod_name: varchar(100) NOT NULL
dev_mod_desc: varchar(255) DEFAULT NULL
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (4, 3, '大气数据激励设备', 'DQJ-001', 'DQJ-0011', 0, '2019-11-29 16:05:30', '2023-08-17 15:34:18', NULL, 'qn001')
  row1: (5, 5, '外部测量设备', 'WBCL-001', 'WBCL-001', 0, '2020-06-04 11:02:52', '2020-06-05 19:46:37', 'admin', 'admin')
  row2: (6, 6, '转台', 'PF001', 'PF001', 0, '2020-06-06 15:38:12', '2020-06-06 15:59:02', 'admin', 'gy001')
```

### `device_monitor` — 0+ rows, 7 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
device_type: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
entity_desc: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
exe_function_id: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
exe_function_name: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
execute_time: datetime DEFAULT CURRENT_TIMESTAMP
status: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
```

### `device_order` — 42+ rows, 8 cols

Schema:
```
id: int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键'
device_id: int NOT NULL COMMENT '设备id'
plan_id: varchar(45) NOT NULL COMMENT '测试计划id'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (8, 1123, '018ff891-6cce-424d-9d8a-8ff9279d5ecd', 0, NULL, NULL, NULL, NULL)
  row1: (9, 1130, '018ff891-6cce-424d-9d8a-8ff9279d5ecd', 0, NULL, NULL, NULL, NULL)
  row2: (10, 1131, '018ff891-6cce-424d-9d8a-8ff9279d5ecd', 0, NULL, NULL, NULL, NULL)
```

### `device_type` — 6+ rows, 9 cols

Schema:
```
device_type_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
device_category_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
device_type_name: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
device_type_desc: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```

Sample (first 3):
```
  row0: ('01', '001', '设备型号1', '设备型号模拟数据', 0, NULL, NULL, NULL, NULL)
  row1: ('02', '001', '设备型号2', '设备型号模拟数据', 0, NULL, NULL, NULL, NULL)
  row2: ('03', '002', '设备型号3', '设备型号模拟数据', 0, NULL, NULL, NULL, NULL)
```

### `device_unit` — 66+ rows, 9 cols

Schema:
```
dev_unit_id: int unsigned NOT NULL AUTO_INCREMENT
dev_cat_id: int NOT NULL
unit_code: varchar(45) NOT NULL
dev_unit_desc: varchar(255) DEFAULT NULL
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, 1, '舱内左', '舱内左', 0, NULL, '2020-08-12 14:30:36', NULL, 'system')
  row1: (4, 1, '舱内右', '舱内右', 0, '2019-12-02 11:10:28', '2019-12-02 11:10:28', 'admin', 'admin')
  row2: (5, 1, '舱外', '舱外', 0, '2019-12-02 11:11:08', '2019-12-02 11:11:08', 'admin', 'admin')
```

### `device_usage_info` — 78+ rows, 7 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT COMMENT '主键'
exe_function_id: char(36) NOT NULL COMMENT '执行模块id'
exe_step_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NO
device_id: int unsigned NOT NULL DEFAULT '0' COMMENT '设备id'
dev_cat: int unsigned NOT NULL COMMENT '设备类型id'
use_time: varchar(100) DEFAULT '0' COMMENT '用时时长，单位精确到秒'
create_time: datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
```

Sample (first 3):
```
  row0: (1, 'c07cd177-8b09-4e36-b121-d25dd528dadb', '', 0, 25, '18', '2025-09-10 10:09:54')
  row1: (2, 'c07cd177-8b09-4e36-b121-d25dd528dadb', '', 0, 37, '18', '2025-09-10 10:09:54')
  row2: (3, '2372b4c5-b8b9-4eb3-a18e-b3600918483c', '', 0, 37, '0', '2025-09-10 10:09:15')
```

### `check_device` — 8+ rows, 7 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
name: varchar(100) DEFAULT NULL COMMENT '检测设备名称'
ip: varchar(100) DEFAULT NULL
port: varchar(100) DEFAULT NULL
status: int unsigned DEFAULT '0' COMMENT '0表示未知，1表示通，2表示不通'
check_time: datetime DEFAULT CURRENT_TIMESTAMP
jumpto: varchar(100) DEFAULT NULL
```

Sample (first 3):
```
  row0: (2, '视频抽引', '127.0.0.1', '8080', 1, '2025-03-05 11:34:44', NULL)
  row1: (3, '摄像头', '127.0.0.1', '8080', 1, '2025-03-05 11:34:44', NULL)
  row2: (4, '主飞控地面维护设备', '127.0.0.1', '8080', 1, '2025-03-05 11:34:44', NULL)
```

### `support_device_map` — 745+ rows, 5 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
dev_cat_id: int NOT NULL
field_name: varchar(255) DEFAULT NULL
field_desc: varchar(255) DEFAULT NULL
num_to_str: varchar(255) DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, 10, 'Channel', '通道号', NULL)
  row1: (2, 10, 'LD_PIT', '左驾驶柱位移', NULL)
  row2: (3, 10, 'VLD_PIT', '左驾驶柱位移表决值', NULL)
```

### `support_device_newmap` — 655+ rows, 5 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
dev_cat_id: int NOT NULL
field_name: varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAU
field_desc: varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAU
num_to_str: varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAU
```

Sample (first 3):
```
  row0: (1, 42, '1', '系统时间高', NULL)
  row1: (2, 42, '2', '系统时间低', NULL)
  row2: (3, 42, '3', '左驾驶柱位移（毫米）', NULL)
```

### `driver` — 43+ rows, 6 cols

Schema:
```
id: char(36) NOT NULL COMMENT '驱动Id'
status: int DEFAULT '0' COMMENT '驱动状态'
error_msg: varchar(100) DEFAULT NULL COMMENT '错误信息'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
```

Sample (first 3):
```
  row0: ('AIR_MACHINE_002', 1, '', 0, '2020-08-20 14:33:55', '2025-09-16 17:33:47')
  row1: ('AIR_MACHINE_003', 0, '驱动故障', 0, '2025-12-18 10:07:13', '2025-12-18 14:21:24')
  row2: ('AIR_MACHINE_UDP_001', 0, '驱动故障', 0, '2025-07-01 09:11:33', '2025-07-01 09:38:14')
```


## Group: `cabling`

### `exe_cable_dwg` — 158+ rows, 15 cols

Schema:
```
id: int unsigned NOT NULL AUTO_INCREMENT
cable_plan_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
test_dwg_id: int NOT NULL COMMENT '关联图号模板'
dwg_name: varchar(60) NOT NULL
description: varchar(100) NOT NULL
dwg_order: int DEFAULT NULL
fun_group_id: int NOT NULL COMMENT '关联子系统'
num: int NOT NULL COMMENT '编号'
status: int NOT NULL DEFAULT '0' COMMENT '状态'
using_by: varchar(400) DEFAULT NULL COMMENT '占用'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL COMMENT '\n'
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (31, '0f7c76f7-b933-49a9-9fad-07a8bb116a9c', 16, '424', '供电', 16, 13, 600, 6, NULL, 0, '2021-12-04 01:43:33', '2021-12-04 01:43:33', NULL, NULL)
  row1: (32, '0f7c76f7-b933-49a9-9fad-07a8bb116a9c', 17, '433', '照明', 17, 14, 610, 6, NULL, 0, '2021-12-04 01:43:34', '2021-12-04 01:43:34', NULL, NULL)
  row2: (33, '0f7c76f7-b933-49a9-9fad-07a8bb116a9c', 1, '452', '舱门', 1, 7, 100, 6, NULL, 0, '2021-12-04 01:43:34', '2021-12-04 01:43:34', NULL, NULL)
```

### `exe_cable_step` — 6224+ rows, 24 cols

Schema:
```
id: int unsigned NOT NULL AUTO_INCREMENT
exe_dwg_id: int NOT NULL COMMENT '关联执行图号'
test_step_id: int DEFAULT NULL COMMENT '关联模板步骤'
description: varchar(100) NOT NULL
step_order: int DEFAULT NULL
status: int NOT NULL DEFAULT '0' COMMENT '状态'
from_device: varchar(45) DEFAULT NULL COMMENT '起始设备号'
from_connector: varchar(45) DEFAULT NULL COMMENT '起始连接器'
from_pinhole: varchar(45) DEFAULT NULL COMMENT '起始针孔号'
to_device: varchar(45) DEFAULT NULL COMMENT '目标设备号'
to_connector: varchar(45) DEFAULT NULL COMMENT '目标连接器'
to_pinhole: varchar(45) DEFAULT NULL COMMENT '目标针孔号'
wiring_dwg: varchar(60) DEFAULT NULL COMMENT '接线图'
test_type: int NOT NULL DEFAULT '0' COMMENT '测试类型'
is_automatic: tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否自动执行'
result_data: varchar(400) DEFAULT NULL COMMENT '结果数据'
judge_result: varchar(300) DEFAULT NULL COMMENT '判断结果'
test_times: varchar(100) DEFAULT NULL COMMENT '测试次数'
flag_manual: varchar(100) DEFAULT NULL COMMENT '标记手动'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (31, 31, NULL, '..', NULL, 6, 'LRU02', 'U-631301X1', 'A-A1', 'JSZSU1E', 'U-424105X1', 'S', NULL, 1, 1, '0.000', '1', '1', '1', 0, '2021-12-04 01:43:33', '2021-12-04 01:43:33', NULL, NULL)
  row1: (32, 31, NULL, '..', NULL, 6, 'LRU02', 'U-631301X1', 'A-A2', 'JSZSU1A', 'U-424105X2', 'K', NULL, 1, 1, '0.000', '1', '1', '1', 0, '2021-12-04 01:43:33', '2021-12-04 01:43:33', NULL, NULL)
  row2: (33, 31, NULL, '..', NULL, 6, 'LRU02', 'U-631301X1', 'A-A3', 'JSZSU1C', 'U-424105X3', 'L', NULL, 1, 1, '0.000', '1', '1', '1', 0, '2021-12-04 01:43:33', '2021-12-04 01:43:33', NULL, NULL)
```

### `test_cable_dwg` — 34+ rows, 13 cols

Schema:
```
id: int unsigned NOT NULL AUTO_INCREMENT
dwg_name: varchar(60) NOT NULL
description: varchar(100) NOT NULL
dwg_order: int DEFAULT NULL
fun_group_id: int NOT NULL COMMENT '关联子系统'
num: int NOT NULL COMMENT '编号'
status: int NOT NULL DEFAULT '0' COMMENT '状态'
using_by: varchar(400) DEFAULT NULL COMMENT '占用'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL COMMENT '\n'
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, '452', '舱门', 1, 7, 100, 6, NULL, 0, NULL, NULL, NULL, NULL)
  row1: (2, '66', '任务', 2, 8, 200, 6, NULL, 0, NULL, NULL, NULL, NULL)
  row2: (3, '426', '动力', 3, 9, 300, 6, NULL, 0, NULL, NULL, NULL, NULL)
```

### `test_cable_plan` — 6+ rows, 16 cols

Schema:
```
plan_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
entity_struct_id: int DEFAULT NULL
entity_id: int DEFAULT NULL COMMENT '关联测试目标'
plan_start_time: datetime DEFAULT NULL COMMENT '计划开始时间'
plan_end_time: datetime DEFAULT NULL COMMENT '计划结束时间'
actual_start_time: datetime DEFAULT NULL COMMENT '实际开始时间'
actual_end_time: datetime DEFAULT NULL COMMENT '实际结束时间'
status: int DEFAULT '0' COMMENT '计划状态'
plan_number: varchar(45) DEFAULT NULL COMMENT '计划号'
plan_name: varchar(45) DEFAULT NULL COMMENT '计划名'
area_id: int DEFAULT NULL COMMENT '关联仓位'
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```

Sample (first 3):
```
  row0: ('0f7c76f7-b933-49a9-9fad-07a8bb116a9c', 18, 33, NULL, NULL, NULL, NULL, 0, NULL, 'Y20-A-54', NULL, 0, '2021-12-03 15:04:07', '2021-12-03 15:04:07', NULL, NULL)
  row1: ('3e7d130d-d64e-4752-a267-eb0a11a94dd3', 18, 35, NULL, NULL, NULL, NULL, 0, NULL, 'Y20-A-0057', NULL, 0, '2021-12-04 11:04:47', '2021-12-04 11:04:47', NULL, NULL)
  row2: ('3f7c3b05-0f32-4757-bf82-b028e291da94', 18, 31, NULL, NULL, NULL, NULL, 0, NULL, 'Y20-A-50', NULL, 0, '2021-12-04 10:08:52', '2021-12-04 10:08:52', NULL, NULL)
```


## Group: `lookup_dropped`

### `test_caution` — 14+ rows, 9 cols

Schema:
```
test_caution_id: int NOT NULL AUTO_INCREMENT
caution_content: varchar(255) DEFAULT NULL
subject_id: int NOT NULL
subject_name: varchar(60) NOT NULL
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, '通电时无关人员不要上机，严禁乱动机上配电设备', 1, '飞控', 0, '2019-10-09 12:59:00', '2020-08-21 11:42:23', 'admin', 'admin')
  row1: (2, '使用地面交直流电源车对飞机供电时，其功率和品质因素调整符合机上电源使用要求（直流电源：28.5V、交流电源：115V , 1, '飞控', 0, '2019-10-09 12:59:00', '2019-10-09 12:59:00', 'admin', 'admin')
  row2: (3, '严禁带电插拔插头和带电拆装导线，防止系统短路', 1, '飞控', 0, '2019-10-09 12:59:00', '2019-10-09 12:59:00', 'admin', 'admin')
```

### `subject_source` — 43+ rows, 11 cols

Schema:
```
subject_source_id: int NOT NULL AUTO_INCREMENT
source_num: varchar(100) DEFAULT NULL COMMENT '技术条件编号'
source_version: varchar(50) NOT NULL
source_desc: varchar(255) DEFAULT NULL
subject_id: int NOT NULL
subject_name: varchar(60) NOT NULL
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (8, 'XXX-JT327-018', 'A', 'XXX-JT327-018', 1, '飞控', 0, '2020-11-16 09:21:45', '2021-04-26 17:41:22', 'admin', 'admin')
  row1: (9, 'XXX-JT327-038', 'A', 'XXX-JT327-038', 1, '飞控', 0, '2020-11-16 09:21:59', '2021-04-26 17:41:39', 'admin', 'admin')
  row2: (10, 'XXX-JT327-032', 'A', 'XXX-JT327-032', 1, '飞控', 0, '2020-11-16 09:22:19', '2021-04-26 17:41:53', 'admin', 'admin')
```

### `test_function_group` — 35+ rows, 12 cols

Schema:
```
fungrp_id: int unsigned NOT NULL AUTO_INCREMENT
fungrp_name: varchar(60) NOT NULL
subject_id: int DEFAULT NULL
subject_name: varchar(60) DEFAULT NULL
fungrp_desc: varchar(255) DEFAULT NULL
num: int NOT NULL COMMENT '编号'
system_type: int NOT NULL DEFAULT '1' COMMENT '系统类型：1表示系统测,2表示线缆测'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, '主飞控', 1, '飞控', '主飞控', 32700, 1, 0, '2021-04-26 17:38:30', '2021-04-26 17:38:30', 'admin', 'admin')
  row1: (2, '自动飞控', 1, '飞控', '自动飞控', 32200, 1, 0, '2021-04-26 17:39:48', '2021-04-26 17:39:48', 'admin', 'admin')
  row2: (3, '高升力', 1, '飞控', '高升力', 32100, 1, 0, '2021-04-26 17:40:25', '2021-04-26 17:40:25', 'admin', 'admin')
```

### `executor_group` — 27+ rows, 9 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
leader_id: varchar(45) NOT NULL
exe_group_id: varchar(45) NOT NULL
executor_id: varchar(45) NOT NULL
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, 'e634bc00-9c47-4cd5-9c6c-a75994068e9f', '1', '523e1f60-e978-49a9-bd9d-05e505ec925e', 0, NULL, '2021-04-27 09:24:34', NULL, 'zh001')
  row1: (2, 'e634bc00-9c47-4cd5-9c6c-a75994068e9f', '1', '79eb4d65-bc42-4837-bc38-9fe88eac7e5a', 0, '2021-04-27 09:24:34', '2021-04-27 09:24:34', 'zh001', 'zh001')
  row2: (3, '1bdbee28-b490-41f2-9e2d-985e4536f040', '966ae626-05e2-4dcf-b8c6-df3af2bd02d5', '1bdbee28-b490-41f2-9e2d-985e4536f040', 0, '2021-10-21 12:41:31', '2021-10-21 12:41:31', 'qn', 'qn')
```

### `test_subject` — 9+ rows, 9 cols

Schema:
```
subject_id: int unsigned NOT NULL AUTO_INCREMENT
subject_name: varchar(60) NOT NULL
subject_desc: varchar(255) DEFAULT NULL
num: int NOT NULL COMMENT '编号'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, '飞控', '飞控项目', 32, 0, '2022-12-26 00:00:00', '2021-04-26 17:37:45', 'admin', 'admin')
  row1: (2, '电气', '机电系统', 0, 0, '2022-12-26 00:00:20', '2021-05-31 17:31:27', 'admin', '28400647')
  row2: (3, '航电', '航电系统', 0, 0, '2022-12-26 00:00:10', '2021-01-21 14:14:53', 'admin', '28400647')
```

### `default_assign` — 16+ rows, 9 cols

Schema:
```
id: int unsigned NOT NULL AUTO_INCREMENT
user_id: varchar(45) DEFAULT NULL COMMENT '分配方用户'
item: varchar(45) DEFAULT NULL COMMENT '分配项目'
target_user: varchar(45) DEFAULT NULL COMMENT '分配目标方用户'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, '719cc782-b0c4-45b3-8fc1-964414f6fcbf', '审签校对员', '09890526-232b-45f4-909e-211d5d077b38', 0, '2021-01-11 10:01:37', '2021-03-19 17:57:21', 'gy001', 'gy001')
  row1: (2, '09890526-232b-45f4-909e-211d5d077b38', '审签质审员', 'cd86f6e2-921a-4372-b327-2fa31c79686a', 0, '2021-01-11 10:02:16', '2021-03-19 17:57:39', 'jd001', 'jd001')
  row2: (3, 'cd86f6e2-921a-4372-b327-2fa31c79686a', '审签审查员', 'a3242a8f-d323-4954-968c-021200833951', 0, '2021-01-11 10:02:32', '2021-03-19 17:58:07', 'zs001', 'zs001')
```

### `entity_model` — 2+ rows, 8 cols

Schema:
```
entity_model_id: int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键'
entity_model_name: varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
entity_model_desc: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```

Sample (first 3):
```
  row0: (14, 'xxx-2', 'xxx-2', 0, '2020-11-13 15:18:23', '2020-11-13 15:18:23', 'admin', 'admin')
  row1: (17, 'EA', 'EA', 0, NULL, NULL, NULL, NULL)
```

### `entity_structure` — 4+ rows, 10 cols

Schema:
```
entity_struct_id: int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键'
entity_model_id: int NOT NULL COMMENT '关联大类'
entity_model_name: varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
entity_struct_name: varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
entity_struct_desc: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```

Sample (first 3):
```
  row0: (18, 14, 'xxx-2', 'A', 'xxx-2-A', 0, '2020-11-13 15:18:44', '2020-11-13 15:18:44', 'admin', 'admin')
  row1: (19, 14, 'xxx-2', 'B', 'xxx-2-B', 0, '2020-11-13 15:18:58', '2020-11-13 15:18:58', 'admin', 'admin')
  row2: (20, 14, 'xxx-2', 'R', 'YR', 0, '2020-11-13 15:19:08', '2020-11-13 15:19:08', 'admin', 'admin')
```

### `entity_target` — 77+ rows, 10 cols

Schema:
```
entity_id: int unsigned NOT NULL AUTO_INCREMENT
entity_struct_id: int NOT NULL
entity_name: int NOT NULL
entity_desc: varchar(255) DEFAULT NULL
suites: text COMMENT '分配的测试集'
deleted: tinyint(1) unsigned zerofill NOT NULL DEFAULT '0'
created_at: datetime DEFAULT CURRENT_TIMESTAMP
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (26, 19, 2002, 'Y20-B-2002', '1:1;', 0, '2020-11-16 09:15:22', '2020-11-21 13:39:03', 'admin', 'gy001')
  row1: (27, 18, 3, 'Y20-A-03', '1:21;', 1, '2020-11-19 09:22:55', '2020-11-21 13:27:11', 'admin', 'admin')
  row2: (28, 18, 44, 'Y20-A-44', '1:3;', 0, '2021-01-11 09:50:15', '2021-01-20 10:47:54', 'admin', '28400647')
```

### `paint_model` — 41+ rows, 11 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
title: varchar(45) NOT NULL COMMENT '模型图形标题'
model_data: varchar(2000) DEFAULT NULL COMMENT '模型图形数据'
type: varchar(45) NOT NULL COMMENT '模型图形类型'
xaxis_name: varchar(45) DEFAULT NULL COMMENT 'x轴名称'
yaxis_name: varchar(45) DEFAULT NULL COMMENT 'y轴名称'
xaxis_unit: varchar(45) DEFAULT NULL COMMENT 'x轴单位'
yaxis_unit: varchar(45) DEFAULT NULL COMMENT 'y轴单位'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (2, '主驾驶盘角位移-操纵力', '[[-2.5,0],[-2.5,20],[0,20],[0,45],[45,120],[66,190],[66,100, 'line', '主驾驶盘角位移', '操纵力', '度', 'N', 0, NULL, NULL)
  row1: (3, '副驾驶盘角位移-操纵力', '[[-2.5,0],[-2.5,20],[0,20],[0,45],[45,120],[66,190],[66,100, 'line', '副驾驶盘角位移', '操纵力', '度', 'N', 0, NULL, NULL)
  row2: (4, '主驾驶脚蹬位移-操纵力', '[[-3,0],[-3,20],[0,20],[0,110],[88,500],[88,280],[3,0],[3,-, 'line', '主驾驶脚蹬位移', '操纵力', 'mm', 'N', 0, NULL, NULL)
```

### `tech_management` — 1+ rows, 9 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
entity_struct_id: int unsigned NOT NULL COMMENT '构型'
entity_id: int unsigned NOT NULL COMMENT '架次'
area_id: int unsigned DEFAULT NULL COMMENT '站位'
status: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
process: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
locked: int DEFAULT '0' COMMENT '状态锁定，0不锁定。1锁定'
create_time: datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
entity_model_id: int unsigned DEFAULT NULL
```

Sample (first 3):
```
  row0: (8, 18, 28, 16, '在途', '90', 0, '2024-03-22 23:44:40', 14)
```

### `tech_status` — 0+ rows, 6 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT COMMENT '主键'
entity_id: int DEFAULT NULL COMMENT '构型'
plane_number: int DEFAULT NULL COMMENT '架次'
area_id: int DEFAULT NULL COMMENT '站位'
process: varchar(100) DEFAULT NULL COMMENT '生产进度'
create_time: datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
```

### `system_config` — 0+ rows, 11 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT COMMENT '主键'
parent_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
config_key: varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
config_value: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
value_type: varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
sc_desc: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```

### `software_version` — 6+ rows, 6 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
notice: varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
software: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
update_info: varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_
updater: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
create_time: datetime DEFAULT CURRENT_TIMESTAMP
```

Sample (first 3):
```
  row0: (2, '初始版本', '3.0.0', '部署数字化军检系统', '', '2025-02-26 09:14:43')
  row1: (3, '进行功能回归测试', '3.0.1', '修改了一些权限问题', '', '2025-02-26 09:15:49')
  row2: (4, '进行功能回归测试', '3.0.2', '上机验证实验，修复测试bug', '', '2025-03-11 11:25:17')
```


## Group: `ui_misc`

### `area` — 3+ rows, 12 cols

Schema:
```
area_id: int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键'
factory_no: char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
area_no: char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
factory_name: varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
area_name: varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
ip_range: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
area_desc: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
deleted: tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
updated_at: datetime DEFAULT NULL COMMENT '更新时间'
created_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
updated_by: varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
```

Sample (first 3):
```
  row0: (16, NULL, NULL, NULL, '3站位', NULL, '3站位', 0, '2020-11-13 15:19:32', '2020-11-13 15:19:32', 'admin', 'admin')
  row1: (17, NULL, NULL, NULL, '4站位', NULL, '4站位', 0, '2020-11-13 15:19:41', '2020-11-13 15:19:41', 'admin', 'admin')
  row2: (24, NULL, NULL, NULL, '2站位', NULL, '2站位', 0, '2023-08-22 10:22:47', '2023-08-22 10:22:47', '28404121', '28404121')
```

### `arealocation` — 3+ rows, 8 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
areaNumber: int DEFAULT NULL COMMENT '区域编号'
XPos: double DEFAULT '0' COMMENT 'X坐标'
YPos: double DEFAULT '0' COMMENT 'Y坐标'
ZPos: double DEFAULT '0' COMMENT 'Z坐标'
APos: double DEFAULT NULL
BPos: double DEFAULT NULL
CPos: double DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, 0, -62.6, -552, 83.8, -3.1415926, 0, -3.1415926)
  row1: (2, 2, 3.3, 88.5, 150.4, NULL, NULL, NULL)
  row2: (3, 1, 80, -107, 1137, 0, 0.36652, 0)
```

### `buttonlocation` — 35+ rows, 16 cols

Schema:
```
areaNumber: int NOT NULL DEFAULT '0' COMMENT '区域编号'
buttonNumber: int NOT NULL COMMENT '按钮在区域内的编号'
name: varchar(20) NOT NULL COMMENT '按钮名'
type: enum('Point','Toggle','RotaryKnob','Rocker') DEFAULT NULL CO
gears: int DEFAULT NULL COMMENT '按钮总挡位数'
nowGear: int DEFAULT NULL COMMENT '按钮现在在的挡位'
gearAngle: double DEFAULT NULL COMMENT '旋钮每档对应角度'
XPos: double DEFAULT '0' COMMENT '针对点动按钮和旋钮'
YPos: double DEFAULT '0' COMMENT '针对点动按钮和旋钮'
ZPos: double DEFAULT '0' COMMENT '针对点动按钮和旋钮'
Pos1X: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
Pos1Y: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
Pos1Z: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
Pos2X: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
Pos2Y: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
Pos2Z: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
```

Sample (first 3):
```
  row0: (0, 1, '无挡位旋钮', 'RotaryKnob', 3, 2, 36, 319.18, 298.16, 20, 0, 0, 0, 0, 0, 0)
  row1: (1, 1, '无档位顶控板', 'RotaryKnob', NULL, 3, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0)
  row2: (0, 2, '3档长条旋钮', 'RotaryKnob', 3, 2, 45, 377.5, 57.5, 20, 0, 0, 0, 0, 0, 0)
```

### `buttonlocation1` — 30+ rows, 16 cols

Schema:
```
areaNumber: int NOT NULL DEFAULT '0' COMMENT '区域编号'
buttonNumber: int NOT NULL COMMENT '按钮在区域内的编号'
name: varchar(20) NOT NULL COMMENT '按钮名'
type: enum('Point','Toggle','RotaryKnob','Rocker') DEFAULT NULL CO
gears: int DEFAULT NULL COMMENT '按钮总挡位数'
nowGear: int DEFAULT NULL COMMENT '按钮现在在的挡位'
gearAngle: double DEFAULT NULL COMMENT '旋钮每档对应角度'
XPos: double DEFAULT '0' COMMENT '针对点动按钮和旋钮'
YPos: double DEFAULT '0' COMMENT '针对点动按钮和旋钮'
ZPos: double DEFAULT '0' COMMENT '针对点动按钮和旋钮'
Pos1X: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
Pos1Y: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
Pos1Z: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
Pos2X: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
Pos2Y: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
Pos2Z: double DEFAULT '0' COMMENT '针对船型开关和拨钮'
```

Sample (first 3):
```
  row0: (0, 1, '无挡位旋钮', 'RotaryKnob', 3, 2, 36, 319.18, 298.16, 20, 0, 0, 0, 0, 0, 0)
  row1: (1, 1, '无档位顶控板', 'RotaryKnob', NULL, 1, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0)
  row2: (0, 2, '3档长条旋钮', 'RotaryKnob', 3, 2, 45, 377.5, 57.5, 20, 0, 0, 0, 0, 0, 0)
```

### `designer_config` — 35+ rows, 22 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
user_id: varchar(45) NOT NULL
fun_proofer: varchar(45) DEFAULT NULL
fun_verifier: varchar(45) DEFAULT NULL
fun_qualityer: varchar(45) DEFAULT NULL
fun_checker: varchar(45) DEFAULT NULL
fun_approver: varchar(45) DEFAULT NULL
suite_proofer: varchar(45) DEFAULT NULL
suite_approver: varchar(45) DEFAULT NULL
subject_id: int DEFAULT NULL
fun_proofer_name: varchar(255) DEFAULT NULL
fun_verifier_name: varchar(255) DEFAULT NULL
fun_qualityer_name: varchar(255) DEFAULT NULL
fun_checker_name: varchar(255) DEFAULT NULL
fun_approver_name: varchar(255) DEFAULT NULL
suite_proofer_name: varchar(255) DEFAULT NULL
suite_approver_name: varchar(255) DEFAULT NULL
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
updated_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, '719cc782-b0c4-45b3-8fc1-964414f6fcbf', 'f7082093-92d9-4db7-8020-da57696b61ad', '31ad34f7-bb2b-4216-887f-a04bc385f931', '4c078e70-c1ba-4cad-bcd7-c958bbddd536', '06b18ded-d038-4dd5-a7e7-73833d9be0f1', '0121be39-8961-41cd-b04b-1b8e5d83aae0', 'f7082093-92d9-4db7-8020-da57696b61ad', '0121be39-8961-41cd-b04b-1b8e5d83aae0', 1, '山迪聪', '兰泽宏', '张浩', '张朵', '梁原瑞', '山迪聪', '梁原瑞', 0, '2021-04-26 17:58:52', '2021-06-09 23:54:05', 'gy001', 'gy001')
  row1: (2, 'ec4331d5-d6b3-4386-9106-3f2fdb014c55', 'ec4331d5-d6b3-4386-9106-3f2fdb014c55', 'ec4331d5-d6b3-4386-9106-3f2fdb014c55', 'ec4331d5-d6b3-4386-9106-3f2fdb014c55', 'a3242a8f-d323-4954-968c-021200833951', 'ec4331d5-d6b3-4386-9106-3f2fdb014c55', NULL, NULL, 1, '王萍', '王萍', '王萍', '审查001', '王萍', NULL, NULL, 0, '2021-04-28 14:53:50', '2021-06-09 18:16:44', '28400647', '28400647')
  row2: (3, '1bdbee28-b490-41f2-9e2d-985e4536f040', '1bdbee28-b490-41f2-9e2d-985e4536f040', '1bdbee28-b490-41f2-9e2d-985e4536f040', '1bdbee28-b490-41f2-9e2d-985e4536f040', '1bdbee28-b490-41f2-9e2d-985e4536f040', '1bdbee28-b490-41f2-9e2d-985e4536f040', '1bdbee28-b490-41f2-9e2d-985e4536f040', '1bdbee28-b490-41f2-9e2d-985e4536f040', 1, '张磊磊.', '张磊磊.', '张磊磊.', '张磊磊.', '张磊磊.', '张磊磊.', '张磊磊.', 0, '2021-10-21 11:41:08', '2023-10-09 18:02:11', 'qn', '28404121')
```

### `pump_address` — 20+ rows, 6 cols

Schema:
```
id: int NOT NULL
name: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
address: varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
area_id: int unsigned DEFAULT NULL COMMENT '站位'
create_time: datetime DEFAULT CURRENT_TIMESTAMP
rtsp_source: varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
```

Sample (first 3):
```
  row0: (1, '左机头', 'http://192.168.0.108:8899/live?port=1935&app=live&stream=18, 16, '2024-06-03 09:43:25', 'rtsp://admin:fastop123@192.168.0.120/Streaming/Channels/101)
  row1: (2, '左机翼后缘', 'http://192.168.0.108:8899/live?port=1935&app=live&stream=10, 16, '2024-06-03 09:42:46', 'rtsp://admin:fastop123@192.168.0.112/Streaming/Channels/401)
  row2: (3, '起落架', '', 16, '2024-06-03 09:49:29', '')
```

### `pump_video` — 79+ rows, 8 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
name: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
path: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
create_time: datetime DEFAULT CURRENT_TIMESTAMP
duration: int DEFAULT NULL COMMENT '时长'
start_time: datetime DEFAULT NULL
end_time: datetime DEFAULT NULL
type: int DEFAULT '0' COMMENT '0表示视频抽引，1表示摄像头'
```

Sample (first 3):
```
  row0: (18, 'null_1750327892006.mp4', '/mydata/dockerImg/jar/dumpVideo/null_1750327892006.mp4', '2025-06-19 18:11:32', 8367, '2025-06-19 18:11:32', '2025-06-19 20:31:00', 0)
  row1: (19, 'null_1750328039365.mp4', '/mydata/dockerImg/jar/dumpVideo/null_1750328039365.mp4', '2025-06-19 18:13:59', 17160, '2025-06-19 18:13:59', '2025-06-19 23:00:00', 0)
  row2: (20, '起落架_1750328387958.mp4', '/mydata/dockerImg/jar/dumpVideo/起落架_1750328387958.mp4', '2025-06-19 18:19:47', 16812, '2025-06-19 18:19:48', '2025-06-19 23:00:00', 0)
```

### `c30ptest` — 0+ rows, 1 cols

Schema:
```
a: char(1) DEFAULT NULL
```

### `ft010_in_v` — 0+ rows, 5 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
db_source: varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
db_str: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
use_sign: int DEFAULT NULL
creation_date: datetime DEFAULT CURRENT_TIMESTAMP
```

### `ft010_out_v` — 0+ rows, 5 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
db_source: varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
db_str: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
use_sign: int DEFAULT NULL
creation_date: datetime DEFAULT CURRENT_TIMESTAMP
```

### `staff` — 1+ rows, 8 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
name: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
position: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
status: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
remark: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_c
sex: varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
create_time: datetime DEFAULT CURRENT_TIMESTAMP
role_id: char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NO
```

Sample (first 3):
```
  row0: (7, '王飞', '工艺', '空闲', NULL, NULL, '2024-03-22 18:27:36', '02')
```

### `employee` — 0+ rows, 7 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT COMMENT '主键'
name: varchar(100) DEFAULT NULL COMMENT '姓名'
position: varchar(100) DEFAULT NULL COMMENT '职务'
role: varchar(100) DEFAULT NULL COMMENT '角色'
status: int DEFAULT '0' COMMENT '当前状态，0表示空闲，1表示忙碌，2表示离岗'
remark: varchar(100) DEFAULT NULL COMMENT '备注'
create_time: datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
```


## Group: `approve_history`

### `approve_history` — 2056+ rows, 16 cols

Schema:
```
id: int unsigned NOT NULL AUTO_INCREMENT
entity_model_id: int NOT NULL
entity_model_name: varchar(50) DEFAULT NULL
entity_struct_id: int NOT NULL
entity_struct_name: varchar(50) DEFAULT NULL
subject_id: int NOT NULL
subject_name: varchar(60) DEFAULT NULL
fungrp_id: int NOT NULL
fungrp_name: varchar(60) DEFAULT NULL
base_id: int NOT NULL
base_type: int DEFAULT NULL
appr_type: int NOT NULL COMMENT '审签类型 1:模块审签；2:清单审签'
appr_status: int DEFAULT NULL COMMENT '审签状态 2->通过；3->不通过'
deleted: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL
created_by: varchar(45) DEFAULT NULL
```

Sample (first 3):
```
  row0: (1, 14, 'Y20', 18, 'A', 1, '飞控', 1, '主飞控', 19, 1, 1, 2, 0, '2021-05-31 17:52:24', 'jd001')
  row1: (2, 14, 'Y20', 18, 'A', 1, '飞控', 1, '主飞控', 19, 1, 1, 2, 0, '2021-05-31 17:53:20', 'zs001')
  row2: (3, 14, 'Y20', 18, 'A', 1, '飞控', 1, '主飞控', 19, 1, 1, 2, 0, '2021-05-31 17:53:37', 'sc001')
```

### `approve_function_history` — 2281+ rows, 17 cols

Schema:
```
id: int unsigned NOT NULL AUTO_INCREMENT COMMENT '审签测试功能历史Id'
fun_id: int DEFAULT NULL COMMENT '测试功能Id'
fun_name: varchar(60) DEFAULT NULL COMMENT '功能名称'
appr_his_id: int DEFAULT NULL COMMENT '审签测试集历史Id'
version: int DEFAULT NULL COMMENT '功能版本'
flow_version: int DEFAULT NULL COMMENT '功能分类Id'
num: int DEFAULT NULL COMMENT '功能编号'
plane_effect_min: int DEFAULT NULL
plane_effect_max: int DEFAULT NULL
approve_status: int DEFAULT NULL COMMENT '审签状态 2->通过；3->不通过'
appr_chain: char(36) DEFAULT NULL COMMENT '标识用于标记此次审签链'
all_passed: tinyint(1) NOT NULL DEFAULT '0' COMMENT '标识此模块是否被全通过'
change_flag: int DEFAULT NULL COMMENT '标记修改状态'
military: tinyint(1) NOT NULL DEFAULT '0'
key_process: tinyint(1) NOT NULL DEFAULT '0'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
created_by: varchar(45) DEFAULT NULL COMMENT '创建人'
```

Sample (first 3):
```
  row0: (1, 12, '通电前准备', 1, 2, 0, 100, 50, 99, 2, 'eb0a0654-5013-4fd0-8c7e-0bca2c8d045e', 1, 4, 0, 0, '2021-05-31 17:52:27', 'jd001')
  row1: (2, 13, '零位检查和操纵行程检查', 1, 1, 0, 200, 50, 99, 2, '2d38f0bf-5881-4f36-8478-6fdce061be89', 1, 4, 0, 0, '2021-05-31 17:52:27', 'jd001')
  row2: (3, 14, '传动比及极性检查', 1, 1, 0, 300, 50, 99, 2, '5218d9c8-5306-4cb9-a68d-8607b3f788cd', 1, 4, 0, 0, '2021-05-31 17:52:27', 'jd001')
```

### `approve_suite_history` — 689+ rows, 14 cols

Schema:
```
id: int unsigned NOT NULL AUTO_INCREMENT COMMENT '审签测试集历史Id'
suite_id: int DEFAULT NULL COMMENT '测试集Id'
suite_name: varchar(60) DEFAULT NULL COMMENT '测试集名称'
appr_his_id: int NOT NULL
appr_status: int NOT NULL DEFAULT '0' COMMENT '审签状态 2->通过；3->不通过'
military: tinyint(1) DEFAULT '0' COMMENT '军检测试集标志  0->不是；1->是'
version: int DEFAULT NULL
plane_effect_min: int DEFAULT NULL
plane_effect_max: int DEFAULT NULL
key_process: tinyint(1) DEFAULT NULL
appr_chain: char(36) DEFAULT NULL COMMENT '标识用于标记此次审签链'
all_passed: tinyint(1) NOT NULL DEFAULT '0' COMMENT '标识此模块是否被全通过'
created_at: datetime DEFAULT NULL COMMENT '创建时间'
created_by: varchar(45) DEFAULT NULL COMMENT '创建人'
```

Sample (first 3):
```
  row0: (1, 4, '20210531', 5, 2, 0, 3, 50, 50, 0, '91e3ee70-3422-4251-9808-b05ae53aa881', 1, '2021-05-31 17:56:09', 'jd001')
  row1: (2, 4, '20210531', 6, 2, 0, 3, 50, 50, 0, '91e3ee70-3422-4251-9808-b05ae53aa881', 1, '2021-05-31 17:56:28', 'pz001')
  row2: (3, 5, 'test', 11, 2, 0, 0, 50, 999, 0, '9336989d-3541-4a11-bbb0-771372d44925', 1, '2021-06-07 16:57:51', 'jd001')
```


## Group: `detect`

### `detect_result` — 5412+ rows, 6 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
detect_time: datetime DEFAULT NULL
source: varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
boxes: varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_
image: varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
exe_function_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DE
```

Sample (first 3):
```
  row0: (1, '2024-03-23 11:46:10', '/video.mp4', '', '1711165570.4799178.jpg', 'ad5a6795-7b36-4018-b143-e75cd35804df')
  row1: (2, '2024-06-05 17:09:55', 'rtsp://admin:fastop123@192.168.0.122:554/Streaming/Channels, '', '1717578595.193597.jpg', 'e6340ba8-2285-44ea-ab59-09b3534a55a3')
  row2: (3, '2024-06-05 17:10:18', 'rtsp://admin:fastop123@192.168.0.122:554/Streaming/Channels, '', '1717578618.1737716.jpg', 'e6340ba8-2285-44ea-ab59-09b3534a55a3')
```


## Group: `system_events`

### `event` — 9611+ rows, 15 cols

Schema:
```
event_id: int NOT NULL AUTO_INCREMENT
type: tinyint(1) DEFAULT NULL
content: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
level: tinyint(1) DEFAULT NULL
status: tinyint(1) DEFAULT NULL
timestamp: datetime DEFAULT NULL
device_id: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
test_etId: char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DE
image_path: varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_c
details: text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
duplicated: tinyint(1) NOT NULL DEFAULT '0'
alarm_status: tinyint(1) DEFAULT '0'
created_at: datetime DEFAULT NULL
updated_at: datetime DEFAULT NULL
deleted: tinyint(1) NOT NULL DEFAULT '0'
```

Sample (first 3):
```
  row0: (1, NULL, 'hello', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '2022-09-08 15:17:20', '2022-09-08 15:17:20', 0)
  row1: (2, NULL, 'hello', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '2022-09-08 15:17:20', '2022-09-08 15:17:20', 0)
  row2: (3, NULL, 'hello', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '2022-09-08 15:25:21', '2022-09-08 15:25:21', 0)
```

### `manufacture_analysis` — 3+ rows, 13 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
create_time: datetime DEFAULT CURRENT_TIMESTAMP
entity_id: int unsigned DEFAULT NULL
area_id: int unsigned DEFAULT NULL
test_subject_id: int DEFAULT NULL COMMENT '专业'
name: varchar(100) DEFAULT NULL COMMENT '系统名称'
main_serial_no: varchar(100) DEFAULT NULL COMMENT '成品主套号'
main_name: varchar(100) DEFAULT NULL
airborne_equipment_name: varchar(100) DEFAULT NULL COMMENT '机载设备名称'
airborne_equipment_model: varchar(100) DEFAULT NULL COMMENT '机载设备型号'
equip_num: int DEFAULT NULL COMMENT '安装数量'
testing_type: int DEFAULT '0' COMMENT '0检验，1联试'
corporation: varchar(100) DEFAULT NULL COMMENT '承制单位'
```

Sample (first 3):
```
  row0: (5, '2024-06-04 15:20:01', 28, 16, 1, '111', '111', '111', '111', '111', 111, 0, '111')
  row1: (7, '2024-06-04 15:34:21', 30, 17, 3, '系统名称', '成品主套号', '成品主套号名称', '机载设备名称', '机载设备型号', 12, 1, '承制单位')
  row2: (8, '2024-08-23 10:04:37', 28, 16, 1, '3', '3', '3', '3', '3', 3, 0, '3')
```

### `input_technical_status` — 0+ rows, 21 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
entity_model_id: int unsigned DEFAULT NULL
entity_struct_id: int unsigned DEFAULT NULL
start_entity_id: int DEFAULT NULL
end_entity_id: int DEFAULT NULL
area_id: int unsigned DEFAULT NULL
subject_id: int unsigned DEFAULT NULL
system_name: varchar(255) DEFAULT NULL
finished_set_number: varchar(255) DEFAULT NULL
finished_set_name: varchar(255) DEFAULT NULL
airborne_equipment_model: varchar(255) DEFAULT NULL
airborne_equipment_name: varchar(255) DEFAULT NULL
installation_quantity: int DEFAULT NULL
test_joint_trial: varchar(255) DEFAULT NULL
contractor: varchar(255) DEFAULT NULL
software_layer_version: varchar(255) DEFAULT NULL
software_item_name: varchar(255) DEFAULT NULL
software_item_number: varchar(255) DEFAULT NULL
software_version: varchar(255) DEFAULT NULL
software_research_unit: varchar(255) DEFAULT NULL
test_func_group_id: int unsigned DEFAULT NULL
```

### `spec_input_technical_status` — 0+ rows, 20 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT
entity_model_id: int unsigned DEFAULT NULL
entity_struct_id: int unsigned DEFAULT NULL
entity_id: int unsigned DEFAULT NULL
area_id: int unsigned DEFAULT NULL
subject_id: int unsigned DEFAULT NULL
system_name: varchar(255) DEFAULT NULL
finished_set_number: varchar(255) DEFAULT NULL
finished_set_name: varchar(255) DEFAULT NULL
airborne_equipment_model: varchar(255) DEFAULT NULL
airborne_equipment_name: varchar(255) DEFAULT NULL
installation_quantity: int DEFAULT NULL
test_joint_trial: varchar(255) DEFAULT NULL
contractor: varchar(255) DEFAULT NULL
software_layer_version: varchar(255) DEFAULT NULL
software_item_name: varchar(255) DEFAULT NULL
software_item_number: varchar(255) DEFAULT NULL
software_version: varchar(255) DEFAULT NULL
software_research_unit: varchar(255) DEFAULT NULL
test_func_group_id: int unsigned DEFAULT NULL
```

### `year_plan` — 3+ rows, 9 cols

Schema:
```
id: int NOT NULL AUTO_INCREMENT COMMENT '主键'
year: varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_c
all_plan_entity_count: int DEFAULT '0' COMMENT '检验计划架次数量'
plan_finish_entity_count: int DEFAULT '0' COMMENT '检验完成架次数量'
plan_exing_entity_count: int DEFAULT '0' COMMENT '检验进行中架次数量'
plan_verify_entity_count: int DEFAULT '0' COMMENT '剩余检验架次数量'
plan_in_time_entity_count: int DEFAULT '0' COMMENT '按时完成检验数量'
plan_over_time_entity_count: int DEFAULT '0' COMMENT '超时完成检验数量'
create_time: datetime DEFAULT CURRENT_TIMESTAMP
```

Sample (first 3):
```
  row0: (3, '2025', 22, 10, 3, 9, 10, 0, '2024-06-04 14:59:14')
  row1: (6, '2024', 21, 21, 0, 0, 21, 0, '2024-06-05 15:41:19')
  row2: (7, '2023', 20, 1, 1, 1, 1, 1, '2025-09-15 18:45:21')
```

