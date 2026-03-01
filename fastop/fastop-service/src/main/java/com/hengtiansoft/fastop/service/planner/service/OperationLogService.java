package com.hengtiansoft.fastop.service.planner.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.utils.OperationLog;

import java.util.Date;

/**
 * 操作日志服务：记录与查询用户关键操作
 */
public interface OperationLogService {

    /** 记录一条操作日志 */
    Response record(OperationLog log);

    /** 分页查询操作日志 */
    Response list(String operatorName, String module, String action, Date startTime, Date endTime, int page, int size);
}
