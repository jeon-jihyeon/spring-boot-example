package com.example.spring.boot.persistence.player;

import com.example.spring.boot.BaseContextTest;
import com.example.spring.boot.core.exception.EntityNotFoundException;
import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.player.domain.model.Player;
import com.example.spring.boot.modules.player.domain.model.PlayerId;
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
        final Player model = Player.of(11L, Grade.NOVICE, "first", "last", 22L);
        final PlayerId saved = adapter.save(model);
        assertThat(saved.value()).isEqualTo(11L);

        final Player found = adapter.findById(saved);
        assertThat(found.getId().value()).isEqualTo(11L);
        assertThat(found.getGrade()).isEqualTo(Grade.NOVICE);
        assertThat(found.getFullName().firstName()).isEqualTo("first");
        assertThat(found.getFullName().lastName()).isEqualTo("last");
        assertThat(found.getTeamId().value()).isEqualTo(22L);

        adapter.deleteById(saved);
        assertThrows(EntityNotFoundException.class, () -> adapter.findById(saved));
    }
}