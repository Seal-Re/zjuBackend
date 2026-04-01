package com.hengtiansoft.fastop.model.planner.dto.ems;

import lombok.Data;

import java.util.Map;

@Data
public class MessageEvents {

    private Map<String, Object> data;

    private String eventId;

    /** 与设备 topic 一致 */
    private String eventType;

    private String happenTime;

    private String srcIndex;

    private String srcName;

    private String srcParentIndex;

    private String srcType;

    private Integer status;

    private Integer timeout;
}
