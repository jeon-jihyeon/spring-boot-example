package com.example.spring.infrastructure.api;

import com.example.spring.application.common.ResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "command-api", configuration = FeignExceptionConfig.class)
public interface CommandFeignApi {
    @GetMapping(value = "/teams/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseModel<TeamResponse> getTeam(@PathVariable Long id);
}
