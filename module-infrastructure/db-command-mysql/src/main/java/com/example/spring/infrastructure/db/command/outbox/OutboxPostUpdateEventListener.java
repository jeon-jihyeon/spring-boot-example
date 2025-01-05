package com.example.spring.infrastructure.db.command.outbox;

import com.example.spring.domain.event.InboxCommandApiClient;
import com.example.spring.domain.event.model.DomainEvent;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OutboxPostUpdateEventListener implements PostUpdateEventListener {
    private final InboxCommandApiClient client;

    public OutboxPostUpdateEventListener(InboxCommandApiClient client) {
        this.client = client;
    }

    @Async
    @Override
    public void onPostUpdate(PostUpdateEvent postEvent) {
        final Object entity = postEvent.getEntity();
        if (entity instanceof OutboxEventEntity) {
            final DomainEvent event = ((OutboxEventEntity) entity).toModel();
            if (event.completed()) client.create(event.copyInbox());
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
