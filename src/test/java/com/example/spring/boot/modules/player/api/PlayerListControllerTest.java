package com.example.spring.boot.modules.player.api;

import com.example.spring.boot.core.response.ResponseModel;
import com.example.spring.boot.modules.player.api.response.PlayerListResponse;
import com.example.spring.boot.modules.player.domain.model.FullName;
import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.player.domain.repository.PlayerQueryRepository;
import com.example.spring.boot.modules.player.domain.repository.condition.PlayerCondition;
import com.example.spring.boot.modules.player.domain.repository.query.PlayerListQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerListControllerTest {
    @Mock
    private PlayerQueryRepository repository;
    @Spy
    private ObjectMapper objectMapper;
    @InjectMocks
    private PlayerListController controller;
    private MockMvc mvc;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Player 목록 API 테스트")
    void test_invoke() throws Exception {
        final List<PlayerListQuery> query = List.of(new PlayerListQuery(new PlayerId(1L), Grade.NOVICE, new FullName("first", "last"), false));
        when(repository.findPlayers(any(PlayerCondition.class))).thenReturn(query);

        // then
        mvc.perform(MockMvcRequestBuilders.get("/api/players"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(ResponseModel.ok(query.stream().map(PlayerListResponse::from).toList()))));
    }
}