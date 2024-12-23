package com.example.spring.domain.event;

import com.example.spring.domain.player.PlayerCommandApiClient;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.player.PlayerQueryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class PlayerEventHandler {
    private final DomainEventInbox inbox;
    private final PlayerQueryRepository repository;
    private final PlayerCommandApiClient client;

    public PlayerEventHandler(DomainEventInbox inbox, PlayerQueryRepository repository, PlayerCommandApiClient client) {
        this.inbox = inbox;
        this.repository = repository;
        this.client = client;
    }

    @Transactional
    public void handle(DomainEvent event) {
        if (inbox.exists(event.id())) return;
        inbox.save(event);
        repository.save(client.findById(new PlayerId(event.modelId())));
    }
}
