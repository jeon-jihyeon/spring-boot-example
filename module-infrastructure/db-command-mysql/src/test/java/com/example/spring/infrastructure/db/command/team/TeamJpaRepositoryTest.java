package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.player.PlayerCommandMessageService;
import com.example.spring.domain.team.TeamCommandMessageService;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.Team;
import com.example.spring.domain.team.model.TeamName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
public class TeamJpaRepositoryTest {
    @Autowired
    private TeamJpaRepository repository;
    @MockBean
    private TeamCommandMessageService teamCommandMessageService;
    @MockBean
    private PlayerCommandMessageService playerMessageService;
    @SpyBean
    private LocalContainerEntityManagerFactoryBean testCommandEntityManagerFactory;

    @Test
    void test() {
        final Team team = Team.create(new TeamCreateCommand(new TeamName("name")));
        final TeamEntity entity = repository.save(TeamEntity.from(TeamData.from(team)));

        repository.save(new TeamEntity(entity.getId(), "name2", entity.getStartsAt(), entity.getPlayerIds()));
        repository.save(entity);
        repository.save(new TeamEntity(entity.getId(), entity.getName(), LocalDateTime.now(), entity.getPlayerIds()));
    }
}
