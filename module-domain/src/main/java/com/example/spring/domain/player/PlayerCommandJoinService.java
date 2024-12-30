package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.dto.PlayerJoinCommand;
import org.springframework.stereotype.Service;

@Service
public class PlayerCommandJoinService {
    private final PlayerCommandRepository repository;

    public PlayerCommandJoinService(PlayerCommandRepository repository) {
        this.repository = repository;
    }

    public PlayerData invoke(PlayerJoinCommand command) {
        return repository.save(PlayerData.from(repository.findById(command.playerId()).toModel().joinTeam(command.teamId())));
    }
}
