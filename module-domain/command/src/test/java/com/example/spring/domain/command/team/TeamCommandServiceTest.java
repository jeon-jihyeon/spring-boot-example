package com.example.spring.domain.command.team;

import com.example.spring.domain.BaseUnitTest;
import com.example.spring.domain.command.team.dto.TeamCreateCommand;
import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.Team;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.command.team.model.TeamName;
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
class TeamCommandServiceTest extends BaseUnitTest {
    private static final TeamCreateCommand COMMAND = new TeamCreateCommand(new TeamName("name"));
    @Mock
    private TeamCommandRepository commandRepository;
    @InjectMocks
    private TeamCommandService service;

    @Test
    @DisplayName("Team 조회 서비스 테스트")
    void shouldReturnValidResponse() {
        final TeamData data = TeamData.from(Team.create(COMMAND));
        when(commandRepository.findById(any(TeamId.class))).thenReturn(data);

        assertThat(service.read(data.id())).isEqualTo(data);
    }

    @Test
    @DisplayName("Team 생성 서비스 테스트")
    void shouldCreateTeamAndReturnValidResponse() {
        final TeamData data = TeamData.from(Team.create(COMMAND));
        when(commandRepository.save(any(TeamData.class))).thenReturn(data);

        assertThat(service.create(COMMAND)).isEqualTo(data);
    }
}