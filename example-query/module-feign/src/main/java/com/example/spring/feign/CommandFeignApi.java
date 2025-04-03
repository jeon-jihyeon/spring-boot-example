package com.example.spring.feign;

import com.example.spring.common.ResponseModel;
import com.example.spring.domain.event.DomainEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "command-api", configuration = FeignExceptionConfig.class)
public interface CommandFeignApi {
    @GetMapping(value = "/players/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseModel<PlayerApiResponse> findPlayer(final @PathVariable Long id);

    @PatchMapping(value = "/events/outbox", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseModel<?> completeOutboxEvent(final @RequestBody DomainEvent event);
}
