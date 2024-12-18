package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.player.Grade;
import com.example.spring.domain.player.Player;
import com.example.spring.infrastructure.db.BaseContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerCommandMapperTest extends BaseContextTest {
    private final PlayerCommandMapper mapper;

    public PlayerCommandMapperTest(PlayerCommandMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    @DisplayName("PlayerEntity 엔티티-모델 맵핑 테스트")
    void shouldMapPlayerEntityToDomainModel() {
        final Player model = mapper.toDomain(new PlayerEntity(11L, Grade.NOVICE, "first", "last", 22L));
        assertThat(model.getId().value()).isEqualTo(11L);
        assertThat(model.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(model.getFullName().firstName()).isEqualTo("first");
        assertThat(model.getFullName().lastName()).isEqualTo("last");
        assertThat(model.getTeamId().value()).isEqualTo(22L);
    }

    @Test
    @DisplayName("Player 모델-엔티티 맵핑 테스트")
    void shouldMapPlayerModelToEntity() {
        final PlayerEntity entity = mapper.toEntity(Player.of(11L, Grade.NOVICE, "first", "last", 22L));
        assertThat(entity.getId()).isEqualTo(11L);
        assertThat(entity.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(entity.getFirstName()).isEqualTo("first");
        assertThat(entity.getLastName()).isEqualTo("last");
        assertThat(entity.getTeamId()).isEqualTo(22L);
    }
}