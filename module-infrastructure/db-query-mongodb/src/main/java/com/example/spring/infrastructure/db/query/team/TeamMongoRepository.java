package com.example.spring.infrastructure.db.query.team;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamMongoRepository extends MongoRepository<TeamDocument, Long> {
}
