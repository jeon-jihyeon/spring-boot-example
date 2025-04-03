package com.example.spring.domain.query;

import java.util.List;

public interface PlayerQueryRepository {
    PlayerQuery create(PlayerQuery player);

    PlayerQuery update(PlayerQuery player);

    PlayerQuery findById(Long id);

    List<PlayerQuery> findPlayersByTeamId(Long teamId);

    void deleteById(Long id);
}
