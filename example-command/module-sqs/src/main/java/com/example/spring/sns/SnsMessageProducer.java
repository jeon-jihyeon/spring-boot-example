package com.example.spring.sns;

import com.example.spring.domain.outbox.OutboxEvent;
import com.example.spring.sqs.SqsMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishBatchRequest;
import software.amazon.awssdk.services.sns.model.PublishBatchRequestEntry;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SnsMessageProducer {
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

    private Map<String, MessageAttributeValue> getMessageAttributes(String type) {
        return Map.of(properties.typeKey(), MessageAttributeValue.builder().stringValue(type).dataType("String").build(),
                "contentType", MessageAttributeValue.builder().stringValue("application/json").dataType("String").build());
    }

    public void send(OutboxEvent event) throws JsonProcessingException {
        // TODO: restoration
        final SqsMessage message = SqsMessage.from(event);
        snsAsyncClient.publish(PublishRequest.builder()
                .topicArn(properties.topicArn())
                .messageAttributes(getMessageAttributes(message.type()))
                .message(objectMapper.writeValueAsString(message))
                .build());
    }

    public void sendBatch(List<OutboxEvent> events) throws JsonProcessingException {
        // TODO: restoration
        if (events != null && !events.isEmpty()) {
            final List<SqsMessage> messages = events.stream().map(SqsMessage::from).toList();
            final List<PublishBatchRequestEntry> entries = new ArrayList<>();
            for (SqsMessage m : messages) entries.add(m.toEntry(objectMapper, properties.typeKey()));

            snsAsyncClient.publishBatch(PublishBatchRequest.builder()
                    .topicArn(properties.topicArn())
                    .publishBatchRequestEntries(entries)
                    .build());
        }
    }
}
