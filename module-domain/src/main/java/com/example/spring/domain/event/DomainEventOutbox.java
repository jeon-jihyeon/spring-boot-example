package com.example.spring.domain.event;

import com.example.spring.domain.event.dto.DomainEventCommand;

import java.time.LocalDateTime;
import java.util.List;

public interface DomainEventOutbox {
    void save(DomainEvent event);

    DomainEvent findEvent(DomainEventCommand command);

    void publishAll(List<Long> ids, LocalDateTime now);
}
