package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.event.DomainEventOutboxService;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandPostInsertEventListener implements PostInsertEventListener {
    private final DomainEventOutboxService outboxService;

    public CommandPostInsertEventListener(DomainEventOutboxService outboxService) {
        this.outboxService = outboxService;
    }

    @Async
    @Override
    public void onPostInsert(PostInsertEvent event) {
        final Object e = event.getEntity();
        if (e instanceof BaseCommandEntity) outboxService.process(((BaseCommandEntity) e).getCommand());
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
