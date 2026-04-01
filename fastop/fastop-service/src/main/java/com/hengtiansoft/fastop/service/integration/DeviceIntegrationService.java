package com.hengtiansoft.fastop.service.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengtiansoft.fastop.model.planner.dto.DeviceTopicsPayload;
import com.hengtiansoft.fastop.service.config.IntegrationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceIntegrationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IntegrationProperties integrationProperties;

    public DeviceTopicsPayload fetchTopics() throws Exception {
        String base = trimTrailingSlash(integrationProperties.getDeviceControllerUrl());
        if (base.isEmpty()) {
            throw new IllegalStateException("deviceControllerUrl 未配置");
        }
        String url = UriComponentsBuilder.fromHttpUrl(base + "/topics").build().toUriString();
        String body = restTemplate.getForObject(url, String.class);
        JsonNode root = objectMapper.readTree(body);
        JsonNode data = root.get("data");
        if (data == null || data.isNull()) {
            data = root;
        }
        List<String> topics = new ArrayList<>();
        JsonNode topicsNode = data.get("topics");
        if (topicsNode != null && topicsNode.isArray()) {
            for (JsonNode n : topicsNode) {
                topics.add(n.asText());
            }
        }
        Object example;
        JsonNode ex = data.get("example");
        if (ex == null || ex.isNull()) {
            example = new java.util.LinkedHashMap<String, Object>();
        } else {
            example = objectMapper.treeToValue(ex, Object.class);
        }
        return DeviceTopicsPayload.builder().topics(topics).example(example).build();
    }

    private static String trimTrailingSlash(String s) {
        if (s == null) {
            return "";
        }
        return s.endsWith("/") ? s.substring(0, s.length() - 1) : s;
    }
}
