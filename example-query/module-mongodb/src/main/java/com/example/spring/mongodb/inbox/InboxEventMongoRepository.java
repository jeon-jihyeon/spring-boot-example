package com.example.spring.mongodb.inbox;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InboxEventMongoRepository extends MongoRepository<InboxEventDocument, Long> {
}
