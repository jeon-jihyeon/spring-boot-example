package com.example.spring.application.api.team;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.TeamName;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.repository.TeamCommandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamCreateServiceTest {
    @Mock
    private TeamCommandRepository commandRepository;
    @Mock
    private ApplicationEventPublisher publisher;
    @InjectMocks
    private TeamCreateService service;

    @Test
    @DisplayName("Team 생성 서비스 테스트")
    void shouldCreateTeamAndReturnValidResponse() {
        final TeamCreateCommand command = new TeamCreateCommand(new TeamName("name"), List.of(new PlayerId(1L)));
        final Team model = Team.create(command);
        when(commandRepository.save(any(Team.class))).thenReturn(model);

        assertThat(service.invoke(command)).isEqualTo(TeamData.from(model));
    }
}