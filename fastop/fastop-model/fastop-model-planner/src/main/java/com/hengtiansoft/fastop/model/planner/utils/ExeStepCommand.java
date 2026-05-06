package com.hengtiansoft.fastop.model.planner.utils;

import lombok.Data;

@Data
public class ExeStepCommand {

    private String exeStepId;

    private String deviceId;

    private String command;

    private String url;

}
