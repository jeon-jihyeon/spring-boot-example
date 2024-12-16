package com.example.spring.infrastructure.event.publisher;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class AwsSqsConfig {
    private final AwsSqsProperties properties;

    public AwsSqsConfig(AwsSqsProperties properties) {
        this.properties = properties;
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .credentialsProvider(properties::toCredentials)
                .region(Region.of(properties.region()))
                .endpointOverride(URI.create(properties.endPoint()))
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(@Qualifier("sqsAsyncClient") SqsAsyncClient client) {
        return SqsTemplate.newTemplate(client);
    }
}
