package com.example.spring.infrastructure.api.command;

import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.query.OutboxQueryApiClient;
import org.springframework.stereotype.Component;

@Component
public class OutboxQueryFeignClient implements OutboxQueryApiClient {
    private final CommandFeignApi api;

    public OutboxQueryFeignClient(CommandFeignApi api) {
        this.api = api;
    }

    @Override
    public void complete(DomainEvent event) {
        api.completeOutboxEvent(event);
    }
}
