package com.example.spring.application.api.team;

import com.example.spring.application.api.BaseContextTest;
import com.example.spring.application.api.team.data.TeamCreateRequest;
import com.example.spring.application.api.team.data.TeamResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.dto.TeamData;
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

public class TeamIntegrationTest extends BaseContextTest {
    ResponseModel<TeamResponse> mapResponse(MvcResult result) throws Exception {
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, TeamResponse.class);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @Test
    @DisplayName("Team 생성 통합 테스트")
    void shouldCreatePlayerByApiSuccessfully() throws Exception {
        final TeamCreateRequest request = new TeamCreateRequest("name", List.of(1L));
        final ResponseModel<TeamResponse> expected = ResponseModel.ok(TeamResponse.from(TeamData.from(Team.create(request.toCommand()))));
        final Long id = mapResponse(mvc.perform(MockMvcRequestBuilders.post("/api/teams")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expected)))
                .andReturn()).data().id();

        final TeamResponse data = mapResponse(mvc.perform(MockMvcRequestBuilders.get("/api/teams/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expected)))
                .andReturn()).data();

        assertThat(data.id()).isNotNull();
        assertThat(data.name()).isEqualTo(request.name());
        assertThat(data.startsAt()).isBefore(LocalDateTime.now());
        assertThat(data.playerIds()).isEqualTo(request.playerIds());
    }
}
