package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.dto.PlayerLeaveCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCommandLeaveService {
    private final PlayerCommandRepository repository;

    public PlayerCommandLeaveService(PlayerCommandRepository repository) {
        this.repository = repository;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public PlayerData invoke(PlayerLeaveCommand command) {
        return repository.save(PlayerData.from(repository.findById(command.playerId()).toModel().leaveTeam()));
    }
}
