package com.hengtiansoft.fastop.service.designer.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.dto.TestSuiteRequestDto;
import com.hengtiansoft.fastop.model.designer.entity.TestSuite;

public interface TestSuiteService {

    Response add(TestSuite testSuite);

    // Added createTestSuite to Interface to match Impl
    boolean createTestSuite(TestSuiteRequestDto nTestSuite);

    Response update(TestSuiteRequestDto testSuiteRequestDto);

    Response delete(Integer suiteId);

    Response getById(Integer suiteId);

    Response listByBaseId(Integer baseId);

    Response listAll();

    Response check(Integer suiteId, String checkWorker, Integer level);

    boolean isCanEdit(TestSuite tSuite);

    //RPC 2 local
    TestSuite getTestSuiteInfoById(Integer suiteId);

    boolean updateTestSuite(TestSuite suite);

    boolean reviewSuiteSpecial(Integer suiteId);

    boolean updateSuiteListAppStatusToUnApp(TestSuite tSuite);

}
