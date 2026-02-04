package com.example.app.advice;

import com.example.core.exception.BaseException;
import com.example.core.exception.ErrorResponse;
import com.example.core.exception.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LogAdvice {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        switch (e.getLogLevel()) {
            case INFO -> logger.info(e.getMessage());
            case DEBUG -> logger.debug(e.getMessage());
            case WARN -> logger.warn(e.getMessage());
            case ERROR -> logger.error(e.getMessage());
        }
        ErrorResponse body = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(toHttpStatus(e.getLogLevel())).body(body);
    }

    private HttpStatus toHttpStatus(LogLevel logLevel) {
        return switch (logLevel) {
            case ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            case WARN, INFO, DEBUG -> HttpStatus.BAD_REQUEST;
        };
    }
}
