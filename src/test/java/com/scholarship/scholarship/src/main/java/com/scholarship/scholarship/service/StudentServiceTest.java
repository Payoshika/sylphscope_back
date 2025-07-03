package com.scholarship.scholarship.service;
import com.scholarship.scholarship.dto.StudentDto;
import com.scholarship.scholarship.enums.Country;
import com.scholarship.scholarship.model.Student;
import com.scholarship.scholarship.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void createMinimalStudent_ShouldCreateWithUserId() {
        // Given
        String userId = "test123";
        // Capture the saved student to verify properties were copied correctly
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        // Setup the mock to return a student with an ID
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> {
            Student savedStudent = invocation.getArgument(0);
            savedStudent.setId("generatedId");
            return savedStudent;
        });
        // When
        StudentDto result = studentService.createMinimalStudent(userId);
        // Then
        verify(studentRepository).save(studentCaptor.capture());
        Student capturedStudent = studentCaptor.getValue();

        assertEquals(userId, capturedStudent.getUserId());
        assertNotNull(result);
        assertEquals("generatedId", result.getId());
        assertEquals(userId, result.getUserId());
    }
}