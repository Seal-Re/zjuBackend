-- 执行步骤日志表（军检审计）
-- 若库中无此表，请执行本脚本
USE autosys_1014;

CREATE TABLE IF NOT EXISTS `exe_log` (
  `log_id` varchar(36) NOT NULL COMMENT '日志主键',
  `step_id` varchar(36) DEFAULT NULL COMMENT '关联执行步骤ID',
  `plan_id` varchar(36) DEFAULT NULL COMMENT '计划ID，便于按计划查询',
  `content` text COMMENT '日志内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `idx_step_id` (`step_id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='执行步骤日志-军检审计';
