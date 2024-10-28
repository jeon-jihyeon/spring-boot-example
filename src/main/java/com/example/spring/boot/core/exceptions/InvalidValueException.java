package com.example.spring.boot.core.exceptions;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class InvalidValueException extends RuntimeException implements ApiException {
    public InvalidValueException() {
        super("Invalid value.");
    }

    public InvalidValueException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public LogLevel getLogLevel() {
        return LogLevel.WARN;
    }

    @Override
    public Object getData() {
        return null;
    }
}