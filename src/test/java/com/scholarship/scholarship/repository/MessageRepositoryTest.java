package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageRepositoryTest {
    @Mock
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByStudentId() {
        Message m1 = new Message();
        m1.setId("1");
        m1.setStudentId("student1");
        Message m2 = new Message();
        m2.setId("2");
        m2.setStudentId("student1");
        when(messageRepository.findByStudentId("student1")).thenReturn(Arrays.asList(m1, m2));
        List<Message> result = messageRepository.findByStudentId("student1");
        assertEquals(2, result.size());
        assertEquals("student1", result.get(0).getStudentId());
        assertEquals("student1", result.get(1).getStudentId());
    }

    @Test
    void findByProviderStaffId() {
        Message m1 = new Message();
        m1.setId("1");
        m1.setProviderStaffId("staff1");
        Message m2 = new Message();
        m2.setId("2");
        m2.setProviderStaffId("staff1");
        when(messageRepository.findByProviderStaffId("staff1")).thenReturn(Arrays.asList(m1, m2));
        List<Message> result = messageRepository.findByProviderStaffId("staff1");
        assertEquals(2, result.size());
        assertEquals("staff1", result.get(0).getProviderStaffId());
        assertEquals("staff1", result.get(1).getProviderStaffId());
    }
}