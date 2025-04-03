package com.example.spring.core;

import com.example.spring.common.ApiException;
import com.example.spring.common.InvalidValueException;
import com.example.spring.common.ResponseModel;
import com.example.spring.domain.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ApiExceptionAdvice {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseModel<?>> handleApiException(BaseException base) {
        final ApiException e = ApiException.from(base);
        switch (e.getLogLevel()) {
            case ERROR -> log.error("ApiException : {}", e.getMessage(), e);
            case WARN -> log.warn("ApiException : {}", e.getMessage(), e);
            default -> log.info("ApiException : {}", e.getMessage(), e);
        }
        return ResponseEntity.status(e.getHttpStatus()).body(ResponseModel.error(e));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseModel<?>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.error(new InvalidValueException(e.getBindingResult().getAllErrors().stream().toList())));
    }
}
