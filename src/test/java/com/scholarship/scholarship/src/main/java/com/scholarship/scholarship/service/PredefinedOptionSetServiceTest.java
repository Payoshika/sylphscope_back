package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.PredefinedOptionSetDto;
import com.scholarship.scholarship.model.PredefinedOptionSet;
import com.scholarship.scholarship.repository.PredefinedOptionSetRepository;
import com.scholarship.scholarship.valueObject.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PredefinedOptionSetServiceTest {

    @Mock
    private PredefinedOptionSetRepository optionSetRepository;

    @InjectMocks
    private PredefinedOptionSetService optionSetService;

    private PredefinedOptionSetDto optionSetDto;
    private PredefinedOptionSet optionSet;
    private List<Option> options;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create test options
        options = Arrays.asList(
                new Option("value1", "Option 1", "First option description"),
                new Option("value2", "Option 2", "Second option description")
        );

        // Create test DTO
        optionSetDto = new PredefinedOptionSetDto();
        optionSetDto.setKey("test-options");
        optionSetDto.setDefaultQuestionText("Test question");
        optionSetDto.setDefaultDescription("Test description");
        optionSetDto.setOptions(options);

        // Create entity with same data
        optionSet = new PredefinedOptionSet();
        BeanUtils.copyProperties(optionSetDto, optionSet);
        optionSet.setId("testId123");
        optionSet.setCreatedAt(Instant.now());
    }

    @Test
    void createOptionSet_Success() {
        // Configure mock to return false for existsByKey check (key doesn't exist yet)
        when(optionSetRepository.existsByKey(optionSetDto.getKey())).thenReturn(false);

        // Configure mock to return our test data when save is called
        when(optionSetRepository.save(any(PredefinedOptionSet.class))).thenReturn(optionSet);

        // Call the service method
        PredefinedOptionSetDto result = optionSetService.createOptionSet(optionSetDto);

        // Verify the method was called
        verify(optionSetRepository).save(any(PredefinedOptionSet.class));

        // Assert the result matches expectations
        assertNotNull(result);
        assertEquals(optionSet.getId(), result.getId());
        assertEquals(optionSetDto.getKey(), result.getKey());
        assertEquals(optionSetDto.getDefaultQuestionText(), result.getDefaultQuestionText());
        assertEquals(optionSetDto.getDefaultDescription(), result.getDefaultDescription());
        assertEquals(optionSetDto.getOptions().size(), result.getOptions().size());
    }
}