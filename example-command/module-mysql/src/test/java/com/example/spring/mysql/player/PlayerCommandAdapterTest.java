package com.example.spring.mysql.player;

import com.example.spring.common.EntityNotFoundException;
import com.example.spring.domain.command.dto.PlayerData;
import com.example.spring.domain.command.model.Grade;
import com.example.spring.domain.command.model.TeamId;
import com.example.spring.mysql.BaseEmbeddedDbTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerCommandAdapterTest extends BaseEmbeddedDbTest {
    @Autowired
    private PlayerCommandAdapter adapter;

    @Test
    @DisplayName("Player DB 생성-조회-삭제 테스트")
    void shouldBeSavedAndFoundAndDeleted() {
        final PlayerData saved = adapter.save(PlayerData.of(11L, Grade.NOVICE, 0, "first", "last", 22L));
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

    @Test
    void shouldLeaveAll() {
        adapter.save(PlayerData.of(1L, Grade.NOVICE, 0, "first", "last", 22L));
        adapter.save(PlayerData.of(2L, Grade.NOVICE, 0, "first", "last", 22L));
        adapter.save(PlayerData.of(3L, Grade.NOVICE, 0, "first", "last", 22L));

        var teamId = new TeamId(22L);
        assertThat(adapter.findIdsByTeamId(teamId).size()).isEqualTo(3);
        adapter.leaveAll(teamId);
        assertThat(adapter.findIdsByTeamId(teamId).size()).isEqualTo(0);
    }
}