package com.example.spring.infrastructure.event.queue;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cloud.aws.sqs.queue")
public record AwsSqsQueue(
        String player,
        String team
) {
}
