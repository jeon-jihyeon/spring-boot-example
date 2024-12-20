package com.example.spring.application.common;

import com.example.spring.domain.BaseException;
import com.example.spring.domain.ErrorCode;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public abstract class ApiException extends BaseException {
    private final HttpStatus httpStatus;
    private final LogLevel logLevel;

    protected ApiException(String message, HttpStatus httpStatus, LogLevel logLevel, ErrorCode code, Object data) {
        super(message, code, data);
        this.httpStatus = httpStatus;
        this.logLevel = logLevel;
    }

    protected ApiException(HttpStatus httpStatus, LogLevel logLevel, ErrorCode code, Object data) {
        super(code, data);
        this.httpStatus = httpStatus;
        this.logLevel = logLevel;
    }

    protected ApiException(ErrorCode code, Object data) {
        super(code, data);
        this.httpStatus = toHttpStatus(code);
        this.logLevel = toLogLevel(code);
    }

    public static LogLevel toLogLevel(ErrorCode code) {
        return switch (code) {
            case NOT_FOUND, INVALID_VALUE -> LogLevel.WARN;
            case INTERNAL_SERVER -> LogLevel.ERROR;
        };
    }

    public static HttpStatus toHttpStatus(ErrorCode code) {
        return switch (code) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_VALUE -> HttpStatus.BAD_REQUEST;
            case INTERNAL_SERVER -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }
}
