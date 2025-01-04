package com.example.spring.infrastructure.db.command.outbox;

import com.example.spring.domain.event.MessageProducer;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OutboxPostInsertEventListener implements PostInsertEventListener {
    private final MessageProducer producer;

    public OutboxPostInsertEventListener(MessageProducer producer) {
        this.producer = producer;
    }

    @Async
    @Override
    public void onPostInsert(PostInsertEvent event) {
        final Object e = event.getEntity();
        if (e instanceof OutboxEventEntity) producer.send(((OutboxEventEntity) e).toModel());
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
