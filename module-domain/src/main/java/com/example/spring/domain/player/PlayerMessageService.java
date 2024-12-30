package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.player.model.Player;
import com.example.spring.domain.player.model.PlayerId;
import org.springframework.stereotype.Component;

@Component
public class PlayerMessageService {
    private final DomainEventOutbox outbox;
    private final PlayerMessageProducer producer;

    public PlayerMessageService(DomainEventOutbox outbox, PlayerMessageProducer producer) {
        this.outbox = outbox;
        this.producer = producer;
    }

    private String getModelName() {
        return Player.class.getSimpleName();
    }

    public void sendCreateType(PlayerId playerId) {
        final DomainEvent event = DomainEvent.createType(getModelName(), playerId.value());
        producer.send(event);
        outbox.save(event);
    }

    public void sendUpdateType(PlayerId playerId) {
        final DomainEvent event = DomainEvent.updateType(getModelName(), playerId.value());
        producer.send(event);
        outbox.save(event);
    }

    public void sendDeleteType(PlayerId playerId) {
        final DomainEvent event = DomainEvent.deleteType(getModelName(), playerId.value());
        producer.send(event);
        outbox.save(event);
    }
}
