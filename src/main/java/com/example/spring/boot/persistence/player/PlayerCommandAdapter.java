package com.example.spring.boot.persistence.player;

import com.example.spring.boot.core.exception.EntityNotFoundException;
import com.example.spring.boot.modules.player.domain.model.Player;
import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.player.domain.repository.PlayerCommandRepository;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandAdapter implements PlayerCommandRepository {
    private final PlayerJpaRepository jpaRepository;
    private final PlayerMapper mapper;

    public PlayerCommandAdapter(PlayerJpaRepository jpaRepository, PlayerMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public PlayerId save(Player player) {
        return new PlayerId(jpaRepository.save(mapper.toEntity(player)).getId());
    }

    @Override
    public Player findById(PlayerId id) {
        return mapper.toDomain(jpaRepository.findById(id.value()).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void deleteById(PlayerId id) {
        jpaRepository.deleteById(id.value());
    }
}
