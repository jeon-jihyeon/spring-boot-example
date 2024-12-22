package com.example.spring.infrastructure.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
class DBTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(DBTestApplication.class, args);
    }
}