package com.example.spring.domain.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.QueryInboxService;
import com.example.spring.domain.event.QueryOutboxService;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.stereotype.Component;

@Component
public class TeamQueryHandler {
    private final TeamQueryService service;
    private final QueryInboxService inboxService;
    private final QueryOutboxService outboxService;

    public TeamQueryHandler(
            TeamQueryService service,
            QueryInboxService inboxService,
            QueryOutboxService outboxService
    ) {
        this.service = service;
        this.inboxService = inboxService;
        this.outboxService = outboxService;
    }

    public void handle(DomainEvent event) {
        inboxService.receive(event);
        outboxService.complete(event);

        final TeamId teamId = new TeamId(event.modelId());
        switch (event.type()) {
            case CREATE, UPDATE -> service.save(teamId);
            case DELETE -> service.delete(teamId);
        }
        inboxService.complete(event);
    }
}
