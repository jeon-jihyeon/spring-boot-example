package com.example.spring.domain.command.dto;

import com.example.spring.domain.command.model.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerDataTest {

    @Test
    void shouldCreateFromModel() {
        var id = new PlayerId(1L);
        var point = new PlayerPoint(100);
        var name = new FullName("first", "last");
        var teamId = new TeamId(2L);
        var model = Player.of(id, Grade.A, point, name, teamId);
        var data = PlayerData.from(model);

        assertThat(data.id()).isEqualTo(id);
        assertThat(data.grade()).isEqualTo(Grade.A);
        assertThat(data.point()).isEqualTo(point);
        assertThat(data.teamId()).isEqualTo(teamId);
        assertThat(data.fullName()).isEqualTo(name);
    }

    @Test
    void shouldCreateFromBasicTypes() {
        var data = PlayerData.of(1L, Grade.A, 100, "first", "last", 2L);
        assertThat(data.id().value()).isEqualTo(1L);
        assertThat(data.grade()).isEqualTo(Grade.A);
        assertThat(data.point().value()).isEqualTo(100);
        assertThat(data.teamId().value()).isEqualTo(2L);
        assertThat(data.fullName().firstName()).isEqualTo("first");
        assertThat(data.fullName().lastName()).isEqualTo("last");
    }

    @Test
    void shouldConvertToModel() {
        var id = new PlayerId(1L);
        var point = new PlayerPoint(100);
        var name = new FullName("first", "last");
        var teamId = new TeamId(2L);
        var model = new PlayerData(id, Grade.A, point, name, teamId).toModel();

        assertThat(model.getId()).isEqualTo(id);
        assertThat(model.getGrade()).isEqualTo(Grade.A);
        assertThat(model.getPoint()).isEqualTo(point);
        assertThat(model.getTeamId()).isEqualTo(teamId);
        assertThat(model.getFullName()).isEqualTo(name);
    }
}