package com.example.spring.application.api.team;

import com.example.spring.application.api.BaseEmbeddedDbTest;
import com.example.spring.application.api.team.data.TeamCommandResponse;
import com.example.spring.application.api.team.data.TeamCreateRequest;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.Team;
import com.fasterxml.jackson.databind.JavaType;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class TeamIntegrationTest extends BaseEmbeddedDbTest {
    ResponseModel<TeamCommandResponse> mapResponse(MvcResult result) throws Exception {
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, TeamCommandResponse.class);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @DisplayName("Team 생성 통합 테스트")
    void shouldCreatePlayerByApiSuccessfully() throws Exception {
        var request = new TeamCreateRequest("name");
        var expected = ResponseModel.ok(TeamCommandResponse.from(TeamData.from(Team.create(request.toCommand()))));

        var id = mapResponse(mvc.perform(MockMvcRequestBuilders.post("/api/teams")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expected)))
                .andReturn()).data().id();

        mvc.perform(MockMvcRequestBuilders.get("/api/teams/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expected)));
    }
}
