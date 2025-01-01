package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.dto.PlayerLeaveCommand;
import com.example.spring.domain.player.dto.PlayerTeamEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCommandLeaveService {
    private final PlayerCommandRepository repository;
    private final ApplicationEventPublisher publisher;

    public PlayerCommandLeaveService(PlayerCommandRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public PlayerData invoke(PlayerLeaveCommand command) {
        final PlayerData data = repository.findById(command.playerId());
        final PlayerData saved = repository.save(PlayerData.from(data.toModel().leaveTeam()));
        publisher.publishEvent(PlayerTeamEvent.from(data.id()));
        return saved;
    }
}
