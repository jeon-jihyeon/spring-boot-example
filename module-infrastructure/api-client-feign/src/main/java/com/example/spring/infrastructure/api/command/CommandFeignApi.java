package com.example.spring.infrastructure.api.command;

import com.example.spring.application.common.ResponseModel;
import com.example.spring.infrastructure.api.FeignExceptionConfig;
import com.example.spring.infrastructure.api.PlayerApiResponse;
import com.example.spring.infrastructure.api.TeamApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "command-api", configuration = FeignExceptionConfig.class)
public interface CommandFeignApi {
    @GetMapping(value = "/teams/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseModel<TeamApiResponse> getTeam(@PathVariable Long id);

    @GetMapping(value = "/players/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseModel<PlayerApiResponse> getPlayer(@PathVariable Long id);
}
