package com.example.spring.domain.event;

import com.example.spring.domain.player.PlayerCommandRepository;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.TeamApiClient;
import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.dto.TeamData;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class TeamCreateEventHandler {
    private final DomainEventInbox inbox;
    private final PlayerCommandRepository repository;
    private final TeamApiClient client;

    public TeamCreateEventHandler(DomainEventInbox inbox, PlayerCommandRepository repository, TeamApiClient client) {
        this.inbox = inbox;
        this.repository = repository;
        this.client = client;
    }

    @Transactional
    public void invoke(DomainEvent event) {
        if (inbox.exists(event.id())) return;

        inbox.save(event);
        final TeamId teamId = new TeamId(event.modelId());
        final TeamData team = client.findById(teamId);
        repository.updateAll(teamId, team.playerIds().stream().map(PlayerId::value).toList());
    }
}
