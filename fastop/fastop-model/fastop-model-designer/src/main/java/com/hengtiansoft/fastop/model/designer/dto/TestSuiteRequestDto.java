package com.hengtiansoft.fastop.model.designer.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestSuiteRequestDto {

    private Integer sourceSuiteId;

    private Integer targetSuiteId;

    private String suiteName;

    private String suiteDesc;

    private Integer subjectId;

    private String subjectName;

    private Integer entityStructId;

    private List<Integer> funIds;

    private Boolean military;

    private String approveAssign;

    private Integer testBaseId;

}
