package com.example.spring.infrastructure.db.event.outbox;

import com.example.spring.domain.event.DomainEventProducer;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OutboxPostInsertEventListener implements PostInsertEventListener {
    private final DomainEventProducer producer;

    public OutboxPostInsertEventListener(DomainEventProducer producer) {
        this.producer = producer;
    }

    @Async
    @Override
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof OutboxEventEntity) {
            producer.send(((OutboxEventEntity) event.getEntity()).toModel().complete());
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
