package com.example.spring.domain;

import com.example.spring.domain.event.DomainEvent;

public interface OutboxQueryApiClient {
    void complete(DomainEvent event);
}
