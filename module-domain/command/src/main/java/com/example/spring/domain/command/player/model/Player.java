package com.example.spring.domain.command.player.model;

import com.example.spring.domain.command.player.PlayerHasNoTeamException;
import com.example.spring.domain.command.player.PlayerInvalidPointException;
import com.example.spring.domain.command.player.dto.PlayerCreateCommand;
import com.example.spring.domain.command.player.dto.PlayerPointCommand;
import com.example.spring.domain.command.team.model.TeamId;

public class Player {
    private final PlayerId id;
    private final Grade grade;
    private final PlayerPoint point;
    private final FullName fullName;
    private final TeamId teamId;

    private Player(PlayerId id, Grade grade, PlayerPoint point, FullName fullName, TeamId teamId) {
        this.id = id;
        this.grade = grade;
        this.point = point;
        this.fullName = fullName;
        this.teamId = teamId;
    }

    public static Player create(PlayerCreateCommand command) {
        return new Player(
                PlayerId.newId(),
                Grade.NOVICE,
                PlayerPoint.ZERO,
                command.fullName(),
                TeamId.NoTeam
        );
    }

    public static Player of(PlayerId id, Grade grade, PlayerPoint point, FullName fullName, TeamId teamId) {
        return new Player(id, grade, point, fullName, teamId);
    }

    public Player joinTeam(TeamId teamId) {
        return new Player(id, grade, point, fullName, teamId);
    }

    public Player leaveTeam() {
        if (teamId.equals(TeamId.NoTeam)) throw new PlayerHasNoTeamException();
        return new Player(id, grade, point, fullName, TeamId.NoTeam);
    }

    public Player addPoint(PlayerPointCommand command) {
        if (command.playerPoint().value() < 0) throw new PlayerInvalidPointException();
        final PlayerPoint computed = new PlayerPoint(point.value() + command.playerPoint().value());
        return new Player(id, grade, computed, fullName, teamId);
    }

    public PlayerId getId() {
        return id;
    }

    public Grade getGrade() {
        return grade;
    }

    public PlayerPoint getPoint() {
        return point;
    }

    public FullName getFullName() {
        return fullName;
    }

    public TeamId getTeamId() {
        return teamId;
    }
}
