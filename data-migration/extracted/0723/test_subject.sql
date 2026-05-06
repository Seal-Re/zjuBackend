-- Table structure for table `test_subject`
--

DROP TABLE IF EXISTS `test_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_subject` (
  `subject_id` int unsigned NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(60) NOT NULL,
  `subject_desc` varchar(255) DEFAULT NULL,
  `num` int NOT NULL COMMENT '编号',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_subject`
--

LOCK TABLES `test_subject` WRITE;
/*!40000 ALTER TABLE `test_subject` DISABLE KEYS */;
INSERT INTO `test_subject` VALUES (1,'飞控','飞控项目',32,0,'2022-12-26 00:00:00','2021-04-26 17:37:45','admin','admin'),(2,'电气','机电系统',0,0,'2022-12-26 00:00:20','2021-05-31 17:31:27','admin','28400647'),(3,'航电','航电系统',0,0,'2022-12-26 00:00:10','2021-01-21 14:14:53','admin','28400647'),(4,'环控','机电系统',0,0,'2022-12-26 00:00:40','2021-01-21 14:12:20','28400647','28400647'),(7,'动力','机电系统',0,0,'2022-12-26 00:00:30','2021-05-31 17:31:55','28400647','28400647'),(9,'任务','航电系统',0,0,'2022-12-26 00:00:50','2021-01-21 14:14:45','28400647','28400647'),(11,'液起','机电系统',0,0,'2022-12-26 00:01:00','2021-05-31 17:30:39','28400647','28400647'),(12,'机电','机电系统',0,0,'2023-03-09 15:27:47','2023-03-09 15:27:47','29300613','29300613'),(24,'机械','机械系统',1,0,'2023-08-22 10:21:40','2023-08-22 10:21:40','28404121','28404121');
/*!40000 ALTER TABLE `test_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
