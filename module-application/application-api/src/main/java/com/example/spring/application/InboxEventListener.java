package com.example.spring.application;

import com.example.spring.domain.event.QueryInboxService;
import com.example.spring.domain.event.dto.InboxCompleteEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class InboxEventListener {
    private final QueryInboxService service;

    public InboxEventListener(QueryInboxService service) {
        this.service = service;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(InboxCompleteEvent event) {
        service.complete(event);
    }
}
