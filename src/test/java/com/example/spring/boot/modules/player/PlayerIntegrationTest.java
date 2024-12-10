package com.example.spring.boot.modules.player;

import com.example.spring.boot.BaseIntegrationTest;
import com.example.spring.boot.core.response.ResponseModel;
import com.example.spring.boot.modules.player.api.data.PlayerCreateRequest;
import com.example.spring.boot.modules.player.api.data.PlayerListResponse;
import com.example.spring.boot.modules.team.api.data.TeamCreateRequest;
import com.fasterxml.jackson.databind.JavaType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

public class PlayerIntegrationTest extends BaseIntegrationTest {

    ResponseModel<List<PlayerListResponse>> getPlayerResponse(MvcResult result) throws Exception {
        JavaType lt = objectMapper.getTypeFactory().constructParametricType(List.class, PlayerListResponse.class);
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, lt);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @Test
    void should() throws Exception {
        PlayerCreateRequest pr = new PlayerCreateRequest("first", "last");
        mvc.perform(MockMvcRequestBuilders.post("/api/players")
                .content(objectMapper.writeValueAsString(pr))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.post("/api/players")
                .content(objectMapper.writeValueAsString(pr))
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
        // fixme:
    }
}
