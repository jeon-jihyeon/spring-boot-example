package com.example.spring.common;

public class InternalServerException extends BaseException {
    public InternalServerException(Object data) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, data);
    }
}
