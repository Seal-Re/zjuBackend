-- 操作日志表（用户关键操作审计）
USE autosys_1014;

CREATE TABLE IF NOT EXISTS `operation_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `operator_id` varchar(64) DEFAULT NULL COMMENT '操作人ID',
  `operator_name` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `module` varchar(64) DEFAULT NULL COMMENT '模块：测试计划/模块库/清单库/测试执行等',
  `action` varchar(32) DEFAULT NULL COMMENT '动作：创建/修改/删除/派发/开始/暂停等',
  `target_type` varchar(32) DEFAULT NULL COMMENT '对象类型：计划/模块/清单等',
  `target_id` varchar(64) DEFAULT NULL COMMENT '对象ID',
  `detail` varchar(500) DEFAULT NULL COMMENT '详情描述',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_module_action` (`module`, `action`),
  KEY `idx_operator` (`operator_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志-军检审计';
