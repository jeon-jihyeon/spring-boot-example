package com.example.spring.domain.player;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.player.model.PlayerId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerQueryServiceTest extends BaseUnitTest {
    private static final PlayerId PLAYER_ID = new PlayerId(1L);
    @Mock
    private PlayerQueryRepository repository;
    @Mock
    private PlayerCommandApiClient client;
    @InjectMocks
    private PlayerQueryService service;

    @Test
    void shouldNotSaveWhenClientCausesException() {
        when(client.findById(any(PlayerId.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.save(PLAYER_ID));

        verify(repository, never()).save(any());
        verify(client, times(1)).findById(any());
    }
}