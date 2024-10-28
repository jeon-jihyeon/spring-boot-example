package com.example.spring.boot.core.exceptions;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RuntimeException implements ApiException {
    public EntityNotFoundException() {
        super("The entity does not exist.");
    }

    public EntityNotFoundException(String name) {
        super(String.format("The %s does not exist.", name));
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
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
