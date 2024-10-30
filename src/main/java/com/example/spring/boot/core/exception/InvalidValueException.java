package com.example.spring.boot.core.exception;

import com.example.spring.boot.core.ErrorCode;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class InvalidValueException extends ApiException {
    private static final String DEFAULT_MESSAGE = "The data contains an invalid value.";

    public InvalidValueException() {
        super(DEFAULT_MESSAGE, HttpStatus.BAD_REQUEST, LogLevel.WARN, ErrorCode.NO, null);
    }
}