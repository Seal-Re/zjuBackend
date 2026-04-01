package com.hengtiansoft.fastop.service.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengtiansoft.fastop.model.planner.dto.ems.MessageEtt;
import com.hengtiansoft.fastop.model.planner.dto.ems.MessageEvents;
import com.hengtiansoft.fastop.model.planner.dto.ems.MessageParams;
import com.hengtiansoft.fastop.model.planner.entity.ExeStepWithBLOBs;
import com.hengtiansoft.fastop.service.config.IntegrationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class EmsMessageService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IntegrationProperties integrationProperties;

    public MessageEtt buildFromExeStep(ExeStepWithBLOBs exeStep) {
        String topic = exeStep.getOperationObject();
        Object example = null;
        Map<String, Object> params = Collections.emptyMap();
        String raw = exeStep.getCommandData();
        if (raw != null && !raw.isEmpty()) {
            try {
                JsonNode root = objectMapper.readTree(raw);
                if (root.has("topic") && !root.get("topic").isNull()) {
                    topic = root.get("topic").asText();
                }
                if (root.has("example")) {
                    example = objectMapper.treeToValue(root.get("example"), Object.class);
                }
                if (root.has("params") && root.get("params").isObject()) {
                    params = objectMapper.convertValue(root.get("params"), new TypeReference<Map<String, Object>>() {});
                }
            } catch (Exception ignored) {
            }
        }
        if (topic == null || topic.trim().isEmpty()) {
            topic = "unknown.topic";
        }
        Map<String, Object> dataMap = mergeExampleParams(example, params);
        return buildShell(topic.trim(), dataMap);
    }

    private MessageEtt buildShell(String topic, Map<String, Object> dataMap) {
        String now = OffsetDateTime.now().toString();
        MessageEvents ev = new MessageEvents();
        ev.setEventId(UUID.randomUUID().toString());
        ev.setEventType(topic);
        ev.setHappenTime(now);
        ev.setData(dataMap);
        ev.setStatus(0);
        ev.setTimeout(30);
        ev.setSrcIndex("");
        ev.setSrcName("");
        ev.setSrcParentIndex("");
        ev.setSrcType("");

        MessageParams p = new MessageParams();
        p.setAbility(integrationProperties.getEmsAbilityDefault());
        p.setSendTime(now);
        p.setEvents(Collections.singletonList(ev));

        MessageEtt ett = new MessageEtt();
        ett.setMethod("send");
        ett.setParams(p);
        return ett;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> mergeExampleParams(Object example, Map<String, Object> params) {
        Map<String, Object> base = new LinkedHashMap<>();
        if (example != null) {
            if (example instanceof Map) {
                base = deepCopyMap((Map<String, Object>) example);
            } else {
                try {
                    base = objectMapper.convertValue(example, new TypeReference<Map<String, Object>>() {});
                } catch (IllegalArgumentException e) {
                    base = new LinkedHashMap<>();
                }
            }
        }
        if (params != null) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                if (e.getKey() == null) {
                    continue;
                }
                putDotPath(base, e.getKey(), e.getValue());
            }
        }
        return base;
    }

    private Map<String, Object> deepCopyMap(Map<String, Object> src) {
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(src), new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return new LinkedHashMap<>(src);
        }
    }

    @SuppressWarnings("unchecked")
    private void putDotPath(Map<String, Object> map, String path, Object value) {
        String[] parts = path.split("\\.", 2);
        if (parts.length == 1) {
            map.put(parts[0], value);
            return;
        }
        Object sub = map.get(parts[0]);
        if (!(sub instanceof Map)) {
            sub = new LinkedHashMap<String, Object>();
            map.put(parts[0], sub);
        }
        putDotPath((Map<String, Object>) sub, parts[1], value);
    }
}
