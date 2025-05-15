package com.example.spring.common;

public class InvalidValueException extends BaseException {
    public InvalidValueException(Object data) {
        super(ErrorCode.INVALID_VALUE, data);
    }
}