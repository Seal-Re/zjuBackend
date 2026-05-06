package com.hengtiansoft.fastop.service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengtiansoft.fastop.model.planner.dto.ems.MessageEtt;
import com.hengtiansoft.fastop.model.planner.entity.ExeStepWithBLOBs;
import com.hengtiansoft.fastop.service.config.IntegrationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * EmsMessageService.buildFromExeStep 是 EMS 异步指令下发的核心拼装链路：
 *   ExeStep.commandData (JSON: {topic, example, params}) → MessageEtt
 * 这里覆盖关键路径：topic 提取、example 与 params 合并、点路径覆盖、空 commandData 兜底。
 */
class EmsMessageServiceTest {

    private EmsMessageService service;

    @BeforeEach
    void setUp() throws Exception {
        service = new EmsMessageService();
        // service 内部用 @Autowired 注入；单测用反射直接灌依赖避免起 Spring 上下文
        inject(service, "objectMapper", new ObjectMapper());
        IntegrationProperties props = new IntegrationProperties();
        props.setEmsAbilityDefault("fastop.send");
        inject(service, "integrationProperties", props);
    }

    @Test
    void topicFromCommandDataOverridesOperationObject() {
        ExeStepWithBLOBs step = new ExeStepWithBLOBs();
        step.setOperationObject("fallback.topic");
        step.setCommandData("{\"topic\":\"ems_topic_switchOn\",\"example\":{\"deviceCode\":\"\"},\"params\":{\"deviceCode\":\"D001\"}}");

        MessageEtt ett = service.buildFromExeStep(step);
        assertNotNull(ett.getParams());
        assertEquals("send", ett.getMethod());
        assertEquals("ems_topic_switchOn", ett.getParams().getEvents().get(0).getEventType());
    }

    @Test
    void paramsOverwriteExampleFieldsAtSameKey() {
        ExeStepWithBLOBs step = new ExeStepWithBLOBs();
        step.setOperationObject("ems_topic_setVoltage");
        step.setCommandData("{\"topic\":\"ems_topic_setVoltage\",\"example\":{\"voltage\":0,\"meta\":{\"channel\":1}},\"params\":{\"voltage\":24}}");

        MessageEtt ett = service.buildFromExeStep(step);
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) ett.getParams().getEvents().get(0).getData();
        assertEquals(24, data.get("voltage"), "params 同 key 应覆盖 example 值");
        // example 内未被 params 覆盖的字段保留
        assertNotNull(data.get("meta"));
    }

    @Test
    void dotPathParamsWriteIntoNestedMap() {
        ExeStepWithBLOBs step = new ExeStepWithBLOBs();
        step.setOperationObject("ems_topic_x");
        // params 用点路径 meta.channel 写入嵌套 map
        step.setCommandData("{\"topic\":\"t\",\"example\":{\"meta\":{\"channel\":0}},\"params\":{\"meta.channel\":7}}");

        MessageEtt ett = service.buildFromExeStep(step);
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) ett.getParams().getEvents().get(0).getData();
        @SuppressWarnings("unchecked")
        Map<String, Object> meta = (Map<String, Object>) data.get("meta");
        assertEquals(7, meta.get("channel"), "点路径 meta.channel 应写入嵌套 meta map");
    }

    @Test
    void emptyCommandDataFallsBackToOperationObject() {
        ExeStepWithBLOBs step = new ExeStepWithBLOBs();
        step.setOperationObject("ems_topic_fallback");
        step.setCommandData("");

        MessageEtt ett = service.buildFromExeStep(step);
        assertEquals("ems_topic_fallback",
                ett.getParams().getEvents().get(0).getEventType(),
                "commandData 为空时 topic 应回退到 operationObject");
    }

    @Test
    void invalidJsonCommandDataDoesNotThrow() {
        ExeStepWithBLOBs step = new ExeStepWithBLOBs();
        step.setOperationObject("ems_topic_bad");
        step.setCommandData("{this is not json");

        MessageEtt ett = assertDoesNotThrow(() -> service.buildFromExeStep(step));
        assertNotNull(ett, "非法 JSON 不应抛异常，应静默回退");
    }

    @Test
    void messageShellHasExactlyOneEventWithMethodSend() {
        ExeStepWithBLOBs step = new ExeStepWithBLOBs();
        step.setOperationObject("ems_topic_x");
        step.setCommandData("{\"topic\":\"t\",\"example\":{},\"params\":{}}");

        MessageEtt ett = service.buildFromExeStep(step);
        assertEquals("send", ett.getMethod(), "EMS 方法固定为 send");
        assertEquals(1, ett.getParams().getEvents().size(), "每个 step 派发 1 个 event");
        assertEquals("fastop.send", ett.getParams().getAbility(),
                "ability 应取 IntegrationProperties.emsAbilityDefault");
    }

    @Test
    void eventDefaultStatusAndTimeoutAreSet() {
        ExeStepWithBLOBs step = new ExeStepWithBLOBs();
        step.setOperationObject("ems_topic_y");
        step.setCommandData("{\"topic\":\"t\"}");

        MessageEtt ett = service.buildFromExeStep(step);
        assertEquals(0, ett.getParams().getEvents().get(0).getStatus(),
                "新建 event status 默认 0");
        assertEquals(30, ett.getParams().getEvents().get(0).getTimeout(),
                "默认 timeout 30 秒（与 buildShell 内常量约定）");
    }

    @Test
    void unknownTopicFallbackWhenBothOperationObjectAndCommandDataMissing() {
        ExeStepWithBLOBs step = new ExeStepWithBLOBs();
        // operationObject 为 null + commandData 为 null
        MessageEtt ett = service.buildFromExeStep(step);
        assertEquals("unknown.topic", ett.getParams().getEvents().get(0).getEventType(),
                "无任何 topic 信息时应回退 unknown.topic 而非 null");
    }

    private static void inject(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }
}
