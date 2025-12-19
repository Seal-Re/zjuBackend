package com.hengtiansoft.fastop.service.planner.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;

public interface ExeFunctionService {

    Response getExeFunctionInExeListByPlanId(String planId);

    Response getExeFunctionByFunctionId(String functionId);

    boolean conveyTestFunction2ExeFunction(Integer suiteId, String planId);

    int deleteExeFunction(String planId);
}
