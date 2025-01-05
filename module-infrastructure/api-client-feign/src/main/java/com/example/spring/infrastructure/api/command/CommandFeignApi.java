package com.example.spring.infrastructure.api.command;

import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.infrastructure.api.FeignExceptionConfig;
import com.example.spring.infrastructure.api.PlayerApiResponse;
import com.example.spring.infrastructure.api.TeamApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "command-api", configuration = FeignExceptionConfig.class)
public interface CommandFeignApi {
    @GetMapping(value = "/teams/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseModel<TeamApiResponse> findTeam(final @PathVariable Long id);

    @GetMapping(value = "/players/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseModel<PlayerApiResponse> findPlayer(final @PathVariable Long id);

    @PatchMapping(value = "/events/outbox", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseModel<?> completeOutboxEvent(final @RequestBody DomainEvent event);
}
