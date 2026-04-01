package com.hengtiansoft.fastop.model.planner.dto.ems;

import lombok.Data;

import java.util.List;

@Data
public class MessageParams {

    private String ability;

    private String sendTime;

    private List<MessageEvents> events;
}
