package com.hengtiansoft.fastop.service.planner.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;

public interface ExeStepService {

    int deleteExeStep(String exeFunctionId);

    void conveyTestStep2ExeStep(Integer funId, String exeFunctionId);

}
