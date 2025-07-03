package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.PredefinedOptionSetDto;
import com.scholarship.scholarship.dto.PredefinedQuestionDefinitionDto;
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

    private PredefinedOptionSetDto optionSetDto;
    private PredefinedQuestionDefinitionDto questionDefinitionDto;
    private PredefinedOptionSet optionSet;
    private PredefinedQuestionDefinition questionDefinition;
    private List<Option> options;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test options
        options = Arrays.asList(
                new Option("yes", "Yes", "Affirmative response"),
                new Option("no", "No", "Negative response"),
                new Option("maybe", "Maybe", "Uncertain response")
        );

        // Setup option set test data
        optionSetDto = new PredefinedOptionSetDto();
        optionSetDto.setKey("yes-no-maybe");
        optionSetDto.setDefaultQuestionText("Do you agree?");
        optionSetDto.setDefaultDescription("Select your response");
        optionSetDto.setOptions(options);

        optionSet = new PredefinedOptionSet();
        optionSet.setId("optionSet123");
        optionSet.setKey(optionSetDto.getKey());
        optionSet.setDefaultQuestionText(optionSetDto.getDefaultQuestionText());
        optionSet.setDefaultDescription(optionSetDto.getDefaultDescription());
        optionSet.setOptions(optionSetDto.getOptions());
        optionSet.setCreatedAt(Instant.now());

        // Setup question definition test data
        questionDefinitionDto = new PredefinedQuestionDefinitionDto();
        questionDefinitionDto.setKey("agreement-question");
        questionDefinitionDto.setName("Agreement Question");
        questionDefinitionDto.setDescription("Question about agreement");
        questionDefinitionDto.setDefaultInputType(InputType.RADIO);
        questionDefinitionDto.setValueDataType(DataType.STRING);
        questionDefinitionDto.setPredefinedOptionSetKey(optionSetDto.getKey());

        questionDefinition = new PredefinedQuestionDefinition();
        questionDefinition.setId("question123");
        questionDefinition.setKey(questionDefinitionDto.getKey());
        questionDefinition.setName(questionDefinitionDto.getName());
        questionDefinition.setDescription(questionDefinitionDto.getDescription());
        questionDefinition.setDefaultInputType(questionDefinitionDto.getDefaultInputType());
        questionDefinition.setValueDataType(questionDefinitionDto.getValueDataType());
        questionDefinition.setPredefinedOptionSetKey(questionDefinitionDto.getPredefinedOptionSetKey());
        questionDefinition.setCreatedAt(Instant.now());
    }

    @Test
    void createOptionSet() {
        // Configure mock to return false for existsByKey check (key doesn't exist yet)
        when(optionSetRepository.existsByKey(optionSetDto.getKey())).thenReturn(false);

        // Configure mock to return our test data when save is called
        when(optionSetRepository.save(any(PredefinedOptionSet.class))).thenReturn(optionSet);

        // Call the service method
        PredefinedOptionSetDto result = optionSetService.createOptionSet(optionSetDto);

        // Verify the method was called and the result matches expectations
        verify(optionSetRepository).save(any(PredefinedOptionSet.class));

        assertNotNull(result);
        assertEquals(optionSet.getId(), result.getId());
        assertEquals(optionSetDto.getKey(), result.getKey());
        assertEquals(optionSetDto.getDefaultQuestionText(), result.getDefaultQuestionText());
        assertEquals(optionSetDto.getDefaultDescription(), result.getDefaultDescription());
        assertEquals(optionSetDto.getOptions().size(), result.getOptions().size());
    }
}