package com.hengtiansoft.fastop.service.designer.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionStep;

public interface TestFunctionStepService {

    Response add(TestFunctionStep testFunctionStep);

    Response update(TestFunctionStep testFunctionStep);

    Response delete(Integer StepId);

    Response getByStepId(Integer StepId);

    Response getByCaseId(Integer funId);

    Response listAll();

    Response checkStep(Integer StepId);

    Response getAllStepsByFunctionId(Integer functionId);

}
