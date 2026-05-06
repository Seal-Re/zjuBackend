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
INSERT INTO `test_plan` VALUES ('87a69571-3854-4661-802e-8b0b198bccef',19,90,3,30,312,0,'2025-07-04 08:00:00','2025-07-31 08:00:00',NULL,NULL,5,'20250704-RETEST',1,'日期格式修复后的测试计划',16,'id','id',NULL,NULL,NULL,NULL,0,0,0,1,'2025-11-11 09:27:04',NULL,NULL,NULL,0,'id',0);
/*!40000 ALTER TABLE `test_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
