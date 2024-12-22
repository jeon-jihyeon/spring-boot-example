package com.example.spring.infrastructure.event.command;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishBatchRequest;
import software.amazon.awssdk.services.sns.model.PublishBatchRequestEntry;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SnsMessageProducer implements DomainEventProducer {
    private final AwsSnsProperties properties;
    private final ObjectMapper objectMapper;
    private final SnsAsyncClient snsAsyncClient;

    public SnsMessageProducer(
            AwsSnsProperties properties,
            ObjectMapper objectMapper,
            SnsAsyncClient snsAsyncClient
    ) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.snsAsyncClient = snsAsyncClient;
    }

    @Override
    public void send(DomainEvent event) throws JsonProcessingException {
        final AwsSnsMessage message = AwsSnsMessage.from(event);
        snsAsyncClient.publish(PublishRequest.builder()
                .topicArn(properties.topicArn())
                .messageAttributes(Map.of(properties.typeKey(), MessageAttributeValue.builder().stringValue(message.type()).build()))
                .message(objectMapper.writeValueAsString(message))
                .build());
    }

    @Override
    public void sendBatch(List<DomainEvent> events) throws JsonProcessingException {
        if (events != null && !events.isEmpty()) {
            final List<AwsSnsMessage> messages = events.stream().map(AwsSnsMessage::from).toList();
            final List<PublishBatchRequestEntry> entries = new ArrayList<>();
            for (AwsSnsMessage m : messages) entries.add(m.toEntry(objectMapper, properties.typeKey()));

            snsAsyncClient.publishBatch(PublishBatchRequest.builder()
                    // TODO: by topic
                    .topicArn(properties.topicArn())
                    .publishBatchRequestEntries(entries)
                    .build());
        }
    }
}
