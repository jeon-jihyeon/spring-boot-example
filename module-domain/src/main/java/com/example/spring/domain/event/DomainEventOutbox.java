package com.example.spring.domain.event;

import java.time.LocalDateTime;
import java.util.List;

public interface DomainEventOutbox {
    void save(DomainEvent event);

    void publishAll(List<Long> ids, LocalDateTime now);
}
