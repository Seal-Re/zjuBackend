package com.hengtiansoft.fastop.model.planner.dto;

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
@ApiModel("设备管理服务 topic + 示例报文")
public class DeviceTopicsPayload {

    @ApiModelProperty("topic 列表（eventType）")
    private List<String> topics;

    @ApiModelProperty("示例 JSON 对象，用于推导可填字段")
    private Object example;
}
