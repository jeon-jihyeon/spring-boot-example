package com.example.spring.domain.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInbox;
import com.example.spring.domain.player.PlayerCommandRepository;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.dto.TeamData;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class TeamEventHandler {
    private final DomainEventInbox inbox;
    private final PlayerCommandRepository playerRepository;
    private final TeamQueryRepository repository;
    private final TeamCommandApiClient client;

    public TeamEventHandler(
            DomainEventInbox inbox,
            PlayerCommandRepository playerRepository,
            TeamQueryRepository repository,
            TeamCommandApiClient client
    ) {
        this.inbox = inbox;
        this.playerRepository = playerRepository;
        this.repository = repository;
        this.client = client;
    }

    @Transactional
    public void handle(DomainEvent event) {
        if (inbox.exists(event.id())) return;

        inbox.save(event);
        final TeamId teamId = new TeamId(event.modelId());
        final TeamData team = client.findById(teamId);
        repository.save(team);
        playerRepository.updateAll(teamId, team.playerIds().stream().map(PlayerId::value).toList());
    }
}
