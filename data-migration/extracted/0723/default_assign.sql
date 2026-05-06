-- Table structure for table `default_assign`
--

DROP TABLE IF EXISTS `default_assign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `default_assign` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) DEFAULT NULL COMMENT '分配方用户',
  `item` varchar(45) DEFAULT NULL COMMENT '分配项目',
  `target_user` varchar(45) DEFAULT NULL COMMENT '分配目标方用户',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `default_assign`
--

LOCK TABLES `default_assign` WRITE;
/*!40000 ALTER TABLE `default_assign` DISABLE KEYS */;
INSERT INTO `default_assign` VALUES (1,'719cc782-b0c4-45b3-8fc1-964414f6fcbf','审签校对员','09890526-232b-45f4-909e-211d5d077b38',0,'2021-01-11 10:01:37','2021-03-19 17:57:21','gy001','gy001'),(2,'09890526-232b-45f4-909e-211d5d077b38','审签质审员','cd86f6e2-921a-4372-b327-2fa31c79686a',0,'2021-01-11 10:02:16','2021-03-19 17:57:39','jd001','jd001'),(3,'cd86f6e2-921a-4372-b327-2fa31c79686a','审签审查员','a3242a8f-d323-4954-968c-021200833951',0,'2021-01-11 10:02:32','2021-03-19 17:58:07','zs001','zs001'),(4,'a3242a8f-d323-4954-968c-021200833951','审签批准员','e42cfa15-87a1-4b3c-8187-aaa9b1d17cbb',0,'2021-01-11 10:02:50','2021-03-19 17:58:31','sc001','sc001'),(5,'09890526-232b-45f4-909e-211d5d077b38','审签批准员','e42cfa15-87a1-4b3c-8187-aaa9b1d17cbb',0,'2021-01-11 10:05:03','2021-03-19 17:57:46','jd001','jd001'),(6,'a7ba6280-51a2-4a5f-9f03-cdd3c08e117b','审签审查员','a7ba6280-51a2-4a5f-9f03-cdd3c08e117b',0,'2021-01-11 11:11:21','2021-01-18 11:37:46','qn001','qn001'),(7,'a7ba6280-51a2-4a5f-9f03-cdd3c08e117b','审签批准员','a7ba6280-51a2-4a5f-9f03-cdd3c08e117b',0,'2021-01-11 11:11:28','2021-01-18 11:38:13','qn001','qn001'),(8,'a7ba6280-51a2-4a5f-9f03-cdd3c08e117b','审签校对员','a7ba6280-51a2-4a5f-9f03-cdd3c08e117b',0,'2021-01-11 11:14:39','2021-01-18 11:37:26','qn001','qn001'),(9,'a7ba6280-51a2-4a5f-9f03-cdd3c08e117b','审签质审员','a7ba6280-51a2-4a5f-9f03-cdd3c08e117b',0,'2021-01-11 11:14:56','2021-01-18 11:37:37','qn001','qn001'),(10,'79eb4d65-bc42-4837-bc38-9fe88eac7e5a','审签校对员','79eb4d65-bc42-4837-bc38-9fe88eac7e5a',0,'2021-01-12 20:20:41','2021-01-19 16:53:42','18001130','18001130'),(11,'a7ba6280-51a2-4a5f-9f03-cdd3c08e117b','客户','a7ba6280-51a2-4a5f-9f03-cdd3c08e117b',0,'2021-01-18 11:22:33','2021-01-18 11:22:33','qn001','qn001'),(12,'79eb4d65-bc42-4837-bc38-9fe88eac7e5a','审签质审员','79eb4d65-bc42-4837-bc38-9fe88eac7e5a',0,'2021-01-19 17:04:37','2021-01-19 17:04:37','18001130','18001130'),(13,'ec4331d5-d6b3-4386-9106-3f2fdb014c55','审签校对员','ec4331d5-d6b3-4386-9106-3f2fdb014c55',0,'2021-01-20 11:23:01','2021-01-21 14:55:38','28400647','28400647'),(14,'ec4331d5-d6b3-4386-9106-3f2fdb014c55','审签质审员','ec4331d5-d6b3-4386-9106-3f2fdb014c55',0,'2021-01-20 11:23:53','2021-01-20 19:25:41','28400647','28400647'),(15,'ec4331d5-d6b3-4386-9106-3f2fdb014c55','审签批准员','ec4331d5-d6b3-4386-9106-3f2fdb014c55',0,'2021-01-20 16:28:58','2021-01-20 19:25:57','28400647','28400647'),(16,'ec4331d5-d6b3-4386-9106-3f2fdb014c55','审签审查员','a3242a8f-d323-4954-968c-021200833951',0,'2021-01-20 19:25:50','2021-01-20 19:25:50','28400647','28400647');
/*!40000 ALTER TABLE `default_assign` ENABLE KEYS */;
UNLOCK TABLES;

--
