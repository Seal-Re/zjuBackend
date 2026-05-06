package com.hengtiansoft.fastop.service.config;

import com.hengtiansoft.fastop.base.common.exception.BusinessException;
import com.hengtiansoft.fastop.base.common.exception.ForbiddenException;
import com.hengtiansoft.fastop.base.common.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Standalone MockMvc 集成测：仅装载 controller + GlobalExceptionHandler，不启动 Spring 上下文，
 * 跑得快又不依赖 MySQL/Druid。
 *
 * 验证从 controller throw 业务异常 → GlobalExceptionHandler 拦截 → 严格按 ResponseFactory 格式
 * 输出（含 message 字段）的全栈闭环。
 */
class GlobalExceptionHandlerMvcTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProbeController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void unauthorizedExceptionRendersJson401() throws Exception {
        mockMvc.perform(get("/_probe/unauthorized"))
                .andExpect(jsonPath("$.code", equalTo(401)))
                .andExpect(jsonPath("$.message", containsString("登录")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void forbiddenExceptionRendersJson403() throws Exception {
        mockMvc.perform(get("/_probe/forbidden"))
                .andExpect(jsonPath("$.code", equalTo(403)))
                .andExpect(jsonPath("$.message", equalTo("非该清单审签责任人")));
    }

    @Test
    void businessExceptionRendersJson300WithMessage() throws Exception {
        mockMvc.perform(get("/_probe/business"))
                .andExpect(jsonPath("$.code", equalTo(300)))
                .andExpect(jsonPath("$.message", equalTo("计划状态不允许派发")));
    }

    @Test
    void uncaughtExceptionFallsBackToSafeMessage() throws Exception {
        mockMvc.perform(get("/_probe/boom"))
                .andExpect(jsonPath("$.code", equalTo(300)))
                .andExpect(jsonPath("$.message", equalTo("服务内部异常，请稍后重试")));
    }

    @Test
    void responseAlwaysHasMessageNotMsgField() throws Exception {
        // PP.1 端到端回归：异常路径输出也必须含 message 而非 msg
        String body = mockMvc.perform(get("/_probe/business"))
                .andReturn().getResponse().getContentAsString();
        assertTrue(body.contains("\"message\""), "异常响应必须包含 message 字段：" + body);
        assertFalse(body.contains("\"msg\":"), "异常响应不应含 msg 字段：" + body);
    }

    @Test
    void subclassExceptionMatchesSpecificHandlerNotBusiness() throws Exception {
        // UnauthorizedException 是 BusinessException 子类；Spring @ExceptionHandler 必须用最具体的子类 handler，
        // 否则会被 BusinessException handler 截胡，code 错变成 300 而非 401
        mockMvc.perform(get("/_probe/unauthorized"))
                .andExpect(jsonPath("$.code", equalTo(401)));
        // 若未来有人误删 handleUnauthorized，本测试会立即红
    }

    @Test
    void timestampIsPositiveAcrossExceptionPaths() throws Exception {
        mockMvc.perform(get("/_probe/forbidden"))
                .andExpect(jsonPath("$.timestamp").value(org.hamcrest.Matchers.greaterThan(0)));
    }

    @RestController
    static class ProbeController {
        @GetMapping("/_probe/unauthorized")
        public void unauthorized() { throw new UnauthorizedException(); }

        @GetMapping("/_probe/forbidden")
        public void forbidden() { throw new ForbiddenException("非该清单审签责任人"); }

        @GetMapping("/_probe/business")
        public void business() { throw new BusinessException("计划状态不允许派发"); }

        @GetMapping("/_probe/boom")
        public void boom() { throw new RuntimeException("内部 SQL [select * from users]"); }
    }
}
