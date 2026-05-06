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
  `case_id` int NOT NULL,
  `step_status` int NOT NULL,
  PRIMARY KEY (`step_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_function_step`
--

LOCK TABLES `test_function_step` WRITE;
/*!40000 ALTER TABLE `test_function_step` DISABLE KEYS */;
INSERT INTO `test_function_step` VALUES (1,'步骤1：输入有效的注册信息','tester_C','在用户注册页面，输入预设的用户名、邮箱和密码。','这是核心的成功路径步骤。','2025-10-27 09:10:00','输入','注册表单','提交新用户数据',1,'无',5001,1),(2,'步骤1：输入有效的注册信息','tester_C','在用户注册页面，输入预设的用户名、邮箱和密码。','这是核心的成功路径步骤。','2025-10-27 09:10:00','输入','注册表单','提交新用户数据',1,'无',5002,0);
/*!40000 ALTER TABLE `test_function_step` ENABLE KEYS */;
UNLOCK TABLES;

--
