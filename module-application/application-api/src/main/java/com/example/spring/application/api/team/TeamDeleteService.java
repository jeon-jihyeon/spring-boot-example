package com.example.spring.application.api.team;

import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.event.TeamDeleteEvent;
import com.example.spring.domain.team.repository.TeamCommandRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamDeleteService {
    private final TeamCommandRepository commandRepository;
    private final ApplicationEventPublisher publisher;

    public TeamDeleteService(TeamCommandRepository commandRepository, ApplicationEventPublisher publisher) {
        this.commandRepository = commandRepository;
        this.publisher = publisher;
    }

    @Transactional
    public void invoke(TeamId teamId) {
        commandRepository.deleteById(teamId);
        publisher.publishEvent(TeamDeleteEvent.from(teamId));
    }
}
