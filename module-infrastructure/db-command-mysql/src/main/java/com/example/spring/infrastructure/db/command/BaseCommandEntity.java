package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.event.dto.DomainEventCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseCommandEntity {
    @Id
    protected Long id;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getId() {
        return id;
    }

    private String getModelName() {
        return getClass().getSimpleName().replace("Entity", "").toLowerCase();
    }

    public DomainEventCommand getCommand() {
        return new DomainEventCommand(getModelName(), getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(id, ((BaseCommandEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
