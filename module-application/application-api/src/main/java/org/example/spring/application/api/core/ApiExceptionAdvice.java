package org.example.spring.application.api.core;

import org.example.spring.application.api.core.exception.ApiException;
import org.example.spring.application.api.core.response.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ApiExceptionAdvice {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ResponseModel<?>> handleApiException(ApiException e) {
        switch (e.getLogLevel()) {
            case ERROR -> log.error("ApiException : {}", e.getMessage(), e);
            case WARN -> log.warn("ApiException : {}", e.getMessage(), e);
            default -> log.info("ApiException : {}", e.getMessage(), e);
        }
        return ResponseEntity.status(e.getHttpStatus()).body(ResponseModel.error(e));
    }
}
