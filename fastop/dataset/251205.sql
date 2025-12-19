-- autosys_1014.function_suite definition

CREATE TABLE `function_suite` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `test_fun_id` int NOT NULL COMMENT '关联测试模块',
                                  `fun_num` int DEFAULT NULL COMMENT '模块编号',
                                  `fun_version` int DEFAULT NULL COMMENT '模块版本',
                                  `fun_order` int NOT NULL COMMENT '模块序号',
                                  `depends_on` varchar(100) DEFAULT NULL COMMENT '模块依赖 以 ； 符号分隔',
                                  `suite_id` int NOT NULL COMMENT '关联测试清单',
                                  `deleted` tinyint(1) NOT NULL DEFAULT '0',
                                  `created_at` datetime DEFAULT NULL,
                                  `updated_at` datetime DEFAULT NULL,
                                  `created_by` varchar(45) DEFAULT NULL,
                                  `updated_by` varchar(45) DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=758 DEFAULT CHARSET=utf8mb3;


-- autosys_1014.test_base definition

CREATE TABLE `test_base` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `name` varchar(45) DEFAULT NULL,
                             `entity_struct_id` int NOT NULL COMMENT '实体id',
                             `fun_group_id` int NOT NULL,
                             `base_type` int NOT NULL DEFAULT '1' COMMENT '测试库类型 1：普通测试库 2：临时测试库',
                             `deleted` tinyint(1) NOT NULL DEFAULT '0',
                             `created_at` datetime DEFAULT NULL,
                             `updated_at` datetime DEFAULT NULL,
                             `created_by` varchar(45) DEFAULT NULL,
                             `updated_by` varchar(45) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=288 DEFAULT CHARSET=utf8mb3;


-- autosys_1014.test_function definition

