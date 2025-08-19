package com.scholarship.scholarship.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.scholarship.scholarship.dto.QuestionDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionWithOptionsDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionGroupEligibilityInfoDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionEligibilityInfoDto;
import com.scholarship.scholarship.model.Question;
import com.scholarship.scholarship.service.QuestionService;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class QuestionControllerTest {
    @Mock
    private QuestionService questionService;
    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createQuestion() {
        QuestionWithOptionsDto payload = new QuestionWithOptionsDto();
        QuestionDto questionDto = new QuestionDto();
        when(questionService.createQuestionWithOptions(any(), any())).thenReturn(questionDto);
        ResponseEntity<QuestionDto> response = questionController.createQuestion(payload);
        assertEquals(questionDto, response.getBody());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void getQuestionById() {
        QuestionDto questionDto = new QuestionDto();
        when(questionService.getQuestionById("id1")).thenReturn(questionDto);
        ResponseEntity<QuestionDto> response = questionController.getQuestionById("id1");
        assertEquals(questionDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getAllQuestions() {
        List<QuestionDto> questions = List.of(new QuestionDto());
        when(questionService.getAllQuestions()).thenReturn(questions);
        ResponseEntity<List<QuestionDto>> response = questionController.getAllQuestions();
        assertEquals(questions, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void updateQuestion() {
        QuestionDto questionDto = new QuestionDto();
        when(questionService.updateQuestion(eq("id1"), any(QuestionDto.class))).thenReturn(questionDto);
        ResponseEntity<QuestionDto> response = questionController.updateQuestion("id1", questionDto);
        assertEquals(questionDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deleteQuestion() {
        doNothing().when(questionService).deleteQuestion("id1");
        ResponseEntity<Void> response = questionController.deleteQuestion("id1");
        verify(questionService, times(1)).deleteQuestion("id1");
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void getQuestionsForEligibility() {
        List<QuestionEligibilityInfoDto> resultList = List.of(new QuestionEligibilityInfoDto());
        when(questionService.getQuestionsForEligibility()).thenReturn(resultList);
        ResponseEntity<List<QuestionEligibilityInfoDto>> response = questionController.getQuestionsForEligibility();
        assertEquals(resultList, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getQuestionGroupsForEligibility() {
        List<QuestionGroupEligibilityInfoDto> resultList = List.of(new QuestionGroupEligibilityInfoDto());
        when(questionService.getQuestionGroupsForEligibility()).thenReturn(resultList);
        ResponseEntity<List<QuestionGroupEligibilityInfoDto>> response = questionController.getQuestionGroupsForEligibility();
        assertEquals(resultList, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}