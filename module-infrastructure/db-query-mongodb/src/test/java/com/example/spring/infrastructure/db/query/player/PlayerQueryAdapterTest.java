package com.example.spring.infrastructure.db.query.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.infrastructure.db.query.BaseContextTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerQueryAdapterTest extends BaseContextTest {
    private static final PlayerData PLAYER_DATA = PlayerData.of(11L, Grade.NOVICE, "first", "last", 22L);

    @Autowired
    private PlayerQueryAdapter adapter;

    @Test
    void save() {
        adapter.save(PLAYER_DATA);
        final PlayerData found = adapter.findById(PLAYER_DATA.id());
        assertThat(found.id().value()).isEqualTo(11L);
        assertThat(found.grade()).isEqualTo(Grade.NOVICE);
    }
}