package com.example.spring.infrastructure.db.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
class QueryDbTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(QueryDbTestApplication.class, args);
    }
}