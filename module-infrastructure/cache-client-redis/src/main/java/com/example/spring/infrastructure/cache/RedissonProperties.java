package com.example.spring.infrastructure.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis")
public record RedissonProperties(String host, String port) {

    public String getAddress() {
        return String.format("redis://%s:%s", host, port);
    }
}
