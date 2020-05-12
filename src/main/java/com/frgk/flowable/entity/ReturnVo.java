package com.frgk.flowable.entity;

import com.frgk.flowable.common.CodeEnum;
import com.frgk.flowable.common.MyException;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReturnVo<T> implements Serializable {
    private T data;
    private Integer code;
    private String message;
    private CodeEnum codeEnum;

    public ReturnVo() {
    }

    ReturnVo(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    ReturnVo(CodeEnum codeEnum, T data) {
        this.codeEnum = codeEnum;
        this.data = data;
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public static ReturnVo success() {

        return new ReturnVo(CodeEnum.success);
    }

    public static <T> ReturnVo success(T data) {

        return new ReturnVo(CodeEnum.success, data);
    }

    public static <T> ReturnVo exception(MyException e)
    {
        return new ReturnVo(e.getCodeEnum());
    }

}
