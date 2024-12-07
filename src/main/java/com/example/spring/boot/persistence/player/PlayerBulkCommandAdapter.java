package com.example.spring.boot.persistence.player;

import com.example.spring.boot.modules.player.domain.repository.PlayerBulkCommandRepository;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import static com.example.spring.boot.persistence.player.QPlayerEntity.playerEntity;

@Component
public class PlayerBulkCommandAdapter implements PlayerBulkCommandRepository {
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public PlayerBulkCommandAdapter(EntityManager em, JPAQueryFactory jpaQueryFactory) {
        this.em = em;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public void updateAllTeam(TeamId teamId, TeamId newId) {
        jpaQueryFactory.update(playerEntity)
                .where(playerEntity.teamId.eq(teamId.value()))
                .set(playerEntity.teamId, newId.value())
                .execute();
        em.flush();
        em.clear();
    }
}
