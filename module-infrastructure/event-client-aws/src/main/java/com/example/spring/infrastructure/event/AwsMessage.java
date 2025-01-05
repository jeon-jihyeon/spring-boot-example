package com.example.spring.infrastructure.event;

import com.example.spring.domain.event.model.DomainEvent;
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
        Boolean completed,
        String type,
        String queueName,
        Long modelId,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime completedAt
) {
    public static AwsMessage from(DomainEvent e) {
        return new AwsMessage(e.id(), e.completed(), e.type().name(), e.queueName(), e.modelId(), e.createdAt(), e.completedAt());
    }

    public PublishBatchRequestEntry toEntry(ObjectMapper objectMapper, String typeKey) throws JsonProcessingException {
        return PublishBatchRequestEntry.builder()
                .message(objectMapper.writeValueAsString(this))
                .messageAttributes(Map.of(typeKey, MessageAttributeValue.builder().stringValue(type).build()))
                .build();
    }
}
