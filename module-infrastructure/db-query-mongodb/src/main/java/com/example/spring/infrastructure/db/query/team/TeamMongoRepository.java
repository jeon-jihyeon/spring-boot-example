package com.example.spring.infrastructure.db.query.team;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamMongoRepository extends MongoRepository<TeamDocument, Long> {
    List<TeamDocument> findAllByStartsAtAfter(LocalDateTime at);
}
