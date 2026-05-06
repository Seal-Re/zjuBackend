-- Table structure for table `tech_status`
--

DROP TABLE IF EXISTS `tech_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tech_status` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `entity_id` int DEFAULT NULL COMMENT '构型',
  `plane_number` int DEFAULT NULL COMMENT '架次',
  `area_id` int DEFAULT NULL COMMENT '站位',
  `process` varchar(100) DEFAULT NULL COMMENT '生产进度',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='技术状态';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tech_status`
--

LOCK TABLES `tech_status` WRITE;
/*!40000 ALTER TABLE `tech_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `tech_status` ENABLE KEYS */;
UNLOCK TABLES;

--
