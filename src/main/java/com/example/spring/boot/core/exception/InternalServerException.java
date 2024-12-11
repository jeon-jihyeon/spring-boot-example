package com.example.spring.boot.core.exception;

import com.example.spring.boot.core.ErrorCode;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class InternalServerException extends ApiException {
    private static final String DEFAULT_MESSAGE = "An internal error occurred.";

    public InternalServerException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR, ErrorCode.NO, null);
    }

    public InternalServerException() {
        super(DEFAULT_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR, ErrorCode.NO, null);
    }
}
