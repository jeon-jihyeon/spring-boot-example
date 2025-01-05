package com.example.spring.infrastructure.api.query;

import com.example.spring.domain.event.InboxCommandApiClient;
import com.example.spring.domain.event.model.DomainEvent;
import org.springframework.stereotype.Component;

@Component
public class InboxCommandFeignClient implements InboxCommandApiClient {
    private final QueryFeignApi api;

    public InboxCommandFeignClient(QueryFeignApi api) {
        this.api = api;
    }

    @Override
    public void create(DomainEvent event) {
        api.createInboxEvent(event);
    }
}
