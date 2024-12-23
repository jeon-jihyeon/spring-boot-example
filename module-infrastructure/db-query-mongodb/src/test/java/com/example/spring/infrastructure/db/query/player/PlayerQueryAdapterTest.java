package com.example.spring.infrastructure.db.query.player;

import com.example.spring.domain.player.Grade;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.infrastructure.db.query.BaseQueryDbTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerQueryAdapterTest extends BaseQueryDbTest {

    @Autowired
    private PlayerMongoRepository repository;

    @Test
    void save() {
        repository.save(PlayerDocument.from(PlayerData.of(11L, Grade.NOVICE, "first", "last", 22L)));
        final PlayerData found = repository.findById(11L).orElseThrow().toData();
        assertThat(found.id().value()).isEqualTo(11L);
    }
}