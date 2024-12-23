package com.example.spring.infrastructure.event;

import com.example.spring.domain.event.DomainEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishBatchRequestEntry;

import java.time.LocalDateTime;
import java.util.Map;

public record AwsMessage(
        Long id,
        String layer,
        String type,
        String modelName,
        Long modelId,
        Boolean completed,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime completedAt,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {
    public static AwsMessage from(DomainEvent e) {
        return new AwsMessage(e.id(), e.layer().name(), e.type().name(), e.modelName(), e.modelId(), e.completed(), e.completedAt(), e.createdAt());
    }

    public PublishBatchRequestEntry toEntry(ObjectMapper objectMapper, String typeKey) throws JsonProcessingException {
        return PublishBatchRequestEntry.builder()
                .message(objectMapper.writeValueAsString(this))
                .messageAttributes(Map.of(typeKey, MessageAttributeValue.builder().stringValue(type).build()))
                .build();
    }

    public String getQueueName() {
        // TODO: sns fan-out logic
        return String.format("%s-%s-%s", modelName, type.toLowerCase(), layer.toLowerCase());
    }
}
