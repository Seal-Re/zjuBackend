package com.hengtiansoft.fastop.model.designer.dto;

import lombok.Data;

@Data
public class FunctionSuiteDto {

    private Integer id;

    private Integer funId;

    private String funName;

    private Integer num;

    private Integer version;

    private Integer planeEffectMin;

    private Integer planeEffectMax;

    private String versionDescription;

    private Boolean military;

    private Integer keyProCount;

    private Integer funOrder;

    private String dependsOn;

    private Integer suiteId;

    private boolean deleted;


}