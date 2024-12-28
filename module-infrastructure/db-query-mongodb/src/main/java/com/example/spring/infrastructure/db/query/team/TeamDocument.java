package com.example.spring.infrastructure.db.query.team;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.TeamId;
import com.example.spring.domain.team.model.TeamName;
import com.example.spring.infrastructure.db.query.BaseQueryDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Document(collection = "teams")
public class TeamDocument extends BaseQueryDocument {
    private final String name;
    private final LocalDateTime startsAt;
    private final Set<Long> playerIds;

    private TeamDocument(
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

    public static TeamDocument from(TeamData data) {
        return new TeamDocument(
                data.id().value(),
                data.name().value(),
                data.startsAt(),
                data.playerIds().stream().map(PlayerId::value).collect(Collectors.toSet())
        );
    }

    public TeamData toData() {
        return new TeamData(new TeamId(id), new TeamName(name), startsAt, playerIds.stream().map(PlayerId::new).toList());
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
