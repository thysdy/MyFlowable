package com.frgk.flowable.common;
//错误编码
public enum CodeEnum {
    success(200, "SUCCESS！"),
    nameRepect(100, "用户名重复！"),
    commonException(300, "EXCEPTION！"),
    ioException(301, "IOEXCEPTION！");

    private Integer code;

    private String message;

    CodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
