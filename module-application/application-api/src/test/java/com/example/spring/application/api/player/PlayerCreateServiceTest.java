package com.example.spring.application.api.player;

import com.example.spring.domain.player.FullName;
import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.repository.PlayerCommandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerCreateServiceTest {
    @Mock
    private PlayerCommandRepository commandRepository;
    @InjectMocks
    private PlayerCreateService service;

    @Test
    @DisplayName("Player 생성 서비스 테스트")
    void shouldCreatePlayerAndReturnValidResponse() {
        final PlayerCreateCommand command = new PlayerCreateCommand(new FullName("first", "last"));
        final Player model = Player.create(command);
        when(commandRepository.save(any(Player.class))).thenReturn(model);

        // then
        assertThat(service.invoke(command)).isEqualTo(PlayerData.from(model));
    }
}