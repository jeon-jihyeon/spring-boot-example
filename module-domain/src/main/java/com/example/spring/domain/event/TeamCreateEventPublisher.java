package com.example.spring.domain.event;

public interface TeamCreateEventPublisher {
    void publish(TeamCreateEvent event);
}