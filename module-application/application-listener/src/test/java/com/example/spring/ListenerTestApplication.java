package com.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
class ListenerTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ListenerTestApplication.class, args);
    }
}