package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.event.CommandMessageService;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandPostInsertEventListener implements PostInsertEventListener {
    private final CommandMessageService outboxService;

    public CommandPostInsertEventListener(CommandMessageService outboxService) {
        this.outboxService = outboxService;
    }

    @Async
    @Override
    public void onPostInsert(PostInsertEvent event) {
        final Object e = event.getEntity();
        if (e instanceof BaseCommandEntity) outboxService.sendCreateType(((BaseCommandEntity) e).getCommand());
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
