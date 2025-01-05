package com.example.spring.domain.event;

import com.example.spring.domain.event.model.DomainEvent;

public interface InboxCommandApiClient {
    void create(DomainEvent event);
}
