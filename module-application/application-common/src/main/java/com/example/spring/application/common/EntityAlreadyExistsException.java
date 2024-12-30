package com.example.spring.application.common;

import com.example.spring.domain.ErrorCode;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends ApiException {
    public EntityAlreadyExistsException() {
        super(HttpStatus.CONFLICT, LogLevel.WARN, ErrorCode.ALREADY_EXiST, null);
    }

    public EntityAlreadyExistsException(Object data) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.ERROR, ErrorCode.INTERNAL_SERVER, data);
    }
}