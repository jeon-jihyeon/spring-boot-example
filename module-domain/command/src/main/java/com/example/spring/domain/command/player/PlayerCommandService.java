package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerCreateCommand;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Player;
import com.example.spring.domain.command.player.model.PlayerId;
import org.springframework.stereotype.Service;

@Service
public class PlayerCommandService {
    private final PlayerCommandRepository repository;

    public PlayerCommandService(PlayerCommandRepository repository) {
        this.repository = repository;
    }

    public PlayerData read(PlayerId id) {
        return repository.findById(id);
    }

    public PlayerData create(PlayerCreateCommand command) {
        return repository.save(PlayerData.from(Player.create(command)));
    }
}
