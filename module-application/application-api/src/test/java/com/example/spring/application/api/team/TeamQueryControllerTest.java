package com.example.spring.application.api.team;

import com.example.spring.application.api.BaseUnitTest;
import com.example.spring.application.api.team.response.TeamQueryResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.command.player.model.Grade;
import com.example.spring.domain.query.player.dto.PlayerQuery;
import com.example.spring.domain.query.team.TeamQueryService;
import com.example.spring.domain.query.team.dto.TeamQuery;
import com.example.spring.domain.query.team.dto.TeamQueryCondition;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class TeamQueryControllerTest extends BaseUnitTest {
    @Mock
    private TeamQueryService service;
    @InjectMocks
    private TeamQueryController controller;
    private ObjectMapper objectMapper;
    private MockMvc mvc;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    ResponseModel<List<TeamQueryResponse>> mapResponse(MvcResult result) throws Exception {
        JavaType lt = objectMapper.getTypeFactory().constructParametricType(List.class, TeamQueryResponse.class);
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, lt);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @Test
    void shouldReturnValidResponse() throws Exception {
        var now = LocalDateTime.now();
        var data = List.of(new TeamQuery(1L, "n", now, List.of(new PlayerQuery(1L, Grade.C, 0, "f", "l", 1L))),
                new TeamQuery(2L, "n", now, List.of(new PlayerQuery(2L, Grade.C, 0, "f", "l", 2L))));
        when(service.findTeams(any(TeamQueryCondition.class))).thenReturn(data);

        var expected = objectMapper.writeValueAsString(ResponseModel.ok(data.stream().map(TeamQueryResponse::from).toList()));
        var response = mapResponse(mvc.perform(MockMvcRequestBuilders.get("/api/teams"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected))
                .andReturn()).data();

        assertThat(response.size()).isEqualTo(2);
    }
}