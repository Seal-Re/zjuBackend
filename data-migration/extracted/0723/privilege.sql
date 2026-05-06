-- Table structure for table `privilege`
--

DROP TABLE IF EXISTS `privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privilege` (
  `id` int NOT NULL AUTO_INCREMENT,
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'default.icon',
  `router_link` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  `leaf` int DEFAULT '0' COMMENT '是否是叶子节点,1表示是，0表示不是',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `redirect` varchar(200) DEFAULT NULL,
  `sorted` int DEFAULT '0' COMMENT '排序，递增',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilege`
--

LOCK TABLES `privilege` WRITE;
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
INSERT INTO `privilege` VALUES (1,'icon-yibiaopan','/dashboard','总装集成测试',23,1,'2023-10-27 03:14:37',NULL,18),(2,'icon-yibiaopan','/analysis-board','多维分析',23,1,'2023-10-27 03:15:40',NULL,19),(3,'icon-yibiaopan','/video-fly','现场监督',NULL,0,'2023-10-27 03:17:30',NULL,2),(4,'icon-testlink','/test-function-design','测试设计',NULL,0,'2023-10-27 03:17:53',NULL,3),(5,'icon-testlink','/test-check-sign-list','测试审签',NULL,0,'2023-10-27 03:18:33',NULL,4),(6,'icon-jihuarenwu','/test-task-manage','测试计划',NULL,0,'2023-10-27 03:18:47',NULL,5),(7,'icon-fangxunyingjizhihui','/test-task-command','测试指挥',NULL,0,'2023-10-27 03:19:10',NULL,6),(8,'icon-zhihangzhongsuan','/test-task-execute','测试执行',NULL,0,'2023-10-27 03:19:27',NULL,7),(9,'icon-pingwenxingjianyan','/test-task-verify','测试质检',NULL,0,'2023-10-27 03:20:04',NULL,8),(10,'icon-gongjujianyan','/test-board','军检测试',NULL,0,'2023-10-27 03:20:48',NULL,9),(11,'icon-gongjujianyan','/military-test-design','军检设计',10,1,'2023-10-27 03:21:16',NULL,91),(12,'icon-gongjujianyan','/military-test-plan','军检计划',10,1,'2023-10-27 03:21:58',NULL,92),(13,'icon-gongjujianyan','/test-client-verify','测试看板',10,1,'2023-10-27 03:22:38',NULL,93),(15,'icon-baogao','/test-result-report','测试报告',NULL,0,'2023-10-27 03:23:32',NULL,10),(17,'icon-shebei','/device','设备管理',NULL,0,'2023-10-27 03:25:02',NULL,13),(18,'icon-shebei','/device','设备信息',17,1,'2023-10-27 03:25:22',NULL,121),(19,'icon-shebeiguanli','/support-device','设备维护',17,1,'2023-10-27 03:25:22',NULL,122),(20,'icon-xitongguanli','/maintain','系统管理',NULL,0,'2023-10-27 03:25:22',NULL,14),(21,'icon-pingtaiguanli','/application','平台管理',NULL,NULL,'2023-10-27 03:26:48',NULL,14),(22,'icon-guzhangxian','/fault-base','故障库',NULL,0,'2023-10-27 03:27:10',NULL,15),(23,'icon-yibiaopan','/dashboard','仪表盘',NULL,0,'2023-11-14 03:01:18',NULL,1),(24,'event_available','/test-check-sign-list','审签列表',5,1,'2023-11-14 06:24:50',NULL,41),(25,'event_available','/suite-sign-list','清单审签',5,1,'2023-11-14 06:25:07',NULL,42),(26,'event_available','/test-check-sign-history','审签历史',5,1,'2023-11-14 06:25:24',NULL,43),(27,'default.icon','/target','飞机信息',20,1,'2023-11-21 08:22:14',NULL,133),(28,'default.icon','/target-status','成品分析',20,1,'2023-11-21 08:24:19',NULL,134),(29,'default.icon','/people-status','人员管理',20,1,'2023-11-21 08:24:19',NULL,135),(30,'default.icon','/metadata','数据配置',20,1,'2023-11-21 08:24:55',NULL,131),(31,'default.icon','/maintain','系统维护',20,1,'2023-11-21 08:26:26',NULL,132),(32,'default.icon','/user','用户管理',21,1,'2023-11-21 08:28:13',NULL,141),(34,'default.icon','/operation-log','操作日志',21,1,'2023-11-21 08:28:13',NULL,143),(35,'icon-shebei','/deviceRecord','设备数据',NULL,0,'2024-03-24 19:08:25',NULL,11);
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
