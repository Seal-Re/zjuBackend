package com.hengtiansoft.fastop.service.designer.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionModule;

import java.util.List;

public interface TestFunctionModuleService {

    Response add(TestFunctionModule testFunctionModule);

    Response update(TestFunctionModule testFunctionModule);

    Response delete(Integer ModuleId);

    Response getByModuleId(Integer ModuleId);

    Response getByFunId(Integer funId);

    Response listAll();

    List<TestFunctionModule> getUpdateAll();

    Response deletePhy(Integer moduleId);
}
