package com.hengtiansoft.fastop.model.designer.dto;

import lombok.Data;

import java.util.List;

@Data
public class FunctionSuiteDeleteDto {

    Integer suiteId;

    List<FunctionSuiteDto> functionSuiteDtos;

}
