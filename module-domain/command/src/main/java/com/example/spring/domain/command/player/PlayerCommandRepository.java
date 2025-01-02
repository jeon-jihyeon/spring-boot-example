package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;

import java.util.List;

public interface PlayerCommandRepository {
    PlayerData save(PlayerData player);

    PlayerData findById(PlayerId id);

    void deleteById(PlayerId id);

    List<PlayerId> findIdsByTeamId(TeamId teamId);

    void leaveAll(TeamId teamId);
}
