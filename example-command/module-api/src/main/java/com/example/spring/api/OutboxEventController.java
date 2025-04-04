package com.example.spring.api;

import com.example.spring.common.ResponseModel;
import com.example.spring.domain.outbox.OutboxEvent;
import com.example.spring.domain.outbox.OutboxEventCompleteService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OutboxEventController {
    private final OutboxEventCompleteService outboxService;

    public OutboxEventController(OutboxEventCompleteService outboxService) {
        this.outboxService = outboxService;
    }

    @PatchMapping("/api/outbox")
    public ResponseModel<?> complete(final @RequestBody OutboxEvent event) {
        outboxService.invoke(event);
        return ResponseModel.ok();
    }
}
