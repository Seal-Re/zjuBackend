package com.hengtiansoft.fastop.model.designer.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TestSuiteRequestDto {

    private Integer suiteId;

    private String suiteName;

    private String suiteDesc;

    private Integer version;

    private Integer planeEffectMin;

    private Integer planeEffectMax;

    private String proofer;

    private String approver;

    private String submitter;

    private Integer testBaseId;

    private Integer listApprStatus;

    private Boolean military;

    private Boolean keyProcess;

    private String apprChain;

    private Boolean deleted;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    private String mesdceCode;

    private List<Integer> funIds;

}
