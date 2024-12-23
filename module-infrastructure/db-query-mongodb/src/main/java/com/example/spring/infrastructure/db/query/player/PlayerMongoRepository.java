package com.example.spring.infrastructure.db.query.player;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerMongoRepository extends MongoRepository<PlayerDocument, Long> {
}
