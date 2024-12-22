package com.example.spring.infrastructure.event.publisher;

import io.awspring.cloud.sns.core.TopicArnResolver;
import io.awspring.cloud.sns.core.TopicsListingTopicArnResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class AwsSnsConfig {
    private final AwsProperties properties;

    public AwsSnsConfig(AwsProperties properties) {
        this.properties = properties;
    }

    @Bean
    public TopicArnResolver topicArnResolver(SnsClient snsClient) {
        return new TopicsListingTopicArnResolver(snsClient);
    }

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(properties.toCredentials()))
                .region(Region.of(properties.region()))
                .build();
    }
}
