package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.event.DomainEventType;
import com.example.spring.domain.player.PlayerCommandMessageService;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.TeamCommandMessageService;
import com.example.spring.domain.team.model.TeamId;
import com.example.spring.infrastructure.db.command.player.PlayerEntity;
import com.example.spring.infrastructure.db.command.team.TeamEntity;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandPostInsertEventListener implements PostInsertEventListener {
    private static final DomainEventType TYPE = DomainEventType.CREATE;
    private final TeamCommandMessageService teamMessage;
    private final PlayerCommandMessageService playerMessage;

    public CommandPostInsertEventListener(TeamCommandMessageService teamMessage, PlayerCommandMessageService playerMessage) {
        this.teamMessage = teamMessage;
        this.playerMessage = playerMessage;
    }

    @Async
    @Override
    public void onPostInsert(PostInsertEvent event) {
        final Object e = event.getEntity();
        if (e instanceof TeamEntity) teamMessage.send(TYPE, new TeamId(((TeamEntity) e).getId()));
        else if (e instanceof PlayerEntity) playerMessage.send(TYPE, new PlayerId(((PlayerEntity) e).getId()));
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
