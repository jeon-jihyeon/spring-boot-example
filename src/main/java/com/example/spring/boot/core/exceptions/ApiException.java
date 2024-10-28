package com.example.spring.boot.core.exceptions;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public interface ApiException {
    String getMessage();

    Object getData();

    HttpStatus getStatus();

    LogLevel getLogLevel();
}
