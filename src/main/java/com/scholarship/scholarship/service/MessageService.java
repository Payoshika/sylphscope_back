package com.scholarship.scholarship.service;

import com.scholarship.scholarship.model.Message;
import com.scholarship.scholarship.model.MessageContent;
import com.scholarship.scholarship.repository.MessageContentRepository;
import com.scholarship.scholarship.repository.MessageRepository;
import com.scholarship.scholarship.repository.ProviderRepository;
import com.scholarship.scholarship.repository.ProviderStaffRepository;
import com.scholarship.scholarship.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageContentRepository messageContentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProviderStaffRepository providerStaffRepository;
    @Autowired
    private ProviderRepository providerRepository;

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Optional<Message> findById(String id) {
        return messageRepository.findById(id);
    }

    public void deleteById(String id) {
        messageRepository.deleteById(id);
    }

    public List<Message> findByStudentId(String studentId) {
        return messageRepository.findByStudentId(studentId);
    }

    public List<Message> findByProviderStaffId(String providerStaffId) {
        return messageRepository.findByProviderStaffId(providerStaffId);
    }

    public MessageContent addMessageContentToMessage(String messageId, MessageContent messageContent) {
        // Save MessageContent
        messageContent.setMessageId(messageId);
        MessageContent savedContent = messageContentRepository.save(messageContent);
        // Add to Message
        Optional<Message> messageOpt = messageRepository.findById(messageId);
        if (messageOpt.isPresent()) {
            Message message = messageOpt.get();
            if (message.getMessages() == null) {
                message.setMessages(new java.util.ArrayList<>());
            }
            message.getMessages().add(savedContent);
            messageRepository.save(message);
        }
        return savedContent;
    }

    public Message createMessageWithContent(Message message, MessageContent messageContent) {
        // Set providerName from providerId
        if (message.getProviderId() != null) {
            com.scholarship.scholarship.model.Provider provider = providerRepository.findById(message.getProviderId()).orElse(null);
            if (provider != null) {
                message.setProviderName(provider.getOrganisationName());
            }
        }
        // Save MessageContent first
        MessageContent savedContent = messageContentRepository.save(messageContent);
        // Add MessageContent to Message
        message.setMessages(new ArrayList<>());
        message.getMessages().add(savedContent);
        // Save Message
        Message savedMessage = messageRepository.save(message);
        // Update MessageId in MessageContent
        savedContent.setMessageId(savedMessage.getId());
        messageContentRepository.save(savedContent);
        return savedMessage;
    }
}
