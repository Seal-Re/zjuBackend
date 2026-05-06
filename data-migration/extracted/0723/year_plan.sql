-- Table structure for table `year_plan`
--

DROP TABLE IF EXISTS `year_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `year_plan` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '年份',
  `all_plan_entity_count` int DEFAULT '0' COMMENT '检验计划架次数量',
  `plan_finish_entity_count` int DEFAULT '0' COMMENT '检验完成架次数量',
  `plan_exing_entity_count` int DEFAULT '0' COMMENT '检验进行中架次数量',
  `plan_verify_entity_count` int DEFAULT '0' COMMENT '剩余检验架次数量',
  `plan_in_time_entity_count` int DEFAULT '0' COMMENT '按时完成检验数量',
  `plan_over_time_entity_count` int DEFAULT '0' COMMENT '超时完成检验数量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `year_plan_unique` (`year`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `year_plan`
--

LOCK TABLES `year_plan` WRITE;
/*!40000 ALTER TABLE `year_plan` DISABLE KEYS */;
INSERT INTO `year_plan` VALUES (3,'2025',22,10,3,9,10,0,'2024-06-04 14:59:14'),(6,'2024',21,21,0,0,21,0,'2024-06-05 15:41:19'),(7,'2023',20,1,1,1,1,1,'2025-09-15 18:45:21');
/*!40000 ALTER TABLE `year_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
