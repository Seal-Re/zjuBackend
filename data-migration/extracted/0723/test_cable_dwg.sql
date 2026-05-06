-- Table structure for table `test_cable_dwg`
--

DROP TABLE IF EXISTS `test_cable_dwg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_cable_dwg` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `dwg_name` varchar(60) NOT NULL,
  `description` varchar(100) NOT NULL,
  `dwg_order` int DEFAULT NULL,
  `fun_group_id` int NOT NULL COMMENT '关联子系统',
  `num` int NOT NULL COMMENT '编号',
  `status` int NOT NULL DEFAULT '0' COMMENT '状态',
  `using_by` varchar(400) DEFAULT NULL COMMENT '占用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT NULL COMMENT '\n',
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_cable_dwg`
--

LOCK TABLES `test_cable_dwg` WRITE;
/*!40000 ALTER TABLE `test_cable_dwg` DISABLE KEYS */;
INSERT INTO `test_cable_dwg` VALUES (1,'452','舱门',1,7,100,6,NULL,0,NULL,NULL,NULL,NULL),(2,'66','任务',2,8,200,6,NULL,0,NULL,NULL,NULL,NULL),(3,'426','动力',3,9,300,6,NULL,0,NULL,NULL,NULL,NULL),(4,'428','动力',4,9,310,6,NULL,0,NULL,NULL,NULL,NULL),(5,'449','动力',5,9,320,6,NULL,0,NULL,NULL,NULL,NULL),(6,'476','动力',6,9,330,6,NULL,0,NULL,NULL,NULL,NULL),(7,'477','动力',7,9,340,6,NULL,0,NULL,NULL,NULL,NULL),(8,'480','动力',8,9,350,6,NULL,0,NULL,NULL,NULL,NULL),(9,'430','防冰',9,10,400,6,NULL,0,NULL,NULL,NULL,NULL),(10,'425','装饰',10,11,410,6,NULL,0,NULL,NULL,NULL,NULL),(11,'438','装饰',11,11,420,6,NULL,0,NULL,NULL,NULL,NULL),(12,'421','环控',12,12,430,6,NULL,0,NULL,NULL,NULL,NULL),(13,'436','环控',13,12,440,6,NULL,0,NULL,NULL,NULL,NULL),(14,'322','飞控',14,6,500,6,NULL,0,NULL,NULL,NULL,NULL),(15,'327','飞控',15,6,510,6,NULL,0,NULL,NULL,NULL,NULL),(16,'424','供电',16,13,600,6,NULL,0,NULL,NULL,NULL,NULL),(17,'433','照明',17,14,610,6,NULL,0,NULL,NULL,NULL,NULL),(18,'495','照明',18,14,620,6,NULL,0,NULL,NULL,NULL,NULL),(19,'486','机电管理',19,15,630,6,NULL,0,NULL,NULL,NULL,NULL),(20,'487','机电管理',20,15,640,6,NULL,0,NULL,NULL,NULL,NULL),(21,'631','仪表',21,16,700,6,NULL,0,NULL,NULL,NULL,NULL),(22,'634','仪表',22,16,710,6,NULL,0,NULL,NULL,NULL,NULL),(23,'633','仪表',23,16,720,6,NULL,0,NULL,NULL,NULL,NULL),(24,'649','仪表',24,16,730,6,NULL,0,NULL,NULL,NULL,NULL),(25,'642','无线电',25,17,740,6,NULL,0,NULL,NULL,NULL,NULL),(26,'648','无线电',26,17,750,6,NULL,0,NULL,NULL,NULL,NULL),(27,'643','无线电',27,17,760,6,NULL,0,NULL,NULL,NULL,NULL),(28,'688','雷达',28,18,770,6,NULL,0,NULL,NULL,NULL,NULL),(29,'689','雷达',29,18,780,6,NULL,0,NULL,NULL,NULL,NULL),(30,'693','雷达',30,18,790,6,NULL,0,NULL,NULL,NULL,NULL),(31,'699','雷达',31,18,800,6,NULL,0,NULL,NULL,NULL,NULL),(32,'435','氧气',32,19,810,6,NULL,0,NULL,NULL,NULL,NULL),(33,'429','液压',33,20,900,6,NULL,0,NULL,NULL,NULL,NULL),(34,'432','起落架',34,21,910,6,NULL,0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `test_cable_dwg` ENABLE KEYS */;
UNLOCK TABLES;

--
