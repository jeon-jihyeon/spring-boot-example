package com.example.spring.application.api.team;

import com.example.spring.application.api.BaseIntegrationTest;
import com.example.spring.application.api.team.data.TeamCreateRequest;
import com.example.spring.application.api.team.data.TeamResponse;
import com.example.spring.application.common.ResponseModel;
import com.fasterxml.jackson.databind.JavaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamIntegrationTest extends BaseIntegrationTest {
    private final TeamCreateRequest TEAM_REQUEST = new TeamCreateRequest("name", List.of(1L));

    ResponseModel<TeamResponse> getTeamResponse(MvcResult result) throws Exception {
        JavaType lt = objectMapper.getTypeFactory().constructParametricType(List.class, TeamResponse.class);
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, lt);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @Test
    @DisplayName("Team 생성 통합 테스트")
    void shouldCreatePlayerByApiSuccessfully() throws Exception {
        Long id = getTeamResponse(mvc.perform(MockMvcRequestBuilders.post("/api/teams")
                        .content(objectMapper.writeValueAsString(TEAM_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()).data().id();

        TeamResponse data = getTeamResponse(mvc.perform(MockMvcRequestBuilders.get("/api/teams/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()).data();

        assertThat(data.id()).isNotNull();
        assertThat(data.name()).isEqualTo(TEAM_REQUEST.name());
        assertThat(data.startsAt()).isBefore(LocalDateTime.now());
        assertThat(data.playerIds().get(0)).isEqualTo(1L);
    }
}
