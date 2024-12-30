package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.QueryInboxService;
import com.example.spring.domain.event.QueryOutboxService;
import com.example.spring.domain.player.model.PlayerId;
import org.springframework.stereotype.Component;

@Component
public class PlayerMessageHandler {
    private final QueryInboxService inboxService;
    private final QueryOutboxService outboxService;
    private final PlayerQueryService service;

    public PlayerMessageHandler(
            QueryInboxService inboxService,
            QueryOutboxService outboxService,
            PlayerQueryService service
    ) {
        this.inboxService = inboxService;
        this.outboxService = outboxService;
        this.service = service;
    }

    public void handle(DomainEvent event) {
        inboxService.receive(event);
        outboxService.complete(event);
        
        final PlayerId playerId = new PlayerId(event.modelId());
        switch (event.type()) {
            case CREATE, UPDATE -> service.save(playerId);
            case DELETE -> service.delete(playerId);
        }
        inboxService.complete(event);
    }
}
