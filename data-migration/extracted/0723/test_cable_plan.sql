-- Table structure for table `test_cable_plan`
--

DROP TABLE IF EXISTS `test_cable_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_cable_plan` (
  `plan_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `entity_struct_id` int DEFAULT NULL,
  `entity_id` int DEFAULT NULL COMMENT '关联测试目标',
  `plan_start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
  `plan_end_time` datetime DEFAULT NULL COMMENT '计划结束时间',
  `actual_start_time` datetime DEFAULT NULL COMMENT '实际开始时间',
  `actual_end_time` datetime DEFAULT NULL COMMENT '实际结束时间',
  `status` int DEFAULT '0' COMMENT '计划状态',
  `plan_number` varchar(45) DEFAULT NULL COMMENT '计划号',
  `plan_name` varchar(45) DEFAULT NULL COMMENT '计划名',
  `area_id` int DEFAULT NULL COMMENT '关联仓位',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建操作用户的id',
  `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新操作用户的id',
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='线缆测试计划表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_cable_plan`
--

LOCK TABLES `test_cable_plan` WRITE;
/*!40000 ALTER TABLE `test_cable_plan` DISABLE KEYS */;
INSERT INTO `test_cable_plan` VALUES ('0f7c76f7-b933-49a9-9fad-07a8bb116a9c',18,33,NULL,NULL,NULL,NULL,0,NULL,'Y20-A-54',NULL,0,'2021-12-03 15:04:07','2021-12-03 15:04:07',NULL,NULL),('3e7d130d-d64e-4752-a267-eb0a11a94dd3',18,35,NULL,NULL,NULL,NULL,0,NULL,'Y20-A-0057',NULL,0,'2021-12-04 11:04:47','2021-12-04 11:04:47',NULL,NULL),('3f7c3b05-0f32-4757-bf82-b028e291da94',18,31,NULL,NULL,NULL,NULL,0,NULL,'Y20-A-50',NULL,0,'2021-12-04 10:08:52','2021-12-04 10:08:52',NULL,NULL),('6a212f95-1f29-40bf-9c8a-4f876a8c11a9',18,32,NULL,NULL,NULL,NULL,0,NULL,'Y20-A-55',NULL,0,'2021-12-03 18:00:20','2021-12-03 18:00:20',NULL,NULL),('c483044d-8ae2-4aad-9882-95f47ac36147',18,30,NULL,NULL,NULL,NULL,0,NULL,'Y20-A-48',NULL,0,'2021-12-04 09:37:49','2021-12-04 09:37:49',NULL,NULL),('fc332e13-4d5d-4500-abe5-9d28d365a55c',18,34,NULL,NULL,NULL,NULL,0,NULL,'Y20-A-56',NULL,0,'2021-12-04 01:15:43','2021-12-04 01:15:43',NULL,NULL);
/*!40000 ALTER TABLE `test_cable_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
