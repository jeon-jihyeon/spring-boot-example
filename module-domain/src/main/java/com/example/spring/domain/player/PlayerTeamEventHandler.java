package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.QueryInboxService;
import com.example.spring.domain.event.dto.InboxCompleteEvent;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import com.example.spring.domain.team.dto.TeamDeleteEvent;
import com.example.spring.domain.team.dto.TeamUpdateEvent;
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
            case CREATE -> service.handle(TeamCreateEvent.from(event));
            case DELETE -> service.handle(TeamDeleteEvent.from(event));
            case UPDATE -> service.handle(TeamUpdateEvent.from(event));
        }
        publisher.publishEvent(new InboxCompleteEvent(event.id()));
    }
}
