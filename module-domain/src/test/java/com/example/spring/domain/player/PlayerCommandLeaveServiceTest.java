package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.dto.PlayerLeaveCommand;
import com.example.spring.domain.player.model.Grade;
import com.example.spring.domain.player.model.PlayerId;
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
class PlayerCommandLeaveServiceTest {
    private static final PlayerLeaveCommand LEAVE_COMMAND = new PlayerLeaveCommand(new PlayerId(1L));
    private static final PlayerData DATA = PlayerData.of(1L, Grade.C, "first", "last", 2L);
    @Mock
    private PlayerCommandRepository repository;
    @InjectMocks
    private PlayerCommandLeaveService service;


    @Test
    void shouldLeaveFromTeamAndReturnValidResponse() {
        when(repository.findById(any(PlayerId.class))).thenReturn(DATA);
        when(repository.save(any(PlayerData.class))).thenReturn(DATA);
        // then
        assertThat(service.invoke(LEAVE_COMMAND)).isEqualTo(DATA);
    }

    @Test
    void shouldNotSaveWhenExceptionOccursInLeave() {
        when(repository.findById(any(PlayerId.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.invoke(LEAVE_COMMAND));

        // then
        verify(repository, never()).save(any());
    }
}