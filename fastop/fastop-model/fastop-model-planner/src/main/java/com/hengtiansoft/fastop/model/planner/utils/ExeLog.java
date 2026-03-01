package com.hengtiansoft.fastop.model.planner.utils;

import lombok.Data;
import java.util.Date;

@Data
public class ExeLog {
    private String logId;
    private String stepId;
    /** 计划ID，便于按计划查询 */
    private String planId;
    private String content;
    private Date createTime;
}
