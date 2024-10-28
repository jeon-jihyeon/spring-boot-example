package com.example.spring.boot.core.exceptions;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class InternalServerException extends RuntimeException implements ApiException {
    private final Object data;

    public InternalServerException() {
        super("An internal error occurred. Try again later.");
        this.data = null;
    }

    public InternalServerException(Object data) {
        super("An internal error occurred. Try again later.");
        this.data = data;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public LogLevel getLogLevel() {
        return LogLevel.ERROR;
    }

    @Override
    public Object getData() {
        return data;
    }
}
