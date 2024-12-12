package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.player.repository.PlayerCommandRepository;
import com.example.spring.infrastructure.db.command.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandAdapter implements PlayerCommandRepository {
    private final PlayerJpaRepository repository;
    private final PlayerCommandMapper mapper;

    public PlayerCommandAdapter(PlayerJpaRepository repository, PlayerCommandMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PlayerId save(Player player) {
        return new PlayerId(repository.save(mapper.toEntity(player)).getId());
    }

    @Override
    public Player findById(PlayerId id) {
        return mapper.toDomain(repository.findById(id.value()).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void deleteById(PlayerId id) {
        repository.deleteById(id.value());
    }
}
