package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerLeaveAllCommand;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.QueryInboxService;
import com.example.spring.domain.event.QueryOutboxService;
import org.springframework.stereotype.Service;

@Service
public class PlayerCommandHandler {
    private final QueryInboxService inboxService;
    private final QueryOutboxService outboxService;
    private final PlayerCommandLeaveAllService service;

    public PlayerCommandHandler(
            QueryInboxService inboxService,
            QueryOutboxService outboxService,
            PlayerCommandLeaveAllService service
    ) {
        this.inboxService = inboxService;
        this.outboxService = outboxService;
        this.service = service;
    }

    public void handle(DomainEvent event) {
        inboxService.receive(event);
        outboxService.complete(event);
        service.invoke(new PlayerLeaveAllCommand(new TeamId(event.modelId())));
        inboxService.complete(event);
    }
}
