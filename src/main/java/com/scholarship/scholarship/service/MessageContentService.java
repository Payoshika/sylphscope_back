package com.scholarship.scholarship.service;

import com.scholarship.scholarship.model.MessageContent;
import com.scholarship.scholarship.repository.MessageContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageContentService {
    @Autowired
    private MessageContentRepository messageContentRepository;

    public MessageContent save(MessageContent messageContent) {
        return messageContentRepository.save(messageContent);
    }

    public List<MessageContent> findByMessageId(String messageId) {
        return messageContentRepository.findByMessageId(messageId);
    }

    public Optional<MessageContent> findById(String id) {
        return messageContentRepository.findById(id);
    }

    public void deleteById(String id) {
        messageContentRepository.deleteById(id);
    }
}

