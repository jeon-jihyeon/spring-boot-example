package com.example.spring.domain.team;

import com.example.spring.domain.player.PlayerCommandApiClient;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.dto.TeamPlayerCommand;
import com.example.spring.domain.team.model.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamCommandPlayerService {
    private final TeamCommandRepository repository;
    private final PlayerCommandApiClient playerClient;

    public TeamCommandPlayerService(TeamCommandRepository repository, PlayerCommandApiClient playerClient) {
        this.repository = repository;
        this.playerClient = playerClient;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public void invoke(TeamPlayerCommand command) {
        final PlayerData player = playerClient.findById(command.playerId());

        repository.findByPlayerId(player.id())
                .ifPresent(data -> repository.save(TeamData.from(data.toModel().removePlayer(player.id()))));

        if (!player.teamId().isNoTeam()) {
            final Team team = repository.findById(player.teamId()).toModel();
            repository.save(TeamData.from(team.addPlayer(command.playerId())));
        }
    }
}
