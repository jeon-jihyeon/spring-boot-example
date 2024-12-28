package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.infrastructure.db.command.BaseCommandEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "teams")
public class TeamEntity extends BaseCommandEntity {
    @Column(length = 30, nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime startsAt;
    @ElementCollection(fetch = FetchType.EAGER)
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

    public static TeamEntity from(TeamData data) {
        return new TeamEntity(
                data.id().value(),
                data.name().value(),
                data.startsAt(),
                data.playerIds().stream().map(PlayerId::value).collect(Collectors.toSet())
        );
    }

    public TeamData toData() {
        return TeamData.of(id, name, startsAt, playerIds.stream().toList());
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
