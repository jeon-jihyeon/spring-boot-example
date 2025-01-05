package com.example.spring.domain.query.player;

import com.example.spring.domain.command.player.PlayerCommandApiClient;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.query.InboxQueryService;
import com.example.spring.domain.query.OutboxQueryApiClient;
import org.springframework.stereotype.Component;

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
        this.client = client;
        this.repository = repository;
    }

    public void handle(DomainEvent event) {
        inboxService.receive(event);
        outboxClient.complete(event);
        final PlayerId playerId = new PlayerId(event.modelId());
        switch (event.type()) {
            case CREATE -> repository.create(client.findById(playerId));
            case UPDATE -> repository.update(client.findById(playerId));
            case DELETE -> repository.deleteById(playerId);
        }
        inboxService.complete(event);
    }
}
