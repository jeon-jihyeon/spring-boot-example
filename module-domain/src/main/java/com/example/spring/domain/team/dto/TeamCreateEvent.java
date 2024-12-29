package com.example.spring.domain.team.dto;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.dto.DomainEventCommand;
import com.example.spring.domain.team.model.TeamId;

public record TeamCreateEvent(TeamId teamId) {
    public static TeamCreateEvent from(TeamData data) {
        return new TeamCreateEvent(data.id());
    }

    public DomainEvent generalize() {
        return DomainEvent.createType(new DomainEventCommand("team", teamId.value()));
    }
}
