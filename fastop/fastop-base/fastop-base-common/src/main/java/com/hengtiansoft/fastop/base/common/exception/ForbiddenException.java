package com.hengtiansoft.fastop.base.common.exception;

import com.hengtiansoft.fastop.base.common.constants.Response.ResponseCode;

/**
 * 当前账号无权访问。GlobalExceptionHandler 映射为 code=403。
 * 配合前端路由守卫 + 后端接口校验（例如审签流的 expectedWorker）使用。
 */
public class ForbiddenException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public ForbiddenException() {
        super(ResponseCode.FORBIDDEN, "当前账号无权执行该操作");
    }

    public ForbiddenException(String message) {
        super(ResponseCode.FORBIDDEN, message);
    }
}
