package com.hengtiansoft.fastop.base.common.entity.Response;

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

    @ApiModelProperty("错误信息")
    private String msg;

    @ApiModelProperty("返回结果")
    private T data;

    @ApiModelProperty("查询数据总数")
    private long totalNum;

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
