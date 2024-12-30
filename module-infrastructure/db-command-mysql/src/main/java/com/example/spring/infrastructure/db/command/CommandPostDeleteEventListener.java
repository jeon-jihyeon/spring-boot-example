package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.player.PlayerMessageService;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.TeamMessageService;
import com.example.spring.domain.team.model.TeamId;
import com.example.spring.infrastructure.db.command.player.PlayerEntity;
import com.example.spring.infrastructure.db.command.team.TeamEntity;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandPostDeleteEventListener implements PostDeleteEventListener {
    private final TeamMessageService teamMessage;
    private final PlayerMessageService playerMessage;

    public CommandPostDeleteEventListener(TeamMessageService teamMessage, PlayerMessageService playerMessage) {
        this.teamMessage = teamMessage;
        this.playerMessage = playerMessage;
    }

    @Async
    @Override
    public void onPostDelete(PostDeleteEvent event) {
        final Object e = event.getEntity();
        if (e instanceof TeamEntity) teamMessage.sendDeleteType(new TeamId(((TeamEntity) e).getId()));
        else if (e instanceof PlayerEntity) playerMessage.sendDeleteType(new PlayerId(((PlayerEntity) e).getId()));
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
