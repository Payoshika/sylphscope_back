package com.scholarship.scholarship.service;

import com.scholarship.scholarship.model.Message;
import com.scholarship.scholarship.model.MessageContent;
import com.scholarship.scholarship.model.Provider;
import com.scholarship.scholarship.repository.MessageContentRepository;
import com.scholarship.scholarship.repository.MessageRepository;
import com.scholarship.scholarship.repository.ProviderRepository;
import com.scholarship.scholarship.repository.ProviderStaffRepository;
import com.scholarship.scholarship.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessageContentRepository messageContentRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ProviderStaffRepository providerStaffRepository;
    @Mock
    private ProviderRepository providerRepository;
    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Message message = new Message();
        message.setId("msg1");
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        Message result = messageService.save(message);
        assertEquals("msg1", result.getId());
    }

    @Test
    void findAll() {
        Message m1 = new Message();
        m1.setId("msg1");
        Message m2 = new Message();
        m2.setId("msg2");
        when(messageRepository.findAll()).thenReturn(Arrays.asList(m1, m2));
        List<Message> result = messageService.findAll();
        assertEquals(2, result.size());
        assertEquals("msg1", result.get(0).getId());
        assertEquals("msg2", result.get(1).getId());
    }

    @Test
    void findById_found() {
        Message message = new Message();
        message.setId("msg1");
        when(messageRepository.findById("msg1")).thenReturn(Optional.of(message));
        Optional<Message> result = messageService.findById("msg1");
        assertTrue(result.isPresent());
        assertEquals("msg1", result.get().getId());
    }

    @Test
    void findById_notFound() {
        when(messageRepository.findById("msg2")).thenReturn(Optional.empty());
        Optional<Message> result = messageService.findById("msg2");
        assertFalse(result.isPresent());
    }

    @Test
    void deleteById() {
        doNothing().when(messageRepository).deleteById("msg1");
        messageService.deleteById("msg1");
        verify(messageRepository, times(1)).deleteById("msg1");
    }

    @Test
    void findByStudentId() {
        Message m1 = new Message();
        m1.setStudentId("student1");
        when(messageRepository.findByStudentId("student1")).thenReturn(Arrays.asList(m1));
        List<Message> result = messageService.findByStudentId("student1");
        assertEquals(1, result.size());
        assertEquals("student1", result.get(0).getStudentId());
    }

    @Test
    void findByProviderStaffId() {
        Message m1 = new Message();
        m1.setProviderStaffId("staff1");
        when(messageRepository.findByProviderStaffId("staff1")).thenReturn(Arrays.asList(m1));
        List<Message> result = messageService.findByProviderStaffId("staff1");
        assertEquals(1, result.size());
        assertEquals("staff1", result.get(0).getProviderStaffId());
    }

    @Test
    void addMessageContentToMessage() {
        Message message = new Message();
        message.setId("msg1");
        message.setMessages(new java.util.ArrayList<>());
        MessageContent content = new MessageContent();
        content.setId("content1");
        when(messageContentRepository.save(any(MessageContent.class))).thenReturn(content);
        when(messageRepository.findById("msg1")).thenReturn(Optional.of(message));
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        MessageContent result = messageService.addMessageContentToMessage("msg1", content);
        assertEquals("content1", result.getId());
        assertEquals("msg1", result.getMessageId());
    }

    @Test
    void createMessageWithContent_withProvider() {
        Message message = new Message();
        message.setProviderId("provider1");
        Provider provider = new Provider();
        provider.setOrganisationName("ProviderOrg");
        when(providerRepository.findById("provider1")).thenReturn(Optional.of(provider));
        MessageContent content = new MessageContent();
        content.setId("content1");
        when(messageContentRepository.save(any(MessageContent.class))).thenReturn(content);
        Message savedMessage = new Message();
        savedMessage.setId("msg1");
        when(messageRepository.save(any(Message.class))).thenReturn(savedMessage);
        when(messageContentRepository.save(any(MessageContent.class))).thenReturn(content);
        Message result = messageService.createMessageWithContent(message, content);
        assertEquals("msg1", result.getId());
    }

    @Test
    void createMessageWithContent_withoutProvider() {
        Message message = new Message();
        MessageContent content = new MessageContent();
        content.setId("content1");
        when(messageContentRepository.save(any(MessageContent.class))).thenReturn(content);
        Message savedMessage = new Message();
        savedMessage.setId("msg2");
        when(messageRepository.save(any(Message.class))).thenReturn(savedMessage);
        when(messageContentRepository.save(any(MessageContent.class))).thenReturn(content);
        Message result = messageService.createMessageWithContent(message, content);
        assertEquals("msg2", result.getId());
    }
}