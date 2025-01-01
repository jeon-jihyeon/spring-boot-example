package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.DomainEventType;
import com.example.spring.domain.event.MessageProducer;
import com.example.spring.domain.player.model.Player;
import com.example.spring.domain.player.model.PlayerId;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandMessageService {
    private final DomainEventOutbox outbox;
    private final MessageProducer producer;

    public PlayerCommandMessageService(DomainEventOutbox outbox, MessageProducer producer) {
        this.outbox = outbox;
        this.producer = producer;
    }

    public void send(DomainEventType type, PlayerId playerId) {
        final DomainEvent event = DomainEvent.of(type, Player.class.getSimpleName().toLowerCase(), playerId.value());
        producer.send(event);
        outbox.save(event);
    }
}
