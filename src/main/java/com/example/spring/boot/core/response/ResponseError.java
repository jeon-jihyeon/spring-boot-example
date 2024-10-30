package com.example.spring.boot.core.response;

import com.example.spring.boot.core.ErrorCode;
import com.example.spring.boot.core.exception.ApiException;

public record ResponseError(ErrorCode code, String message, Object data) {
    public static ResponseError from(ApiException e) {
        return new ResponseError(e.getCode(), e.getMessage(), e.getData());
    }
}
