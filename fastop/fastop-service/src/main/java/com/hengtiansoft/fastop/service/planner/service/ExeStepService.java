package com.hengtiansoft.fastop.service.planner.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.utils.ExeLog;
import com.hengtiansoft.fastop.model.planner.utils.ExeStepCommand;

public interface ExeStepService {

    int deleteExeStep(String exeFunctionId);

    void conveyTestStep2ExeStep(Integer funId, String exeFunctionId);

    Response listExeSteps(String exeFunctionId);
    Response updateStepExeToPause(String exeFunctionId);
    Response updateStepStatusByOption(String exeStepId, String option);
    Response doV1(ExeStepCommand exeStepCommand);

    /** 保存步骤执行日志（军检审计落库） */
    Response saveLog(ExeLog exeLog);

    /** 分页查询执行日志（按步骤、计划、时间筛选） */
    Response listExeLogs(String stepId, String planId, java.util.Date startTime, java.util.Date endTime, int page, int size);
}
