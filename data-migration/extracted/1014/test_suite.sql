-- Table structure for table `test_suite`
--

DROP TABLE IF EXISTS `test_suite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_suite` (
  `suite_id` int unsigned NOT NULL AUTO_INCREMENT,
  `suite_name` varchar(60) NOT NULL,
  `suite_desc` varchar(255) DEFAULT NULL,
  `version` int NOT NULL COMMENT '版本',
  `plane_effect_min` int NOT NULL COMMENT '架次有效性最小值',
  `plane_effect_max` int NOT NULL COMMENT '架次有效性最大值',
  `proofer` varchar(45) DEFAULT NULL COMMENT '审签校对者',
  `approver` varchar(45) DEFAULT NULL COMMENT '审签批准者',
  `submitter` varchar(45) DEFAULT NULL COMMENT '清单提交者',
  `test_base_id` int NOT NULL COMMENT '关联测试清单',
  `list_appr_status` int DEFAULT '0' COMMENT '清单审签状态',
  `military` tinyint(1) DEFAULT '0',
  `key_process` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是关键工序测试集 0:不是；1:是',
  `appr_chain` varchar(45) DEFAULT NULL COMMENT '标识用于标记此次审签链',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `mesdce_code` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`suite_id`)
) ENGINE=InnoDB AUTO_INCREMENT=346 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_suite`
--

LOCK TABLES `test_suite` WRITE;
/*!40000 ALTER TABLE `test_suite` DISABLE KEYS */;
INSERT INTO `test_suite` VALUES (345,'测试集','用于验证更新状态',0,100,500,'1','2','3',42,1,0,1,'555',0,NULL,NULL,'admin_user','admin_user','MESDCE_001');
/*!40000 ALTER TABLE `test_suite` ENABLE KEYS */;
UNLOCK TABLES;

--
