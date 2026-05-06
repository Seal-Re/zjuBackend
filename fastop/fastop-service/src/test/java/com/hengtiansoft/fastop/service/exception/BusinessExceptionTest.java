package com.hengtiansoft.fastop.service.exception;

import com.hengtiansoft.fastop.base.common.constants.Response.ResponseCode;
import com.hengtiansoft.fastop.base.common.exception.BusinessException;
import com.hengtiansoft.fastop.base.common.exception.ForbiddenException;
import com.hengtiansoft.fastop.base.common.exception.UnauthorizedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 业务异常基类与子类契约：
 *  - BusinessException 默认 code = FAILURE_CODE (300)
 *  - 自定义 code 构造器允许业务指定状态
 *  - 父子类型关系：UnauthorizedException / ForbiddenException 均是 BusinessException
 *    （GlobalExceptionHandler 必须用具体类型 handler 优先，否则会被 BusinessException handler 截胡）
 */
class BusinessExceptionTest {

    @Test
    void defaultCodeIsFailureCode() {
        BusinessException ex = new BusinessException("生产校验失败");
        assertEquals(ResponseCode.FAILURE_CODE, ex.getCode());
        assertEquals("生产校验失败", ex.getMessage());
    }

    @Test
    void customCodeOverridesDefault() {
        BusinessException ex = new BusinessException(409, "数据冲突");
        assertEquals(409, ex.getCode());
    }

    @Test
    void unauthorizedCarriesCode401() {
        UnauthorizedException ex = new UnauthorizedException();
        assertEquals(ResponseCode.UNAUTHORIZED, ex.getCode());
        assertEquals(401, ex.getCode());
        assertTrue(ex instanceof BusinessException, "UnauthorizedException 必须是 BusinessException 子类");
    }

    @Test
    void forbiddenCarriesCode403WithCustomMessage() {
        ForbiddenException ex = new ForbiddenException("非该清单审签责任人");
        assertEquals(ResponseCode.FORBIDDEN, ex.getCode());
        assertEquals("非该清单审签责任人", ex.getMessage());
        assertTrue(ex instanceof BusinessException);
    }

    @Test
    void exceptionsAreRuntimeNotChecked() {
        // service 层可以不写 throws 子句，让 controller 与 GlobalExceptionHandler 自动接管
        BusinessException ex = new BusinessException("test");
        assertTrue(ex instanceof RuntimeException, "业务异常应继承 RuntimeException 避免侵入式 throws 声明");
    }
}
