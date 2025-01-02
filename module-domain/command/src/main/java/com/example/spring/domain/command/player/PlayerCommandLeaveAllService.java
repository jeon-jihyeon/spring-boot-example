package com.example.spring.domain.command.player;

import com.example.spring.domain.command.player.dto.PlayerLeaveAllCommand;
import com.example.spring.domain.command.player.dto.PlayerLeaveAllEvent;
import com.example.spring.domain.command.player.model.PlayerId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerCommandLeaveAllService {
    private final PlayerCommandRepository repository;
    private final ApplicationEventPublisher publisher;

    public PlayerCommandLeaveAllService(PlayerCommandRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional(transactionManager = "commandTransactionManager")
    public void invoke(PlayerLeaveAllCommand command) {
        final List<PlayerId> ids = repository.findIdsByTeamId(command.teamId());
        if (ids.isEmpty()) return;
        repository.leaveAll(command.teamId());
        publisher.publishEvent(new PlayerLeaveAllEvent(ids));
    }
}
