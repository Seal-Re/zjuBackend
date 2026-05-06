-- Table structure for table `test_caution`
--

DROP TABLE IF EXISTS `test_caution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_caution` (
  `test_caution_id` int NOT NULL AUTO_INCREMENT,
  `caution_content` varchar(255) DEFAULT NULL,
  `subject_id` int NOT NULL,
  `subject_name` varchar(60) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`test_caution_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_caution`
--

LOCK TABLES `test_caution` WRITE;
/*!40000 ALTER TABLE `test_caution` DISABLE KEYS */;
INSERT INTO `test_caution` VALUES (1,'通电时无关人员不要上机，严禁乱动机上配电设备',1,'飞控',0,'2019-10-09 12:59:00','2020-08-21 11:42:23','admin','admin'),(2,'使用地面交直流电源车对飞机供电时，其功率和品质因素调整符合机上电源使用要求（直流电源：28.5V、交流电源：115V 400HZ）后方可向机上供电',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(3,'严禁带电插拔插头和带电拆装导线，防止系统短路',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(4,'通电时出现故障，必须立即断电，记下故障现象。在没有查清故障原因和排除故障之前，不允许对该系统进行通电。在确保成品安全的条件下，允许用更换成品的方法来分析、查找故障',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(5,'试验中前起落架及舱门、左/右主起落架及舱门、襟/缝翼、副翼、扰流板、方向舵、升降舵、水平安定面、襟缝翼、左/右登机门、后货舱门、机组逃逸门、冲压空气涡轮（RAT）、左/右尾撑及舱门运动区域及周围需有专人监控',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(6,'飞机顶起时，各活动翼面活动区域不允许有尾翼工作平台等障碍物；飞机落地时，各活动翼面运动区域的工作平台等障碍物必须撤离',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(7,'试验中禁止扳动驾驶舱前控制面板上的起落架收放手柄和左右操纵台上的转弯手轮',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(8,'试验中专人负责操作驾驶舱的驾驶柱/盘、脚蹬、中央操纵台上的水平安定面配平手柄、减速操纵手柄、襟缝翼操作手柄及操控控制按钮',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(9,'试验中禁止驾驶舱顶控板上的扳动/按压液压开关',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(10,'人员到位，且处于安全区域，不能在舵面及机构可动范围内',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(11,'除不影响操纵机构及舵面运动的测试设备外，安装支架、保护装置以及定位设备等，不能在舵面及机构可动范围内',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(12,'拔出机械操纵机构的中立销，确认驾驶舱操纵机构限位钉处于正常限位位置',1,'飞控',0,'2019-10-09 12:59:00','2019-10-09 12:59:00','admin','admin'),(13,'目视检查操纵机构及机械备份钢索可平滑工作，舵面运动无限位（或限制）装置',1,'飞控',0,'2019-10-09 12:59:00','2020-08-20 16:50:25','admin','system'),(16,'人员到位，且处于安全区域',2,'电气',0,'2021-01-20 15:29:54','2021-01-20 15:29:54','admin','admin');
/*!40000 ALTER TABLE `test_caution` ENABLE KEYS */;
UNLOCK TABLES;

--
