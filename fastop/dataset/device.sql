-- 设备主数据表 (frontend/src/api/device.ts DeviceDto 后端表)
-- status: 0=离线 1=在线 2=故障 3=维护
-- 软删除：deleted=1 表示已删除，所有查询都带 deleted=0 过滤
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `device` (
  `id`          VARCHAR(36)   NOT NULL COMMENT '设备主键 UUID',
  `code`        VARCHAR(64)   NOT NULL COMMENT '业务编码（唯一）',
  `name`        VARCHAR(128)  NOT NULL COMMENT '设备名称',
  `type`        VARCHAR(64)   DEFAULT NULL COMMENT '设备类型',
  `status`      INT           NOT NULL DEFAULT 0 COMMENT '0离线 1在线 2故障 3维护',
  `description` VARCHAR(500)  DEFAULT NULL COMMENT '描述',
  `deleted`     TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `created_at`  DATETIME      DEFAULT NULL COMMENT '创建时间',
  `updated_at`  DATETIME      DEFAULT NULL COMMENT '更新时间',
  `created_by`  VARCHAR(64)   DEFAULT NULL COMMENT '创建人 (UserContextHolder)',
  `updated_by`  VARCHAR(64)   DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_device_code_active` (`code`, `deleted`),
  KEY `idx_device_type` (`type`),
  KEY `idx_device_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备主数据';

SET FOREIGN_KEY_CHECKS = 1;
