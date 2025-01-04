package com.example.spring.application.api.team;

import com.example.spring.application.api.BaseUnitTest;
import com.example.spring.application.api.team.data.TeamCommandResponse;
import com.example.spring.application.api.team.data.TeamCreateRequest;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.command.team.TeamCommandService;
import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.Team;
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
class TeamCommandControllerTest extends BaseUnitTest {
    @Mock
    private TeamCommandService service;
    @InjectMocks
    private TeamCommandController controller;
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
        var request = new TeamCreateRequest("name");
        var data = TeamData.from(Team.create(request.toCommand()));

        when(service.create(any())).thenReturn(data);
        when(service.read(any())).thenReturn(data);

        var expected = objectMapper.writeValueAsString(ResponseModel.ok(TeamCommandResponse.from(data)));
        mvc.perform(MockMvcRequestBuilders.post("/api/teams")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));

        mvc.perform(MockMvcRequestBuilders.get("/api/teams/{id}", data.id().value()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }
}