package com.hengtiansoft.fastop.base.common.factory;

import com.hengtiansoft.fastop.base.common.constants.Response.ResponseCode;
import com.hengtiansoft.fastop.base.common.constants.Response.ResponseMsg;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.entity.Response.ResponseBody;
import org.springframework.http.HttpStatus;

public class ResponseFactory {

    private ResponseFactory() {
    }

    public static class ResponseBuilder<T> {
        private int code;
        private String msg;
        private T data;
        private long totalNum;
        private HttpStatus httpStatus;

        public ResponseBuilder() {
            this.code = ResponseCode.SUCC_CODE;
            this.msg = ResponseMsg.SUCCESS;
            this.httpStatus = HttpStatus.OK;
        }

        public ResponseBuilder<T> withCode(int code) {
            this.code = code;
            return this;
        }

        public ResponseBuilder<T> withMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public ResponseBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public ResponseBuilder<T> withTotalNum(long totalNum) {
            this.totalNum = totalNum;
            return this;
        }

        public ResponseBuilder<T> withStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Response<T> build() {
            ResponseBody<T> body = new ResponseBody<>(this.code, this.msg, this.data, this.totalNum);
            return new Response<>(body, this.httpStatus);
        }
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder<>();
    }

    public static <T> Response<T> build(int code, String msg) {
        return (Response<T>) builder().withCode(code).withMsg(msg).build();
    }

    public static <T> Response<T> success(T data) {
        return (Response<T>) builder().withData(data).build();
    }

    /**
     * 仅返回提示文案的 success（不写入 data 字段），适用于"删除/操作成功"等无业务数据返回的场景。
     * 旧代码大量使用 success("xxx 成功") 把字符串塞进 data 字段，前端 res.data 拿到字符串不便处理；
     * 新代码请使用 successMsg("...")，data 保持 null，message 字段承载提示文案。
     */
    public static <T> Response<T> successMsg(String msg) {
        return (Response<T>) builder().withMsg(msg).build();
    }

    public static <T> Response<T> failure(String msg) {
        return (Response<T>) builder().withCode(ResponseCode.FAILURE_CODE).withMsg(msg).build();
    }
}
