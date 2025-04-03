package com.example.spring.application.api;

import com.example.spring.api.PlayerQueryController;
import com.example.spring.api.PlayerQueryResponse;
import com.example.spring.common.ResponseModel;
import com.example.spring.domain.query.PlayerQuery;
import com.example.spring.domain.query.PlayerQueryCondition;
import com.example.spring.domain.query.PlayerQueryService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerQueryControllerTest extends BaseUnitTest {
    @Mock
    private PlayerQueryService service;
    @Spy
    private ObjectMapper objectMapper;
    @InjectMocks
    private PlayerQueryController controller;
    private MockMvc mvc;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    ResponseModel<List<PlayerQueryResponse>> mapResponse(MvcResult result) throws Exception {
        JavaType lt = objectMapper.getTypeFactory().constructParametricType(List.class, PlayerQueryResponse.class);
        JavaType t = objectMapper.getTypeFactory().constructParametricType(ResponseModel.class, lt);
        return objectMapper.readValue(result.getResponse().getContentAsString(), t);
    }

    @Test
    void shouldFindPlayers() throws Exception {
        var data = List.of(new PlayerQuery(1L, "C", 0, "f", "l", 1L),
                new PlayerQuery(2L, "C", 0, "f", "l", 2L));
        when(service.findPlayers(any(PlayerQueryCondition.class))).thenReturn(data);

        var expected = objectMapper.writeValueAsString(ResponseModel.ok(data.stream().map(PlayerQueryResponse::from).toList()));
        var response = mapResponse(mvc.perform(MockMvcRequestBuilders.get("/api/players")
                        .param("teamIds", "1", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected))
                .andReturn()).data();

        assertThat(response.size()).isEqualTo(2);
    }
}