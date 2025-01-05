package com.example.spring.infrastructure.api;

import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients
@Configuration
class FeignConfig {
    @Bean
    public Client client() {
        return new ApacheHttpClient();
    }
}
