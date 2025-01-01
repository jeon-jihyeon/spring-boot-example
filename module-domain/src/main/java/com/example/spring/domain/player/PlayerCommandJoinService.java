package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.dto.PlayerJoinCommand;
import com.example.spring.domain.player.dto.PlayerTeamEvent;
import com.example.spring.domain.team.TeamCommandApiClient;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCommandJoinService {
    private final PlayerCommandRepository repository;
    private final ApplicationEventPublisher publisher;
    private final TeamCommandApiClient teamClient;

    public PlayerCommandJoinService(
            PlayerCommandRepository repository,
            ApplicationEventPublisher publisher,
            TeamCommandApiClient teamClient
    ) {
        this.repository = repository;
        this.publisher = publisher;
        this.teamClient = teamClient;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public PlayerData invoke(PlayerJoinCommand command) {
        final PlayerData data = repository.findById(command.playerId());
        final TeamId teamId = teamClient.findById(command.teamId()).id();
        final PlayerData saved = repository.save(PlayerData.from(data.toModel().joinTeam(teamId)));
        publisher.publishEvent(PlayerTeamEvent.from(data.id()));
        return saved;
    }
}
