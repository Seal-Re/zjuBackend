package com.hengtiansoft.fastop.base.common.constants.Response;

public final class ResponseMsg {

    public static final String SUCCESS = "操作成功";
    public static final String FAILURE = "操作失败";
    public static final String ERROR_DATA = "数据错误";
    public static final String ERROR_INVALID_PARAM = "参数校验失败";
    public static final String ERROR_SYSTEM = "系统错误";
    public static final String ERROR_SUITE_UNPASSED = "测试集未审签";
    public static final String ERROR_DATA_NO_APPROVER = "未指定审签人员";
    public static final String ERROR_DATA_UNEXIT = "查询内容不存在";
    public static final String ERROR_DATA_UPDATE = "数据更新错误";
    public static final String NO_VALID_DATA = "没有符合条件的数据";
    public static final String BUSINESS_ERROR = "发生业务异常";
    public static final String PARAMETERS_VALIDATION_ERROR = "参数不合法";
    public static final String NOT_FOUND = "资源未找到";
    public static final String JSON_NOT_READABLE_ERROR = "Json解析异常";
    public static final String MAX_UPLOAD_SIZE_EXCEEDED_ERROR = "文件大小超过%sMB限制!";
    public static final String DEFAULT_ERROR = "服务器开小差了，请稍候再试";
    public static final String RPC_ERROR = "发生远程调用异常";
}
