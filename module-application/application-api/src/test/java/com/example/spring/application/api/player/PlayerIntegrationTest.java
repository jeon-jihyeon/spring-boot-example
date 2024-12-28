package com.example.spring.application.api.player;

import com.example.spring.application.api.BaseContextTest;
import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.api.player.data.PlayerResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.Grade;
import com.example.spring.domain.player.model.Player;
import com.example.spring.domain.team.model.TeamId;
import com.fasterxml.jackson.databind.JavaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerIntegrationTest extends BaseContextTest {
    ResponseModel<PlayerResponse> mapResponse(MvcResult result) throws Exception {
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, PlayerResponse.class);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @Test
    @DisplayName("Player 생성 통합 테스트")
    void shouldCreatePlayerByApiSuccessfully() throws Exception {
        final PlayerCreateRequest request = new PlayerCreateRequest("first", "last");
        final ResponseModel<PlayerResponse> expected = ResponseModel.ok(PlayerResponse.from(PlayerData.from(Player.create(request.toCommand()))));
        final Long id = mapResponse(mvc.perform(MockMvcRequestBuilders.post("/api/players")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expected)))
                .andReturn()).data().id();

        final PlayerResponse data = mapResponse(mvc.perform(MockMvcRequestBuilders.get("/api/players/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expected)))
                .andReturn()).data();

        assertThat(data.id()).isNotNull();
        assertThat(data.grade()).isEqualTo(Grade.NOVICE);
        assertThat(data.teamId()).isEqualTo(TeamId.NoTeam.value());
        assertThat(data.firstName()).isEqualTo(request.firstName());
        assertThat(data.lastName()).isEqualTo(request.lastName());
    }
}
