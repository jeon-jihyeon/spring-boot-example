package com.example.spring.boot.modules.team.api;

import com.example.spring.boot.core.response.ResponseModel;
import com.example.spring.boot.modules.team.api.request.TeamCreateRequest;
import com.example.spring.boot.modules.team.api.response.TeamResponse;
import com.example.spring.boot.modules.team.domain.TeamCreateService;
import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.repository.command.TeamCreateCommand;
import com.example.spring.boot.modules.team.domain.repository.query.TeamQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamCreateControllerTest {
    @Mock
    private TeamCreateService service;
    @InjectMocks
    private TeamCreateController controller;
    private ObjectMapper objectMapper;
    private MockMvc mvc;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Team 생성 API 테스트")
    void shouldReturnValidResponseForTeamCreation() throws Exception {
        final TeamCreateRequest data = new TeamCreateRequest("name");
        final TeamQuery query = TeamQuery.from(Team.create(data.toCommand()));

        when(service.invoke(any(TeamCreateCommand.class))).thenReturn(query);

        // then
        mvc.perform(MockMvcRequestBuilders.post("/api/teams")
                        .content(objectMapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(ResponseModel.ok(TeamResponse.from(query)))));
    }
}