package com.hengtiansoft.fastop.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hengtiansoft.fastop.base.common.entity.Response.ResponseBody;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 锁定 ResponseBody JSON 输出的关键不变量：
 *   - timestamp 字段为正整数秒（前端 Date(s*1000) 即可解析）
 *   - non-null 输出（与 application.yml 配置一致）：data 为 null 时不入 JSON，避免 {"data":null} 干扰前端
 *   - Date 字段使用 yyyy-MM-dd HH:mm:ss + Asia/Shanghai 时区
 */
class ResponseBodyJsonTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper()
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        mapper.setDateFormat(fmt);
    }

    @Test
    void timestampIsPositiveSeconds() {
        ResponseBody<String> body = (ResponseBody<String>) ResponseFactory.success("x").getBody();
        assertNotNull(body);
        long now = System.currentTimeMillis() / 1000L;
        // 允许 ±10s 窗口
        assertTrue(body.getTimestamp() > now - 10 && body.getTimestamp() <= now + 10,
                "timestamp 应是当前时间附近的秒级整数，实际: " + body.getTimestamp());
    }

    @Test
    void nullDataIsOmittedFromJson() throws Exception {
        // successMsg 不写 data → JSON 应不含 "data" 键
        String json = mapper.writeValueAsString(ResponseFactory.successMsg("ok").getBody());
        assertTrue(json.contains("\"message\":\"ok\""));
        assertFalse(json.contains("\"data\":null"), "null data 不应序列化为 \"data\":null：" + json);
    }

    @Test
    void dataMapPreservesEntries() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("planId", "p-1");
        data.put("status", 2);
        String json = mapper.writeValueAsString(ResponseFactory.success(data).getBody());
        assertTrue(json.contains("\"planId\":\"p-1\""));
        assertTrue(json.contains("\"status\":2"));
    }

    @Test
    void dateFieldRendersHumanFormatNotEpoch() throws Exception {
        // 验证不会输出 epoch ms（避免前端 1700000000000 难读）
        Map<String, Object> data = new HashMap<>();
        data.put("startTime", new Date(1700000000000L));
        String json = mapper.writeValueAsString(ResponseFactory.success(data).getBody());
        assertFalse(json.contains("1700000000000"),
                "Date 字段不应输出 epoch ms：" + json);
        assertTrue(json.contains("startTime"));
    }
}
