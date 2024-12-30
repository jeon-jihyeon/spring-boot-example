package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.dto.PlayerLeaveCommand;
import org.springframework.stereotype.Service;

@Service
public class PlayerCommandLeaveService {
    private final PlayerCommandRepository repository;

    public PlayerCommandLeaveService(PlayerCommandRepository repository) {
        this.repository = repository;
    }

    public PlayerData invoke(PlayerLeaveCommand command) {
        return repository.save(PlayerData.from(repository.findById(command.playerId()).toModel().leaveTeam()));
    }
}
