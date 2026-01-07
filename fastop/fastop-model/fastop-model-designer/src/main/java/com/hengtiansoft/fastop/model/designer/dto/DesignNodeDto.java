package com.hengtiansoft.fastop.model.designer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("设计节点树DTO")
public class DesignNodeDto {
    @ApiModelProperty("节点唯一Key (e.g. module_1)")
    private String key;

    @ApiModelProperty("真实ID")
    private Integer id;

    @ApiModelProperty("节点名称")
    private String name;

    @ApiModelProperty("节点类型: MODULE, CASE, STEP")
    private String type;

    @ApiModelProperty("子节点")
    private List<DesignNodeDto> children;

    @ApiModelProperty("修改人")
    private String changeUser;

    // Step Only fields
    @ApiModelProperty("操作动作")
    private String operation;

    @ApiModelProperty("操作对象")
    private String obj;

    @ApiModelProperty("操作目的")
    private String purpose;

    @ApiModelProperty("描述")
    private String description;
}
