package com.example.spring.boot.core.response;

import com.example.spring.boot.core.exception.ApiException;

public record ResponseModel<T>(T data, ResponseError error) {
    public static ResponseModel<?> ok() {
        return new ResponseModel<>(null, null);
    }

    public static <T> ResponseModel<T> ok(T data) {
        return new ResponseModel<>(data, null);
    }

    public static ResponseModel<?> error(ApiException e) {
        return new ResponseModel<>(null, ResponseError.from(e));
    }
}
