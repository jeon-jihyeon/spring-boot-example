package com.example.spring.application.api.player;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.repository.PlayerCommandRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerDetailService {
    private final PlayerCommandRepository repository;

    public PlayerDetailService(PlayerCommandRepository repository) {
        this.repository = repository;
    }

    public PlayerData invoke(PlayerId id) {
        return PlayerData.from(repository.findById(id));
    }
}
