package com.example.spring.boot.persistence.team;

import com.example.spring.boot.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity(name = "teams")
public class TeamEntity extends BaseEntity {
    @Column(length = 30, nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime startsAt;

    public TeamEntity() {
    }
    public TeamEntity(
            Long id,
            String name,
            LocalDateTime startsAt
    ) {
        this.id = id;
        this.name = name;
        this.startsAt = startsAt;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }
}
