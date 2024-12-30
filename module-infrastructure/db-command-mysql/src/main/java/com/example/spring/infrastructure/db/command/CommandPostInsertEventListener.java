package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.player.PlayerMessageService;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.TeamMessageService;
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
    private final TeamMessageService teamMessage;
    private final PlayerMessageService playerMessage;

    public CommandPostInsertEventListener(TeamMessageService teamMessage, PlayerMessageService playerMessage) {
        this.teamMessage = teamMessage;
        this.playerMessage = playerMessage;
    }

    @Async
    @Override
    public void onPostInsert(PostInsertEvent event) {
        final Object e = event.getEntity();
        if (e instanceof TeamEntity) teamMessage.sendCreateType(new TeamId(((TeamEntity) e).getId()));
        else if (e instanceof PlayerEntity) playerMessage.sendCreateType(new PlayerId(((PlayerEntity) e).getId()));
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
