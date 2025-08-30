package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionRepositoryTest {
    @Mock
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIsRequired() {
        Question q1 = new Question();
        q1.setId("1");
        q1.setIsRequired(true);
        Question q2 = new Question();
        q2.setId("2");
        q2.setIsRequired(true);
        when(questionRepository.findByIsRequired(true)).thenReturn(Arrays.asList(q1, q2));
        List<Question> result = questionRepository.findByIsRequired(true);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getIsRequired());
        assertTrue(result.get(1).getIsRequired());
    }
}