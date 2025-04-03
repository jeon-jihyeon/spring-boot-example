package com.example.spring.mongodb.player;

import com.example.spring.domain.query.PlayerQuery;
import com.example.spring.mongodb.BaseQueryDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "players")
public class PlayerDocument extends BaseQueryDocument {
    private String grade;
    private Integer point;
    private String firstName;
    private String lastName;
    private Long teamId;

    protected PlayerDocument() {
    }

    private PlayerDocument(Long id, String grade, Integer point, String firstName, String lastName, Long teamId) {
        super(id);
        this.grade = grade;
        this.point = point;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
    }

    private PlayerDocument(Long id, String grade, Integer point, String firstName, String lastName, Long teamId, LocalDateTime createdAt) {
        super(id, createdAt);
        this.grade = grade;
        this.point = point;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
    }

    public static PlayerDocument create(PlayerQuery query) {
        return new PlayerDocument(
                query.id(),
                query.grade(),
                query.point(),
                query.firstName(),
                query.lastName(),
                query.teamId()
        );
    }

    public PlayerDocument update(PlayerQuery query) {
        return new PlayerDocument(
                query.id(),
                query.grade(),
                query.point(),
                query.firstName(),
                query.lastName(),
                query.teamId(),
                getCreatedAt()
        );
    }

    public PlayerQuery toQuery() {
        return new PlayerQuery(getId(), grade, point, firstName, lastName, teamId);
    }

    public String getGrade() {
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
