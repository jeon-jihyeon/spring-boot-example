package com.example.spring.common;

public record ResponseError(ErrorCode code, String message, Object data) {
    public static ResponseError from(BaseException e) {
        return new ResponseError(e.getCode(), e.getMessage(), e.getData());
    }
}
