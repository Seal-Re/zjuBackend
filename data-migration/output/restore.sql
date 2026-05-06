-- Combined V2.5 restore bundle.
-- Apply against an empty database `autosys_1014`.
--   docker exec -i fastop-mysql mysql -uroot -p'Fastop@123' autosys_1014 < restore.sql
--
-- Order:
--   1. v25_schema.sql              — V2.5 base DDL (260302 with INSERTs stripped)
--   2. alter_step_device_ems.sql   — adds step_command_example/params to test_function_step
--   3. alter_test_plan_remark.sql  — adds remark to test_plan (idempotent)
--   4. device.sql                  — CREATE TABLE IF NOT EXISTS device
--   5. operation_log.sql           — CREATE TABLE IF NOT EXISTS operation_log
--   6. combined_v25_data.sql       — actual data: 0723 + 1014 (shifted)

SET FOREIGN_KEY_CHECKS = 0;
SET UNIQUE_CHECKS = 0;
SET SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO';

SOURCE v25_schema.sql;
SOURCE ../../fastop/dataset/alter_step_device_ems.sql;
SOURCE ../../fastop/dataset/alter_test_plan_remark.sql;
SOURCE ../../fastop/dataset/device.sql;
SOURCE ../../fastop/dataset/operation_log.sql;
SOURCE combined_v25_data.sql;

SET FOREIGN_KEY_CHECKS = 1;
SET UNIQUE_CHECKS = 1;
