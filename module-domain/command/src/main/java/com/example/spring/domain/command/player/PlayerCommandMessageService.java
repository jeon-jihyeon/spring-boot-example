package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerLeaveAllEvent;
import com.example.spring.domain.command.player.model.Player;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.event.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerCommandMessageService {
    private static final String QUEUE_NAME = Player.class.getSimpleName().toLowerCase();
    private final DomainEventOutbox outbox;
    private final MessageProducer producer;
    private final MessageBatchProducer batchProducer;

    public PlayerCommandMessageService(DomainEventOutbox outbox, MessageProducer producer, MessageBatchProducer batchProducer) {
        this.outbox = outbox;
        this.producer = producer;
        this.batchProducer = batchProducer;
    }

    public void send(DomainEventType type, PlayerId playerId) {
        final DomainEvent event = DomainEvent.of(type, QUEUE_NAME, playerId.value());
        producer.send(event);
        outbox.save(event);
    }

    public void sendBatch(PlayerLeaveAllEvent event) {
        final List<DomainEvent> events = DomainEvent.updateTypes(QUEUE_NAME, event.playerIds().stream().map(PlayerId::value).toList());
        batchProducer.sendBatch(events);
        outbox.createAll(events);
    }
}
