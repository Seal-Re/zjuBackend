-- Table structure for table `pump_address`
--

DROP TABLE IF EXISTS `pump_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pump_address` (
  `id` int NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `address` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `area_id` int unsigned DEFAULT NULL COMMENT '站位',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `rtsp_source` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pump_address`
--

LOCK TABLES `pump_address` WRITE;
/*!40000 ALTER TABLE `pump_address` DISABLE KEYS */;
INSERT INTO `pump_address` VALUES (1,'左机头','http://192.168.0.108:8899/live?port=1935&app=live&stream=18',16,'2024-06-03 09:43:25','rtsp://admin:fastop123@192.168.0.120/Streaming/Channels/101'),(2,'左机翼后缘','http://192.168.0.108:8899/live?port=1935&app=live&stream=10',16,'2024-06-03 09:42:46','rtsp://admin:fastop123@192.168.0.112/Streaming/Channels/401'),(3,'起落架','',16,'2024-06-03 09:49:29',''),(4,'左平尾','http://192.168.0.108:8900/live?port=1935&app=live&stream=13',16,'2024-06-03 09:42:46','rtsp://admin:fastop123@192.168.0.111/Streaming/Channels/101'),(5,'驾驶舱','',16,'2024-06-03 09:50:18',''),(6,'货舱门','',16,'2024-06-03 09:50:18',''),(7,'垂尾（方向舵）','http://192.168.0.108:8901/live?port=1935&app=live&stream=16',16,'2024-06-03 09:43:07','rtsp://admin:fastop123@192.168.0.111/Streaming/Channels/201'),(8,'右平尾','http://192.168.0.108:8901/live?port=1935&app=live&stream=15',16,'2024-06-03 09:42:07','rtsp://admin:fastop123@192.168.0.128/Streaming/Channels/101'),(9,'右机头','http://192.168.0.108:8901/live?port=1935&app=live&stream=14',16,'2024-06-03 09:43:37','rtsp://admin:fastop123@192.168.0.111/Streaming/Channels/101'),(10,'右机翼后缘','http://192.168.0.108:8901/live?port=1935&app=live&stream=11',16,'2024-03-25 00:06:07','rtsp://admin:fastop123@192.168.0.128/Streaming/Channels/301'),(21,'左机头','http://192.168.0.108:8900/live?port=1935&app=live&stream=0',17,'2024-06-03 09:49:10','rtsp://admin:fastop123@192.168.0.115/Streaming/Channels/101'),(22,'左机翼后缘','http://192.168.0.108:8900/live?port=1935&app=live&stream=1',17,'2024-06-03 09:49:29','rtsp://admin:fastop123@192.168.0.116/Streaming/Channels/101'),(23,'起落架','',17,'2024-06-03 09:49:29','rtsp://admin:fastop123@192.168.0.116/Streaming/Channels/101'),(24,'左平尾','http://192.168.0.108:8901/live?port=1935&app=live&stream=3',17,'2024-06-03 09:50:18','rtsp://admin:fastop123@192.168.0.119/Streaming/Channels/201'),(25,'驾驶舱','',17,'2024-06-03 09:50:18','rtsp://admin:fastop123@192.168.0.119/Streaming/Channels/201'),(26,'货舱门','',17,'2024-06-03 09:50:18','rtsp://admin:fastop123@192.168.0.119/Streaming/Channels/201'),(27,'垂尾（方向舵）','http://192.168.0.108:8901/live?port=1935&app=live&stream=6',17,'2024-06-03 09:50:08','rtsp://admin:fastop123@192.168.0.119/Streaming/Channels/101'),(28,'右平尾','http://192.168.0.108:8901/live?port=1935&app=live&stream=7',17,'2024-06-03 09:49:54','rtsp://admin:fastop123@192.168.0.117/Streaming/Channels/201'),(29,'右机头','http://192.168.0.108:8901/live?port=1935&app=live&stream=8',17,'2024-06-03 09:50:18','rtsp://admin:fastop123@192.168.0.118/Streaming/Channels/101'),(30,'右机翼后缘','http://192.168.0.108:8900/live?port=1935&app=live&stream=9',17,'2024-06-03 09:49:43','rtsp://admin:fastop123@192.168.0.117/Streaming/Channels/101');
/*!40000 ALTER TABLE `pump_address` ENABLE KEYS */;
UNLOCK TABLES;

--
