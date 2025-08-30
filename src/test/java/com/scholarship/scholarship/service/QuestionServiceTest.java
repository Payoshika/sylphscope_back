package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.OptionDto;
import com.scholarship.scholarship.dto.QuestionDto;
import com.scholarship.scholarship.dto.QuestionOptionSetDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionEligibilityInfoDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionGroupEligibilityInfoDto;
import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.model.Option;
import com.scholarship.scholarship.model.Question;
import com.scholarship.scholarship.model.QuestionGroup;
import com.scholarship.scholarship.model.QuestionOptionSet;
import com.scholarship.scholarship.repository.QuestionOptionSetRepository;
import com.scholarship.scholarship.repository.QuestionRepository;
import com.scholarship.scholarship.repository.QuestionGroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private OptionService optionService;
    @Mock
    private QuestionOptionSetRepository questionOptionSetRepository;
    @Mock
    private QuestionGroupRepository questionGroupRepository;
    @Mock
    private QuestionOptionSetService questionOptionSetService;
    @InjectMocks
    private QuestionService questionService;

    @Test
    void createQuestionWithOptions() {
        QuestionDto dto = QuestionDto.builder()
                .name("Q1")
                .questionText("Text")
                .inputType(InputType.RADIO)
                .questionDataType(DataType.ARRAY)
                .description("desc")
                .isRequired(true)
                .requiresConditionalUpload(false)
                .createdAt(Instant.now())
                .build();
        OptionDto optionDto = OptionDto.builder().label("A").value("A").build();
        OptionDto savedOption = OptionDto.builder().label("A").value("A").build();
        QuestionOptionSetDto optionSetDto = QuestionOptionSetDto.builder()
                .optionSetLabel("Q1 Options")
                .description("Options for Q1")
                .optionDataType(DataType.STRING)
                .options(List.of(savedOption))
                .build();
        QuestionOptionSetDto savedOptionSet = QuestionOptionSetDto.builder().id("os1").options(List.of(savedOption)).build();
        when(optionService.createOption(optionDto)).thenReturn(savedOption);
        when(questionOptionSetService.createQuestionOptionSet(any(QuestionOptionSetDto.class))).thenReturn(savedOptionSet);
        Question question = Question.builder().id("q1").name("Q1").inputType(InputType.RADIO).questionDataType(DataType.ARRAY).createdAt(Instant.now()).build();
        when(questionRepository.save(any(Question.class))).thenReturn(question);
        QuestionDto result = questionService.createQuestionWithOptions(dto, List.of(optionDto));
        assertEquals("Q1", result.getName());
        assertEquals("q1", result.getId());
    }

    @Test
    void getQuestionById() {
        Question question = Question.builder().id("q2").name("Q2").build();
        when(questionRepository.findById("q2")).thenReturn(Optional.of(question));
        QuestionDto result = questionService.getQuestionById("q2");
        assertEquals("q2", result.getId());
        assertEquals("Q2", result.getName());
    }

    @Test
    void getQuestionEligibilityInfoById() {
        Question question = Question.builder().id("q3").name("Q3").inputType(InputType.RADIO).optionSetId("os2").build();
        when(questionRepository.findById("q3")).thenReturn(Optional.of(question));
        QuestionOptionSet optionSet = QuestionOptionSet.builder().id("os2").options(List.of(Option.builder().label("A").value("A").build())).build();
        when(questionOptionSetRepository.findById("os2")).thenReturn(Optional.of(optionSet));
        QuestionEligibilityInfoDto result = questionService.getQuestionEligibilityInfoById("q3");
        assertEquals("Q3", result.getQuestion().getName());
        assertTrue(result.getOptions().size() > 0);
    }

    @Test
    void updateQuestion() {
        QuestionDto dto = QuestionDto.builder().name("Updated").questionText("Updated Text").description("desc").isRequired(true).requiresConditionalUpload(false).build();
        Question existing = Question.builder().id("q9").name("Old").build();
        when(questionRepository.findById("q9")).thenReturn(Optional.of(existing));
        when(questionRepository.save(any(Question.class))).thenReturn(existing);
        QuestionDto result = questionService.updateQuestion("q9", dto);
        assertEquals("Updated", result.getName());
    }

    @Test
    void getQuestionsForEligibility() {
        Question question = Question.builder().id("q12").name("Q12").inputType(InputType.RADIO).build();
        when(questionRepository.findAll()).thenReturn(List.of(question));
        List<QuestionEligibilityInfoDto> result = questionService.getQuestionsForEligibility();
        assertEquals(1, result.size());
    }

    @Test
    void getQuestionGroupsForEligibility() {
        QuestionGroup group = QuestionGroup.builder().id("g1").name("Group1").description("desc").questionIds(List.of("q13")).build();
        when(questionGroupRepository.findAll()).thenReturn(List.of(group));
        Question question = Question.builder().id("q13").name("Q13").inputType(InputType.RADIO).build();
        when(questionRepository.findById("q13")).thenReturn(Optional.of(question));
        List<QuestionGroupEligibilityInfoDto> result = questionService.getQuestionGroupsForEligibility();
        assertEquals(1, result.size());
        assertEquals("Group1", result.get(0).getName());
    }
}