package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventLayer;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.infrastructure.db.command.player.PlayerEntity;
import com.example.spring.infrastructure.db.command.team.TeamEntity;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandPostInsertEventListener implements PostInsertEventListener {
    private final DomainEventOutbox outbox;

    public CommandPostInsertEventListener(DomainEventOutbox outbox) {
        this.outbox = outbox;
    }

    @Async
    @Override
    public void onPostInsert(PostInsertEvent event) {
        final Object e = event.getEntity();
        if (e instanceof PlayerEntity || e instanceof TeamEntity) {
            outbox.save(DomainEvent.createType(
                    DomainEventLayer.PERSISTENCE,
                    e.getClass().getSimpleName().replace("Entity", ""),
                    (Long) event.getId()
            ));
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
