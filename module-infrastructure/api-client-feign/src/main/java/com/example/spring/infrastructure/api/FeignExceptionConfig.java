package com.example.spring.infrastructure.api;

import com.example.spring.application.common.ApiException;
import com.example.spring.application.common.EntityNotFoundException;
import com.example.spring.application.common.InternalServerException;
import com.example.spring.application.common.InvalidValueException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

public class FeignExceptionConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return this::decode;
    }

    private ApiException decode(String methodKey, Response response) {
        final ApiException e = switch (response.status()) {
            case 404 -> new EntityNotFoundException(response.body());
            case 400 -> new InvalidValueException(response.body());
            default -> new InternalServerException(response.body());
        };
        switch (e.getLogLevel()) {
            case ERROR -> log.error("FeignException : {}", e.getMessage(), e);
            case WARN -> log.warn("FeignException : {}", e.getMessage(), e);
            default -> log.info("FeignException : {}", e.getMessage(), e);
        }
        return e;
    }
}
