package com.scholarship.scholarship.service;

import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import com.scholarship.scholarship.model.PredefinedOptionSet;
import com.scholarship.scholarship.model.PredefinedQuestionDefinition;
import com.scholarship.scholarship.repository.PredefinedOptionSetRepository;
import com.scholarship.scholarship.repository.PredefinedQuestionDefinitionRepository;
import com.scholarship.scholarship.valueObject.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PredefinedDefinitionServiceTest {

    @Mock
    private PredefinedOptionSetRepository optionSetRepository;

    @Mock
    private PredefinedQuestionDefinitionRepository questionDefinitionRepository;

    @InjectMocks
    private PredefinedOptionSetService optionSetService;

    @InjectMocks
    private PredefinedQuestionDefinitionService questionDefinitionService;

    private PredefinedOptionSet optionSet;
    private PredefinedQuestionDefinition questionDefinition;
    private List<Option> options;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test options
        options = Arrays.asList(
                new Option("yes", "Yes", 1),
                new Option("no", "No", 2),
                new Option("maybe", "Maybe", 3)
        );

        // Setup option set test data
        optionSet = new PredefinedOptionSet();
        optionSet.setKey("yes-no-maybe");
        optionSet.setDefaultQuestionText("Do you agree?");
        optionSet.setDefaultDescription("Select your response");
        optionSet.setOptions(options);
        optionSet.setId("optionSet123");
        optionSet.setCreatedAt(Instant.now());

        // Setup question definition test data
        questionDefinition = new PredefinedQuestionDefinition();
        questionDefinition.setKey("agreement-question");
        questionDefinition.setName("Agreement Question");
        questionDefinition.setDescription("Question about agreement");
        questionDefinition.setDefaultInputType(InputType.RADIO);
        questionDefinition.setValueDataType(DataType.STRING);
        questionDefinition.setPredefinedOptionSetKey(optionSet.getKey());
        questionDefinition.setId("question123");
        questionDefinition.setCreatedAt(Instant.now());
    }

    @Test
    void createQuestionDefinition_Success() {
        // Configure mock to return false for existsByKey check (key doesn't exist yet)
        when(questionDefinitionRepository.existsByKey(questionDefinition.getKey())).thenReturn(false);

        // Configure mock to return our test data when save is called
        when(questionDefinitionRepository.save(any(PredefinedQuestionDefinition.class))).thenReturn(questionDefinition);

        // Call the service method
        PredefinedQuestionDefinition result = questionDefinitionService.createQuestionDefinition(questionDefinition);

        // Verify the method was called
        verify(questionDefinitionRepository).save(any(PredefinedQuestionDefinition.class));

        // Assert the result matches expectations
        assertNotNull(result);
        assertEquals(questionDefinition.getId(), result.getId());
        assertEquals(questionDefinition.getKey(), result.getKey());
        assertEquals(questionDefinition.getName(), result.getName());
        assertEquals(questionDefinition.getDescription(), result.getDescription());
        assertEquals(questionDefinition.getDefaultInputType(), result.getDefaultInputType());
        assertEquals(questionDefinition.getValueDataType(), result.getValueDataType());
        assertEquals(questionDefinition.getPredefinedOptionSetKey(), result.getPredefinedOptionSetKey());
    }

}