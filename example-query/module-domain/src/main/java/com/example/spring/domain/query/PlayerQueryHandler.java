package com.example.spring.domain.query;

import com.example.spring.domain.InboxQueryService;
import com.example.spring.domain.OutboxQueryApiClient;
import com.example.spring.domain.event.InboxEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PlayerQueryHandler {
    private final InboxQueryService inboxService;
    private final OutboxQueryApiClient outboxClient;
    private final PlayerCommandApiClient client;
    private final PlayerQueryRepository repository;

    public PlayerQueryHandler(
            InboxQueryService inboxService,
            OutboxQueryApiClient outboxClient,
            PlayerCommandApiClient client,
            PlayerQueryRepository repository
    ) {
        this.inboxService = inboxService;
        this.outboxClient = outboxClient;
        this.repository = repository;
        this.client = client;
    }

    @Transactional
    public void handle(InboxEvent event) {
        inboxService.receive(event);
        outboxClient.complete(event);
        switch (event.type()) {
            case PLAYER_CREATED -> repository.create(client.findById(event.entityId()));
            case PLAYER_POINT_ADDED -> repository.update(client.findById(event.entityId()));
            case PLAYER_DELETED -> repository.deleteById(event.entityId());
        }
        inboxService.complete(event);
    }
}
