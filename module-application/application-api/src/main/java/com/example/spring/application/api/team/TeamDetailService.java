package com.example.spring.application.api.team;

import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.repository.TeamCommandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamDetailService {
    private final TeamCommandRepository repository;

    public TeamDetailService(TeamCommandRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TeamData invoke(TeamId id) {
        return TeamData.from(repository.findById(id));
    }
}
