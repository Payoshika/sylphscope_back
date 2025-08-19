package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StudentRepositoryTest {
    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByUserId() {
        Student student = new Student();
        student.setId("1");
        student.setUserId("user1");
        when(studentRepository.findByUserId("user1")).thenReturn(Optional.of(student));
        Optional<Student> result = studentRepository.findByUserId("user1");
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUserId());
    }
}