package com.example.spring.domain.player;

import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.team.TeamId;

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

    public static Player of(Long id, Grade grade, String firstName, String lastName, Long teamId) {
        return new Player(new PlayerId(id), grade, new FullName(firstName, lastName), new TeamId(teamId));
    }

    public static Player create(PlayerCreateCommand command) {
        return new Player(
                PlayerId.newId(),
                Grade.NOVICE,
                command.fullName(),
                TeamId.NoTeam
        );
    }

    public PlayerId getId() {
        return id;
    }

    public Grade getGrade() {
        return grade;
    }

    public FullName getFullName() {
        return fullName;
    }

    public TeamId getTeamId() {
        return teamId;
    }
}
