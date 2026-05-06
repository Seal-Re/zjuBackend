-- Table structure for table `device_ep_map`
--

DROP TABLE IF EXISTS `device_ep_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device_ep_map` (
  `device_ep_map_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `area_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联仓位',
  `device_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联设备',
  `ip_addr` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ip 地址',
  `ip_port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ip 地址',
  `is_ready` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可用：0-断开；1-连接',
  `status` int DEFAULT '0' COMMENT '0-无效,1-可用,2-故障',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-启用；1-删除',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建操作用户的id',
  `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新操作用户的id',
  PRIMARY KEY (`device_ep_map_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='设备终端映射表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_ep_map`
--

LOCK TABLES `device_ep_map` WRITE;
/*!40000 ALTER TABLE `device_ep_map` DISABLE KEYS */;
INSERT INTO `device_ep_map` VALUES ('82d3ac86-c6c5-4e99-94c0-d6fc62efbf69','1','bfca1c35-1944-4ddb-8d86-748ed45176a2','192.152.12.11','12',1,1,0,'2019-11-08 14:19:29','2019-11-08 14:26:59','admin','admin'),('e980ca38-27c8-49e4-9ea6-28a2fddd939f','1','13ec4020-30fb-4e8e-bf59-af4bf437618f','192.157.2.123','13',0,1,0,'2019-11-08 14:16:54','2019-11-08 14:27:14','admin','admin');
/*!40000 ALTER TABLE `device_ep_map` ENABLE KEYS */;
UNLOCK TABLES;

--
