package com.example.spring.infrastructure.db.query.inbox;

import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.event.model.DomainEventType;
import com.example.spring.infrastructure.db.query.BaseQueryDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "inbox_events")
public class InboxEventDocument extends BaseQueryDocument {
    private final Boolean completed;
    private final DomainEventType type;
    private final String queueName;
    private final Long modelId;
    private final LocalDateTime completedAt;

    public InboxEventDocument(
            Long id,
            Boolean completed,
            DomainEventType type,
            String queueName,
            Long modelId,
            LocalDateTime completedAt
    ) {
        super(id);
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
                event.completedAt()
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
