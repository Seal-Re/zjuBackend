-- Table structure for table `exe_step_archive`
--

DROP TABLE IF EXISTS `exe_step_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exe_step_archive` (
  `exe_step_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `step_id` int DEFAULT NULL,
  `exe_function_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联执行测试功能',
  `step_level` int DEFAULT NULL COMMENT '级别：区分子模块、用例、步骤',
  `step_order` int DEFAULT NULL COMMENT '步骤顺序',
  `level_seq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '序号',
  `step_seq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '步骤序号',
  `step_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `content_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联步骤内容',
  `exe_status` int DEFAULT '0' COMMENT '执行状态',
  `verify_status` int DEFAULT '0' COMMENT '检验状态',
  `military_status` int DEFAULT '0' COMMENT '军检状态  0：不合格；1：合格',
  `result_comments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果描述',
  `military_comment` varchar(255) DEFAULT NULL COMMENT '军检说明',
  `step_result` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '步骤结果',
  `is_manual` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-工业相机；1-人工',
  `level_one_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表示所属模块的信息id',
  `judge_result` int DEFAULT '0' COMMENT '自动判定结果的状态：0-无 1-通过 2-不通过',
  `can_next` tinyint(1) DEFAULT '1' COMMENT '是否可以下一步：0-不可以；1-可以,默认为1',
  `command_data` text,
  `fail_cause` text COMMENT '用于存放设备指令执行结果消息',
  `operation` varchar(100) DEFAULT NULL COMMENT '操作',
  `operation_object` varchar(100) DEFAULT NULL COMMENT '操作目标',
  `operation_content` varchar(100) DEFAULT NULL COMMENT '操作内容',
  `criterion_standard_id` int DEFAULT NULL COMMENT '判据规范Id',
  `criterion_standard` varchar(100) DEFAULT NULL COMMENT '判据规范',
  `criterion_type` int DEFAULT NULL COMMENT '判据类型',
  `criterion_value_unit` varchar(100) DEFAULT NULL,
  `criterion_content` varchar(200) DEFAULT NULL,
  `criterion_desc` varchar(255) DEFAULT NULL,
  `guide_url` varchar(50) DEFAULT NULL,
  `key_process` tinyint(1) NOT NULL DEFAULT '0' COMMENT '关键重要标识',
  `depend_on_device` tinyint(1) DEFAULT '0' COMMENT '0:不需要；1：需要',
  `caution` varchar(255) DEFAULT NULL COMMENT '步骤提示',
  `commander` varchar(45) DEFAULT NULL COMMENT '指挥人员',
  `verfier` varchar(45) DEFAULT NULL COMMENT '检验人员',
  `soldier` varchar(45) DEFAULT NULL COMMENT '军检人员',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `change_flag` int DEFAULT NULL COMMENT '标记更改状态',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建操作用户的id',
  `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新操作用户的id',
  PRIMARY KEY (`exe_step_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='执行步骤归档表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exe_step_archive`
--

LOCK TABLES `exe_step_archive` WRITE;
/*!40000 ALTER TABLE `exe_step_archive` DISABLE KEYS */;
/*!40000 ALTER TABLE `exe_step_archive` ENABLE KEYS */;
UNLOCK TABLES;

--
