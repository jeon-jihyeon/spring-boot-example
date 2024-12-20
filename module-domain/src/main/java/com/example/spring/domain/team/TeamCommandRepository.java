package com.example.spring.domain.team;

public interface TeamCommandRepository {
    Team save(Team team);

    Team findById(TeamId id);

    void deleteById(TeamId id);
}
