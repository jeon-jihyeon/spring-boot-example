package com.example.spring.infrastructure.db.query.player;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerMongoRepository extends MongoRepository<PlayerDocument, Long> {
    List<PlayerDocument> findAllByTeamIdIn(List<Long> teamIds);
}
