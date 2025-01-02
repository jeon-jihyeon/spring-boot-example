package com.example.spring.infrastructure.db.query;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

public abstract class BaseQueryDocument implements Persistable<Long> {
    @Id
    protected Long id;
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public BaseQueryDocument() {
    }

    public BaseQueryDocument(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
