package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInbox;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PlayerQueryEventHandler {
    private final DomainEventInbox inbox;
    private final PlayerQueryRepository repository;
    private final PlayerCommandApiClient client;

    public PlayerQueryEventHandler(DomainEventInbox inbox, PlayerQueryRepository repository, PlayerCommandApiClient client) {
        this.inbox = inbox;
        this.repository = repository;
        this.client = client;
    }

    @Transactional(transactionManager = "inboxTransactionManager")
    public void handle(DomainEvent event) {
        if (inbox.exists(event.id())) return;
        inbox.save(event);
        repository.save(client.findById(new PlayerId(event.modelId())));
    }
}
