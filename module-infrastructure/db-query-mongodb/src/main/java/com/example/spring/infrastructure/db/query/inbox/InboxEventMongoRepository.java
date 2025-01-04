package com.example.spring.infrastructure.db.query.inbox;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InboxEventMongoRepository extends MongoRepository<InboxEventDocument, Long> {
}
