package com.example.spring.application.api.player;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.dto.PlayerCreateCommand;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.repository.PlayerCommandRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCreateService {
    private final PlayerCommandRepository repository;
    private final ApplicationEventPublisher publisher;

    public PlayerCreateService(PlayerCommandRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional
    public PlayerData invoke(PlayerCreateCommand command) {
        final PlayerData player = PlayerData.from(repository.save(Player.create(command)));
        publisher.publishEvent(DomainEvent.of(Player.class.getSimpleName(), player.id()));
        return player;
    }
}
