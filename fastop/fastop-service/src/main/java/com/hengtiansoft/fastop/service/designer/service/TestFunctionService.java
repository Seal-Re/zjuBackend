package com.hengtiansoft.fastop.service.designer.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.dto.TestFunctionInfoRequestDto;
import com.hengtiansoft.fastop.model.designer.entity.TestFunction;

import java.util.Collection;
import java.util.Map;

public interface TestFunctionService {

    Response add(TestFunctionInfoRequestDto tFunctionInfo);

    Response update(TestFunctionInfoRequestDto tFunctionInfo);
    Response delete(Integer funId);
    Response listAll();
    Response getById(Integer funId);
    Response listByTestBaseId(Integer testBaseId);
    Response query(TestFunctionInfoRequestDto tFunctionInfo);
    Response check(Integer funId, String checkWorker, Integer level);
    Response getCheckTestFunction();

    Integer countMilitaryByFunId(Integer funId);
    Integer countKeyProcessByFunId(Integer funId);

    Map<Integer, TestFunction> getFunctionsByIds(Collection<Integer> funIds);

    // Added for TestSuite sync creation logic
    void copyTestFunctionForSuite(Integer funId, Integer targetSuiteId, Integer funOrder);
}
