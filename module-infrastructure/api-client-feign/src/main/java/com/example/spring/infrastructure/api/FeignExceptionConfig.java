package com.example.spring.infrastructure.api;

import com.example.spring.application.common.ApiException;
import com.example.spring.application.common.EntityNotFoundException;
import com.example.spring.application.common.InternalServerException;
import com.example.spring.application.common.InvalidValueException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignExceptionConfig {
    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return this::decode;
    }

    private ApiException decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 404 -> new EntityNotFoundException(response.body());
            case 400 -> new InvalidValueException(response.body());
            default -> new InternalServerException(response.body());
        };
    }
}
