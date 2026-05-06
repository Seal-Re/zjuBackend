package com.hengtiansoft.fastop.base.common.exception;

import com.hengtiansoft.fastop.base.common.constants.Response.ResponseCode;

/**
 * 未登录 / token 失效。GlobalExceptionHandler 映射为 code=401，
 * 前端 axios 拦截器收到 401 会自动清 token + 跳 /login。
 */
public class UnauthorizedException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException() {
        super(ResponseCode.UNAUTHORIZED, "未登录或登录已过期");
    }

    public UnauthorizedException(String message) {
        super(ResponseCode.UNAUTHORIZED, message);
    }
}
