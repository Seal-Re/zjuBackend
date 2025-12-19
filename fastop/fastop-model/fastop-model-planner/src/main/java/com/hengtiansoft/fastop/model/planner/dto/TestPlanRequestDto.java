package com.hengtiansoft.fastop.model.planner.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TestPlanRequestDto {

    private String planId;

    private Integer entityStructId;

    private Integer entityId;

    private Integer subjectId;

    private Integer funGroupId;

    private List<Integer> funGroupIds;

    private Integer suiteId;

    private Date planStartTime;

    private Date planEndTime;

    private Integer status;

    private String planNumber;

    private Integer planRound;

    private String planName;

    private Integer areaId;

    private Boolean military;

    private String dispatcherId;

    private String commanderId;

    private String management;
    @ApiModelProperty("该测试计划主要用于测试设备记录,0表示不是1表示是")
    private Integer forRecordData;

    private String remark;

}
