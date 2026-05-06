package com.hengtiansoft.fastop.model.planner.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "计划编号不能为空")
    private String planNumber;

    private Integer planRound;

    @NotBlank(message = "计划名称不能为空")
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
