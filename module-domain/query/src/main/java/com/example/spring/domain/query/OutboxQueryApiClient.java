package com.example.spring.domain.query;

import com.example.spring.domain.event.model.DomainEvent;

public interface OutboxQueryApiClient {
    void complete(DomainEvent event);
}
