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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '603ebf01-c5c9-11f0-9d07-902e16d50acc:1-1104';

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
-- Dumping data for table `base_struct`
--

LOCK TABLES `base_struct` WRITE;
/*!40000 ALTER TABLE `base_struct` DISABLE KEYS */;
INSERT INTO `base_struct` VALUES (1,'M1','P1','S1'),(2,'M1','P1','S2'),(3,'M2','P2','s1'),(4,'M2','P1','s1');
/*!40000 ALTER TABLE `base_struct` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `exe_function`
--

LOCK TABLES `exe_function` WRITE;
/*!40000 ALTER TABLE `exe_function` DISABLE KEYS */;
INSERT INTO `exe_function` VALUES ('7f413089-2a3c-4a21-bd9e-9f210437ce68','新测试用例-通电前准备','a5da51d4-94ef-4056-a4ea-49ef67c32100','1208',0,0,10100,0,1,NULL,'8',1,0,2,0,0,0,0,'',0,0,NULL,NULL,'',NULL,NULL,1,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0',NULL),('f6d89450-095a-4b9e-b94c-6d358a4bdf6b','Test Function 1','a5da51d4-94ef-4056-a4ea-49ef67c32100','1209',0,0,1001,NULL,NULL,NULL,NULL,2,0,2,0,0,0,0,'Initial version',0,0,NULL,NULL,'',NULL,NULL,1,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0',NULL);
/*!40000 ALTER TABLE `exe_function` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `exe_log`
--

LOCK TABLES `exe_log` WRITE;
/*!40000 ALTER TABLE `exe_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `exe_log` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `exe_step`
--

LOCK TABLES `exe_step` WRITE;
/*!40000 ALTER TABLE `exe_step` DISABLE KEYS */;
INSERT INTO `exe_step` VALUES ('2fae3b70-d30b-4f8f-ab98-2ad8f5d6f901',1,'7f413089-2a3c-4a21-bd9e-9f210437ce68',NULL,NULL,NULL,NULL,'在用户注册页面，输入预设的用户名、邮箱和密码。',NULL,3,0,0,NULL,NULL,NULL,NULL,0,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'ALL_SUCCESS',0),('394f82f1-af6a-48b1-be36-7c4fdcf46145',3,'7f413089-2a3c-4a21-bd9e-9f210437ce68',NULL,NULL,NULL,NULL,'撕开灭火器上的封口',NULL,0,0,0,NULL,NULL,NULL,NULL,0,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'ALL_SUCCESS',0),('a802bdbf-b808-436f-9b95-5762be39eae4',5,'f6d89450-095a-4b9e-b94c-6d358a4bdf6b',NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,NULL,NULL,NULL,NULL,0,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'ALL_SUCCESS',0),('c7afa0d3-91bf-41ed-9189-5785df04bcfc',4,'7f413089-2a3c-4a21-bd9e-9f210437ce68',NULL,NULL,NULL,NULL,'使用灭火器，对准火源，打开灭火器',NULL,2,0,0,NULL,NULL,NULL,NULL,0,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'ALL_SUCCESS',0);
/*!40000 ALTER TABLE `exe_step` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `function_suite`
--

LOCK TABLES `function_suite` WRITE;
/*!40000 ALTER TABLE `function_suite` DISABLE KEYS */;
INSERT INTO `function_suite` VALUES (762,1215,1,0,1,NULL,354,0,NULL,NULL,NULL,NULL),(763,1216,2,0,2,NULL,354,0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `function_suite` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `test_base`
--

LOCK TABLES `test_base` WRITE;
/*!40000 ALTER TABLE `test_base` DISABLE KEYS */;
INSERT INTO `test_base` VALUES (1,'1',1,1,1,1,NULL,NULL,NULL,NULL,'','',''),(288,NULL,NULL,NULL,NULL,0,'2026-01-06 16:37:44','2026-01-06 16:37:44',NULL,NULL,'model1','prof1','sub_fc_2'),(289,NULL,NULL,NULL,NULL,0,'2026-01-06 17:04:39','2026-01-06 17:04:39',NULL,NULL,'model1','',''),(290,NULL,NULL,NULL,NULL,0,'2026-01-06 17:04:40','2026-01-06 17:04:40',NULL,NULL,'model1','prof1',''),(291,NULL,NULL,NULL,NULL,0,'2026-01-06 17:04:41','2026-01-06 17:04:41',NULL,NULL,'model1','prof1','sub_fc_1'),(292,NULL,NULL,NULL,NULL,0,'2026-01-08 15:11:22','2026-01-08 15:11:22',NULL,NULL,'model1','prof2','sub_fc_2'),(293,NULL,NULL,NULL,NULL,0,'2026-01-08 15:11:23','2026-01-08 15:11:23',NULL,NULL,'model2','prof2','sub_fc_2'),(294,NULL,NULL,NULL,NULL,0,'2026-01-09 16:30:22','2026-01-09 16:30:22',NULL,NULL,'model1','prof2','sub_power_2'),(295,NULL,NULL,NULL,NULL,0,'2026-01-22 11:05:09','2026-01-22 11:05:09',NULL,NULL,'model1','prof2','sub_fc_1'),(296,NULL,NULL,NULL,NULL,0,'2026-01-22 14:13:08','2026-01-22 14:13:08',NULL,NULL,'model1','prof2',''),(297,NULL,NULL,NULL,NULL,0,'2026-01-22 14:18:28','2026-01-22 14:18:28',NULL,NULL,'model1','prof1','sub_power_2'),(298,NULL,NULL,NULL,NULL,0,'2026-01-23 12:40:39','2026-01-23 12:40:39',NULL,NULL,'M1','P1','S1'),(299,NULL,NULL,NULL,NULL,0,'2026-01-23 12:40:39','2026-01-23 12:40:39',NULL,NULL,'M1','P1','S2'),(300,NULL,NULL,NULL,NULL,0,'2026-01-23 12:40:39','2026-01-23 12:40:39',NULL,NULL,'M2','P2','s1'),(301,NULL,NULL,NULL,NULL,0,'2026-01-23 12:40:39','2026-01-23 12:40:39',NULL,NULL,'M2','P1','s1'),(302,NULL,NULL,NULL,NULL,0,'2026-01-23 13:13:04','2026-01-23 13:13:04',NULL,NULL,'model1','P1','S1');
/*!40000 ALTER TABLE `test_base` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `test_function`
--

LOCK TABLES `test_function` WRITE;
/*!40000 ALTER TABLE `test_function` DISABLE KEYS */;
INSERT INTO `test_function` VALUES (1208,'新测试用例-通电前准备',NULL,19,0,0,1,9999,10100,1,0,NULL,'','','8','[]','[]',NULL,NULL,NULL,0,0,0,'86746e93-3895-4b4d-adab-f7ab928b68e3',0,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2025-12-01 17:20:39','2025-12-01 17:20:39','TODO','',0,0,NULL),(1209,'Test Function 1',NULL,1,0,0,1,100,1001,NULL,NULL,NULL,'Initial version',NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,0,6,NULL,0,NULL,'worker1','worker1','worker1','worker1','worker1','worker1',NULL,NULL,0,'2025-12-08 14:38:48','2025-12-08 14:38:48','TODO',NULL,0,0,NULL),(1210,'前端测试1',NULL,1,0,0,1,100,1,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,6,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2026-01-06 16:38:03','2026-01-06 16:38:03','TODO',NULL,0,0,NULL),(1211,'前端测试2-1',NULL,288,0,0,1,100,2,NULL,NULL,NULL,'',NULL,NULL,'[]','[]',NULL,NULL,NULL,0,0,6,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2026-01-06 16:39:58','2026-01-06 16:39:58','TODO',NULL,0,0,NULL),(1212,'测试模块2',NULL,291,0,0,1,100,1,NULL,NULL,NULL,'',NULL,NULL,'[]','[]',NULL,NULL,NULL,0,0,6,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2026-01-08 21:28:12','2026-01-08 21:28:12','TODO',NULL,0,0,NULL),(1213,'4',NULL,1,0,0,1,100,444,NULL,NULL,NULL,'',NULL,NULL,'[]','[]',NULL,NULL,NULL,0,0,6,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2026-01-22 14:11:52','2026-01-22 14:11:52','TODO',NULL,0,0,NULL),(1214,'',NULL,294,0,0,1,100,22,NULL,NULL,NULL,'',NULL,NULL,'[]','[]',NULL,NULL,NULL,0,0,0,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2026-01-22 14:18:51','2026-01-22 14:18:51','TODO',NULL,0,0,NULL),(1215,'test1',NULL,298,0,0,1,100,1,NULL,NULL,NULL,'',NULL,NULL,'[]','[]',NULL,NULL,NULL,0,0,6,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2026-01-23 13:06:28','2026-01-23 13:06:28','TODO',NULL,0,0,NULL),(1216,'魔筷2',NULL,298,0,0,1,100,2,NULL,NULL,NULL,'',NULL,NULL,'[]','[]',NULL,NULL,NULL,0,0,6,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2026-01-23 13:11:34','2026-01-23 13:11:34','TODO',NULL,0,0,NULL),(1217,'260302',NULL,298,0,0,1,100,260302,NULL,NULL,NULL,'',NULL,NULL,'[]','[[\"device1\"]]',NULL,NULL,NULL,0,0,6,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2026-03-02 02:33:17','2026-03-02 02:33:17','TODO',NULL,0,0,NULL);
/*!40000 ALTER TABLE `test_function` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `test_function_case`
--

LOCK TABLES `test_function_case` WRITE;
/*!40000 ALTER TABLE `test_function_case` DISABLE KEYS */;
INSERT INTO `test_function_case` VALUES (5001,'C001-成功注册验证','tester_C','输入有效信息，验证用户是否成功创建并登录。','关联模块 1001。','2025-10-26 09:00:00',2,0,0),(5002,'C001-成功注册验证','tester_C','输入有效信息，验证用户是否成功创建并登录。','关联模块 1001。','2025-10-26 09:00:00',1,0,0),(5003,'C002-成功注册验证','tester_C','输入有效信息，验证用户是否成功创建并登录。','关联模块 1001。','2025-10-26 09:00:00',1,0,0),(5004,'灭火器准备',NULL,'灭火器准备子用例','无注意事项','',2,0,0),(5005,NULL,NULL,NULL,NULL,'Mon Dec 08 14:46:30 CST 2025',3,0,0),(5006,'2',NULL,'',NULL,'Wed Jan 07 22:45:35 CST 2026',4,0,1),(5007,'1',NULL,'',NULL,'Thu Jan 08 14:58:45 CST 2026',7,0,1),(5008,'2',NULL,'',NULL,'Thu Jan 08 14:59:31 CST 2026',7,0,1),(5009,'子用例1',NULL,'',NULL,'Fri Jan 23 13:08:51 CST 2026',8,0,1),(5010,'子用例2',NULL,'',NULL,'Fri Jan 23 13:08:58 CST 2026',8,0,1),(5011,'c1',NULL,'',NULL,'Fri Jan 23 13:11:54 CST 2026',9,0,1),(5012,'中餐',NULL,'',NULL,'Mon Mar 02 02:34:02 CST 2026',11,0,1),(5013,'睡觉',NULL,'',NULL,'Mon Mar 02 02:35:03 CST 2026',11,2,1);
/*!40000 ALTER TABLE `test_function_case` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `test_function_module`
--

LOCK TABLES `test_function_module` WRITE;
/*!40000 ALTER TABLE `test_function_module` DISABLE KEYS */;
INSERT INTO `test_function_module` VALUES (1,'用户注册流程模块','developer_A','覆盖新用户注册、登录、信息校验的全部测试用例。','待编写最后几条边界用例。','2025-10-25 10:30:00',439,0,0),(2,'器材准备',NULL,'通电前准备下的器材准备用例','无注意事项','',1208,0,0),(3,'Module 1',NULL,NULL,NULL,'Mon Dec 08 14:43:19 CST 2025',1209,0,0),(4,NULL,NULL,NULL,NULL,'Wed Jan 07 22:40:46 CST 2026',1211,0,1),(5,NULL,NULL,NULL,NULL,'Wed Jan 07 22:45:08 CST 2026',1211,0,1),(6,NULL,NULL,NULL,NULL,'Wed Jan 07 22:45:10 CST 2026',1211,0,1),(7,NULL,NULL,NULL,NULL,'Thu Jan 08 14:58:35 CST 2026',1210,0,1),(8,NULL,NULL,NULL,NULL,'Fri Jan 23 13:08:43 CST 2026',1215,0,1),(9,NULL,NULL,NULL,NULL,'Fri Jan 23 13:11:42 CST 2026',1216,0,1),(10,NULL,NULL,NULL,NULL,'Fri Jan 23 13:11:47 CST 2026',1216,0,1),(11,NULL,NULL,NULL,NULL,'Mon Mar 02 02:33:47 CST 2026',1217,0,1);
/*!40000 ALTER TABLE `test_function_module` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `test_function_rely`
--

LOCK TABLES `test_function_rely` WRITE;
/*!40000 ALTER TABLE `test_function_rely` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_function_rely` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `test_function_step`
--

LOCK TABLES `test_function_step` WRITE;
/*!40000 ALTER TABLE `test_function_step` DISABLE KEYS */;
INSERT INTO `test_function_step` VALUES (1,'步骤1：输入有效的注册信息','tester_C','在用户注册页面，输入预设的用户名、邮箱和密码。','这是核心的成功路径步骤。','2025-10-27 09:10:00','输入','注册表单','提交新用户数据',1,'无',5001,1,0),(2,'步骤1：输入有效的注册信息','tester_C','在用户注册页面，输入预设的用户名、邮箱和密码。','这是核心的成功路径步骤。','2025-10-27 09:10:00','输入','注册表单','提交新用户数据',1,'无',5002,0,0),(3,'灭火器启封',NULL,'撕开灭火器上的封口','无注意事项','',NULL,NULL,NULL,NULL,NULL,5004,0,0),(4,'灭火器使用',NULL,'使用灭火器，对准火源，打开灭火器','无注意事项','',NULL,NULL,NULL,NULL,NULL,5004,0,0),(5,'Step 1',NULL,NULL,NULL,'Mon Dec 08 14:47:38 CST 2025',NULL,NULL,NULL,NULL,NULL,5005,0,0),(6,'1',NULL,'',NULL,'Thu Jan 08 14:59:08 CST 2026','扭一扭','ala','',NULL,NULL,5007,0,1),(7,'2',NULL,'',NULL,'Thu Jan 08 14:59:26 CST 2026','舔一舔','ala','',NULL,NULL,5007,0,1),(8,'2',NULL,'',NULL,'Thu Jan 08 14:59:47 CST 2026','泡一泡','ala','',NULL,NULL,5008,0,1),(9,'步骤1',NULL,'',NULL,'Fri Jan 23 13:09:25 CST 2026','内容步骤1','设备1','',NULL,NULL,5009,0,1),(10,'步骤2',NULL,'',NULL,'Fri Jan 23 13:09:41 CST 2026','内容2','设备2','',NULL,NULL,5010,0,1),(11,'步骤3',NULL,'',NULL,'Fri Jan 23 13:12:06 CST 2026','内容3','设备1','',NULL,NULL,5011,0,1),(12,'川菜',NULL,'',NULL,'Mon Mar 02 02:34:30 CST 2026','火锅','device1','',NULL,NULL,5012,0,1),(13,'江西小炒',NULL,'',NULL,'Mon Mar 02 02:34:54 CST 2026','鸡爪','device2','',NULL,NULL,5012,0,1);
/*!40000 ALTER TABLE `test_function_step` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `test_plan`
--

LOCK TABLES `test_plan` WRITE;
/*!40000 ALTER TABLE `test_plan` DISABLE KEYS */;
INSERT INTO `test_plan` VALUES ('273a7c88-3b59-4402-b1e7-1c8ac5c99538',1,298,1,1,354,0,'2026-01-23 08:00:00','2026-02-13 08:00:00','2026-03-02 02:35:42',NULL,2,'',2,'测试计划11',1,NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,1,'2026-01-23 14:59:03',NULL,NULL,NULL,0,'Default',0),('50ef56b7-b926-4bab-b640-849f5e01b76b',1,298,1,1,352,0,'2026-01-23 08:00:00','2026-02-13 08:00:00','2026-01-23 14:57:01',NULL,3,'',1,'测试计划1',1,NULL,NULL,NULL,NULL,NULL,NULL,0,0,1,1,'2026-01-23 13:50:56',NULL,NULL,NULL,0,'Default',0),('87a69571-3854-4661-802e-8b0b198bccef',19,90,3,30,312,0,'2025-07-04 08:00:00','2025-07-31 08:00:00',NULL,NULL,0,'20250704-RETEST',1,'日期格式修复后的测试计划',16,'id','id',NULL,NULL,NULL,NULL,0,0,1,1,'2025-11-11 09:27:04',NULL,NULL,NULL,0,'id',0),('a5da51d4-94ef-4056-a4ea-49ef67c32100',NULL,1,NULL,NULL,347,0,'2023-10-27 18:00:00','2023-10-28 18:00:00','2026-01-22 16:40:53',NULL,2,NULL,1,'Plan 1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,1,1,'2025-12-19 16:16:57',NULL,NULL,NULL,0,NULL,0);
/*!40000 ALTER TABLE `test_plan` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `test_suite`
--

LOCK TABLES `test_suite` WRITE;
/*!40000 ALTER TABLE `test_suite` DISABLE KEYS */;
INSERT INTO `test_suite` VALUES (345,'测试集','用于验证更新状态',0,100,500,'1','2','3',42,3,0,1,'555',0,NULL,NULL,'admin_user','admin_user','MESDCE_001'),(347,'Suite 1',NULL,0,0,9999,'proofer','approver','submitter',1,3,0,0,NULL,0,NULL,NULL,NULL,NULL,NULL),(348,'Suite 1',NULL,1,0,9999,NULL,NULL,NULL,1,0,0,0,NULL,0,NULL,NULL,NULL,NULL,NULL),(349,'测试清单1',NULL,0,1,100,NULL,NULL,NULL,288,0,0,0,NULL,0,NULL,NULL,NULL,NULL,NULL),(350,'测试清单1修改测试1修改测试2',NULL,0,1,100,NULL,NULL,NULL,291,3,0,0,NULL,0,NULL,NULL,NULL,NULL,NULL),(351,'燃油1','',0,1,100,NULL,NULL,NULL,294,3,0,0,NULL,0,NULL,NULL,NULL,NULL,NULL),(352,'测试清单1','',0,1,100,NULL,NULL,NULL,298,3,0,0,NULL,0,NULL,NULL,NULL,NULL,NULL),(354,'测试清单2.0','',1,1,100,NULL,NULL,NULL,298,3,0,0,NULL,0,'2026-01-23 16:18:19','2026-01-26 12:16:14',NULL,NULL,NULL);
/*!40000 ALTER TABLE `test_suite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'autosys_1014'
--
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-02  2:40:57
