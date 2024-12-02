package com.example.spring.boot.modules.team.api;

import com.example.spring.boot.core.response.ResponseModel;
import com.example.spring.boot.modules.team.api.data.TeamListResponse;
import com.example.spring.boot.modules.team.domain.repository.TeamQueryRepository;
import com.example.spring.boot.modules.team.domain.repository.condition.TeamCondition;
import com.example.spring.boot.modules.team.domain.repository.query.TeamListQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamListControllerTest {
    @Mock
    private TeamQueryRepository repository;
    @InjectMocks
    private TeamListController controller;
    private ObjectMapper objectMapper;
    private MockMvc mvc;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Team 목록 API 테스트")
    void shouldReturnValidResponseForTeamList() throws Exception {
        final List<TeamListQuery> query = List.of(new TeamListQuery(1L, "name", LocalDateTime.now(), 0));
        when(repository.findTeams(any(TeamCondition.class))).thenReturn(query);

        // then
        mvc.perform(MockMvcRequestBuilders.get("/api/teams"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(ResponseModel.ok(query.stream().map(TeamListResponse::from).toList()))));
    }
}