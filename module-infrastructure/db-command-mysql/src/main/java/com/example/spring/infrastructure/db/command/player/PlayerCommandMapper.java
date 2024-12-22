package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.infrastructure.db.command.BaseCommandMapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandMapper implements BaseCommandMapper<PlayerData, PlayerEntity> {
    @Override
    public PlayerData toDomain(PlayerEntity entity) {
        return PlayerData.of(
                entity.getId(),
                entity.getGrade(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getTeamId()
        );
    }

    @Override
    public PlayerEntity toEntity(PlayerData domain) {
        return new PlayerEntity(
                domain.id().value(),
                domain.grade(),
                domain.fullName().firstName(),
                domain.fullName().lastName(),
                domain.teamId().value()
        );
    }
}
