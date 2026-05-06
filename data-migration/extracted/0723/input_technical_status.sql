-- Table structure for table `input_technical_status`
--

DROP TABLE IF EXISTS `input_technical_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `input_technical_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `entity_model_id` int unsigned DEFAULT NULL,
  `entity_struct_id` int unsigned DEFAULT NULL,
  `start_entity_id` int DEFAULT NULL,
  `end_entity_id` int DEFAULT NULL,
  `area_id` int unsigned DEFAULT NULL,
  `subject_id` int unsigned DEFAULT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  `finished_set_number` varchar(255) DEFAULT NULL,
  `finished_set_name` varchar(255) DEFAULT NULL,
  `airborne_equipment_model` varchar(255) DEFAULT NULL,
  `airborne_equipment_name` varchar(255) DEFAULT NULL,
  `installation_quantity` int DEFAULT NULL,
  `test_joint_trial` varchar(255) DEFAULT NULL,
  `contractor` varchar(255) DEFAULT NULL,
  `software_layer_version` varchar(255) DEFAULT NULL,
  `software_item_name` varchar(255) DEFAULT NULL,
  `software_item_number` varchar(255) DEFAULT NULL,
  `software_version` varchar(255) DEFAULT NULL,
  `software_research_unit` varchar(255) DEFAULT NULL,
  `test_func_group_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entity_model_id` (`entity_model_id`),
  KEY `entity_struct_id` (`entity_struct_id`),
  KEY `area_id` (`area_id`),
  KEY `subject_id` (`subject_id`),
  CONSTRAINT `input_technical_status_ibfk_1` FOREIGN KEY (`entity_model_id`) REFERENCES `entity_model` (`entity_model_id`),
  CONSTRAINT `input_technical_status_ibfk_2` FOREIGN KEY (`entity_struct_id`) REFERENCES `entity_structure` (`entity_struct_id`),
  CONSTRAINT `input_technical_status_ibfk_3` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`),
  CONSTRAINT `input_technical_status_ibfk_4` FOREIGN KEY (`subject_id`) REFERENCES `test_subject` (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `input_technical_status`
--

LOCK TABLES `input_technical_status` WRITE;
/*!40000 ALTER TABLE `input_technical_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `input_technical_status` ENABLE KEYS */;
UNLOCK TABLES;

--
