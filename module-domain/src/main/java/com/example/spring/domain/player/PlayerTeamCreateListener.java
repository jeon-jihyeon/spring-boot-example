package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.TeamApiClient;
import com.example.spring.domain.team.TeamId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class PlayerTeamCreateListener {
    private final PlayerBulkCommandRepository repository;
    private final TeamApiClient client;

    public PlayerTeamCreateListener(PlayerBulkCommandRepository repository, TeamApiClient client) {
        this.repository = repository;
        this.client = client;
    }

    @Transactional
    public void invoke(DomainEvent event) {
        final TeamId teamId = new TeamId(event.modelId());
        final Team team = client.findById(teamId);
        repository.updateAll(teamId, team.getPlayerIds().stream().map(PlayerId::value).toList());
    }
}
