-- Table structure for table `tech_management`
--

DROP TABLE IF EXISTS `tech_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tech_management` (
  `id` int NOT NULL AUTO_INCREMENT,
  `entity_struct_id` int unsigned NOT NULL COMMENT '构型',
  `entity_id` int unsigned NOT NULL COMMENT '架次',
  `area_id` int unsigned DEFAULT NULL COMMENT '站位',
  `status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '状态',
  `process` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '生产进度',
  `locked` int DEFAULT '0' COMMENT '状态锁定，0不锁定。1锁定',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `entity_model_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COMMENT='技术状态管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tech_management`
--

LOCK TABLES `tech_management` WRITE;
/*!40000 ALTER TABLE `tech_management` DISABLE KEYS */;
INSERT INTO `tech_management` VALUES (8,18,28,16,'在途','90',0,'2024-03-22 23:44:40',14);
/*!40000 ALTER TABLE `tech_management` ENABLE KEYS */;
UNLOCK TABLES;

--
