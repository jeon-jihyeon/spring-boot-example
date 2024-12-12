package org.example.spring.application.api.common.exception;

import com.example.spring.domain.ErrorCode;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class InvalidValueException extends ApiException {
    public InvalidValueException() {
        super(HttpStatus.BAD_REQUEST, LogLevel.WARN, ErrorCode.INVALID_VALUE, null);
    }
}