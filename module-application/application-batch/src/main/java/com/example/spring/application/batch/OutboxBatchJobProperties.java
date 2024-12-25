package com.example.spring.application.batch;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.batch.job")
public record OutboxBatchJobProperties(String name, Integer chunkSize) {
}
