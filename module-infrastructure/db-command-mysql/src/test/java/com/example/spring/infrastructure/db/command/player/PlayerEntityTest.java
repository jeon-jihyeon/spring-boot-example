package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.infrastructure.db.command.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerEntityTest extends BaseUnitTest {

    @Test
    @DisplayName("PlayerEntity 생성 테스트")
    void shouldCreateEntity() {
        final PlayerEntity entity = PlayerEntity.from(PlayerData.of(11L, Grade.NOVICE, "first", "last", 22L));
        assertThat(entity.getId()).isEqualTo(11L);
        assertThat(entity.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(entity.getFirstName()).isEqualTo("first");
        assertThat(entity.getLastName()).isEqualTo("last");
        assertThat(entity.getTeamId()).isEqualTo(22L);
    }

    @Test
    @DisplayName("PlayerEntity 모델 변환 테스트")
    void shouldMapToDomainModel() {
        final PlayerData data = PlayerEntity.from(PlayerData.of(11L, Grade.NOVICE, "first", "last", 22L)).toData();
        assertThat(data.id().value()).isEqualTo(11L);
        assertThat(data.grade()).isEqualTo(Grade.NOVICE);
        assertThat(data.fullName().firstName()).isEqualTo("first");
        assertThat(data.fullName().lastName()).isEqualTo("last");
        assertThat(data.teamId().value()).isEqualTo(22L);
    }
}