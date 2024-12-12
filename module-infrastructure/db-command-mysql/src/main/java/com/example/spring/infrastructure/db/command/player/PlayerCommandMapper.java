package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.player.Player;
import com.example.spring.infrastructure.db.command.CommandMapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandMapper implements CommandMapper<Player, PlayerEntity> {
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
