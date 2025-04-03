package com.example.spring.api;

import com.example.spring.api.common.ResponseModel;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.OutboxCompleteService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OutboxEventController {
    private final OutboxCompleteService outboxService;

    public OutboxEventController(OutboxCompleteService outboxService) {
        this.outboxService = outboxService;
    }

    @PatchMapping("/api/events/outbox")
    public ResponseModel<?> complete(final @RequestBody DomainEvent event) {
        outboxService.invoke(event);
        return ResponseModel.ok();
    }
}
