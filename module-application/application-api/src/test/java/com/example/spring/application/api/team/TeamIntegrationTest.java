package com.example.spring.application.api.team;

import com.example.spring.application.api.BaseEmbeddedDbTest;
import com.example.spring.application.api.player.data.PlayerCommandResponse;
import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.api.player.data.PlayerJoinRequest;
import com.example.spring.application.api.team.data.TeamCommandResponse;
import com.example.spring.application.api.team.data.TeamCreateRequest;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.command.team.model.TeamId;
import com.fasterxml.jackson.databind.JavaType;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

class TeamIntegrationTest extends BaseEmbeddedDbTest {
    <T> ResponseModel<T> map(MvcResult result, Class<T> cls) throws Exception {
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, cls);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @DisplayName("Team 생성 통합 테스트")
    void shouldCreatePlayerByApiSuccessfully() throws Exception {
        var request = new TeamCreateRequest("name");
        var response = map(mvc.perform(MockMvcRequestBuilders.post("/api/teams")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn(), TeamCommandResponse.class);

        mvc.perform(MockMvcRequestBuilders.get("/api/teams/{id}", response.data().id()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));
    }

    void shouldInt() throws Exception {
        var player = map(mvc.perform(MockMvcRequestBuilders.post("/api/players")
                        .content(objectMapper.writeValueAsString(new PlayerCreateRequest("first", "last")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn(), PlayerCommandResponse.class).data();

        assertThat(player.id()).isNotNull();
        assertThat(player.grade()).isEqualTo(Grade.NOVICE);
        assertThat(player.firstName()).isEqualTo("first");
        assertThat(player.lastName()).isEqualTo("last");
        assertThat(player.teamId()).isEqualTo(TeamId.NoTeam.value());

        var team = map(mvc.perform(MockMvcRequestBuilders.post("/api/teams")
                        .content(objectMapper.writeValueAsString(new TeamCreateRequest("name")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn(), TeamCommandResponse.class).data();

        assertThat(team.id()).isNotNull();
        assertThat(team.name()).isEqualTo("name");
        assertThat(team.startsAt()).isNotNull();


        var joinPlayer = map(mvc.perform(MockMvcRequestBuilders.patch("/api/players/{id}/teams", player.id())
                        .content(objectMapper.writeValueAsString(new PlayerJoinRequest(team.id())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn(), PlayerCommandResponse.class).data();

        assertThat(joinPlayer.id()).isEqualTo(player.id());
        assertThat(joinPlayer.teamId()).isEqualTo(team.id());
    }
}
