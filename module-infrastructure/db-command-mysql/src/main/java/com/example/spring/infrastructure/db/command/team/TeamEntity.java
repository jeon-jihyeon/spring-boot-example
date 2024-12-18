package com.example.spring.infrastructure.db.command.team;

import com.example.spring.infrastructure.db.base.BaseCommandEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "teams")
public class TeamEntity extends BaseCommandEntity {
    @Column(length = 30, nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime startsAt;
    @ElementCollection
    private Set<Long> playerIds;

    public TeamEntity() {
    }

    public TeamEntity(
            Long id,
            String name,
            LocalDateTime startsAt,
            Set<Long> playerIds
    ) {
        this.id = id;
        this.name = name;
        this.startsAt = startsAt;
        this.playerIds = playerIds;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public Set<Long> getPlayerIds() {
        return playerIds;
    }
}
