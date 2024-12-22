package com.example.spring.infrastructure.event.publisher;

import com.example.spring.domain.event.DomainEvent;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementOrdering;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;
import java.time.Duration;

@Configuration
public class AwsSqsConfig {
    private final AwsProperties properties;

    public AwsSqsConfig(AwsProperties properties) {
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
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory() {
        return SqsMessageListenerContainerFactory
                .builder()
                .configure(options -> options
                        .acknowledgementMode(AcknowledgementMode.ALWAYS)
                        .acknowledgementInterval(Duration.ofSeconds(3))
                        .acknowledgementThreshold(5)
                        .acknowledgementOrdering(AcknowledgementOrdering.ORDERED)
                        .messageConverter(messageConverter())
                )
                .sqsAsyncClient(sqsAsyncClient())
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate() {
        return SqsTemplate.newTemplate(sqsAsyncClient());
    }

    @Bean
    public SqsMessagingMessageConverter messageConverter() {
        SqsMessagingMessageConverter messageConverter = new SqsMessagingMessageConverter();
        messageConverter.setPayloadTypeHeader(DomainEvent.class.getTypeName());
        MappingJackson2MessageConverter payloadConverter = new MappingJackson2MessageConverter();
        payloadConverter.setPrettyPrint(true);
        messageConverter.setPayloadMessageConverter(payloadConverter);
        return messageConverter;
    }
}
