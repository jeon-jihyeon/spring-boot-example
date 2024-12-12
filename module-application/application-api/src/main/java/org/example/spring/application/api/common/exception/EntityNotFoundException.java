package org.example.spring.application.api.common.exception;

import com.example.spring.domain.ErrorCode;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ApiException {
    public EntityNotFoundException() {
        super(HttpStatus.NOT_FOUND, LogLevel.WARN, ErrorCode.NOT_FOUND, null);
    }
}
