package com.hengtiansoft.fastop.model.planner.dto.ems;

import lombok.Data;

/**
 * EMS 入站报文外壳（与 affair-center 等系统对齐）。
 */
@Data
public class MessageEtt {

    private String method;

    private MessageParams params;
}
