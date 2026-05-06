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
  `module_id` int NOT NULL,
  `case_status` int NOT NULL,
  PRIMARY KEY (`case_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_function_case`
--

LOCK TABLES `test_function_case` WRITE;
/*!40000 ALTER TABLE `test_function_case` DISABLE KEYS */;
INSERT INTO `test_function_case` VALUES (5001,'C001-成功注册验证','tester_C','输入有效信息，验证用户是否成功创建并登录。','关联模块 1001。','2025-10-26 09:00:00',2,0),(5002,'C001-成功注册验证','tester_C','输入有效信息，验证用户是否成功创建并登录。','关联模块 1001。','2025-10-26 09:00:00',1,0),(5003,'C002-成功注册验证','tester_C','输入有效信息，验证用户是否成功创建并登录。','关联模块 1001。','2025-10-26 09:00:00',1,0);
/*!40000 ALTER TABLE `test_function_case` ENABLE KEYS */;
UNLOCK TABLES;

--
