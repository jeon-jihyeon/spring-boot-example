package org.example.spring.application.api.core.exception;

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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }
}
