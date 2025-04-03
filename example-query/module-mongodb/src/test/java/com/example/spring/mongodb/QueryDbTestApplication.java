package com.example.spring.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableMongoRepositories
class QueryDbTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(QueryDbTestApplication.class, args);
    }
}