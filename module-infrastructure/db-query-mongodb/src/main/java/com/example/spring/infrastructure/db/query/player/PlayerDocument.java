package com.example.spring.infrastructure.db.query.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.FullName;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.infrastructure.db.query.BaseQueryDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "players")
public class PlayerDocument extends BaseQueryDocument {
    private final Grade grade;
    private final String firstName;
    private final String lastName;
    private final Long teamId;

    private PlayerDocument(Long id, Grade grade, String firstName, String lastName, Long teamId) {
        super(id);
        this.grade = grade;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
    }

    private PlayerDocument(Long id, Grade grade, String firstName, String lastName, Long teamId, LocalDateTime createdAt) {
        super(id, createdAt);
        this.grade = grade;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
    }

    public static PlayerDocument create(PlayerData data) {
        return new PlayerDocument(
                data.id().value(),
                data.grade(),
                data.fullName().firstName(),
                data.fullName().lastName(),
                data.teamId().value()
        );
    }

    public PlayerDocument update(PlayerData data) {
        return new PlayerDocument(
                data.id().value(),
                data.grade(),
                data.fullName().firstName(),
                data.fullName().lastName(),
                data.teamId().value(),
                getCreatedAt()
        );
    }

    public PlayerData toData() {
        return new PlayerData(new PlayerId(getId()), grade, new FullName(firstName, lastName), new TeamId(teamId));
    }

    public Grade getGrade() {
        return grade;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getTeamId() {
        return teamId;
    }
}
