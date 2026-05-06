-- Table structure for table `buttonlocation1`
--

DROP TABLE IF EXISTS `buttonlocation1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buttonlocation1` (
  `areaNumber` int NOT NULL DEFAULT '0' COMMENT '区域编号',
  `buttonNumber` int NOT NULL COMMENT '按钮在区域内的编号',
  `name` varchar(20) NOT NULL COMMENT '按钮名',
  `type` enum('Point','Toggle','RotaryKnob','Rocker') DEFAULT NULL COMMENT '按钮类型',
  `gears` int DEFAULT NULL COMMENT '按钮总挡位数',
  `nowGear` int DEFAULT NULL COMMENT '按钮现在在的挡位',
  `gearAngle` double DEFAULT NULL COMMENT '旋钮每档对应角度',
  `XPos` double DEFAULT '0' COMMENT '针对点动按钮和旋钮',
  `YPos` double DEFAULT '0' COMMENT '针对点动按钮和旋钮',
  `ZPos` double DEFAULT '0' COMMENT '针对点动按钮和旋钮',
  `Pos1X` double DEFAULT '0' COMMENT '针对船型开关和拨钮',
  `Pos1Y` double DEFAULT '0' COMMENT '针对船型开关和拨钮',
  `Pos1Z` double DEFAULT '0' COMMENT '针对船型开关和拨钮',
  `Pos2X` double DEFAULT '0' COMMENT '针对船型开关和拨钮',
  `Pos2Y` double DEFAULT '0' COMMENT '针对船型开关和拨钮',
  `Pos2Z` double DEFAULT '0' COMMENT '针对船型开关和拨钮',
  PRIMARY KEY (`buttonNumber`,`areaNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buttonlocation1`
--

LOCK TABLES `buttonlocation1` WRITE;
/*!40000 ALTER TABLE `buttonlocation1` DISABLE KEYS */;
INSERT INTO `buttonlocation1` VALUES (0,1,'无挡位旋钮','RotaryKnob',3,2,36,319.18,298.16,20,0,0,0,0,0,0),(1,1,'无档位顶控板','RotaryKnob',NULL,1,NULL,0,0,0,0,0,0,0,0,0),(0,2,'3档长条旋钮','RotaryKnob',3,2,45,377.5,57.5,20,0,0,0,0,0,0),(1,2,'3档长条旋钮顶控板','RotaryKnob',3,1,45,150,173,995,0,0,0,0,0,0),(0,3,'3档自锁船型开关','Rocker',3,2,0,0,0,0,239,128,5,239,105.8,5),(0,4,'3档拨钮','Toggle',3,1,0,0,0,0,235,193,30,275,196,30),(1,4,'三档拨钮','Toggle',3,2,0,0,0,0,0,0,0,0,0,0),(0,5,'方形点动开关','Point',2,2,0,44.7,147.3,12,0,0,0,0,0,0),(1,5,'方形点动开关','Point',2,2,0,301,-110,12,0,0,0,0,0,0),(0,6,'7档旋钮','RotaryKnob',7,1,12,125,120,20,0,0,0,0,0,0),(1,6,'方形点动开关','Point',2,1,NULL,301,-138,12,0,0,0,0,0,0),(0,7,'自锁三档船型开关','Rocker',3,1,NULL,0,0,0,-15,-232,3,-43.2,-232,3),(0,8,'三档拨子开关','Toggle',3,3,NULL,0,0,0,-75,-214,30,-75,-246,30),(1,8,'左侧拨钮','Toggle',3,1,NULL,0,0,0,0,0,0,0,0,0),(0,9,'方形点动开关','Point',2,3,NULL,120,231.5,11,0,0,0,0,0,0),(1,9,'中间拨钮','Toggle',3,1,NULL,0,0,0,0,0,0,0,0,0),(0,10,'自复位三档船型开关','Rocker',2,2,NULL,0,0,0,-15,-265,5,-43.2,-265,5),(1,10,'右侧拨钮','Toggle',3,1,NULL,0,0,0,0,0,0,0,0,0),(0,11,'二档拨子开关','Toggle',2,1,NULL,0,0,20,0,0,0,0,0,0),(1,11,'左3档旋钮','RotaryKnob',3,2,36,247,-34,0,0,0,0,0,0,0),(0,12,'方形点动开关','Point',2,1,NULL,120,265,12,0,0,0,0,0,0),(1,12,'中三档旋钮','RotaryKnob',3,2,36,247,-101,0,0,0,0,0,0,0),(0,13,'左侧L1','Toggle',3,2,0,0,0,0,0,0,0,0,0,0),(1,13,'右三档旋钮','RotaryKnob',3,2,36,247,-168,0,0,0,0,0,0,0),(0,14,'右侧L2','Toggle',3,2,NULL,0,0,0,0,0,0,0,0,0),(0,15,'整体L','Toggle',3,2,NULL,0,0,0,0,0,0,0,0,0),(0,16,'中间M','Toggle',3,1,NULL,0,0,0,0,0,0,0,0,0),(0,17,'右侧R','Toggle',7,2,NULL,0,0,0,0,0,0,0,0,0),(0,20,'推杆后面的点动按钮左侧','Point',2,1,NULL,575.2,207,12,0,0,0,0,0,0),(0,21,'推杆后面电动按钮右侧','Point',2,1,NULL,575.2,179,12,0,0,0,0,0,0);
/*!40000 ALTER TABLE `buttonlocation1` ENABLE KEYS */;
UNLOCK TABLES;

--
