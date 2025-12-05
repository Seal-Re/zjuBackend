package com.hengtiansoft.fastop.base.common.factory;

import com.hengtiansoft.fastop.base.common.constants.Response.ResponseCode;
import com.hengtiansoft.fastop.base.common.constants.Response.ResponseMsg;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.entity.Response.ResponseBody;
import org.springframework.http.HttpStatus;

public class ResponseFactory {

    private ResponseFactory() {
    }

    /**
     * 静态内部类，用于构建 Response 对象
     * @param <T>
     */
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

    /**
     * 公共构建方法
     * @return
     * @param <T>
     */
    public static <T> ResponseBuilder<T> builder() {
            return new ResponseBuilder<>();
        }

        public static <T> Response<T> build(int code, String msg) {
            return (Response<T>) builder().withCode(code).withMsg(msg).build();
        }

        public static <T> Response<T> success(T data) {
            return (Response<T>) builder().withData(data).build();
        }

        public static <T> Response<T> failure(String msg) {
            return (Response<T>) builder().withCode(ResponseCode.FAILURE_CODE).withMsg(msg).build();
        }


        /**
         * feign 返回值时获取真实数据
         */
        public static <T> T getFeignData(Response<T> respData) {
            if (respData == null || respData.getCode() != ResponseCode.SUCC_CODE || respData.getData() == null) {
                return null;
            }
            return respData.getData();
        }
}
