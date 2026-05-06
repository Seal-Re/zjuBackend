package com.hengtiansoft.fastop.service.config;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.exception.BusinessException;
import com.hengtiansoft.fastop.base.common.exception.ForbiddenException;
import com.hengtiansoft.fastop.base.common.exception.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GlobalExceptionHandler 兜底契约：所有未捕获异常都映射为 ResponseFactory.failure(code=300)，
 * 且不向用户暴露内部堆栈细节。
 */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void missingParamMessageNamesParameter() {
        MissingServletRequestParameterException ex =
                new MissingServletRequestParameterException("planId", "String");
        Response<Void> resp = handler.handleMissingParam(ex);
        assertEquals(300, resp.getCode());
        assertTrue(resp.getMsg().contains("planId"), "缺参提示应指出参数名: " + resp.getMsg());
    }

    @Test
    void badJsonReturnsFriendlyMessage() {
        HttpMessageNotReadableException ex =
                new HttpMessageNotReadableException("Required request body is missing");
        Response<Void> resp = handler.handleBadJson(ex);
        assertEquals(300, resp.getCode());
        assertEquals("请求体格式错误", resp.getMsg());
    }

    @Test
    void methodNotSupportedNamesMethod() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("GET");
        Response<Void> resp = handler.handleMethodNotSupported(ex);
        assertEquals(300, resp.getCode());
        assertTrue(resp.getMsg().contains("GET"), "提示应指出实际方法: " + resp.getMsg());
    }

    @Test
    void anyExceptionDoesNotLeakStackTrace() {
        Response<Void> resp = handler.handleAny(new RuntimeException("敏感堆栈：内部 SQL [select * from users where id=1]"));
        assertEquals(300, resp.getCode());
        // 兜底文案不应包含原始异常 message，避免堆栈/SQL 泄露
        assertEquals("服务内部异常，请稍后重试", resp.getMsg());
        assertFalse(resp.getMsg().contains("SQL"), "兜底响应不得含 SQL 字眼: " + resp.getMsg());
        assertFalse(resp.getMsg().contains("敏感"), "兜底响应不得回显原始 message: " + resp.getMsg());
    }

    @Test
    void unauthorizedMapsTo401() {
        Response<Void> resp = handler.handleUnauthorized(new UnauthorizedException());
        assertEquals(401, resp.getCode(), "UnauthorizedException 必须映射为 401");
        assertTrue(resp.getMsg().contains("登录"), "提示应包含登录字样: " + resp.getMsg());
    }

    @Test
    void forbiddenMapsTo403() {
        Response<Void> resp = handler.handleForbidden(new ForbiddenException("非审签责任人"));
        assertEquals(403, resp.getCode());
        assertEquals("非审签责任人", resp.getMsg());
    }

    @Test
    void businessExceptionPreservesMessage() {
        Response<Void> resp = handler.handleBusiness(new BusinessException("计划状态不允许派发"));
        assertEquals(300, resp.getCode());
        assertEquals("计划状态不允许派发", resp.getMsg(),
                "业务异常 message 应原样返回前端，不脱敏");
    }

    @Test
    void businessExceptionRespectsCustomCode() {
        // 业务可指定非 300 的 code（如 409 数据冲突），handler 必须透传不覆盖
        Response<Void> resp = handler.handleBusiness(new BusinessException(409, "重复编号"));
        assertEquals(409, resp.getCode());
        assertEquals("重复编号", resp.getMsg());
    }
}
