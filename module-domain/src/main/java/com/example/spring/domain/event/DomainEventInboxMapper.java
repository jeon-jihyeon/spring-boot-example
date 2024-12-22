package com.example.spring.domain.event;

public interface DomainEventInboxMapper<E> {
    DomainEvent toDomain(E entity);

    E toEntity(DomainEvent event);
}
