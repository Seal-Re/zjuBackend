-- MySQL dump 10.13  Distrib 9.5.0, for Win64 (x86_64)
--
-- Host: localhost    Database: autosys_1014
-- ------------------------------------------------------
-- Server version	9.5.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- GTID state at the beginning of the backup 
--


--
-- Table structure for table `base_struct`
--

DROP TABLE IF EXISTS `base_struct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `base_struct` (
  `base_id` int NOT NULL AUTO_INCREMENT,
  `model` varchar(100) NOT NULL,
  `profession` varchar(100) NOT NULL,
  `subsystem` varchar(100) NOT NULL,
  PRIMARY KEY (`base_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `exe_function`
--

DROP TABLE IF EXISTS `exe_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exe_function` (
  `exe_function_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `function_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '模块名称',
  `plan_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联测试计划',
  `function_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联测试模块',
  `version` int DEFAULT NULL COMMENT '版本',
  `flow_version` int DEFAULT NULL COMMENT '流水版本',
  `num` int DEFAULT NULL COMMENT '编号',
  `security` int DEFAULT NULL COMMENT '密级',
  `expect_time` int DEFAULT NULL,
  `test_caution_id` varchar(255) DEFAULT NULL,
  `subject_source_id` varchar(255) DEFAULT NULL,
  `exe_function_order` int DEFAULT NULL,
  `current_step_num` int DEFAULT '0' COMMENT '当前执行到的步骤',
  `exe_status` int DEFAULT '0' COMMENT '状态',
  `verify_status` int DEFAULT '0' COMMENT '检验状态',
  `verify_num` int NOT NULL DEFAULT '0' COMMENT '表示模块下待检验单元的数量',
  `military_status` int DEFAULT '0' COMMENT '军检状态  0：不合格；1：合格',
  `military_num` int NOT NULL DEFAULT '0' COMMENT '表示模块下待军检单元的数量',
  `version_description` varchar(255) DEFAULT NULL,
  `military` tinyint(1) DEFAULT NULL COMMENT '军检标志',
  `key_pro_count` int DEFAULT NULL,
  `change_flag` int DEFAULT NULL COMMENT '标记更改状态',
  `flow_version_line` varchar(255) DEFAULT NULL COMMENT '历史版本线，左老右新以 ； 符号分隔',
  `depends_on` varchar(100) DEFAULT NULL,
  `result_comments` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果描述',
  `caution` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `is_ready` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否准备好：0-未；1-准备好',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '完成时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建操作用户的id',
  `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新操作用户的id',
  `cal_before_time` datetime DEFAULT NULL COMMENT '计算模块执行开始时间',
  `cal_time` varchar(500) DEFAULT NULL COMMENT '以0;20;50格式，分段计算所花费时间,小时为单位',
  `execute_time` int DEFAULT NULL COMMENT '计算模块总计花费时间',
  `redo_count` varchar(100) DEFAULT '0' COMMENT '重复执行率',
  `detect_id` varchar(100) DEFAULT NULL COMMENT '对应图像服务id',
  PRIMARY KEY (`exe_function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='执行测试功能表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `exe_log`
--

DROP TABLE IF EXISTS `exe_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exe_log` (
  `log_id` varchar(36) NOT NULL COMMENT '日志主键',
  `step_id` varchar(36) DEFAULT NULL COMMENT '关联执行步骤ID',
  `plan_id` varchar(36) DEFAULT NULL COMMENT '计划ID，便于按计划查询',
  `content` text COMMENT '日志内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `idx_step_id` (`step_id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='执行步骤日志-军检审计';
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `exe_step`
--

DROP TABLE IF EXISTS `exe_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exe_step` (
  `exe_step_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `step_id` int DEFAULT NULL,
  `exe_function_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联执行测试功能',
  `step_level` int DEFAULT NULL COMMENT '级别：区分子模块、用例、步骤',
  `step_order` int DEFAULT NULL COMMENT '步骤顺序',
  `level_seq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '序号',
  `step_seq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '步骤序号',
  `step_description` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `content_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联步骤内容',
  `exe_status` int DEFAULT '0' COMMENT '执行状态',
  `verify_status` int DEFAULT '0' COMMENT '检验状态',
  `military_status` int DEFAULT '0' COMMENT '军检状态  0：不合格；1：合格',
  `result_comments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果描述',
  `military_comment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '军检说明',
  `step_result` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '步骤结果',
  `data_id` int DEFAULT NULL COMMENT '步骤结果',
  `is_manual` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-工业相机；1-人工',
  `level_one_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表示所属模块的信息id',
  `judge_result` int DEFAULT '0' COMMENT '自动判定结果的状态：0-无 1-通过 2-不通过',
  `can_next` tinyint(1) DEFAULT '1' COMMENT '是否可以下一步：0-不可以；1-可以,默认为1',
  `command_data` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `fail_cause` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '用于存放设备指令执行结果消息',
  `operation` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '操作',
  `operation_object` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `operation_content` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '操作内容',
  `criterion_standard_id` int DEFAULT NULL COMMENT '判据规范Id',
  `criterion_standard` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '判据规范',
  `criterion_type` int DEFAULT '0' COMMENT '判据类型',
  `criterion_value_unit` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `criterion_content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `criterion_desc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `guide_url` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `key_process` tinyint(1) NOT NULL DEFAULT '0' COMMENT '关键重要标识',
  `depend_on_device` tinyint(1) DEFAULT '0' COMMENT '0:不需要；1：需要',
  `caution` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '步骤提示',
  `commander` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '指挥人员',
  `verfier` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '检验人员',
  `soldier` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '军检人员',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `change_flag` int DEFAULT NULL COMMENT '标记更改状态',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建操作用户的id',
  `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新操作用户的id',
  `calculate` varchar(100) DEFAULT 'ALL_SUCCESS' COMMENT 'ANY_SUCCESS | ALL_SUCCESS',
  `parallel_execute` int unsigned DEFAULT '0' COMMENT '并行处理，0表示不能。1表示能',
  PRIMARY KEY (`exe_step_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='执行步骤表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `function_suite`
--

DROP TABLE IF EXISTS `function_suite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=764 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `test_base`
--

DROP TABLE IF EXISTS `test_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_base` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `entity_struct_id` int DEFAULT NULL COMMENT '实体id',
  `fun_group_id` int DEFAULT NULL,
  `base_type` int DEFAULT '1' COMMENT '测试库类型 1：普通测试库 2：临时测试库',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `model` varchar(100) NOT NULL,
  `profession` varchar(100) NOT NULL,
  `subsystem` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=303 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `test_function`
--

DROP TABLE IF EXISTS `test_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=1218 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `test_function_case`
--

DROP TABLE IF EXISTS `test_function_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_function_case` (
  `case_id` int NOT NULL AUTO_INCREMENT,
  `case_name` varchar(100) DEFAULT NULL,
  `change_user` varchar(100) DEFAULT NULL,
  `case_description` varchar(100) DEFAULT NULL,
  `case_note` varchar(100) DEFAULT NULL,
  `case_date` varchar(100) DEFAULT NULL,
  `module_id` int DEFAULT NULL,
  `case_status` int NOT NULL,
  `updated` int NOT NULL,
  PRIMARY KEY (`case_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5014 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `test_function_module`
--

DROP TABLE IF EXISTS `test_function_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_function_module` (
  `module_id` int NOT NULL AUTO_INCREMENT,
  `module_name` varchar(100) DEFAULT NULL,
  `change_user` varchar(100) DEFAULT NULL,
  `module_description` varchar(100) DEFAULT NULL,
  `module_note` varchar(100) DEFAULT NULL,
  `module_date` varchar(100) DEFAULT NULL,
  `fun_id` int DEFAULT NULL,
  `module_status` int NOT NULL,
  `updated` int NOT NULL,
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `test_function_rely`
--

DROP TABLE IF EXISTS `test_function_rely`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_function_rely` (
  `test_function_rely_id` int NOT NULL AUTO_INCREMENT,
  `suite_id` int DEFAULT NULL,
  `test_function_id` int NOT NULL,
  `rely_function_id` int DEFAULT NULL,
  `rely_funtion_ready` tinyint(1) DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建操作用户的id',
  `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新操作用户的id',
  PRIMARY KEY (`test_function_rely_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `test_function_step`
--

DROP TABLE IF EXISTS `test_function_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
  `case_id` int DEFAULT NULL,
  `step_status` int NOT NULL,
  `updated` int NOT NULL,
  PRIMARY KEY (`step_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `test_plan`
--

DROP TABLE IF EXISTS `test_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Table structure for table `test_suite`
--

DROP TABLE IF EXISTS `test_suite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=355 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
--


--
-- Dumping routines for database 'autosys_1014'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-02  2:40:57
