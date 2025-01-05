package com.example.spring.domain.query.team;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.query.player.PlayerQueryApiClient;
import com.example.spring.domain.query.team.dto.TeamQuery;
import com.example.spring.domain.query.team.dto.TeamQueryCondition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamQueryService {
    private final TeamQueryRepository repository;
    private final PlayerQueryApiClient playerClient;

    public TeamQueryService(TeamQueryRepository repository, PlayerQueryApiClient playerClient) {
        this.repository = repository;
        this.playerClient = playerClient;
    }

    public List<TeamQuery> findTeams(TeamQueryCondition condition) {
        final List<TeamData> teams = repository.findTeamsAfter(condition.startsAt());
        final List<PlayerData> players = playerClient.findAllByTeamIds(teams.stream().map(TeamData::id).toList());
        return teams.stream().map(team -> TeamQuery.from(team, players.stream()
                .filter(player -> player.teamId().equals(team.id())).toList())).toList();
    }
}
