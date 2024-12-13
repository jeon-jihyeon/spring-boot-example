package com.example.spring.application.listener;

import com.example.spring.domain.player.FullName;
import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.repository.PlayerCommandRepository;
import com.example.spring.domain.player.repository.command.PlayerCreateCommand;
import com.example.spring.domain.team.event.TeamCreateEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTeamCreateListenerTest extends BaseIntegrationTest {
    private final PlayerCommandRepository repository;
    private final ApplicationEventPublisher publisher;

    public PlayerTeamCreateListenerTest(PlayerCommandRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Test
    @DisplayName("Player 팀 생성 리스너 테스트")
    void shouldHandleTeamCreationEventSuccessfully() throws Exception {
        final PlayerCreateCommand command = new PlayerCreateCommand(new FullName("first", "last"));
        final Player model = Player.create(command);
        repository.save(model);
        publisher.publishEvent(new TeamCreateEvent(1L, List.of(model.getId().value())));

        assertThat(repository.findById(model.getId()).getTeamId().value()).isEqualTo(1L);
        // fixme:
    }
}
