-- 操作日志表（若数据库未包含此表，请执行本脚本）
-- 与 OperationLogMapper.xml / OperationLog 实体字段一致

CREATE TABLE IF NOT EXISTS `operation_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `operator_id` varchar(64) DEFAULT NULL COMMENT '操作人ID',
  `operator_name` varchar(100) DEFAULT NULL COMMENT '操作人姓名',
  `module` varchar(64) DEFAULT NULL COMMENT '模块（如：测试计划、模块库、清单库、测试执行）',
  `action` varchar(64) DEFAULT NULL COMMENT '动作（如：创建、修改、删除、派发、开始、暂停）',
  `target_type` varchar(64) DEFAULT NULL COMMENT '对象类型',
  `target_id` varchar(128) DEFAULT NULL COMMENT '对象ID',
  `detail` varchar(500) DEFAULT NULL COMMENT '详情',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_module_action` (`module`,`action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志';