CREATE TABLE `test_function` (
                                 `fun_id` int unsigned NOT NULL AUTO_INCREMENT,
                                 `fun_name` varchar(60) NOT NULL,
                                 `fun_order` int DEFAULT NULL,
                                 `test_base_id` int NOT NULL COMMENT '关联测试清单',
                                 `version` int NOT NULL COMMENT '版本',
                                 `flow_version` int NOT NULL DEFAULT '0' COMMENT '修改版本',
                                 `plane_effect_min` int NOT NULL COMMENT '架次有效性最小值',
                                 `plane_effect_max` int NOT NULL COMMENT '架次有效性最大值',
                                 `num` int NOT NULL COMMENT '编号',
                                 `expect_time` int DEFAULT NULL COMMENT '预估时间',
                                 `security_level` int DEFAULT NULL COMMENT '保密级别',
                                 `comment` varchar(100) DEFAULT NULL COMMENT '说明',
                                 `version_description` varchar(255) DEFAULT '' COMMENT '换版说明',
                                 `approve_comment` varchar(255) DEFAULT NULL COMMENT '审签说明',
                                 `subject_source_id` varchar(255) DEFAULT NULL COMMENT '项目技术源集， id 以 ； 符号分隔',
                                 `other_tech_files` varchar(1000) DEFAULT NULL,
                                 `device_pool` varchar(1000) DEFAULT NULL,
                                 `test_caution_id` varchar(255) DEFAULT NULL COMMENT '多个提示主键拼接字段，以 ； 符号分隔',
                                 `caution` varchar(1000) DEFAULT NULL,
                                 `depends_on` varchar(100) DEFAULT NULL,
                                 `change_flag` int DEFAULT NULL COMMENT '标记更改状态: 0-未变; 1-调整步骤; 2-修改功能信息;3-变更功能顺序;4-新增功能.\r\n(优先级： 4>3>2>1>0)',
                                 `key_pro_count` int NOT NULL DEFAULT '0' COMMENT '拥有的步骤中关键工序的个数',
                                 `approve_status` int DEFAULT '0' COMMENT '0:未提交；1：待校对；2：待质审；3：待审查；4：待批准；5：审签成功；6：审签失败',
                                 `appr_chain` varchar(45) DEFAULT NULL COMMENT '标识用于标记此次审签链',
                                 `military` tinyint(1) NOT NULL COMMENT '军检标识',
                                 `using_by` varchar(400) DEFAULT NULL COMMENT '占用',
                                 `designer` varchar(45) DEFAULT NULL COMMENT '工艺人员',
                                 `proofer` varchar(45) DEFAULT NULL COMMENT '审签校对人员',
                                 `verifier` varchar(45) DEFAULT NULL COMMENT '审签审核人员',
                                 `checker` varchar(45) DEFAULT NULL COMMENT '审签审查人员',
                                 `qualityer` varchar(45) DEFAULT NULL COMMENT '审签质审人员',
                                 `approver` varchar(45) DEFAULT NULL COMMENT '审签批准人员',
                                 `history_version_line` varchar(255) DEFAULT NULL COMMENT '历史版本线，左老右新以 ； 符号分隔',
                                 `sync_plan` varchar(255) DEFAULT NULL COMMENT '同步测试计划集，id间以 ； 符号分隔',
                                 `deleted` tinyint(1) NOT NULL DEFAULT '0',
                                 `created_at` datetime DEFAULT NULL COMMENT '\n',
                                 `updated_at` datetime DEFAULT NULL,
                                 `created_by` varchar(45) DEFAULT NULL,
                                 `updated_by` varchar(45) DEFAULT NULL,
                                 `create_new` int DEFAULT '0' COMMENT '是否是军检测试，如果=0 表示是之前数据',
                                 `military_func` int DEFAULT '0' COMMENT '是否是军检模块，0表示不是，1表示是',
                                 `detect_id` varchar(200) DEFAULT NULL COMMENT '对应图像服务',
                                 PRIMARY KEY (`fun_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1209 DEFAULT CHARSET=utf8mb3;


-- autosys_1014.test_function_case definition

CREATE TABLE `test_function_case` (
                                      `case_id` int NOT NULL AUTO_INCREMENT,
                                      `case_name` varchar(100) DEFAULT NULL,
                                      `change_user` varchar(100) DEFAULT NULL,
                                      `case_description` varchar(100) DEFAULT NULL,
                                      `case_note` varchar(100) DEFAULT NULL,
                                      `case_date` varchar(100) DEFAULT NULL,
                                      `module_id` int NOT NULL,
                                      `case_status` int NOT NULL,
                                      PRIMARY KEY (`case_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5005 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- autosys_1014.test_function_module definition

CREATE TABLE `test_function_module` (
                                        `module_id` int NOT NULL AUTO_INCREMENT,
                                        `module_name` varchar(100) DEFAULT NULL,
                                        `change_user` varchar(100) DEFAULT NULL,
                                        `module_description` varchar(100) DEFAULT NULL,
                                        `module_note` varchar(100) DEFAULT NULL,
                                        `module_date` varchar(100) DEFAULT NULL,
                                        `fun_id` int NOT NULL,
                                        `module_status` int NOT NULL,
                                        PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- autosys_1014.test_function_step definition

CREATE TABLE `test_function_step` (
                                      `step_id` int NOT NULL AUTO_INCREMENT,
                                      `step_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                      `change_user` varchar(100) DEFAULT NULL,
                                      `step_description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                      `step_note` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                      `step_date` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                      `step_operation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                      `step_obj` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                      `step_purpose` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                      `total_send` tinyint(1) DEFAULT NULL,
                                      `condition_status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                      `case_id` int NOT NULL,
                                      `step_status` int NOT NULL,
                                      PRIMARY KEY (`step_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- autosys_1014.test_plan definition

CREATE TABLE `test_plan` (
                             `plan_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
                             `entity_struct_id` int DEFAULT NULL,
                             `entity_id` int DEFAULT NULL COMMENT '关联测试目标',
                             `subject_id` int DEFAULT NULL,
                             `fun_group_id` int DEFAULT NULL COMMENT '子系统id',
                             `suite_id` int DEFAULT NULL COMMENT '关联模板',
                             `military` tinyint(1) NOT NULL DEFAULT '0' COMMENT '军检计划标志  0：非军检计划；1：军检计划',
                             `plan_start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
                             `plan_end_time` datetime DEFAULT NULL COMMENT '计划结束时间',
                             `actual_start_time` datetime DEFAULT NULL COMMENT '实际开始时间',
                             `actual_end_time` datetime DEFAULT NULL COMMENT '实际结束时间',
                             `status` int DEFAULT '0' COMMENT '计划状态',
                             `plan_number` varchar(45) DEFAULT NULL COMMENT '计划号',
                             `plan_round` int DEFAULT '1',
                             `plan_name` varchar(45) DEFAULT NULL COMMENT '计划名',
                             `area_id` int DEFAULT NULL COMMENT '关联仓位',
                             `dispatcher_id` char(36) DEFAULT NULL COMMENT '派工人员id',
                             `commander_id` char(36) DEFAULT NULL COMMENT '指挥人员id',
                             `executor_group_id` varchar(45) DEFAULT NULL COMMENT '执行组id',
                             `comm_assign` varchar(100) DEFAULT NULL,
                             `execut_assign` varchar(200) DEFAULT NULL,
                             `verify_assign` varchar(100) DEFAULT NULL,
                             `updatable` tinyint(1) DEFAULT '0' COMMENT '0:无可更新；1：可更新',
                             `archived` tinyint(1) DEFAULT '0' COMMENT '用于记录是否归档',
                             `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除',
                             `base_type` int DEFAULT '1' COMMENT '测试库类型 1：正式测试库 2：临时测试库',
                             `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
                             `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建操作用户的id',
                             `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新操作用户的id',
                             `sync` tinyint(1) DEFAULT '0',
                             `management` varchar(100) DEFAULT NULL COMMENT '军检计划',
                             `for_record_data` int NOT NULL DEFAULT '0' COMMENT '是否记录',
                             PRIMARY KEY (`plan_id`),
                             UNIQUE KEY `test_plan_unique` (`plan_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='测试计划表';


-- autosys_1014.test_suite definition

CREATE TABLE `test_suite` (
                              `suite_id` int unsigned NOT NULL AUTO_INCREMENT,
                              `suite_name` varchar(60) NOT NULL,
                              `suite_desc` varchar(255) DEFAULT NULL,
                              `version` int NOT NULL COMMENT '版本',
                              `plane_effect_min` int NOT NULL COMMENT '架次有效性最小值',
                              `plane_effect_max` int NOT NULL COMMENT '架次有效性最大值',
                              `proofer` varchar(45) DEFAULT NULL COMMENT '审签校对者',
                              `approver` varchar(45) DEFAULT NULL COMMENT '审签批准者',
                              `submitter` varchar(45) DEFAULT NULL COMMENT '清单提交者',
                              `test_base_id` int NOT NULL COMMENT '关联测试清单',
                              `list_appr_status` int DEFAULT '0' COMMENT '清单审签状态',
                              `military` tinyint(1) DEFAULT '0',
                              `key_process` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是关键工序测试集 0:不是；1:是',
                              `appr_chain` varchar(45) DEFAULT NULL COMMENT '标识用于标记此次审签链',
                              `deleted` tinyint(1) NOT NULL DEFAULT '0',
                              `created_at` datetime DEFAULT NULL,
                              `updated_at` datetime DEFAULT NULL,
                              `created_by` varchar(45) DEFAULT NULL,
                              `updated_by` varchar(45) DEFAULT NULL,
                              `mesdce_code` varchar(100) DEFAULT NULL,
                              PRIMARY KEY (`suite_id`)
) ENGINE=InnoDB AUTO_INCREMENT=346 DEFAULT CHARSET=utf8mb3;