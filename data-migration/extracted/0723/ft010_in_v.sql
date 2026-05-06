-- Table structure for table `ft010_in_v`
--

DROP TABLE IF EXISTS `ft010_in_v`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ft010_in_v` (
  `id` int NOT NULL AUTO_INCREMENT,
  `db_source` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `db_str` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `use_sign` int DEFAULT NULL,
  `creation_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ft010_in_v`
--

LOCK TABLES `ft010_in_v` WRITE;
/*!40000 ALTER TABLE `ft010_in_v` DISABLE KEYS */;
/*!40000 ALTER TABLE `ft010_in_v` ENABLE KEYS */;
UNLOCK TABLES;

--
