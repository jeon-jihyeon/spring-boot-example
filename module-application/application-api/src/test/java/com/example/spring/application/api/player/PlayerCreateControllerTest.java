package com.example.spring.application.api.player;

import com.example.spring.application.api.player.PlayerCreateController;
import com.example.spring.application.api.player.PlayerCreateService;
import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.repository.command.PlayerCreateCommand;
import com.example.spring.domain.player.repository.query.PlayerQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.spring.application.api.core.response.ResponseModel;
import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.api.player.data.PlayerResponse;
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
class PlayerCreateControllerTest {
    @Mock
    private PlayerCreateService service;
    @Spy
    private ObjectMapper objectMapper;
    @InjectMocks
    private PlayerCreateController controller;
    private MockMvc mvc;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Player 생성 API 테스트")
    void shouldReturnValidResponseForPlayerCreation() throws Exception {
        final PlayerCreateRequest data = new PlayerCreateRequest("first", "last");
        final PlayerQuery query = PlayerQuery.from(Player.create(data.toCommand()));

        when(service.invoke(any(PlayerCreateCommand.class))).thenReturn(query);

        // then
        mvc.perform(MockMvcRequestBuilders.post("/api/players")
                        .content(objectMapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(ResponseModel.ok(PlayerResponse.from(query)))));
    }
}