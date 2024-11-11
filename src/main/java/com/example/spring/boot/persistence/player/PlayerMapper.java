package com.example.spring.boot.persistence.player;

import com.example.spring.boot.modules.player.domain.model.Player;
import com.example.spring.boot.persistence.EntityMapper;

public class PlayerMapper implements EntityMapper<Player, PlayerEntity> {
    @Override
    public Player toDomain(PlayerEntity entity) {
        return Player.of(
                entity.getId(),
                entity.getGrade(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getTeamId()
        );
    }

    @Override
    public PlayerEntity toEntity(Player domain) {
        return new PlayerEntity(
                domain.getId().value(),
                domain.getGrade(),
                domain.getFullName().firstName(),
                domain.getFullName().lastName(),
                domain.getTeamId().value()
        );
    }
}
