package com.example.spring.boot.persistence.player;

import com.example.spring.boot.core.exception.EntityNotFoundException;
import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.player.domain.repository.PlayerQueryRepository;
import com.example.spring.boot.modules.player.domain.repository.condition.PlayerCondition;
import com.example.spring.boot.modules.player.domain.repository.query.PlayerListQuery;
import com.example.spring.boot.modules.player.domain.repository.query.PlayerQuery;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.spring.boot.persistence.player.QPlayerEntity.playerEntity;

@Component
public class PlayerQueryAdapter implements PlayerQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PlayerQueryAdapter(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public static BooleanExpression gradeEq(Grade grade) {
        if (grade == null) return null;
        return playerEntity.grade.eq(grade);
    }

    public static BooleanExpression teamIdEq(Long teamId) {
        if (teamId == null) return null;
        return playerEntity.teamId.eq(teamId);
    }

    public static BooleanExpression contains(String word) {
        if (word == null) return null;
        return playerEntity.firstName.coalesce("").concat(playerEntity.lastName).contains(word);
    }

    @Override
    public List<PlayerListQuery> findPlayers(PlayerCondition condition) {
        return jpaQueryFactory
                .selectFrom(playerEntity)
                .where(gradeEq(condition.grade()),
                        teamIdEq(condition.teamId()),
                        contains(condition.firstName()),
                        contains(condition.lastName()))
                .fetch().stream().map(e -> new PlayerListQuery(
                        e.getId(),
                        e.getGrade(),
                        e.getFirstName(),
                        e.getLastName(),
                        e.getTeamId()
                )).toList();
    }

    @Override
    public PlayerQuery findPlayer(PlayerId id) {
        final PlayerEntity entity = jpaQueryFactory
                .selectFrom(playerEntity)
                .where(playerEntity.id.eq(id.value())).fetchFirst();

        if (entity == null) throw new EntityNotFoundException();
        return new PlayerQuery(
                entity.getId(),
                entity.getGrade(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getTeamId()
        );
    }
}
