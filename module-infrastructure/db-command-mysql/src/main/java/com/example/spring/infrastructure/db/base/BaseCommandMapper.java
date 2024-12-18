package com.example.spring.infrastructure.db.base;

public interface BaseCommandMapper<D, P> {
    D toDomain(P entity);

    P toEntity(D domain);
}
