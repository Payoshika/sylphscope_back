package com.scholarship.scholarship.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.scholarship.scholarship.model.Message;
import com.scholarship.scholarship.model.MessageContent;
import com.scholarship.scholarship.dto.CreateMessageWithContentRequest;
import com.scholarship.scholarship.service.MessageService;
import com.scholarship.scholarship.service.MessageContentService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MessageControllerTest {
    @Mock
    private MessageService messageService;
    @Mock
    private MessageContentService messageContentService;
    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMessage() {
        Message message = new Message();
        when(messageService.save(any(Message.class))).thenReturn(message);
        Message result = messageController.createMessage(message);
        assertEquals(message, result);
    }

    @Test
    void getAllMessages() {
        List<Message> messages = List.of(new Message());
        when(messageService.findAll()).thenReturn(messages);
        List<Message> result = messageController.getAllMessages();
        assertEquals(messages, result);
    }

    @Test
    void getMessageById() {
        String id = "id1";
        Message message = new Message();
        when(messageService.findById(id)).thenReturn(Optional.of(message));
        Optional<Message> result = messageController.getMessageById(id);
        assertTrue(result.isPresent());
        assertEquals(message, result.get());
    }

    @Test
    void deleteMessage() {
        String id = "id1";
        doNothing().when(messageService).deleteById(id);
        messageController.deleteMessage(id);
        verify(messageService, times(1)).deleteById(id);
    }

    @Test
    void addMessageContent() {
        String messageId = "msg1";
        MessageContent content = new MessageContent();
        when(messageContentService.save(any(MessageContent.class))).thenReturn(content);
        MessageContent result = messageController.addMessageContent(messageId, content);
        assertEquals(content, result);
        assertEquals(messageId, result.getMessageId());
    }

    @Test
    void getMessageContents() {
        String messageId = "msg1";
        List<MessageContent> contents = List.of(new MessageContent());
        when(messageContentService.findByMessageId(messageId)).thenReturn(contents);
        List<MessageContent> result = messageController.getMessageContents(messageId);
        assertEquals(contents, result);
    }

    @Test
    void deleteMessageContent() {
        String contentId = "content1";
        doNothing().when(messageContentService).deleteById(contentId);
        messageController.deleteMessageContent(contentId);
        verify(messageContentService, times(1)).deleteById(contentId);
    }

    @Test
    void getMessagesByStudentId() {
        String studentId = "student1";
        List<Message> messages = List.of(new Message());
        when(messageService.findByStudentId(studentId)).thenReturn(messages);
        List<Message> result = messageController.getMessagesByStudentId(studentId);
        assertEquals(messages, result);
    }

    @Test
    void getMessagesByProviderStaffId() {
        String providerStaffId = "staff1";
        List<Message> messages = List.of(new Message());
        when(messageService.findByProviderStaffId(providerStaffId)).thenReturn(messages);
        List<Message> result = messageController.getMessagesByProviderStaffId(providerStaffId);
        assertEquals(messages, result);
    }

    @Test
    void addMessageContentToMessage() {
        String messageId = "msg1";
        MessageContent content = new MessageContent();
        when(messageService.addMessageContentToMessage(eq(messageId), any(MessageContent.class))).thenReturn(content);
        MessageContent result = messageController.addMessageContentToMessage(messageId, content);
        assertEquals(content, result);
    }

    @Test
    void createMessageWithContent() {
        CreateMessageWithContentRequest request = new CreateMessageWithContentRequest();
        Message message = new Message();
        request.setMessage(message);
        request.setMessageContent(new MessageContent());
        when(messageService.createMessageWithContent(any(Message.class), any(MessageContent.class))).thenReturn(message);
        Message result = messageController.createMessageWithContent(request);
        assertEquals(message, result);
    }
}