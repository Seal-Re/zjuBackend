package com.hengtiansoft.fastop.service.planner.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.utils.ExeStepCommand;

public interface ExeStepService {

    int deleteExeStep(String exeFunctionId);

    void conveyTestStep2ExeStep(Integer funId, String exeFunctionId);

    Response listExeSteps(String exeFunctionId);
    Response updateStepExeToPause(String exeFunctionId);
    Response updateStepStatusByOption(String exeStepId, String option);
    Response doV1(ExeStepCommand exeStepCommand);
}
