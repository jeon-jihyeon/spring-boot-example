package com.example.spring.boot.core.exception;

import com.example.spring.boot.core.ErrorCode;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ApiException {
    private static final String DEFAULT_MESSAGE = "The entity does not exist.";

    public EntityNotFoundException() {
        super(DEFAULT_MESSAGE, HttpStatus.NOT_FOUND, LogLevel.WARN, ErrorCode.NO, null);
    }
}
