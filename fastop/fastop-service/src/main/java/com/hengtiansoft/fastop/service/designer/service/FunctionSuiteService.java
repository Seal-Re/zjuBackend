package com.hengtiansoft.fastop.service.designer.service;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.model.designer.dto.FunSuiteIdConnectDto;
import com.hengtiansoft.fastop.model.designer.dto.FunctionSuiteDeleteDto;
import com.hengtiansoft.fastop.model.designer.dto.FunctionSuiteDto;
import com.hengtiansoft.fastop.model.designer.entity.FunctionSuite;

import java.util.List;

public interface FunctionSuiteService {

    Response listAllFunctionSuite();

    Response createFunctionSuite(FunSuiteIdConnectDto funSuiteIdConnectDto);

    Response deleteFunctionSuite(FunctionSuiteDeleteDto functionSuiteDeleteDto);

    Integer countMilitaryBySuite(Integer suiteId);

    Integer countKeyProcessBySuite(Integer suiteId);

    List<FunctionSuite> listFunctionSuiteBySuite(int suiteId);

    List<FunctionSuiteDto> listDtoBySuiteWithExample(Integer suiteId);
}
