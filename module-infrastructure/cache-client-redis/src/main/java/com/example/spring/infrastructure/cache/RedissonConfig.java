package com.example.spring.infrastructure.cache;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    private final RedissonProperties properties;

    public RedissonConfig(RedissonProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RedissonClient redissonClient() {
        final Config config = new Config();
        config.useSingleServer().setAddress(properties.getAddress());
        return Redisson.create(config);
    }
}
