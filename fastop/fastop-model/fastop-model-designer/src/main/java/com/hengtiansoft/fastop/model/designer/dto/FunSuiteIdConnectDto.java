package com.hengtiansoft.fastop.model.designer.dto;

import com.hengtiansoft.fastop.model.designer.entity.TestFunction;
import lombok.Data;

import java.util.List;

@Data
public class FunSuiteIdConnectDto {

    Integer suiteId;

    List<TestFunction> testFunctions;

}
