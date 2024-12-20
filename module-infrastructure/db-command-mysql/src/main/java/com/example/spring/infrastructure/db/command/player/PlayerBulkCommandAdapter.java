package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.player.PlayerBulkCommandRepository;
import com.example.spring.domain.team.TeamId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.spring.infrastructure.db.command.player.QPlayerEntity.playerEntity;

@Component
public class PlayerBulkCommandAdapter implements PlayerBulkCommandRepository {
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public PlayerBulkCommandAdapter(@Qualifier("commandEntityManager") EntityManager em, JPAQueryFactory jpaQueryFactory) {
        this.em = em;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public void updateAll(TeamId teamId, TeamId newId) {
        jpaQueryFactory.update(playerEntity)
                .where(playerEntity.teamId.eq(teamId.value()))
                .set(playerEntity.teamId, newId.value())
                .execute();
        em.flush();
        em.clear();
    }

    @Override
    public void updateAll(TeamId teamId, List<Long> playerIds) {
        jpaQueryFactory.update(playerEntity)
                .where(playerEntity.id.in(playerIds))
                .set(playerEntity.teamId, teamId.value())
                .execute();
        em.flush();
        em.clear();
    }
}
