package com.example.spring.domain.event;

import java.time.LocalDateTime;
import java.util.List;

public interface DomainEventInbox {
    void save(DomainEvent event);

    void processAll(List<Long> ids, LocalDateTime now);
}
