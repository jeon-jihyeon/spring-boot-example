package com.example.spring.domain.team;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.dto.TeamLeavePlayerCommand;
import com.example.spring.domain.team.model.TeamId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamCommandLeavePlayerServiceTest {
    private static final PlayerId PLAYER_ID = new PlayerId(2L);
    private static final TeamId TEAM_ID = new TeamId(1L);
    private static final TeamData TEAM_DATA = TeamData.of(1L, "name", LocalDateTime.now(), List.of(2L));
    @Mock
    private TeamCommandRepository commandRepository;
    @InjectMocks
    private TeamCommandLeavePlayerService service;

    @Test
    void shouldRemovePlayer() {
        when(commandRepository.findById(any())).thenReturn(TEAM_DATA);
        when(commandRepository.save(any())).thenReturn(TEAM_DATA);

        final TeamData data = service.invoke(new TeamLeavePlayerCommand(PLAYER_ID, TEAM_ID));
        assertThat(data).isEqualTo(TEAM_DATA);
    }
}