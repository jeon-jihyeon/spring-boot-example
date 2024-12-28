package com.example.spring.infrastructure.db.event.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutboxService;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OutboxPostUpdateEventListener implements PostUpdateEventListener {
    private final DomainEventOutboxService outboxService;

    public OutboxPostUpdateEventListener(DomainEventOutboxService outboxService) {
        this.outboxService = outboxService;
    }

    @Async
    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof OutboxEventEntity) {
            final DomainEvent e = ((OutboxEventEntity) event.getEntity()).toModel();
            if (e.isProcessed()) outboxService.complete(e);
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
