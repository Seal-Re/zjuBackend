-- 为 exe_log 表增加 plan_id 字段（便于按计划查询执行日志）。若已存在该列可忽略报错。
USE autosys_1014;

ALTER TABLE exe_log ADD COLUMN plan_id varchar(36) DEFAULT NULL COMMENT '计划ID' AFTER step_id;
ALTER TABLE exe_log ADD INDEX idx_exe_log_plan_id (plan_id);
