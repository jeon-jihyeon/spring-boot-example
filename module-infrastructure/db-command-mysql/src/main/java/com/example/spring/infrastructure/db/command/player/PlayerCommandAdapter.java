package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.command.player.PlayerCommandRepository;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class PlayerCommandAdapter implements PlayerCommandRepository {
    private final PlayerJpaRepository repository;

    public PlayerCommandAdapter(PlayerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public PlayerData save(PlayerData player) {
        return repository.save(PlayerEntity.from(player)).toData();
    }

    @Override
    public PlayerData findById(PlayerId id) {
        return repository.findById(id.value()).orElseThrow(EntityNotFoundException::new).toData();
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void deleteById(PlayerId id) {
        repository.deleteById(id.value());
    }
}
