package com.example.spring.boot.modules.player.domain;

import com.example.spring.boot.modules.player.domain.command.CreatePlayerCommand;
import com.example.spring.boot.modules.player.domain.model.Player;
import com.example.spring.boot.modules.player.domain.repository.PlayerCommandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatePlayerService {
    private final PlayerCommandRepository repository;

    public CreatePlayerService(PlayerCommandRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void invoke(CreatePlayerCommand command) {
        repository.save(Player.create(command));
    }
}
