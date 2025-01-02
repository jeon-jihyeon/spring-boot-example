package com.example.spring.infrastructure.api.query;

import com.example.spring.application.common.ResponseModel;
import com.example.spring.infrastructure.api.FeignExceptionConfig;
import com.example.spring.infrastructure.api.PlayerApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "query-api", configuration = FeignExceptionConfig.class)
public interface QueryFeignApi {
    @GetMapping(value = "/players", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseModel<List<PlayerApiResponse>> findPlayers(final @RequestParam(value = "teamIds") List<Long> teamIds);
}
