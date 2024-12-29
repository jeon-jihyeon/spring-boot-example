package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.dto.PlayersLeaveCommand;
import com.example.spring.domain.player.dto.PlayersRegisterCommand;
import com.example.spring.domain.player.model.Player;
import com.example.spring.domain.player.model.PlayerId;
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

    public void registerAll(PlayersRegisterCommand command) {
        repository.updateAll(command.teamId(), command.playerIds().stream().map(PlayerId::value).toList());
    }

    public void leaveAll(PlayersLeaveCommand command) {
        repository.updateAll(command.teamId());
    }
}
