package com.example.spring.feign;

import com.example.spring.domain.OutboxQueryApiClient;
import com.example.spring.domain.event.DomainEvent;
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
