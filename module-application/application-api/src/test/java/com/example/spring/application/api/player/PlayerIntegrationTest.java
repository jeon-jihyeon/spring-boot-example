package com.example.spring.application.api.player;

import com.example.spring.application.api.BaseEmbeddedDbTest;
import com.example.spring.application.api.player.data.PlayerCommandResponse;
import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.common.ResponseModel;
import com.fasterxml.jackson.databind.JavaType;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class PlayerIntegrationTest extends BaseEmbeddedDbTest {
    <T> ResponseModel<T> map(MvcResult result, Class<T> cls) throws Exception {
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, cls);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }


    @DisplayName("Player 생성 통합 테스트")
    void shouldCreatePlayerByApiSuccessfully() throws Exception {
        var player = map(mvc.perform(MockMvcRequestBuilders.post("/api/players")
                        .content(objectMapper.writeValueAsString(new PlayerCreateRequest("first", "last")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn(), PlayerCommandResponse.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/players/{id}", player.data().id()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(player)));
    }
}
