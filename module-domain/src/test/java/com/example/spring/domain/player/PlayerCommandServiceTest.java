package com.example.spring.domain.player;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.FullName;
import com.example.spring.domain.player.model.Player;
import com.example.spring.domain.player.model.PlayerId;
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
class PlayerCommandServiceTest extends BaseUnitTest {
    @Mock
    private PlayerCommandRepository commandRepository;
    @InjectMocks
    private PlayerCommandService service;

    @Test
    @DisplayName("Player 조회 서비스 테스트")
    void shouldReturnValidResponse() {
        final PlayerData data = PlayerData.from(Player.create(new PlayerCreateCommand(new FullName("first", "last"))));
        when(commandRepository.findById(any(PlayerId.class))).thenReturn(data);

        // then
        assertThat(service.read(data.id())).isEqualTo(data);
    }

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