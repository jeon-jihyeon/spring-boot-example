package com.example.spring.domain.team;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.team.model.TeamId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamQueryServiceTest extends BaseUnitTest {
    private static final TeamId TEAM_ID = new TeamId(1L);
    @Mock
    private TeamQueryRepository repository;
    @Mock
    private TeamCommandApiClient client;
    @InjectMocks
    private TeamQueryService service;

    @Test
    void shouldNotSaveWhenClientCausesException() {
        when(client.findById(any(TeamId.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.save(TEAM_ID));

        verify(repository, never()).save(any());
        verify(client, times(1)).findById(any());
    }
}