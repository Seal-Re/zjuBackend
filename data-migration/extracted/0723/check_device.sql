-- Table structure for table `check_device`
--

DROP TABLE IF EXISTS `check_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_device` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '检测设备名称',
  `ip` varchar(100) DEFAULT NULL,
  `port` varchar(100) DEFAULT NULL,
  `status` int unsigned DEFAULT '0' COMMENT '0表示未知，1表示通，2表示不通',
  `check_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `jumpto` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_device`
--

LOCK TABLES `check_device` WRITE;
/*!40000 ALTER TABLE `check_device` DISABLE KEYS */;
INSERT INTO `check_device` VALUES (2,'视频抽引','127.0.0.1','8080',1,'2025-03-05 11:34:44',NULL),(3,'摄像头','127.0.0.1','8080',1,'2025-03-05 11:34:44',NULL),(4,'主飞控地面维护设备','127.0.0.1','8080',1,'2025-03-05 11:34:44',NULL),(5,'高度表','127.0.0.1','8080',1,'2025-03-05 11:34:44',NULL),(6,'蓄电池加温','127.0.0.1','8080',1,'2025-03-05 11:34:44',NULL),(7,'供电模拟器','127.0.0.1','8080',1,'2025-03-05 11:34:44',NULL),(8,'顶控板DO1','127.0.0.1','8080',1,'2025-03-05 11:34:44',NULL),(9,'顶控板DO2','127.0.0.1','8080',1,'2025-03-05 11:34:44',NULL);
/*!40000 ALTER TABLE `check_device` ENABLE KEYS */;
UNLOCK TABLES;

--
