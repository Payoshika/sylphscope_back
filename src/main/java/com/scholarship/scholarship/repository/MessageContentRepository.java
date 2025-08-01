package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.MessageContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageContentRepository extends MongoRepository<MessageContent, String> {
    List<MessageContent> findByMessageId(String messageId);
}

