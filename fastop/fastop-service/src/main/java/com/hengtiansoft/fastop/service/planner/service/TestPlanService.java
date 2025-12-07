package com.hengtiansoft.fastop.service.planner.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanDelBatchDto;
import com.hengtiansoft.fastop.model.planner.dto.TestPlanRequestDto;

public interface TestPlanService {

    Response createTestPlan(TestPlanRequestDto testPlanRequestDto);
    Response updateTestPlan(TestPlanRequestDto testPlanRequestDto);

    Response deleteSingleTestPlan(String planId);
    Response deleteBatchTestPlan(TestPlanDelBatchDto testPlanDelBatchDto);


    Response listAll();

    Response dispatchPlan(String planId);
    Response startPlan(String planId);
    Response pausePlan(String planId);
}
