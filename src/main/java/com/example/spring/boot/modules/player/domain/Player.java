package com.example.spring.boot.modules.player.domain;

import com.example.spring.boot.modules.team.domain.TeamId;

public class Player {
    private final PlayerId id;
    private final Grade grade;
    private final FullName fullName;
    private final TeamId teamId;

    private Player(PlayerId id, Grade grade, FullName fullName, TeamId teamId) {
        this.id = id;
        this.grade = grade;
        this.fullName = fullName;
        this.teamId = teamId;
    }

    public static Player create(PlayerCreate command) {
        return new Player(
                PlayerId.newId(),
                Grade.NOVICE,
                command.fullName(),
                TeamId.NoTeam
        );
    }
}
