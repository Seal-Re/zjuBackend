-- Table structure for table `privilege_action`
--

DROP TABLE IF EXISTS `privilege_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privilege_action` (
  `privilege_action_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `action_value` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`privilege_action_id`),
  KEY `index_PrivilegeActions_1` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilege_action`
--

LOCK TABLES `privilege_action` WRITE;
/*!40000 ALTER TABLE `privilege_action` DISABLE KEYS */;
INSERT INTO `privilege_action` VALUES ('FASTOP15-5cb3-4183-891e-573ba53862ab','NONE',1,'2019-02-16 00:00:00','2019-02-16 00:00:00'),('FASTOP2b-9894-4eaa-8e7f-c7d5b03c9319','VIEW',2,'2019-02-16 00:00:00','2019-02-16 00:00:00'),('FASTOP81-8135-44fe-ab64-acf1eb5afe42','ADD',16,'2019-02-16 00:00:00','2019-02-16 00:00:00'),('FASTOP90-a1a6-4def-a623-4a3f7cf89cc4','DELETE',8,'2019-02-16 00:00:00','2019-02-16 00:00:00'),('FASTOPb9-78da-4774-92d3-1b3db094c70b','EDIT',4,'2019-02-16 00:00:00','2019-02-16 00:00:00');
/*!40000 ALTER TABLE `privilege_action` ENABLE KEYS */;
UNLOCK TABLES;

--
