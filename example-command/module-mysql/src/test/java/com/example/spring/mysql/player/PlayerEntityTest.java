package com.example.spring.mysql.player;

import com.example.spring.domain.command.dto.PlayerData;
import com.example.spring.domain.command.model.Grade;
import com.example.spring.mysql.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerEntityTest extends BaseUnitTest {

    @Test
    @DisplayName("PlayerEntity 생성 테스트")
    void shouldCreateEntity() {
        final PlayerEntity entity = PlayerEntity.from(PlayerData.of(11L, Grade.NOVICE, 11, "first", "last", 22L));
        assertThat(entity.getId()).isEqualTo(11L);
        assertThat(entity.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(entity.getPoint()).isEqualTo(11);
        assertThat(entity.getFirstName()).isEqualTo("first");
        assertThat(entity.getLastName()).isEqualTo("last");
        assertThat(entity.getTeamId()).isEqualTo(22L);
    }

    @Test
    @DisplayName("PlayerEntity 모델 변환 테스트")
    void shouldMapToDomainModel() {
        final PlayerData data = PlayerEntity.from(PlayerData.of(11L, Grade.NOVICE, 11, "first", "last", 22L)).toData();
        assertThat(data.id().value()).isEqualTo(11L);
        assertThat(data.grade()).isEqualTo(Grade.NOVICE);
        assertThat(data.point().value()).isEqualTo(11);
        assertThat(data.fullName().firstName()).isEqualTo("first");
        assertThat(data.fullName().lastName()).isEqualTo("last");
        assertThat(data.teamId().value()).isEqualTo(22L);
    }
}