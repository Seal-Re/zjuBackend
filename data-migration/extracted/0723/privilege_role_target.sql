-- Table structure for table `privilege_role_target`
--

DROP TABLE IF EXISTS `privilege_role_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privilege_role_target` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `privilege_id` int NOT NULL,
  `scope` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'PARK' COMMENT '针对园区网还是工业网，PARK,INDUS',
  `can_read` int NOT NULL DEFAULT '1' COMMENT '是否能读。0不能读，1可以读',
  `can_write` int NOT NULL DEFAULT '1' COMMENT '是否能写，0不能写，1可以写',
  PRIMARY KEY (`id`),
  UNIQUE KEY `privilege_role_target_UN` (`role_id`,`privilege_id`)
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilege_role_target`
--

LOCK TABLES `privilege_role_target` WRITE;
/*!40000 ALTER TABLE `privilege_role_target` DISABLE KEYS */;
INSERT INTO `privilege_role_target` VALUES (1,'01',1,'PARK',1,1),(2,'01',2,'PARK',1,1),(5,'01',3,'PARK',1,1),(6,'01',4,'PARK',1,1),(7,'01',5,'PARK',1,1),(8,'01',6,'PARK',1,1),(9,'01',7,'PARK',1,1),(10,'01',8,'PARK',1,1),(11,'01',9,'PARK',1,1),(12,'01',10,'PARK',1,1),(13,'01',11,'PARK',1,1),(14,'01',12,'PARK',1,1),(15,'01',13,'PARK',1,1),(17,'01',15,'PARK',1,1),(19,'01',17,'PARK',1,1),(20,'01',18,'PARK',1,1),(21,'01',19,'PARK',1,1),(22,'01',20,'PARK',1,1),(23,'01',21,'PARK',1,1),(24,'01',22,'PARK',1,1),(25,'02',1,'PARK',1,1),(26,'02',2,'PARK',1,1),(27,'02',3,'PARK',1,1),(28,'02',4,'PARK',1,1),(30,'02',7,'PARK',1,1),(31,'02',10,'PARK',1,1),(34,'02',15,'PARK',1,1),(36,'02',17,'PARK',1,1),(37,'02',18,'PARK',1,1),(38,'02',19,'PARK',1,1),(39,'02',20,'PARK',1,1),(40,'02',22,'PARK',1,1),(41,'07',2,'PARK',1,1),(42,'07',3,'PARK',1,1),(44,'07',9,'PARK',1,0),(45,'07',10,'PARK',1,0),(47,'07',12,'PARK',1,0),(48,'07',13,'PARK',1,0),(50,'07',15,'PARK',1,0),(52,'07',17,'PARK',1,0),(53,'07',19,'PARK',1,0),(54,'07',18,'PARK',1,0),(56,'01',23,'PARK',1,1),(58,'01',24,'PARK',1,1),(59,'01',25,'PARK',1,1),(60,'01',26,'PARK',1,1),(61,'01',27,'PARK',1,1),(64,'01',30,'PARK',1,1),(65,'01',31,'PARK',1,1),(66,'01',32,'PARK',1,1),(68,'01',34,'PARK',1,1),(69,'01',35,'PARK',1,1),(72,'02',23,'PARK',1,1),(73,'02',24,'PARK',1,1),(74,'02',25,'PARK',1,1),(75,'02',26,'PARK',1,1),(76,'02',27,'PARK',1,1),(79,'02',30,'PARK',1,1),(80,'02',31,'PARK',1,1),(81,'02',32,'PARK',1,1),(83,'02',34,'PARK',1,1),(84,'02',35,'PARK',1,1),(88,'03',1,'PARK',1,1),(89,'03',23,'PARK',1,1),(90,'03',5,'PARK',1,1),(91,'03',24,'PARK',1,1),(92,'03',25,'PARK',1,1),(93,'03',26,'PARK',1,1),(94,'03',17,'PARK',1,1),(95,'03',18,'PARK',1,1),(96,'03',19,'PARK',1,1),(117,'08',6,'PARK',1,1),(118,'08',10,'PARK',1,1),(119,'08',12,'PARK',1,1),(120,'08',17,'PARK',1,1),(121,'08',18,'PARK',1,1),(122,'08',19,'PARK',1,1),(123,'13',1,'PARK',1,1),(124,'13',23,'PARK',1,1),(125,'13',17,'PARK',1,1),(126,'13',18,'PARK',1,1),(127,'13',19,'PARK',1,1),(128,'13',20,'PARK',1,1),(129,'13',27,'PARK',1,1),(132,'13',30,'PARK',1,1),(133,'13',31,'PARK',1,1),(134,'04',1,'PARK',1,1),(135,'04',23,'PARK',1,1),(136,'04',3,'PARK',1,1),(137,'04',18,'PARK',1,1),(138,'04',20,'PARK',1,1),(139,'04',22,'PARK',1,1),(140,'06',23,'PARK',1,1),(141,'06',1,'PARK',1,1),(142,'06',2,'PARK',1,1),(143,'06',3,'PARK',1,1),(144,'06',9,'PARK',1,1),(145,'06',15,'PARK',1,1),(147,'06',17,'PARK',1,1),(148,'06',18,'PARK',1,1),(149,'05',1,'PARK',1,1),(150,'05',23,'PARK',1,1),(151,'05',3,'PARK',1,1),(152,'05',8,'PARK',1,1),(153,'05',17,'PARK',1,1),(154,'05',18,'PARK',1,1),(155,'05',22,'PARK',1,1),(156,'04',7,'PARK',1,1),(157,'02',11,'PARK',1,1),(158,'02',12,'PARK',1,1),(159,'99',1,'PARK',1,1),(160,'99',2,'PARK',1,1),(161,'99',3,'PARK',1,1),(162,'99',4,'PARK',1,1),(163,'99',5,'PARK',1,1),(164,'99',6,'PARK',1,1),(165,'99',7,'PARK',1,1),(166,'99',8,'PARK',1,1),(167,'99',9,'PARK',1,1),(168,'99',10,'PARK',1,1),(169,'99',11,'PARK',1,1),(170,'99',12,'PARK',1,1),(171,'99',13,'PARK',1,1),(172,'99',15,'PARK',1,1),(173,'99',17,'PARK',1,1),(174,'99',18,'PARK',1,1),(175,'99',19,'PARK',1,1),(176,'99',20,'PARK',1,1),(177,'99',21,'PARK',1,1),(178,'99',22,'PARK',1,1),(179,'99',23,'PARK',1,1),(180,'99',24,'PARK',1,1),(181,'99',25,'PARK',1,1),(182,'99',26,'PARK',1,1),(183,'99',27,'PARK',1,1),(186,'99',30,'PARK',1,1),(187,'99',31,'PARK',1,1),(188,'99',32,'PARK',1,1),(189,'99',34,'PARK',1,1),(190,'99',35,'PARK',1,1),(191,'07',23,'PARK',1,0);
/*!40000 ALTER TABLE `privilege_role_target` ENABLE KEYS */;
UNLOCK TABLES;

--
