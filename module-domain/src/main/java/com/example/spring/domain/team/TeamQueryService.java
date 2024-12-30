package com.example.spring.domain.team;

import com.example.spring.domain.team.model.TeamId;
import org.springframework.stereotype.Component;

@Component
public class TeamQueryService {
    private final TeamQueryRepository repository;
    private final TeamCommandApiClient client;

    public TeamQueryService(TeamQueryRepository repository, TeamCommandApiClient client) {
        this.repository = repository;
        this.client = client;
    }

    public void save(TeamId teamId) {
        repository.save(client.findById(teamId));
    }

    public void delete(TeamId teamId) {
        repository.deleteById(teamId);
    }
}
