package com.example.spring.domain.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.QueryInboxService;
import com.example.spring.domain.event.QueryOutboxService;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamPlayerCommand;
import org.springframework.stereotype.Component;

@Component
public class TeamPlayerHandler {
    private final QueryInboxService inboxService;
    private final QueryOutboxService outboxService;
    private final TeamCommandPlayerService teamPlayerService;

    public TeamPlayerHandler(
            QueryInboxService inboxService,
            QueryOutboxService outboxService,
            TeamCommandPlayerService teamPlayerService
    ) {
        this.inboxService = inboxService;
        this.outboxService = outboxService;
        this.teamPlayerService = teamPlayerService;
    }

    public void handle(DomainEvent event) {
        inboxService.receive(event);
        outboxService.complete(event);
        teamPlayerService.invoke(new TeamPlayerCommand(new PlayerId(event.modelId())));
        inboxService.complete(event);
    }
}
