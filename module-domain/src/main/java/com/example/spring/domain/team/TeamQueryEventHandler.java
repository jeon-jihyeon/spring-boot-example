package com.example.spring.domain.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInbox;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class TeamQueryEventHandler {
    private final DomainEventInbox inbox;
    private final TeamQueryRepository repository;
    private final TeamCommandApiClient client;

    public TeamQueryEventHandler(DomainEventInbox inbox, TeamQueryRepository repository, TeamCommandApiClient client) {
        this.inbox = inbox;
        this.repository = repository;
        this.client = client;
    }

    @Transactional
    public void handle(DomainEvent event) {
        if (inbox.exists(event.id())) return;
        inbox.save(event);
        repository.save(client.findById(new TeamId(event.modelId())));
    }
}
