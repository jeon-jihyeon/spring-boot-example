package com.example.spring.domain.player;

import com.example.spring.domain.player.model.PlayerId;
import org.springframework.stereotype.Component;

@Component
public class PlayerQueryService {
    private final PlayerQueryRepository repository;
    private final PlayerCommandApiClient client;

    public PlayerQueryService(PlayerQueryRepository repository, PlayerCommandApiClient client) {
        this.repository = repository;
        this.client = client;
    }

    public void save(PlayerId playerId) {
        repository.save(client.findById(playerId));
    }

    public void delete(PlayerId playerId) {
        repository.deleteById(playerId);
    }
}
