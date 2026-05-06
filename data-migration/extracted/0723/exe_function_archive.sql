-- Table structure for table `exe_function_archive`
--

DROP TABLE IF EXISTS `exe_function_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exe_function_archive` (
  `exe_function_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `plan_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联测试计划',
  `function_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联测试功能',
  `fun_version` int DEFAULT NULL,
  `function_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '功能名称',
  `category_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `current_step_num` int DEFAULT '0' COMMENT '当前执行到的步骤',
  `exe_status` int DEFAULT '0' COMMENT '状态',
  `verify_status` int DEFAULT '0' COMMENT '检验状态',
  `verify_num` int NOT NULL DEFAULT '0' COMMENT '表示模块下待检验单元的数量',
  `military_status` int DEFAULT '0' COMMENT '军检状态  0：不合格；1：合格',
  `military_num` int NOT NULL DEFAULT '0' COMMENT '表示模块下待军检单元的数量',
  `exe_function_order` int DEFAULT NULL COMMENT '执行功能的顺序',
  `result_comments` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果描述',
  `depends_on` varchar(100) DEFAULT NULL,
  `caution` varchar(255) DEFAULT NULL COMMENT '功能提示信息',
  `is_ready` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否准备好：0-未；1-准备好',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '完成时间',
  `change_flag` int DEFAULT NULL COMMENT '标记更改状态',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建操作用户的id',
  `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新操作用户的id',
  PRIMARY KEY (`exe_function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='执行测试功能归档表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exe_function_archive`
--

LOCK TABLES `exe_function_archive` WRITE;
/*!40000 ALTER TABLE `exe_function_archive` DISABLE KEYS */;
/*!40000 ALTER TABLE `exe_function_archive` ENABLE KEYS */;
UNLOCK TABLES;

--
