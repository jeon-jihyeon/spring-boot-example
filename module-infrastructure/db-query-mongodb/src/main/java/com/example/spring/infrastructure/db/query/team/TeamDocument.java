package com.example.spring.infrastructure.db.query.team;

import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.command.team.model.TeamName;
import com.example.spring.infrastructure.db.query.BaseQueryDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "teams")
public class TeamDocument extends BaseQueryDocument {
    private final String name;
    private final LocalDateTime startsAt;

    private TeamDocument(Long id, String name, LocalDateTime startsAt, LocalDateTime createdAt) {
        super(createdAt);
        this.id = id;
        this.name = name;
        this.startsAt = startsAt;
    }

    public static TeamDocument from(TeamData data) {
        return new TeamDocument(data.id().value(), data.name().value(), data.startsAt(), null);
    }

    public TeamDocument update(TeamData data) {
        return new TeamDocument(data.id().value(), data.name().value(), data.startsAt(), getCreatedAt());
    }

    public TeamData toData() {
        return new TeamData(new TeamId(id), new TeamName(name), startsAt);
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }
}
