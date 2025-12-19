package com.hengtiansoft.fastop.service.designer.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionModule;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionStep;

import java.util.List;

public interface TestFunctionStepService {

    Response add(TestFunctionStep testFunctionStep);

    Response update(TestFunctionStep testFunctionStep);

    Response delete(Integer StepId);

    Response getByStepId(Integer StepId);

    Response getByCaseId(Integer funId);

    Response listAll();

    List<TestFunctionStep> getUpdateAll();

    Response deletePhy(Integer stepId);

    Response updateByCaseIds(List<Integer> testFunctionCaseIds);
}
