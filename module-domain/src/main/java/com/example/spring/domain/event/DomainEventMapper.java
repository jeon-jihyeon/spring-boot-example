package com.example.spring.domain.event;

public interface DomainEventMapper<E> {
    DomainEvent toDomain(E entity);

    E toEntity(DomainEvent event);
}
