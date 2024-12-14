package com.example.spring.application.api.player;

import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.player.command.PlayerCreateCommand;
import com.example.spring.domain.player.repository.PlayerCommandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCreateService {
    private final PlayerCommandRepository commandRepository;

    public PlayerCreateService(PlayerCommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Transactional
    public PlayerId invoke(PlayerCreateCommand command) {
        return commandRepository.save(Player.create(command));
    }
}
