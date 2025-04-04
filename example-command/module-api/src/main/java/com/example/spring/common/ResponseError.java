package com.example.spring.common;

import com.example.spring.domain.ErrorCode;

public record ResponseError(ErrorCode code, String message, Object data) {
    public static ResponseError from(ApiException e) {
        return new ResponseError(e.getCode(), e.getMessage(), e.getData());
    }
}
