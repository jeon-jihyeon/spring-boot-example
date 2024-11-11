package com.example.spring.boot.persistence.player;

import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity(name = "players")
public class PlayerEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1, columnDefinition = "varchar(1)")
    private Grade grade;
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
            String firstName,
            String lastName,
            Long teamId
    ) {
        this.id = id;
        this.grade = grade;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
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
