package com.example.spring.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

public abstract class BaseQueryDocument {
    @Id
    @Field(value = "_id")
    private Long id;
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public BaseQueryDocument() {
    }

    public BaseQueryDocument(Long id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }

    public BaseQueryDocument(Long id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
