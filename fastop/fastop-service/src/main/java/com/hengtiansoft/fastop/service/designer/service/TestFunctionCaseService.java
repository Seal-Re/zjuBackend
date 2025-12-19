package com.hengtiansoft.fastop.service.designer.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionCase;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionModule;

import java.util.List;

public interface TestFunctionCaseService {

    Response add(TestFunctionCase testFunctionCase);

    Response update(TestFunctionCase testFunctionCase);

    Response delete(Integer caseId);

    Response getByCaseId(Integer caseId);

    Response getByModuleId(Integer moduleId);

    Response listAll();

    List<TestFunctionCase> getUpdateAll();

    Response deletePhy(Integer caseId);

    Response updateByModuleIds(List<Integer> testFunctionModuleIds);
}
