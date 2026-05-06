-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `priority` int DEFAULT '10',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `default_role` tinyint(1) NOT NULL DEFAULT '0',
  `role_level` tinyint(1) DEFAULT NULL,
  `parent_role_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `Role_Name_Unique` (`name`),
  KEY `index_roles_1` (`role_id`),
  KEY `index_roles_2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('00','系统管理','系统管理',1,'2019-02-16 00:00:00','2019-02-16 00:00:00',1,0,NULL),('01','管理','管理',1,'2019-02-16 00:00:00','2019-02-16 00:00:00',1,1,'00'),('02','工艺','负责设计测试用例',1,'2019-02-16 00:00:00','2019-02-16 00:00:00',1,2,'00'),('03','审签批准员','负责批准测试集',1,'2019-02-16 00:00:00','2019-02-16 00:00:00',1,3,'00'),('04','指挥','负责指挥执行测试用例',1,'2019-02-16 00:00:00','2019-02-16 00:00:00',1,4,'00'),('05','执行','负责执行测试用例',1,'2019-02-16 00:00:00','2019-02-16 00:00:00',1,5,'00'),('06','检验','负责检验测试结果',1,'2019-02-16 00:00:00','2019-02-16 00:00:00',1,6,'00'),('07','军代表','军代表',1,'2019-02-16 00:00:00','2019-02-16 00:00:00',1,7,'00'),('08','计划员','负责管理测试计划',1,'2020-09-01 00:00:00','2020-09-01 00:00:00',1,8,'00'),('09','审签校对员','负责校对测试集',1,'2020-10-13 00:00:00','2020-10-13 00:00:00',1,9,'00'),('10','审签审核员','负责审核测试集',1,'2020-10-13 00:00:00','2020-10-13 00:00:00',1,10,'00'),('11','审签质审员','负责质审测试集',1,'2020-10-13 00:00:00','2020-10-13 00:00:00',1,11,'00'),('12','审签审查员','负责审查测试集',1,'2020-10-13 00:00:00','2020-10-13 00:00:00',1,12,'00'),('13','派工人员','负责派工测试计划',1,'2021-03-08 00:00:00','2021-03-08 00:00:00',1,13,'00'),('14','线缆执行','负责线缆执行测试',1,'2021-11-16 00:00:00','2021-11-16 00:00:00',1,14,'00'),('99','查看角色','查看角色',1,'2021-11-16 00:00:00','2021-11-16 00:00:00',1,15,'00');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
