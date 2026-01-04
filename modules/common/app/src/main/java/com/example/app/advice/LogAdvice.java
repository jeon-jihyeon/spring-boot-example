package com.example.app.advice;

import com.example.core.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LogAdvice {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> hanleBaseException(BaseException e) {
        switch (e.getLogLevel()) {
            case INFO -> logger.info(e.getMessage());
            case DEBUG -> logger.debug(e.getMessage());
            case WARN -> logger.warn(e.getMessage());
            case ERROR -> logger.error(e.getMessage());
        }
        throw e;
    }
}
