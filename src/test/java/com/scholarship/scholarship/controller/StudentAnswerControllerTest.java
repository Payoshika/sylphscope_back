package com.scholarship.scholarship.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.scholarship.scholarship.dto.StudentAnswerDto;
import com.scholarship.scholarship.model.StudentAnswer;
import com.scholarship.scholarship.modelmapper.StudentAnswerMapper;
import com.scholarship.scholarship.service.StudentAnswerService;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentAnswerControllerTest {
    @Mock
    private StudentAnswerService studentAnswerService;
    @Mock
    private StudentAnswerMapper studentAnswerMapper;
    @InjectMocks
    private StudentAnswerController studentAnswerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAnswersByStudentId() {
        String studentId = "student1";
        StudentAnswer answer = new StudentAnswer();
        StudentAnswerDto dto = new StudentAnswerDto();
        when(studentAnswerService.getAnswersByStudentId(studentId)).thenReturn(List.of(answer));
        when(studentAnswerMapper.toDto(answer)).thenReturn(dto);
        ResponseEntity<List<StudentAnswerDto>> response = studentAnswerController.getAnswersByStudentId(studentId);
        assertEquals(List.of(dto), response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getAnswersByApplicationId() {
        String studentId = "student1";
        String applicationId = "app1";
        StudentAnswer answer = new StudentAnswer();
        answer.setStudentId(studentId);
        StudentAnswerDto dto = new StudentAnswerDto();
        when(studentAnswerService.getAnswersByApplicationId(applicationId)).thenReturn(List.of(answer));
        when(studentAnswerMapper.toDto(answer)).thenReturn(dto);
        ResponseEntity<List<StudentAnswerDto>> response = studentAnswerController.getAnswersByApplicationId(studentId, applicationId);
        assertEquals(List.of(dto), response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getAnswersByStudentIdParam() {
        String studentId = "student1";
        StudentAnswer answer = new StudentAnswer();
        answer.setQuestionId("q1");
        answer.setAnsweredAt(java.time.Instant.now());
        StudentAnswerDto dto = new StudentAnswerDto();
        when(studentAnswerService.getAnswersByStudentId(studentId)).thenReturn(List.of(answer));
        when(studentAnswerMapper.toDto(answer)).thenReturn(dto);
        ResponseEntity<List<StudentAnswerDto>> response = studentAnswerController.getAnswersByStudentIdParam(studentId);
        assertEquals(List.of(dto), response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getAnswerById_found() {
        String id = "id1";
        StudentAnswer answer = new StudentAnswer();
        StudentAnswerDto dto = new StudentAnswerDto();
        when(studentAnswerService.getAnswerById(id)).thenReturn(Optional.of(answer));
        when(studentAnswerMapper.toDto(answer)).thenReturn(dto);
        ResponseEntity<StudentAnswerDto> response = studentAnswerController.getAnswerById(id);
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getAnswerById_notFound() {
        String id = "id1";
        when(studentAnswerService.getAnswerById(id)).thenReturn(Optional.empty());
        ResponseEntity<StudentAnswerDto> response = studentAnswerController.getAnswerById(id);
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void createAnswer() {
        StudentAnswerDto dto = new StudentAnswerDto();
        StudentAnswer entity = new StudentAnswer();
        when(studentAnswerMapper.toEntity(dto)).thenReturn(entity);
        when(studentAnswerService.saveAnswer(entity)).thenReturn(entity);
        when(studentAnswerMapper.toDto(entity)).thenReturn(dto);
        ResponseEntity<StudentAnswerDto> response = studentAnswerController.createAnswer(dto);
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void updateAnswers() {
        String studentId = "student1";
        String grantProgramId = "gp1";
        StudentAnswerDto dto = new StudentAnswerDto();
        StudentAnswer entity = new StudentAnswer();
        when(studentAnswerMapper.toEntity(dto)).thenReturn(entity);
        when(studentAnswerService.updateAnswers(eq(studentId), eq(grantProgramId), anyList())).thenReturn(List.of(entity));
        when(studentAnswerMapper.toDto(entity)).thenReturn(dto);
        ResponseEntity<List<StudentAnswerDto>> response = studentAnswerController.updateAnswers(studentId, grantProgramId, List.of(dto));
        assertEquals(List.of(dto), response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deleteAnswer() {
        String id = "id1";
        doNothing().when(studentAnswerService).deleteAnswer(id);
        ResponseEntity<Void> response = studentAnswerController.deleteAnswer(id);
        verify(studentAnswerService, times(1)).deleteAnswer(id);
        assertEquals(204, response.getStatusCodeValue());
    }
}