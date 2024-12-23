package com.example.spring.infrastructure.db.query.player;

import com.example.spring.domain.player.PlayerQueryRepository;
import com.example.spring.domain.player.dto.PlayerData;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerQueryAdapter implements PlayerQueryRepository {
    private final PlayerMongoRepository repository;

    public PlayerQueryAdapter(PlayerMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerData save(PlayerData player) {
        return repository.save(PlayerDocument.from(player)).toData();
    }
}
