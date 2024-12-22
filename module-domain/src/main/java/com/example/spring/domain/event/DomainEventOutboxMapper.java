package com.example.spring.domain.event;

public interface DomainEventOutboxMapper<E> {
    DomainEvent toDomain(E entity);

    E toEntity(DomainEvent event);
}
