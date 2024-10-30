package com.example.spring.boot.core.exception;

import com.example.spring.boot.core.ErrorCode;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final LogLevel logLevel;
    private final ErrorCode code;
    private final Object data;

    protected ApiException(String message, HttpStatus httpStatus, LogLevel logLevel, ErrorCode code, Object data) {
        super(message);
        this.httpStatus = httpStatus;
        this.logLevel = logLevel;
        this.code = code;
        this.data = data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public ErrorCode getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
