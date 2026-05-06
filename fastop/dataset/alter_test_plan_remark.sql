-- TestPlan.remark 字段：原 entity 缺失，UI 输入备注被静默丢弃。
-- 增量补列；幂等检查避免重复执行。
SET @col_exists := (
    SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'test_plan'
      AND COLUMN_NAME = 'remark'
);
SET @ddl := IF(@col_exists = 0,
    'ALTER TABLE test_plan ADD COLUMN remark VARCHAR(500) DEFAULT NULL COMMENT ''计划备注'' AFTER for_record_data',
    'SELECT 1');
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
