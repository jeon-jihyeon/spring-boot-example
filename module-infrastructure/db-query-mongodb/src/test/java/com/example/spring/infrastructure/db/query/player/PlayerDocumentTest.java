package com.example.spring.infrastructure.db.query.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.infrastructure.db.query.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerDocumentTest extends BaseUnitTest {
    private static final PlayerData PLAYER_DATA = PlayerData.of(11L, Grade.NOVICE, "first", "last", 22L);

    @Test
    @DisplayName("PlayerDocument 생성 테스트")
    void shouldCreateDocument() {
        final PlayerDocument document = PlayerDocument.create(PLAYER_DATA);
        assertThat(document.getId()).isEqualTo(11L);
        assertThat(document.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(document.getFirstName()).isEqualTo("first");
        assertThat(document.getLastName()).isEqualTo("last");
        assertThat(document.getTeamId()).isEqualTo(22L);
    }

    @Test
    @DisplayName("PlayerDocument 모델 변환 테스트")
    void shouldMapToDomainModel() {
        final PlayerData data = PlayerDocument.create(PLAYER_DATA).toData();
        assertThat(data.id().value()).isEqualTo(11L);
        assertThat(data.grade()).isEqualTo(Grade.NOVICE);
        assertThat(data.fullName().firstName()).isEqualTo("first");
        assertThat(data.fullName().lastName()).isEqualTo("last");
        assertThat(data.teamId().value()).isEqualTo(22L);
    }
}