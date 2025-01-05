package com.example.spring.application.api;

import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.event.OutboxCompleteService;
import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.query.InboxQueryService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DomainEventController {
    private final InboxQueryService inboxService;
    private final OutboxCompleteService outboxService;

    public DomainEventController(InboxQueryService inboxService, OutboxCompleteService outboxService) {
        this.inboxService = inboxService;
        this.outboxService = outboxService;
    }

    @PostMapping("/api/events/inbox")
    public ResponseModel<?> create(final @RequestBody DomainEvent event) {
        inboxService.create(event);
        return ResponseModel.ok();
    }

    @PatchMapping("/api/events/outbox")
    public ResponseModel<?> complete(final @RequestBody DomainEvent event) {
        outboxService.invoke(event);
        return ResponseModel.ok();
    }
}
