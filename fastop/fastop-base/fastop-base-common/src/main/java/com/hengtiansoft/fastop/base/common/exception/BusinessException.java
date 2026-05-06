package com.hengtiansoft.fastop.base.common.exception;

import com.hengtiansoft.fastop.base.common.constants.Response.ResponseCode;

/**
 * 业务异常基类。
 * 服务层抛出后由 GlobalExceptionHandler 统一映射到 ResponseFactory.failure(...)，
 * 避免每个 controller / service 都手写 if-else + ResponseFactory 包装样板代码。
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int code;

    public BusinessException(String message) {
        super(message);
        this.code = ResponseCode.FAILURE_CODE;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
