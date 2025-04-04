package com.example.spring.domain;

import com.example.spring.domain.event.InboxEvent;

public interface OutboxQueryApiClient {
    void complete(InboxEvent event);
}
