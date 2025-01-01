package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.MessageProducer;
import com.example.spring.domain.player.model.PlayerId;
import org.springframework.stereotype.Component;

@Component
public class PlayerTeamMessageService {
    private final DomainEventOutbox outbox;
    private final MessageProducer producer;

    public PlayerTeamMessageService(DomainEventOutbox outbox, MessageProducer producer) {
        this.outbox = outbox;
        this.producer = producer;
    }

    public void send(PlayerId playerId) {
        final DomainEvent event = DomainEvent.createType("player-team", playerId.value());
        producer.send(event);
        outbox.save(event);
    }
}
