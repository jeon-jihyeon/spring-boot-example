package com.example.spring.sqs;

import com.example.spring.domain.outbox.OutboxEvent;
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
        String queueName,
        Long entityId,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime completedAt
) {
    public static AwsMessage from(OutboxEvent e) {
        final String queueName = switch (e.type()) {
            case CREATE_PLAYER -> "player.created";
            case ADD_POINT -> "player.point.added";
            case DELETE_PLAYER -> "player.deleted";
        };
        return new AwsMessage(e.id(), e.completed(), queueName, e.entityId(), e.createdAt(), e.completedAt());
    }

    public PublishBatchRequestEntry toEntry(ObjectMapper objectMapper, String typeKey) throws JsonProcessingException {
        return PublishBatchRequestEntry.builder()
                .message(objectMapper.writeValueAsString(this))
                .messageAttributes(Map.of(typeKey, MessageAttributeValue.builder().stringValue(queueName).build()))
                .build();
    }
}
