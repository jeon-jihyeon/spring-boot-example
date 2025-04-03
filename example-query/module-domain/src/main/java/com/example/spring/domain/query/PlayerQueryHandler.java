package com.example.spring.domain.query;

import com.example.spring.domain.InboxQueryService;
import com.example.spring.domain.OutboxQueryApiClient;
import com.example.spring.domain.event.DomainEvent;
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
        this.client = client;
        this.repository = repository;
    }

    @Transactional
    public void handle(DomainEvent event) {
        inboxService.receive(event);
        outboxClient.complete(event);
        switch (event.type()) {
            case CREATE -> repository.create(client.findById(event.modelId()));
            case UPDATE -> repository.update(client.findById(event.modelId()));
            case DELETE -> repository.deleteById(event.modelId());
        }
        inboxService.complete(event);
    }
}
