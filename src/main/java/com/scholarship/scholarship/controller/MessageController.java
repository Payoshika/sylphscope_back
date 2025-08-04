package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.model.Message;
import com.scholarship.scholarship.model.MessageContent;
import com.scholarship.scholarship.service.MessageContentService;
import com.scholarship.scholarship.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageContentService messageContentService;

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        return messageService.save(message);
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Message> getMessageById(@PathVariable String id) {
        return messageService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable String id) {
        messageService.deleteById(id);
    }

    @PostMapping("/{messageId}/contents")
    public MessageContent addMessageContent(@PathVariable String messageId, @RequestBody MessageContent messageContent) {
        messageContent.setMessageId(messageId);
        return messageContentService.save(messageContent);
    }

    @GetMapping("/{messageId}/contents")
    public List<MessageContent> getMessageContents(@PathVariable String messageId) {
        return messageContentService.findByMessageId(messageId);
    }

    @DeleteMapping("/contents/{id}")
    public void deleteMessageContent(@PathVariable String id) {
        messageContentService.deleteById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Message> getMessagesByStudentId(@PathVariable String studentId) {
        return messageService.findByStudentId(studentId);
    }

    @GetMapping("/providerStaff/{providerStaffId}")
    public List<Message> getMessagesByProviderStaffId(@PathVariable String providerStaffId) {
        return messageService.findByProviderStaffId(providerStaffId);
    }

    @PostMapping("/{messageId}/add-content")
    public MessageContent addMessageContentToMessage(@PathVariable String messageId, @RequestBody MessageContent messageContent) {
        return messageService.addMessageContentToMessage(messageId, messageContent);
    }

    @PostMapping("/create-with-content")
    public Message createMessageWithContent(@RequestBody Message message, @RequestBody MessageContent messageContent) {
        return messageService.createMessageWithContent(message, messageContent);
    }
}
