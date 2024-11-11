package com.example.spring.boot.persistence;

public interface EntityMapper<D, P> {
    D toDomain(P entity);

    P toEntity(D domain);
}
