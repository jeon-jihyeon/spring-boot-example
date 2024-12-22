package com.example.spring.infrastructure.event.publisher;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cloud.aws.sqs.queue.name")
public record AwsSqsQueueNames(
        String teamCreate
) {
}
