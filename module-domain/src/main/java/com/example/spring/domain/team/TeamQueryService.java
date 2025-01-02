package com.example.spring.domain.team;

import com.example.spring.domain.player.PlayerQueryApiClient;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.dto.TeamQuery;
import com.example.spring.domain.team.dto.TeamQueryCondition;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamQueryService {
    private final TeamQueryRepository repository;
    private final TeamCommandApiClient client;
    private final PlayerQueryApiClient playerClient;

    public TeamQueryService(TeamQueryRepository repository, TeamCommandApiClient client, PlayerQueryApiClient playerClient) {
        this.repository = repository;
        this.client = client;
        this.playerClient = playerClient;
    }

    public void save(TeamId teamId) {
        repository.save(client.findById(teamId));
    }

    public void delete(TeamId teamId) {
        repository.deleteById(teamId);
    }

    public List<TeamQuery> findTeams(TeamQueryCondition condition) {
        final List<TeamData> teams = repository.findTeamsAfter(condition.startsAt());
        final List<PlayerData> players = playerClient.findAllByTeamIds(teams.stream().map(TeamData::id).toList());
        return teams.stream().map(team -> TeamQuery.from(team, players.stream()
                .filter(player -> player.teamId().equals(team.id())).toList())).toList();
    }
}
