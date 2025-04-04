package com.example.spring.mysql.outbox;

import com.example.spring.domain.outbox.OutboxMessageProducer;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OutboxPostInsertEventListener implements PostInsertEventListener {
    private final OutboxMessageProducer producer;

    public OutboxPostInsertEventListener(OutboxMessageProducer producer) {
        this.producer = producer;
    }

    @Async
    @Override
    public void onPostInsert(PostInsertEvent postEvent) {
        final Object entity = postEvent.getEntity();
        if (entity instanceof OutboxEventEntity) producer.send(((OutboxEventEntity) entity).toModel());
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
