package com.example.spring.mysql.player;

import com.example.spring.domain.command.dto.PlayerData;
import com.example.spring.domain.command.model.Grade;
import com.example.spring.mysql.BaseCommandEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity(name = "players")
public class PlayerEntity extends BaseCommandEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1, columnDefinition = "varchar(1)")
    private Grade grade;
    @Column(nullable = false)
    private Integer point;
    @Column(length = 30, nullable = false)
    private String firstName;
    @Column(length = 30, nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Long teamId;

    public PlayerEntity() {
    }

    public PlayerEntity(
            Long id,
            Grade grade,
            Integer point,
            String firstName,
            String lastName,
            Long teamId
    ) {
        super(id);
        this.grade = grade;
        this.point = point;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
    }

    public static PlayerEntity from(PlayerData data) {
        return new PlayerEntity(
                data.id().value(),
                data.grade(),
                data.point().value(),
                data.fullName().firstName(),
                data.fullName().lastName(),
                data.teamId().value()
        );
    }

    public PlayerData toData() {
        return PlayerData.of(getId(), grade, point, firstName, lastName, teamId);
    }

    public Grade getGrade() {
        return grade;
    }

    public Integer getPoint() {
        return point;
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
