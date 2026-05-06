-- Table structure for table `software_version`
--

DROP TABLE IF EXISTS `software_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `software_version` (
  `id` int NOT NULL AUTO_INCREMENT,
  `notice` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `software` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_info` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updater` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `software_version`
--

LOCK TABLES `software_version` WRITE;
/*!40000 ALTER TABLE `software_version` DISABLE KEYS */;
INSERT INTO `software_version` VALUES (2,'初始版本','3.0.0','部署数字化军检系统','','2025-02-26 09:14:43'),(3,'进行功能回归测试','3.0.1','修改了一些权限问题','','2025-02-26 09:15:49'),(4,'进行功能回归测试','3.0.2','上机验证实验，修复测试bug','','2025-03-11 11:25:17'),(5,'进行回归测试','3.0.3','1、修复视频抽引抽流问题、现场监控抽流问题\n2、完成供电图像识别问题；供电模拟器用例设计完成，未上机验证\n3、飞控扭力杆上机验证完成、舵面偏角测量上机验证完成、（图像算法新增起飞复飞算法开发完成未验证，组合算法识别开发完成未验证）\n4、航电图像识别算法验证、算法问题修改完成未验证，算法组合开发完成未验证\n5、现场监控、视频抽引根据时间录像验证完成；\n6、113演示平台数据改造完成，数据跟随架次更新，现场监控视频开发完成','','2025-07-06 14:59:11'),(6,'回归测试','3.0.4','1.测试质检增加专业筛选。（已完成）\n2.3个用例都已检验完，但提示还有未检验的子用例，不能结束，后台看操作成功。（已完成）\n3.模块执行完成，不能结束（已改代码）。\n4.测试设计中删除部分步骤、用例，在执行中显示序号未更新。（已完成）\n5.建议测试设计中增加序号。（已完成）\n6.仅操作的用例，有的质检时有检验，有的无检验。（已改好）\n7.测试设计在步骤画面页，上下滑动，返回操作需完善（已完善）。\n8.大屏模块统计中取消未提交、执行成功（已完成）。\n9.大屏统计左上角总装测试和J检测试数据统计分开。（已完成）\n10.首页5个模块不滚动，亮框取消（已完成）。\n11.更改为模块复测率（已完成）。\n12.首页剩余架次数量（已完成）。\n13.首页选中专业的亮框更改（已完成）。\n14.增加手动添加剩余架次的页面（已完成）\n15.绘制曲线点击显示详情','','2025-09-21 15:49:38'),(7,'回归测试','3.0.5','1、质检突然不显示下一用例按钮（解决）\n2、质检异常数据标红（解决）\n3、创建军代表账号（党），用47架之前的供电数据走一遍流程（流程走完）\n4、任务测试设计指令选择和测试指挥画面确认（完成）\n5、测试质检详情页点击检验后，返回页面刷新都最上面，改成校验后保持在当前位置（完成）\n6、测试质检详情页返回后，质检列表没有保持之前的架次信息（完成）\n7、测试质检专业联动需要联动到计划列表（完成）\n8、测试质检页面，仅操作的结果为无数据，应该为/（完成）\n9、升版只从最新版本升（完成）\n10、多判据选值操作中，每个结果都应有结果（完成）\n11、测试指挥中图片和视频的图标应有区分（完成）\n12、测试指挥页面，说明和操作都显示测试名称，实际操作应显示操作、目的等内容（完成）\n13、测试设计详情页面弹窗支持可拖动（完成）\n14、测试设计页面相关表单标题，与测试指挥页面保持一致（已完成）\n15、测试质检页面多判据图片查看问题（本身支持翻页已告知涵中）\n16、测试指挥页面多判据图片打不开问题（已解决）\n17、测试指挥中，操作会出现0（已完成）\n18、测试设计修改人没有随着人员更改（待完成）\n19、测试指挥页面屏幕有时显示有时不显示（已完成）\n20、质检页面增加绘制曲线按钮（已完成）\n21、控制设备显示按钮（已完成）','','2025-11-26 15:35:15');
/*!40000 ALTER TABLE `software_version` ENABLE KEYS */;
UNLOCK TABLES;

--
