package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventProducer;
import com.example.spring.domain.event.Layer;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import com.example.spring.infrastructure.db.command.player.PlayerEntity;
import com.example.spring.infrastructure.db.command.team.TeamEntity;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandPostInsertEventListener implements PostInsertEventListener {
    private final DomainEventProducer producer;

    public CommandPostInsertEventListener(DomainEventProducer producer) {
        this.producer = producer;
    }

    @Async
    @Override
    public void onPostInsert(PostInsertEvent event) {
        String modelName;
        if (event.getEntity() instanceof PlayerEntity) modelName = "player";
        else if (event.getEntity() instanceof TeamEntity) modelName = "team";
        else throw new EntityNotFoundException();
        producer.send(DomainEvent.createType(Layer.PERSISTENCE, modelName, (Long) event.getId()));
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
