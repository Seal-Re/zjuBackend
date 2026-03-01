package com.hengtiansoft.fastop.model.planner.utils;

import lombok.Data;

import java.util.Date;

/**
 * 操作日志（用户关键操作审计：创建/修改/删除计划、派发、开始/暂停等）
 */
@Data
public class OperationLog {
    private Long id;
    private String operatorId;
    private String operatorName;
    private String module;
    private String action;
    private String targetType;
    private String targetId;
    private String detail;
    private String ip;
    private Date createTime;
}
