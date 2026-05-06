-- Table structure for table `arealocation`
--

DROP TABLE IF EXISTS `arealocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `arealocation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `areaNumber` int DEFAULT NULL COMMENT '区域编号',
  `XPos` double DEFAULT '0' COMMENT 'X坐标',
  `YPos` double DEFAULT '0' COMMENT 'Y坐标',
  `ZPos` double DEFAULT '0' COMMENT 'Z坐标',
  `APos` double DEFAULT NULL,
  `BPos` double DEFAULT NULL,
  `CPos` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arealocation`
--

LOCK TABLES `arealocation` WRITE;
/*!40000 ALTER TABLE `arealocation` DISABLE KEYS */;
INSERT INTO `arealocation` VALUES (1,0,-62.6,-552,83.8,-3.1415926,0,-3.1415926),(2,2,3.3,88.5,150.4,NULL,NULL,NULL),(3,1,80,-107,1137,0,0.36652,0);
/*!40000 ALTER TABLE `arealocation` ENABLE KEYS */;
UNLOCK TABLES;

--
