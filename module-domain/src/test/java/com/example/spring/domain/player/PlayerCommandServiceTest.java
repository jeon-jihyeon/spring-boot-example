package com.example.spring.domain.player;

import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.player.dto.PlayerData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerCommandServiceTest {
    @Mock
    private PlayerCommandRepository commandRepository;
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private DomainEventOutbox outbox;
    @InjectMocks
    private PlayerCommandService service;

    @Test
    @DisplayName("Player 생성 서비스 테스트")
    void shouldCreatePlayerAndReturnValidResponse() {
        final PlayerCreateCommand command = new PlayerCreateCommand(new FullName("first", "last"));
        final PlayerData data = PlayerData.from(Player.create(command));
        when(commandRepository.save(any(PlayerData.class))).thenReturn(data);

        // then
        assertThat(service.create(command)).isEqualTo(data);
    }
}