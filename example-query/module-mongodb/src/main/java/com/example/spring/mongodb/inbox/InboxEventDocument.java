package com.example.spring.mongodb.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventType;
import com.example.spring.mongodb.BaseQueryDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "inbox_events")
public class InboxEventDocument extends BaseQueryDocument {
    private Boolean completed;
    private DomainEventType type;
    private String queueName;
    private Long modelId;
    private LocalDateTime completedAt;

    public InboxEventDocument() {
    }

    private InboxEventDocument(
            Long id,
            Boolean completed,
            DomainEventType type,
            String queueName,
            Long modelId,
            LocalDateTime completedAt,
            LocalDateTime createdAt
    ) {
        super(id, createdAt);
        this.completed = completed;
        this.type = type;
        this.queueName = queueName;
        this.modelId = modelId;
        this.completedAt = completedAt;
    }

    public static InboxEventDocument from(DomainEvent event) {
        return new InboxEventDocument(
                event.id(),
                event.completed(),
                event.type(),
                event.queueName(),
                event.modelId(),
                event.completedAt(),
                event.createdAt()
        );
    }

    public DomainEvent toModel() {
        return new DomainEvent(getId(), completed, type, queueName, modelId, getCreatedAt(), completedAt);
    }

    public Boolean getCompleted() {
        return completed;
    }

    public DomainEventType getType() {
        return type;
    }

    public String getQueueName() {
        return queueName;
    }

    public Long getModelId() {
        return modelId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
