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
