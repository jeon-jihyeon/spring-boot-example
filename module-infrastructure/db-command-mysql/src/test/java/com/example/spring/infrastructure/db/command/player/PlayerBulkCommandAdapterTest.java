package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.player.FullName;
import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.player.repository.command.PlayerCreateCommand;
import com.example.spring.domain.team.TeamId;
import com.example.spring.infrastructure.db.command.BaseContextTest;
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
        final Player model = Player.create(new PlayerCreateCommand(new FullName("first", "last")));
        final PlayerId saved = commandAdapter.save(model);

        adapter.updateAll(new TeamId(0L), new TeamId(1L));
        final Player found = commandAdapter.findById(saved);

        assertThat(found.getTeamId().value()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Player DB Id 목록 단위 일괄 수정 테스트")
    void testUpdateAll() {
        final List<Long> playerIds = new ArrayList<>();
        final Player model = Player.create(new PlayerCreateCommand(new FullName("first", "last")));
        playerIds.add(commandAdapter.save(model).value());
        playerIds.add(commandAdapter.save(model).value());

        adapter.updateAll(new TeamId(1L), playerIds);
        final Player found = commandAdapter.findById(new PlayerId(playerIds.get(0)));

        assertThat(found.getTeamId().value()).isEqualTo(1L);
    }
}