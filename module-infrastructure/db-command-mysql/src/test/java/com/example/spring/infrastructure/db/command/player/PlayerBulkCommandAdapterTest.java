package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.player.FullName;
import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.team.TeamId;
import com.example.spring.infrastructure.db.BaseContextTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class PlayerBulkCommandAdapterTest extends BaseContextTest {
    private final PlayerBulkCommandAdapter adapter;
    private final PlayerCommandAdapter commandAdapter;

    public PlayerBulkCommandAdapterTest(PlayerBulkCommandAdapter adapter, PlayerCommandAdapter commandAdapter) {
        this.adapter = adapter;
        this.commandAdapter = commandAdapter;
    }

    @Test
    @DisplayName("Player DB 팀 단위 일괄 수정 테스트")
    void updateAll() {
        final Player saved = commandAdapter.save(Player.create(new PlayerCreateCommand(new FullName("first", "last"))));
        adapter.updateAll(new TeamId(0L), new TeamId(1L));
        final Player found = commandAdapter.findById(saved.getId());

        assertThat(found.getTeamId().value()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Player DB Id 목록 단위 일괄 수정 테스트")
    void testUpdateAll() {
        final List<Long> playerIds = new ArrayList<>();
        final Player model = Player.create(new PlayerCreateCommand(new FullName("first", "last")));

        playerIds.add(commandAdapter.save(model).getId().value());
        playerIds.add(commandAdapter.save(model).getId().value());
        adapter.updateAll(new TeamId(1L), playerIds);

        final Player found = commandAdapter.findById(new PlayerId(playerIds.get(0)));
        assertThat(found.getTeamId().value()).isEqualTo(1L);
    }
}