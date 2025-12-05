package com.hengtiansoft.fastop.service.designer.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.entity.TestFunctionModule;

public interface TestFunctionModuleService {

    Response add(TestFunctionModule testFunctionModule);

    Response update(TestFunctionModule testFunctionModule);

    Response delete(Integer ModuleId);

    Response getByModuleId(Integer ModuleId);

    Response getByFunId(Integer funId);

    Response listAll();

    Response checkByModuleId(Integer ModuleId);

    Boolean deleteCase(Integer ModuleId);

}
