package com.example.spring.domain.event;

public interface DomainEventRepository {
    void save(DomainEvent event);
}