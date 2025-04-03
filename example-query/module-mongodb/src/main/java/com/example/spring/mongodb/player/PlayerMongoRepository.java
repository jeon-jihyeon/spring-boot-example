package com.example.spring.mongodb.player;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerMongoRepository extends MongoRepository<PlayerDocument, Long> {
    List<PlayerDocument> findAllByTeamId(Long teamId);
}
