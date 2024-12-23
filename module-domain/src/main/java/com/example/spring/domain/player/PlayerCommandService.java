package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.player.dto.PlayerData;
import jakarta.transaction.Transactional;
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

    @Transactional
    public PlayerData create(PlayerCreateCommand command) {
        return repository.save(PlayerData.from(Player.create(command)));
    }
}
