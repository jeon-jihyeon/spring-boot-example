package com.example.spring.boot.modules.team.domain;

public interface TeamCommandRepository {
    Long save(Team team);

    void delete(TeamId id);
}
