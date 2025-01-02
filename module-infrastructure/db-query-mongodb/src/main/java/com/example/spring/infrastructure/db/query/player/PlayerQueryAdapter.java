package com.example.spring.infrastructure.db.query.player;

import com.example.spring.domain.player.PlayerQueryRepository;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.infrastructure.db.query.DocumentNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlayerQueryAdapter implements PlayerQueryRepository {
    private final PlayerMongoRepository repository;

    public PlayerQueryAdapter(PlayerMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerData save(PlayerData player) {
        final Optional<PlayerDocument> existing = repository.findById(player.id().value());
        return repository.save(existing.isPresent() ? existing.get().update(player) : PlayerDocument.from(player)).toData();
    }

    @Override
    public PlayerData findById(PlayerId id) {
        return repository.findById(id.value()).orElseThrow(DocumentNotFoundException::new).toData();
    }

    @Override
    public void deleteById(PlayerId id) {
        repository.deleteById(id.value());
    }
}
