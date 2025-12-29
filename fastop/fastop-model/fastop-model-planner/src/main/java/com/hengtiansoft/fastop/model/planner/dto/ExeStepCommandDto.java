package com.hengtiansoft.fastop.model.planner.dto;

import com.hengtiansoft.fastop.model.planner.entity.ExeStep;
import lombok.Data;

@Data
public class ExeStepCommandDto {

    ExeStep exeStep;

    String command;

    String deviceId;

}
