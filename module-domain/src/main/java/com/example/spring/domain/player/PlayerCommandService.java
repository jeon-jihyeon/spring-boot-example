package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.player.dto.PlayerData;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PlayerCommandService {
    private final PlayerCommandRepository repository;
    private final ApplicationEventPublisher publisher;
    private final DomainEventOutbox outbox;

    public PlayerCommandService(
            PlayerCommandRepository repository,
            ApplicationEventPublisher publisher,
            DomainEventOutbox outbox
    ) {
        this.repository = repository;
        this.publisher = publisher;
        this.outbox = outbox;
    }

    public PlayerData read(PlayerId id) {
        return repository.findById(id);
    }

    @Transactional
    public PlayerData create(PlayerCreateCommand command) {
        final PlayerData data = repository.save(PlayerData.from(Player.create(command)));
        final DomainEvent event = DomainEvent.createType(Player.class.getSimpleName(), data.id().value());
        publisher.publishEvent(event);
        outbox.save(event);
        return data;
    }
}
