package com.example.spring.application.api.team;

import com.example.spring.application.api.BaseUnitTest;
import com.example.spring.application.api.team.data.TeamCreateRequest;
import com.example.spring.application.api.team.data.TeamResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.team.TeamCommandService;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.Team;
import com.example.spring.domain.team.model.TeamId;
import com.fasterxml.jackson.databind.JavaType;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    ResponseModel<TeamResponse> mapResponse(MvcResult result) throws Exception {
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, TeamResponse.class);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @Test
    @DisplayName("Team 생성 API 테스트")
    void shouldReturnValidResponseForTeamCreation() throws Exception {
        final TeamCreateRequest request = new TeamCreateRequest("name", List.of(1L, 2L));
        final TeamData data = TeamData.from(Team.create(request.toCommand()));

        when(service.create(any(TeamCreateCommand.class))).thenReturn(data);
        when(service.read(any(TeamId.class))).thenReturn(data);

        final String expected = objectMapper.writeValueAsString(ResponseModel.ok(TeamResponse.from(data)));
        final Long id = mapResponse(mvc.perform(MockMvcRequestBuilders.post("/api/teams")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected))
                .andReturn()).data().id();

        final TeamResponse response = mapResponse(mvc.perform(MockMvcRequestBuilders.get("/api/teams/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected))
                .andReturn()).data();

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.startsAt()).isBefore(LocalDateTime.now());
        assertThat(response.playerIds()).isEqualTo(request.playerIds());
    }
}