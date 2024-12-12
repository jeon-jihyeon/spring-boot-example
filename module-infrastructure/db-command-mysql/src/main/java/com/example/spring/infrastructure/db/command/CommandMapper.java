package com.example.spring.infrastructure.db.command;

public interface CommandMapper<D, P> {
    D toDomain(P entity);

    P toEntity(D domain);
}
