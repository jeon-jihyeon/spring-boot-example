package com.example.spring.boot.persistence.team;

import com.example.spring.boot.core.exception.EntityNotFoundException;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.repository.TeamQueryRepository;
import com.example.spring.boot.modules.team.domain.repository.condition.TeamCondition;
import com.example.spring.boot.modules.team.domain.repository.query.TeamListQuery;
import com.example.spring.boot.modules.team.domain.repository.query.TeamQuery;
import com.example.spring.boot.persistence.team.dto.QTeamListQueryDto;
import com.example.spring.boot.persistence.team.dto.TeamListQueryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.spring.boot.persistence.player.QPlayerEntity.playerEntity;
import static com.example.spring.boot.persistence.team.QTeamEntity.teamEntity;

@Component
public class TeamQueryAdapter implements TeamQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public TeamQueryAdapter(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public static BooleanExpression contains(String name) {
        if (name == null) return null;
        return teamEntity.name.contains(name);
    }

    public static BooleanExpression afterEq(LocalDateTime startsAt) {
        if (startsAt == null) return null;
        return teamEntity.startsAt.after(startsAt)
                .or(teamEntity.startsAt.eq(startsAt));
    }

    @Override
    public List<TeamListQuery> findTeams(TeamCondition condition) {
        return jpaQueryFactory
                .select(new QTeamListQueryDto(
                        teamEntity.id,
                        teamEntity.name,
                        teamEntity.startsAt,
                        playerEntity.count().intValue()
                ))
                .from(teamEntity)
                .leftJoin(playerEntity).on(playerEntity.teamId.eq(teamEntity.id))
                .groupBy(teamEntity.id)
                .where(contains(condition.name()), afterEq(condition.startsAt()))
                .fetch().stream().map(TeamListQueryDto::toQuery).toList();
    }

    @Override
    public TeamQuery findTeam(TeamId id) {
        final TeamEntity entity = jpaQueryFactory
                .selectFrom(teamEntity)
                .where(teamEntity.id.eq(id.value())).fetchFirst();

        if (entity == null) throw new EntityNotFoundException();
        return new TeamQuery(
                entity.getId(),
                entity.getName(),
                entity.getStartsAt()
        );
    }
}
