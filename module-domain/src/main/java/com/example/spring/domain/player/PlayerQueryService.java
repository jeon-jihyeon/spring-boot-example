package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEvent;
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

    public void create(DomainEvent event) {
        repository.save(client.findById(new PlayerId(event.modelId())));
    }
}
