package com.hengtiansoft.fastop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.entity.Response.ResponseBody;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 锁定 ResponseFactory 与 ResponseBody 的对外契约：
 *  - JSON 字段名必须为 message（与 mock 服务 / 前端 axios 拦截器统一）
 *  - 同时兼容反序列化 msg 字段（@JsonAlias）
 *  - timestamp 字段必填
 *
 * 这些测试是 PP.1（msg vs message 漂移）回归保护，避免再次悄悄改回去。
 */
class ResponseFactoryTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void successResponseSerializesMessageField() throws Exception {
        Response<String> resp = ResponseFactory.success("hello");
        String json = objectMapper.writeValueAsString(resp.getBody());
        assertTrue(json.contains("\"message\""), "JSON 必须包含 message 字段，实际: " + json);
        assertFalse(json.contains("\"msg\""), "JSON 不应再包含 msg 字段，实际: " + json);
        assertTrue(json.contains("\"timestamp\""), "JSON 必须包含 timestamp 字段，实际: " + json);
    }

    @Test
    void failureCarriesUserMessage() throws Exception {
        Response<Void> resp = ResponseFactory.failure("计划名称不能为空");
        String json = objectMapper.writeValueAsString(resp.getBody());
        assertTrue(json.contains("\"message\":\"计划名称不能为空\""), "失败响应应携带原始中文 message: " + json);
        assertEquals(300, resp.getCode(), "ResponseFactory.failure 应返回 FAILURE_CODE=300");
    }

    @Test
    void successMsgKeepsDataNull() throws Exception {
        Response<Void> resp = ResponseFactory.<Void>successMsg("删除成功");
        ResponseBody<Void> body = resp.getBody();
        assertNotNull(body);
        assertEquals(200, body.getCode());
        assertEquals("删除成功", body.getMsg(), "successMsg 应仅写 message 字段");
        assertNull(body.getData(), "successMsg 不应把字符串塞到 data 字段");
    }

    @Test
    void deserializesLegacyMsgAlias() throws Exception {
        // 老客户端可能仍然回传 msg 字段，反序列化必须能兼容
        String legacyJson = "{\"code\":200,\"msg\":\"legacy\",\"data\":null,\"totalNum\":0,\"timestamp\":1700000000}";
        ResponseBody<?> body = objectMapper.readValue(legacyJson, ResponseBody.class);
        assertEquals("legacy", body.getMsg(), "@JsonAlias 必须能反序列化 msg 字段");
    }
}
