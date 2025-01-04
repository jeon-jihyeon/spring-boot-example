package com.example.spring.infrastructure.db.command.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.QueryNewInboxService;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OutboxPostUpdateEventListener implements PostUpdateEventListener {
    private final QueryNewInboxService service;

    public OutboxPostUpdateEventListener(QueryNewInboxService service) {
        this.service = service;
    }

    @Async
    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        final Object e = event.getEntity();
        if (e instanceof OutboxEventEntity) {
            final DomainEvent outboxEvent = ((OutboxEventEntity) e).toModel();
            if (outboxEvent.completed()) service.invoke(outboxEvent);
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
