package com.example.spring.domain.team.dto;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.dto.DomainEventCommand;
import com.example.spring.domain.team.model.TeamId;

public record TeamUpdateEvent(TeamId teamId) {
    public static TeamUpdateEvent from(TeamData data) {
        return new TeamUpdateEvent(data.id());
    }

    public static TeamUpdateEvent from(DomainEvent event) {
        return new TeamUpdateEvent(new TeamId(event.modelId()));
    }

    public DomainEvent generalize() {
        return DomainEvent.updateType(new DomainEventCommand("team", teamId.value()));
    }
}
