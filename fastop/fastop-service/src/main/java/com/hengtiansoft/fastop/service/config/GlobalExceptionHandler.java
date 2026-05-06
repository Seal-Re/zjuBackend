package com.hengtiansoft.fastop.service.config;

import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.exception.BusinessException;
import com.hengtiansoft.fastop.base.common.exception.ForbiddenException;
import com.hengtiansoft.fastop.base.common.exception.UnauthorizedException;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理：所有未捕获异常统一包装为 ResponseFactory.failure(...)，
 * 与正常 controller 返回结构一致，保证前端 axios 拦截器能稳定取到 code/message 字段。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 缺必填请求参数 */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response<Void> handleMissingParam(MissingServletRequestParameterException e) {
        return ResponseFactory.failure("缺少必填参数: " + e.getParameterName());
    }

    /** 请求体 JSON 解析失败 / 字段类型不匹配 */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<Void> handleBadJson(HttpMessageNotReadableException e) {
        log.warn("请求体解析失败: {}", e.getMessage());
        return ResponseFactory.failure("请求体格式错误");
    }

    /** @Valid 参数校验失败 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<Void> handleValidation(MethodArgumentNotValidException e) {
        String detail = e.getBindingResult().getFieldErrors().isEmpty()
                ? "参数校验失败"
                : e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseFactory.failure(detail);
    }

    /** HTTP 方法不支持（如 GET 请求 POST 接口） */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<Void> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return ResponseFactory.failure("不支持的请求方法: " + e.getMethod());
    }

    /** 未登录 / token 过期：映射为 code=401，前端拦截器自动跳 /login */
    @ExceptionHandler(UnauthorizedException.class)
    public Response<Void> handleUnauthorized(UnauthorizedException e) {
        return ResponseFactory.<Void>builder().withCode(e.getCode()).withMsg(e.getMessage()).build();
    }

    /** 权限不足：映射为 code=403 */
    @ExceptionHandler(ForbiddenException.class)
    public Response<Void> handleForbidden(ForbiddenException e) {
        return ResponseFactory.<Void>builder().withCode(e.getCode()).withMsg(e.getMessage()).build();
    }

    /** 业务异常：服务层抛出后统一包装；可携带原始 message 给用户看 */
    @ExceptionHandler(BusinessException.class)
    public Response<Void> handleBusiness(BusinessException e) {
        return ResponseFactory.<Void>builder().withCode(e.getCode()).withMsg(e.getMessage()).build();
    }

    /** 兜底：任何未捕获异常都包装为 failure，避免返回 Spring 默认 HTML / 暴露堆栈 */
    @ExceptionHandler(Exception.class)
    public Response<Void> handleAny(Exception e) {
        log.error("未捕获异常", e);
        // 生产侧不向用户暴露原始异常消息（可能含 SQL 片段 / 堆栈细节）
        return ResponseFactory.failure("服务内部异常，请稍后重试");
    }
}
