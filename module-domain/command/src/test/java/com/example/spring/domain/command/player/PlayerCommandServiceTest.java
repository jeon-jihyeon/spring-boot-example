package com.example.spring.domain.command.player;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.command.player.dto.PlayerCreateCommand;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.FullName;
import com.example.spring.domain.command.player.model.Player;
import com.example.spring.domain.command.player.model.PlayerId;
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
    private static final PlayerCreateCommand CREATE_COMMAND = new PlayerCreateCommand(new FullName("first", "last"));
    private static final PlayerData DATA = PlayerData.from(Player.create(CREATE_COMMAND));
    @Mock
    private PlayerCommandRepository commandRepository;
    @InjectMocks
    private PlayerCommandService service;

    @Test
    @DisplayName("Player 조회 서비스 테스트")
    void shouldReturnValidResponse() {
        when(commandRepository.findById(any(PlayerId.class))).thenReturn(DATA);

        // then
        assertThat(service.read(DATA.id())).isEqualTo(DATA);
    }

    @Test
    @DisplayName("Player 생성 서비스 테스트")
    void shouldCreatePlayerAndReturnValidResponse() {
        when(commandRepository.save(any(PlayerData.class))).thenReturn(DATA);

        // then
        assertThat(service.create(CREATE_COMMAND)).isEqualTo(DATA);
    }
}