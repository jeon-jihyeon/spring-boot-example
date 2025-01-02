package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import com.example.spring.infrastructure.db.command.BaseContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerCommandAdapterTest extends BaseContextTest {
    private final PlayerCommandAdapter adapter;

    public PlayerCommandAdapterTest(PlayerCommandAdapter adapter) {
        this.adapter = adapter;
    }

    @Test
    @DisplayName("Player DB 생성-조회-삭제 테스트")
    void shouldBeSavedAndFoundAndDeleted() {
        final PlayerData saved = adapter.save(PlayerData.of(11L, Grade.NOVICE, "first", "last", 22L));
        assertThat(saved.id().value()).isEqualTo(11L);

        final PlayerData found = adapter.findById(saved.id());
        assertThat(found.id().value()).isEqualTo(11L);
        assertThat(found.grade()).isEqualTo(Grade.NOVICE);
        assertThat(found.fullName().firstName()).isEqualTo("first");
        assertThat(found.fullName().lastName()).isEqualTo("last");
        assertThat(found.teamId().value()).isEqualTo(22L);

        adapter.deleteById(saved.id());
        assertThrows(EntityNotFoundException.class, () -> adapter.findById(saved.id()));
    }
}