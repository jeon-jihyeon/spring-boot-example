package com.example.spring.domain.command;

import com.example.spring.domain.command.dto.PlayerData;
import com.example.spring.domain.command.model.PlayerId;
import com.example.spring.domain.command.model.TeamId;

import java.util.List;

public interface PlayerCommandRepository {
    PlayerData save(PlayerData player);

    PlayerData findById(PlayerId id);

    void deleteById(PlayerId id);

    List<PlayerId> findIdsByTeamId(TeamId teamId);

    void leaveAll(TeamId teamId);
}
