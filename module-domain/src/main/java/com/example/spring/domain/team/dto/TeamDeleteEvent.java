package com.example.spring.domain.team.dto;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.dto.DomainEventCommand;
import com.example.spring.domain.team.model.Team;
import com.example.spring.domain.team.model.TeamId;

public record TeamDeleteEvent(TeamId teamId) {
    public static TeamDeleteEvent from(TeamDeleteCommand command) {
        return new TeamDeleteEvent(command.id());
    }

    public static TeamDeleteEvent from(DomainEvent event) {
        return new TeamDeleteEvent(new TeamId(event.modelId()));
    }

    public DomainEvent generalize() {
        return DomainEvent.createType(new DomainEventCommand(Team.class.getSimpleName(), teamId.value()));
    }
}
