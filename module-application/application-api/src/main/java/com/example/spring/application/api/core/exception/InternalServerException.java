package com.example.spring.application.api.core.exception;

import com.example.spring.domain.ErrorCode;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class InternalServerException extends ApiException {
    public InternalServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR, ErrorCode.INTERNAL_SERVER, null);
    }
}
