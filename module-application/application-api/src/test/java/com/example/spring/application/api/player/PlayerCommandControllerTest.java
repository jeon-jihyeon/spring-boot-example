package com.example.spring.application.api.player;

import com.example.spring.application.api.BaseUnitTest;
import com.example.spring.application.api.player.request.PlayerCreateRequest;
import com.example.spring.application.api.player.response.PlayerCommandResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.command.player.PlayerCommandJoinService;
import com.example.spring.domain.command.player.PlayerCommandLeaveService;
import com.example.spring.domain.command.player.PlayerCommandService;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.Player;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

    @Test
    @DisplayName("Player 생성 API 테스트")
    void shouldReturnValidResponseForPlayerCreation() throws Exception {
        var request = new PlayerCreateRequest("first", "last");
        var data = PlayerData.from(Player.create(request.toCommand()));

        when(service.create(any())).thenReturn(data);
        when(service.read(any())).thenReturn(data);

        var expected = objectMapper.writeValueAsString(ResponseModel.ok(PlayerCommandResponse.from(data)));
        mvc.perform(MockMvcRequestBuilders.post("/api/players")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected))
                .andReturn();

        mvc.perform(MockMvcRequestBuilders.get("/api/players/{id}", data.id().value()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected))
                .andReturn();
    }
}