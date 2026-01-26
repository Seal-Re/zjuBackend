package com.hengtiansoft.fastop.model.planner.utils;

import lombok.Data;
import java.util.Date;

@Data
public class ExeLog {
    private String logId;
    private String stepId;
    private String content;
    private Date createTime;
}
