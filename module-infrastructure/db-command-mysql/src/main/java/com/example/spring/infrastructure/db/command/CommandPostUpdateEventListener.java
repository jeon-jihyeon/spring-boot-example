package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.command.player.PlayerCommandMessageService;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.TeamCommandMessageService;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.event.DomainEventType;
import com.example.spring.infrastructure.db.command.player.PlayerEntity;
import com.example.spring.infrastructure.db.command.team.TeamEntity;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CommandPostUpdateEventListener implements PostUpdateEventListener {
    private static final DomainEventType TYPE = DomainEventType.UPDATE;
    private final TeamCommandMessageService teamMessage;
    private final PlayerCommandMessageService playerMessage;

    public CommandPostUpdateEventListener(TeamCommandMessageService teamMessage, PlayerCommandMessageService playerMessage) {
        this.teamMessage = teamMessage;
        this.playerMessage = playerMessage;
    }

    @Async
    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        final Object e = event.getEntity();
        // final String[] names = event.getPersister().getPropertyNames();
        // final List<String> list = new ArrayList<>();
        // for (int i : event.getDirtyProperties()) if (!names[i].equals("updatedAt")) list.add(names[i]);
        if (e instanceof TeamEntity) teamMessage.send(TYPE, new TeamId(((TeamEntity) e).getId()));
        else if (e instanceof PlayerEntity) playerMessage.send(TYPE, new PlayerId(((PlayerEntity) e).getId()));
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return false;
    }
}
