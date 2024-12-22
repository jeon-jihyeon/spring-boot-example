package com.example.spring.infrastructure.event.publisher;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cloud.aws.sns.topic.name")
public record AwsSnsTopicNames(
        String team,
        String player
) {
}
