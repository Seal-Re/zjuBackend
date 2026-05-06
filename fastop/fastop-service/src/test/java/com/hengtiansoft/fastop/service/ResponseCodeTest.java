package com.hengtiansoft.fastop.service;

import com.hengtiansoft.fastop.base.common.constants.Response.ResponseCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ResponseCode 常量值锁定。
 * 这些数值是前后端契约的一部分（前端 axios 拦截器对 401 自动跳 /login，
 * 对其他非 200 弹 ElMessage.error），改动后必须同步前端拦截器。
 */
class ResponseCodeTest {

    @Test
    void successCodeIs200() {
        assertEquals(200, ResponseCode.SUCC_CODE,
                "SUCC_CODE 必须 200，前端 axios 拦截器仅放行 200 / 201");
    }

    @Test
    void failureCodeIs300() {
        assertEquals(300, ResponseCode.FAILURE_CODE,
                "FAILURE_CODE 是项目自定义业务失败码（非 HTTP 5xx）");
    }

    @Test
    void unauthorizedIsHttp401() {
        assertEquals(401, ResponseCode.UNAUTHORIZED,
                "UNAUTHORIZED 必须 401，前端拦截器收到自动清 token + 跳 /login");
    }

    @Test
    void forbiddenIsHttp403() {
        assertEquals(403, ResponseCode.FORBIDDEN,
                "FORBIDDEN 必须 403，业务校验拒绝（如审签流 expectedWorker 不匹配）");
    }

    @Test
    void notFoundIsHttp404() {
        assertEquals(404, ResponseCode.NOT_FOUND);
    }

    @Test
    void systemErrIs500() {
        assertEquals(500, ResponseCode.SYSTEM_ERR);
    }

    @Test
    void noLegacySystemErrorAlias() {
        // 之前 ResponseCode 有 SYSTEM_ERR + SYSTEM_ERROR 重复死代码，删 SYSTEM_ERROR；
        // 反射验证字段不存在，防止后续重新引入
        for (java.lang.reflect.Field f : ResponseCode.class.getDeclaredFields()) {
            assertNotEquals("SYSTEM_ERROR", f.getName(),
                    "SYSTEM_ERROR 已删除，避免与 SYSTEM_ERR 混淆");
        }
    }
}
