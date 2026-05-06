-- Table structure for table `manufacture_analysis`
--

DROP TABLE IF EXISTS `manufacture_analysis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manufacture_analysis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `entity_id` int unsigned DEFAULT NULL,
  `area_id` int unsigned DEFAULT NULL,
  `test_subject_id` int DEFAULT NULL COMMENT '专业',
  `name` varchar(100) DEFAULT NULL COMMENT '系统名称',
  `main_serial_no` varchar(100) DEFAULT NULL COMMENT '成品主套号',
  `main_name` varchar(100) DEFAULT NULL,
  `airborne_equipment_name` varchar(100) DEFAULT NULL COMMENT '机载设备名称',
  `airborne_equipment_model` varchar(100) DEFAULT NULL COMMENT '机载设备型号',
  `equip_num` int DEFAULT NULL COMMENT '安装数量',
  `testing_type` int DEFAULT '0' COMMENT '0检验，1联试',
  `corporation` varchar(100) DEFAULT NULL COMMENT '承制单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COMMENT='成品分析';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufacture_analysis`
--

LOCK TABLES `manufacture_analysis` WRITE;
/*!40000 ALTER TABLE `manufacture_analysis` DISABLE KEYS */;
INSERT INTO `manufacture_analysis` VALUES (5,'2024-06-04 15:20:01',28,16,1,'111','111','111','111','111',111,0,'111'),(7,'2024-06-04 15:34:21',30,17,3,'系统名称','成品主套号','成品主套号名称','机载设备名称','机载设备型号',12,1,'承制单位'),(8,'2024-08-23 10:04:37',28,16,1,'3','3','3','3','3',3,0,'3');
/*!40000 ALTER TABLE `manufacture_analysis` ENABLE KEYS */;
UNLOCK TABLES;

--
