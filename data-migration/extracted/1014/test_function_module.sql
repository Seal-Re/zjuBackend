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
  `fun_id` int NOT NULL,
  `module_status` int NOT NULL,
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_function_module`
--

LOCK TABLES `test_function_module` WRITE;
/*!40000 ALTER TABLE `test_function_module` DISABLE KEYS */;
INSERT INTO `test_function_module` VALUES (1,'用户注册流程模块','developer_A','覆盖新用户注册、登录、信息校验的全部测试用例。','待编写最后几条边界用例。','2025-10-25 10:30:00',439,0);
/*!40000 ALTER TABLE `test_function_module` ENABLE KEYS */;
UNLOCK TABLES;

--
