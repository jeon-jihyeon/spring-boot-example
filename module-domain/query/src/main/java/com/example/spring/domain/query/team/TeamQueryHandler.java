package com.example.spring.domain.query.team;

import com.example.spring.domain.command.team.TeamCommandApiClient;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.query.InboxQueryService;
import com.example.spring.domain.query.OutboxQueryApiClient;
import org.springframework.stereotype.Component;

@Component
public class TeamQueryHandler {
    private final TeamQueryRepository repository;
    private final InboxQueryService inboxService;
    private final OutboxQueryApiClient outboxClient;
    private final TeamCommandApiClient client;

    public TeamQueryHandler(
            TeamQueryRepository repository,
            InboxQueryService inboxService,
            OutboxQueryApiClient outboxClient,
            TeamCommandApiClient client
    ) {
        this.repository = repository;
        this.inboxService = inboxService;
        this.outboxClient = outboxClient;
        this.client = client;
    }

    public void handle(DomainEvent event) {
        inboxService.receive(event);
        outboxClient.complete(event);
        final TeamId teamId = new TeamId(event.modelId());
        switch (event.type()) {
            case CREATE -> repository.create(client.findById(teamId));
            case UPDATE -> repository.update(client.findById(teamId));
            case DELETE -> repository.deleteById(teamId);
        }
        inboxService.complete(event);
    }
}
