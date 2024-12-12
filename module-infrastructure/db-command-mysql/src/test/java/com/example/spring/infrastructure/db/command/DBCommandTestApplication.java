package com.example.spring.infrastructure.db.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
class DBCommandTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(DBCommandTestApplication.class, args);
    }
}