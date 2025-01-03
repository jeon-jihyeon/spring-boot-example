package com.example.spring.application.api.player;

import com.example.spring.application.api.BaseUnitTest;
import com.example.spring.application.api.player.data.PlayerCommandResponse;
import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.command.player.PlayerCommandJoinService;
import com.example.spring.domain.command.player.PlayerCommandLeaveService;
import com.example.spring.domain.command.player.PlayerCommandService;
import com.example.spring.domain.command.player.dto.PlayerCreateCommand;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.command.player.model.Player;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerCommandControllerTest extends BaseUnitTest {
    @Mock
    private PlayerCommandService service;
    @Mock
    private PlayerCommandJoinService joinService;
    @Mock
    private PlayerCommandLeaveService leaveService;
    @Spy
    private ObjectMapper objectMapper;
    @InjectMocks
    private PlayerCommandController controller;
    private MockMvc mvc;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    ResponseModel<PlayerCommandResponse> mapResponse(MvcResult result) throws Exception {
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, PlayerCommandResponse.class);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @Test
    @DisplayName("Player 생성 API 테스트")
    void shouldReturnValidResponseForPlayerCreation() throws Exception {
        final PlayerCreateRequest request = new PlayerCreateRequest("first", "last");
        final PlayerData data = PlayerData.from(Player.create(request.toCommand()));

        when(service.create(any(PlayerCreateCommand.class))).thenReturn(data);
        when(service.read(any(PlayerId.class))).thenReturn(data);

        final String expected = objectMapper.writeValueAsString(ResponseModel.ok(PlayerCommandResponse.from(data)));
        final Long id = mapResponse(mvc.perform(MockMvcRequestBuilders.post("/api/players")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected))
                .andReturn()).data().id();

        final PlayerCommandResponse response = mapResponse(mvc.perform(MockMvcRequestBuilders.get("/api/players/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected))
                .andReturn()).data();

        assertThat(response.id()).isNotNull();
        assertThat(response.grade()).isEqualTo(Grade.NOVICE);
        assertThat(response.teamId()).isEqualTo(TeamId.NoTeam.value());
        assertThat(response.firstName()).isEqualTo(request.firstName());
        assertThat(response.lastName()).isEqualTo(request.lastName());
    }
}