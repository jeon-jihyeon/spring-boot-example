package com.example.spring.application.api.player;

import com.example.spring.application.api.BaseEmbeddedDbTest;
import com.example.spring.application.api.player.data.PlayerCommandResponse;
import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Player;
import com.fasterxml.jackson.databind.JavaType;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class PlayerIntegrationTest extends BaseEmbeddedDbTest {
    ResponseModel<PlayerCommandResponse> mapResponse(MvcResult result) throws Exception {
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, PlayerCommandResponse.class);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @DisplayName("Player 생성 통합 테스트")
    void shouldCreatePlayerByApiSuccessfully() throws Exception {
        var request = new PlayerCreateRequest("first", "last");
        var expected = ResponseModel.ok(PlayerCommandResponse.from(PlayerData.from(Player.create(request.toCommand()))));

        var id = mapResponse(mvc.perform(MockMvcRequestBuilders.post("/api/players")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expected)))
                .andReturn()).data().id();

        mvc.perform(MockMvcRequestBuilders.get("/api/players/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expected)));
    }
}
