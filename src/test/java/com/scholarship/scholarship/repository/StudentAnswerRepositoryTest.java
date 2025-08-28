package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.StudentAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentAnswerRepositoryTest {
    @Mock
    private StudentAnswerRepository studentAnswerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByStudentId() {
        StudentAnswer sa1 = new StudentAnswer();
        sa1.setId("1");
        sa1.setStudentId("student1");
        StudentAnswer sa2 = new StudentAnswer();
        sa2.setId("2");
        sa2.setStudentId("student1");
        when(studentAnswerRepository.findByStudentId("student1")).thenReturn(Arrays.asList(sa1, sa2));
        List<StudentAnswer> result = studentAnswerRepository.findByStudentId("student1");
        assertEquals(2, result.size());
        assertEquals("student1", result.get(0).getStudentId());
    }

    @Test
    void findByApplicationIdContaining() {
        StudentAnswer sa = new StudentAnswer();
        sa.setId("1");
        sa.setApplicationId(Arrays.asList("app1", "app2"));
        when(studentAnswerRepository.findByApplicationIdContaining("app1")).thenReturn(Arrays.asList(sa));
        List<StudentAnswer> result = studentAnswerRepository.findByApplicationIdContaining("app1");
        assertEquals(1, result.size());
        assertTrue(result.get(0).getApplicationId().contains("app1"));
    }

    @Test
    void findByStudentIdIn() {
        StudentAnswer sa1 = new StudentAnswer();
        sa1.setId("1");
        sa1.setStudentId("student1");
        StudentAnswer sa2 = new StudentAnswer();
        sa2.setId("2");
        sa2.setStudentId("student2");
        List<String> studentIds = Arrays.asList("student1", "student2");
        when(studentAnswerRepository.findByStudentIdIn(studentIds)).thenReturn(Arrays.asList(sa1, sa2));
        List<StudentAnswer> result = studentAnswerRepository.findByStudentIdIn(studentIds);
        assertEquals(2, result.size());
        assertTrue(studentIds.contains(result.get(0).getStudentId()));
        assertTrue(studentIds.contains(result.get(1).getStudentId()));
    }
}