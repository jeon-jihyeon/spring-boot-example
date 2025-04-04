package com.example.spring.sqs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cloud.aws.sqs.queue")
public record SqsQueueNames(String player) {
}
