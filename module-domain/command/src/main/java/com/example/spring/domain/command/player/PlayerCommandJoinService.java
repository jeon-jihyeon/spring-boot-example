package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.dto.PlayerJoinCommand;
import com.example.spring.domain.command.team.TeamCommandApiClient;
import com.example.spring.domain.command.team.model.TeamId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCommandJoinService {
    private final PlayerCommandRepository repository;
    private final TeamCommandApiClient teamClient;

    public PlayerCommandJoinService(PlayerCommandRepository repository, TeamCommandApiClient teamClient) {
        this.repository = repository;
        this.teamClient = teamClient;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public PlayerData invoke(PlayerJoinCommand command) {
        final PlayerData data = repository.findById(command.playerId());
        final TeamId teamId = teamClient.findById(command.teamId()).id();
        return repository.save(PlayerData.from(data.toModel().joinTeam(teamId)));
    }
}
