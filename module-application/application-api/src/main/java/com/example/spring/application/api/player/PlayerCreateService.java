package com.example.spring.application.api.player;

import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.repository.PlayerCommandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCreateService {
    private final PlayerCommandRepository repository;

    public PlayerCreateService(PlayerCommandRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PlayerData invoke(PlayerCreateCommand command) {
        return PlayerData.from(repository.save(Player.create(command)));
    }
}
