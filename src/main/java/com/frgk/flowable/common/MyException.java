package com.frgk.flowable.common;

import lombok.Data;

@Data
public class MyException extends Exception {
    private CodeEnum codeEnum;

    public MyException(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

}
