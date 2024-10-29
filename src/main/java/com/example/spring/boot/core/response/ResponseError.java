package com.example.spring.boot.core.response;

import com.example.spring.boot.core.ErrorCode;

public record ResponseError(ErrorCode code, String message, Object data) {
}
