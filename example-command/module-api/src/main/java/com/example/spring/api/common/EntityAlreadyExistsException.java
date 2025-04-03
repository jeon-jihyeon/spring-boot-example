package com.example.spring.api.common;

import com.example.spring.domain.ErrorCode;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends ApiException {
    public EntityAlreadyExistsException() {
        super(HttpStatus.CONFLICT, LogLevel.WARN, ErrorCode.ALREADY_EXiST, null);
    }

    public EntityAlreadyExistsException(Object data) {
        super(HttpStatus.CONFLICT, LogLevel.ERROR, ErrorCode.ALREADY_EXiST, data);
    }
}