package com.example.spring.mongodb.player;

import com.example.spring.domain.query.PlayerQuery;
import com.example.spring.mongodb.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerDocumentTest extends BaseUnitTest {
    private static final PlayerQuery PLAYER = new PlayerQuery(11L, "NOVICE", 11, "first", "last", 22L);

    @Test
    @DisplayName("PlayerDocument 생성 테스트")
    void shouldCreateDocument() {
        final PlayerDocument document = PlayerDocument.create(PLAYER);
        assertThat(document.getId()).isEqualTo(11L);
        assertThat(document.getGrade()).isEqualTo("NOVICE");
        assertThat(document.getPoint()).isEqualTo(11);
        assertThat(document.getFirstName()).isEqualTo("first");
        assertThat(document.getLastName()).isEqualTo("last");
        assertThat(document.getTeamId()).isEqualTo(22L);
    }

    @Test
    @DisplayName("PlayerDocument 모델 변환 테스트")
    void shouldMapToDomainModel() {
        var query = PlayerDocument.create(PLAYER).toQuery();
        assertThat(query.id()).isEqualTo(11L);
        assertThat(query.grade()).isEqualTo("NOVICE");
        assertThat(query.point()).isEqualTo(11);
        assertThat(query.firstName()).isEqualTo("first");
        assertThat(query.lastName()).isEqualTo("last");
        assertThat(query.teamId()).isEqualTo(22L);
    }
}