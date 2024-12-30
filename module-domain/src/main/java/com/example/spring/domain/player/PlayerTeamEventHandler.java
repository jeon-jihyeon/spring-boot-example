package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.QueryInboxService;
import com.example.spring.domain.event.dto.InboxCompleteEvent;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PlayerTeamEventHandler {
    private final PlayerTeamService service;
    private final ApplicationEventPublisher publisher;
    private final QueryInboxService inboxService;

    public PlayerTeamEventHandler(PlayerTeamService service, ApplicationEventPublisher publisher, QueryInboxService inboxService) {
        this.service = service;
        this.publisher = publisher;
        this.inboxService = inboxService;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public void handle(DomainEvent event) {
        inboxService.receive(event);
        switch (event.type()) {
            case CREATE -> service.handleCreate(new TeamId(event.modelId()));
            case DELETE -> service.handleDelete(new TeamId(event.modelId()));
            case UPDATE -> service.handleUpdate(new TeamId(event.modelId()));
        }
        publisher.publishEvent(new InboxCompleteEvent(event.id()));
    }
}
