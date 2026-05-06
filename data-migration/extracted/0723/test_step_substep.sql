-- Table structure for table `test_step_substep`
--

DROP TABLE IF EXISTS `test_step_substep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_step_substep` (
  `id` int NOT NULL AUTO_INCREMENT,
  `criterion_type` int DEFAULT '0' COMMENT '0仅操作，1选值，2录值',
  `criterion_standard` int NOT NULL DEFAULT '1' COMMENT '0值，1判据关联',
  `criterion_content` varchar(100) DEFAULT NULL COMMENT '判断依据',
  `criterion_value_unit` varchar(10) DEFAULT NULL COMMENT '值单位',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `depend_on_device` int DEFAULT '0' COMMENT '是否使用设备，0表示没有1表示有',
  `device_cat_id` int unsigned DEFAULT NULL COMMENT '设备类型',
  `dev_unit_id` int unsigned DEFAULT NULL COMMENT '设备单元',
  `device_command` int DEFAULT NULL COMMENT '设备指令',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `test_step_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `test_step_substep_FK` (`test_step_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_step_substep`
--

LOCK TABLES `test_step_substep` WRITE;
/*!40000 ALTER TABLE `test_step_substep` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_step_substep` ENABLE KEYS */;
UNLOCK TABLES;

--
