package com.hengtiansoft.fastop.base.common.entity.Response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("业务返回结果")
public class ResponseBody<T> {

    @ApiModelProperty("错误码")
    private int code;

    /**
     * 错误/提示信息。
     * 字段在 Java 端命名为 msg（沿用历史代码），但 JSON 序列化输出为 message，
     * 与 CLAUDE.md / mock 服务 / 前端 axios 拦截器统一约定字段名。
     * @JsonAlias 同时兼容反序列化时上游传来的 msg 字段，避免老客户端断链。
     */
    @ApiModelProperty("错误信息")
    @JsonProperty("message")
    @JsonAlias({"msg", "message"})
    private String msg;

    @ApiModelProperty("返回结果")
    private T data;

    @ApiModelProperty("查询数据总数")
    private long totalNum;

    /** 服务端时间戳（秒），用于前端排查时序问题；与 CLAUDE.md/mock 服务字段约定保持一致 */
    @ApiModelProperty("服务端时间戳（秒）")
    private long timestamp = System.currentTimeMillis() / 1000L;

    public ResponseBody(int code) {
        this.code = code;
    }

    public ResponseBody(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseBody(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseBody(int code, String msg, T data, long totalNum) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.totalNum = totalNum;
    }



}
