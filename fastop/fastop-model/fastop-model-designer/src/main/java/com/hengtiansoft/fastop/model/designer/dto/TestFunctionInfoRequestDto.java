package com.hengtiansoft.fastop.model.designer.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TestFunctionInfoRequestDto {

    private Integer num;

    private Integer funId;

    private String funName;

    private Integer expectTime;

    private Integer securityLevel;

    private Integer planeEffectMax;

    private Integer planeEffectMin;

    private Integer testBaseId;

    private Integer version;

    private Integer flowVersion;

    private String versionDescription;

    private String approveComment;

    private String subjectSourceId;

    private List<List<String>> otherTechFiles;

    private List<List<String>> devicePool;

    private String sourceVersion;

    private String testCautionId;

    private String caution;

    private String dependsOn;

    private Integer changeFlag;

    private Integer approveStatus;

    private String apprChain;

    private String usingBy;

    private Boolean deleted;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    private List<Integer> cautionIds;

    private Integer keyProCount;

    private String comment;

    private String historyVersionLine;

    private Boolean military;

    private String syncPlan;

    private String designer;

    private String proofer;

    private String verifier;

    private String checker;

    private String qualityer;

    private String approver;

    private String detectId;

}
