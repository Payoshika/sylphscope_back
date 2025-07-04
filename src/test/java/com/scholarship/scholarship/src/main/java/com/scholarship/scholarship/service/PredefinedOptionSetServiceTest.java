// PredefinedOptionSetServiceTest.java
package com.scholarship.scholarship.service;

import com.scholarship.scholarship.model.PredefinedOptionSet;
import com.scholarship.scholarship.repository.PredefinedOptionSetRepository;
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

class PredefinedOptionSetServiceTest {

    @Mock
    private PredefinedOptionSetRepository optionSetRepository;

    @InjectMocks
    private PredefinedOptionSetService optionSetService;

    private PredefinedOptionSet optionSet;
    private List<Option> options;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create test options
        options = Arrays.asList(
                new Option("value1", "Option 1", 1),
                new Option("value2", "Option 2", 2)
        );

        // Create entity
        optionSet = new PredefinedOptionSet();
        optionSet.setKey("test-options");
        optionSet.setDefaultQuestionText("Test question");
        optionSet.setDefaultDescription("Test description");
        optionSet.setOptions(options);
        optionSet.setId("testId123");
        optionSet.setCreatedAt(Instant.now());
    }

    @Test
    void createOptionSet_Success() {
        // Configure mock to return false for existsByKey check (key doesn't exist yet)
        when(optionSetRepository.existsByKey(optionSet.getKey())).thenReturn(false);

        // Configure mock to return our test data when save is called
        when(optionSetRepository.save(any(PredefinedOptionSet.class))).thenReturn(optionSet);

        // Call the service method
        PredefinedOptionSet result = optionSetService.createOptionSet(optionSet);

        // Verify the method was called
        verify(optionSetRepository).save(any(PredefinedOptionSet.class));

        // Assert the result matches expectations
        assertNotNull(result);
        assertEquals(optionSet.getId(), result.getId());
        assertEquals(optionSet.getKey(), result.getKey());
        assertEquals(optionSet.getDefaultQuestionText(), result.getDefaultQuestionText());
        assertEquals(optionSet.getDefaultDescription(), result.getDefaultDescription());
        assertEquals(optionSet.getOptions().size(), result.getOptions().size());
    }
}