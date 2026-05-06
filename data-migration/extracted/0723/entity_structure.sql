-- Table structure for table `entity_structure`
--

DROP TABLE IF EXISTS `entity_structure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entity_structure` (
  `entity_struct_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `entity_model_id` int NOT NULL COMMENT '关联大类',
  `entity_model_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机型名称',
  `entity_struct_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `entity_struct_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建操作用户的id',
  `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新操作用户的id',
  PRIMARY KEY (`entity_struct_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COMMENT='测试目标构型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity_structure`
--

LOCK TABLES `entity_structure` WRITE;
/*!40000 ALTER TABLE `entity_structure` DISABLE KEYS */;
INSERT INTO `entity_structure` VALUES (18,14,'xxx-2','A','xxx-2-A',0,'2020-11-13 15:18:44','2020-11-13 15:18:44','admin','admin'),(19,14,'xxx-2','B','xxx-2-B',0,'2020-11-13 15:18:58','2020-11-13 15:18:58','admin','admin'),(20,14,'xxx-2','R','YR',0,'2020-11-13 15:19:08','2020-11-13 15:19:08','admin','admin'),(21,17,'EA','A','EA-A',0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `entity_structure` ENABLE KEYS */;
UNLOCK TABLES;

--
