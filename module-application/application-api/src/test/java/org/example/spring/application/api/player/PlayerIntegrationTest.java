package org.example.spring.application.api.player;

import com.example.spring.domain.player.Grade;
import com.example.spring.domain.team.TeamId;
import com.fasterxml.jackson.databind.JavaType;
import org.example.spring.application.api.BaseIntegrationTest;
import org.example.spring.application.api.core.response.ResponseModel;
import org.example.spring.application.api.player.data.PlayerCreateRequest;
import org.example.spring.application.api.player.data.PlayerListResponse;
import org.example.spring.application.api.team.data.TeamCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerIntegrationTest extends BaseIntegrationTest {
    private final PlayerCreateRequest PLAYER_REQUEST = new PlayerCreateRequest("first", "last");

    ResponseModel<List<PlayerListResponse>> getPlayerResponse(MvcResult result) throws Exception {
        JavaType lt = objectMapper.getTypeFactory().constructParametricType(List.class, PlayerListResponse.class);
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, lt);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @Test
    @DisplayName("Player 생성 통합 테스트")
    void shouldCreatePlayerByApiSuccessfully() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/players")
                .content(objectMapper.writeValueAsString(PLAYER_REQUEST))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/players"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        PlayerListResponse data = getPlayerResponse(result).data().get(0);
        assertThat(data.id()).isNotNull();
        assertThat(data.grade()).isEqualTo(Grade.NOVICE);
        assertThat(data.teamId()).isEqualTo(TeamId.NoTeam.value());
        assertThat(data.firstName()).isEqualTo(PLAYER_REQUEST.firstName());
        assertThat(data.lastName()).isEqualTo(PLAYER_REQUEST.lastName());
    }

    @Test
    @DisplayName("Player 팀 생성 리스너 통합 테스트")
    void shouldHandleTeamCreationEventSuccessfully() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/api/players")
                .content(objectMapper.writeValueAsString(PLAYER_REQUEST))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.post("/api/players")
                .content(objectMapper.writeValueAsString(PLAYER_REQUEST))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/players"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var res = getPlayerResponse(result);
        TeamCreateRequest tr = new TeamCreateRequest("team", res.data().stream().map(PlayerListResponse::id).toList());
        mvc.perform(MockMvcRequestBuilders.post("/api/teams")
                .content(objectMapper.writeValueAsString(tr))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());


        result = mvc.perform(MockMvcRequestBuilders.get("/api/players"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        res = getPlayerResponse(result);
        // TODO:
    }
}
