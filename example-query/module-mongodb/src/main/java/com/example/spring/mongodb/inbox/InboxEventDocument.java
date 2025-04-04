package com.example.spring.mongodb.inbox;

import com.example.spring.domain.event.InboxEvent;
import com.example.spring.domain.event.InboxEventType;
import com.example.spring.mongodb.BaseQueryDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "inbox_events")
public class InboxEventDocument extends BaseQueryDocument {
    private Boolean completed;
    private InboxEventType type;
    private Long entityId;
    private LocalDateTime completedAt;

    public InboxEventDocument() {
    }

    public InboxEventDocument(
            Long id,
            Boolean completed,
            InboxEventType type,
            Long entityId,
            LocalDateTime completedAt,
            LocalDateTime createdAt
    ) {
        super(id, createdAt);
        this.completed = completed;
        this.type = type;
        this.entityId = entityId;
        this.completedAt = completedAt;
    }

    public static InboxEventDocument from(InboxEvent event) {
        return new InboxEventDocument(
                event.id(),
                event.completed(),
                event.type(),
                event.entityId(),
                event.completedAt(),
                event.createdAt()
        );
    }

    public InboxEvent toModel() {
        return new InboxEvent(getId(), completed, type, entityId, getCreatedAt(), completedAt);
    }

    public Boolean getCompleted() {
        return completed;
    }

    public InboxEventType getType() {
        return type;
    }

    public Long getEntityId() {
        return entityId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
