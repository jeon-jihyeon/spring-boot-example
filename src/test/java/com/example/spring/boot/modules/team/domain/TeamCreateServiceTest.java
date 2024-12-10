package com.example.spring.boot.modules.team.domain;

import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.model.TeamName;
import com.example.spring.boot.modules.team.domain.repository.TeamCommandRepository;
import com.example.spring.boot.modules.team.domain.repository.TeamQueryRepository;
import com.example.spring.boot.modules.team.domain.repository.command.TeamCreateCommand;
import com.example.spring.boot.modules.team.domain.repository.query.TeamQuery;
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
    private TeamQueryRepository queryRepository;
    @Mock
    private ApplicationEventPublisher publisher;
    @InjectMocks
    private TeamCreateService service;

    @Test
    @DisplayName("Team 생성 서비스 테스트")
    void shouldCreateTeamAndReturnValidResponse() {
        final TeamCreateCommand command = new TeamCreateCommand(new TeamName("name"), List.of(1L));
        final Team model = Team.create(command);
        final TeamQuery query = TeamQuery.from(model);
        when(commandRepository.save(any(Team.class))).thenReturn(model.getId());
        when(queryRepository.findTeam(any(TeamId.class))).thenReturn(query);

        assertThat(service.invoke(command)).isEqualTo(query);
    }
}