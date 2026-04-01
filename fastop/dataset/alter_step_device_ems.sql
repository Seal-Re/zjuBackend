-- 模块步骤：设备 topic + 示例报文 + 填参（执行派发时写入 exe_step.command_data）
ALTER TABLE test_function_step
  ADD COLUMN step_command_example TEXT NULL COMMENT '设备管理返回的示例报文 JSON' AFTER step_obj,
  ADD COLUMN step_command_params TEXT NULL COMMENT '可填字段键值 JSON（点分路径->值）' AFTER step_command_example;
