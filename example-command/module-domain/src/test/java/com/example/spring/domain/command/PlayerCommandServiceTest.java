package com.example.spring.domain.command;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.command.dto.PlayerCreateCommand;
import com.example.spring.domain.command.dto.PlayerData;
import com.example.spring.domain.command.model.FullName;
import com.example.spring.domain.command.model.Player;
import com.example.spring.domain.command.model.PlayerId;
import com.example.spring.domain.outbox.OutboxEventSaveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerCommandServiceTest extends BaseUnitTest {
    private static final PlayerCreateCommand CREATE_COMMAND = new PlayerCreateCommand(new FullName("first", "last"));
    private static final PlayerData DATA = PlayerData.from(Player.create(CREATE_COMMAND));
    @Mock
    private PlayerCommandRepository commandRepository;
    @Mock
    private OutboxEventSaveService outboxEventSaveService;
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

    @Test
    void shouldNotCreateWhenExceptionOccurs() {
        doThrow(RuntimeException.class).when(commandRepository).save(any());
        assertThrows(RuntimeException.class, () -> service.create(CREATE_COMMAND));

        verify(commandRepository, times(1)).save(any());
        verify(outboxEventSaveService, never()).savePlayerCreatedEvent(any());
    }
}