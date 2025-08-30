package com.scholarship.scholarship.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.scholarship.scholarship.dto.StudentDto;
import com.scholarship.scholarship.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {
    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStudent() {
        StudentDto dto = new StudentDto();
        when(studentService.createStudent(dto)).thenReturn(dto);
        ResponseEntity<StudentDto> response = studentController.createStudent(dto);
        assertEquals(dto, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getStudentByUserId_found() {
        StudentDto dto = new StudentDto();
        when(studentService.getStudentByUserId("user1")).thenReturn(Optional.of(dto));
        ResponseEntity<StudentDto> response = studentController.getStudentByUserId("user1");
        assertEquals(dto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void updateStudent_found() {
        StudentDto dto = new StudentDto();
        when(studentService.updateStudent("id1", dto)).thenReturn(dto);
        ResponseEntity<StudentDto> response = studentController.updateStudent("id1", dto);
        assertEquals(dto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}