-- Table structure for table `entity_model`
--

DROP TABLE IF EXISTS `entity_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entity_model` (
  `entity_model_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `entity_model_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `entity_model_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建操作用户的id',
  `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新操作用户的id',
  PRIMARY KEY (`entity_model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COMMENT='测试目标大类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity_model`
--

LOCK TABLES `entity_model` WRITE;
/*!40000 ALTER TABLE `entity_model` DISABLE KEYS */;
INSERT INTO `entity_model` VALUES (14,'xxx-2','xxx-2',0,'2020-11-13 15:18:23','2020-11-13 15:18:23','admin','admin'),(17,'EA','EA',0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `entity_model` ENABLE KEYS */;
UNLOCK TABLES;

--
