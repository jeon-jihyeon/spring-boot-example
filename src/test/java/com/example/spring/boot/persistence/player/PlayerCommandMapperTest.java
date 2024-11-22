package com.example.spring.boot.persistence.player;

import com.example.spring.boot.BaseContextTest;
import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.player.domain.model.Player;
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
        final PlayerEntity entity = new PlayerEntity(11L, Grade.NOVICE, "first", "last", 22L);
        final Player model = mapper.toDomain(entity);
        assertThat(model.getId().value()).isEqualTo(11L);
        assertThat(model.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(model.getFullName().firstName()).isEqualTo("first");
        assertThat(model.getFullName().lastName()).isEqualTo("last");
        assertThat(model.getTeamId().value()).isEqualTo(22L);
    }

    @Test
    @DisplayName("Player 모델-엔티티 맵핑 테스트")
    void shouldMapPlayerModelToEntity() {
        final Player model = Player.of(11L, Grade.NOVICE, "first", "last", 22L);
        final PlayerEntity entity = mapper.toEntity(model);
        assertThat(entity.getId()).isEqualTo(11L);
        assertThat(entity.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(entity.getFirstName()).isEqualTo("first");
        assertThat(entity.getLastName()).isEqualTo("last");
        assertThat(entity.getTeamId()).isEqualTo(22L);
    }
}