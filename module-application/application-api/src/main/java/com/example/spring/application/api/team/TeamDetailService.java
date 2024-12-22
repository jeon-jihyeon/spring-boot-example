package com.example.spring.application.api.team;

import com.example.spring.domain.team.TeamCommandRepository;
import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.dto.TeamData;
import org.springframework.stereotype.Service;

@Service
public class TeamDetailService {
    private final TeamCommandRepository repository;

    public TeamDetailService(TeamCommandRepository repository) {
        this.repository = repository;
    }

    public TeamData invoke(TeamId id) {
        return repository.findById(id);
    }
}
