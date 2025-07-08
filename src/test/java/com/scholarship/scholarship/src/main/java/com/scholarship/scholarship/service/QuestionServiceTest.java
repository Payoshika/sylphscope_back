package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.QuestionDto;
import com.scholarship.scholarship.model.Question;
import com.scholarship.scholarship.repository.QuestionRepository;
import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    void createQuestion_ShouldCreateAndReturnQuestion() {
        // Given
        QuestionDto inputDto = QuestionDto.builder()
                .name("full_name")
                .questionText("What is your full name?")
                .description("Enter your complete full name")
                .isRequired(true)
                .build();

        Question savedQuestion = Question.builder()
                .id("question123")
                .name("full_name")
                .questionText("What is your full name?")
                .inputType(InputType.TEXT)
                .questionDataType(DataType.STRING)
                .description("Enter your complete full name")
                .isRequired(true)
                .createdAt(Instant.now())
                .build();

        when(questionRepository.save(any(Question.class))).thenReturn(savedQuestion);

        // When
        QuestionDto result = questionService.createQuestion(inputDto);

        // Then
        assertNotNull(result);
        assertEquals("question123", result.getId());
        assertEquals("full_name", result.getName());
        assertEquals("What is your full name?", result.getQuestionText());
        assertTrue(result.isRequired());
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    void getQuestionById_ShouldReturnQuestion() {
        // Given
        String questionId = "question123";
        Question question = Question.builder()
                .id(questionId)
                .name("full_name")
                .questionText("What is your full name?")
                .inputType(InputType.TEXT)
                .questionDataType(DataType.STRING)
                .isRequired(true)
                .createdAt(Instant.now())
                .build();

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // When
        QuestionDto result = questionService.getQuestionById(questionId);

        // Then
        assertNotNull(result);
        assertEquals(questionId, result.getId());
        assertEquals("full_name", result.getName());
        verify(questionRepository, times(1)).findById(questionId);
    }
}