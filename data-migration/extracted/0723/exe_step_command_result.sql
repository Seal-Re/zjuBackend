-- Table structure for table `exe_step_command_result`
--

DROP TABLE IF EXISTS `exe_step_command_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exe_step_command_result` (
  `id` int NOT NULL AUTO_INCREMENT,
  `exe_step_id` char(36) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `type` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `command_mes` varchar(500) DEFAULT NULL,
  `batch` int DEFAULT '1' COMMENT '执行批次',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exe_step_command_result`
--

LOCK TABLES `exe_step_command_result` WRITE;
/*!40000 ALTER TABLE `exe_step_command_result` DISABLE KEYS */;
INSERT INTO `exe_step_command_result` VALUES (1,'6e8375a8-8fe3-443b-8d6d-28ef43b1bb76','2023-08-25 11:16:08',0,1,'',0),(2,'6e8375a8-8fe3-443b-8d6d-28ef43b1bb76','2023-08-25 11:18:34',0,1,'',0),(3,'6e8375a8-8fe3-443b-8d6d-28ef43b1bb76','2023-08-25 11:28:42',0,1,'',0);
/*!40000 ALTER TABLE `exe_step_command_result` ENABLE KEYS */;
UNLOCK TABLES;

--
