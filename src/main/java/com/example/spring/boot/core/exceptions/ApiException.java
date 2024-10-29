package com.example.spring.boot.core.exceptions;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final LogLevel logLevel;
    private final Object data;

    protected ApiException(String message, HttpStatus httpStatus, LogLevel logLevel, Object data) {
        super(message);
        this.httpStatus = httpStatus;
        this.logLevel = logLevel;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }
}
