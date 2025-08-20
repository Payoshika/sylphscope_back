package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.EligibilityCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EligibilityCriteriaRepositoryTest {
    @Mock
    private com.scholarship.scholarship.repository.EligibilityCriteriaRepository eligibilityCriteriaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByGrantProgramId() {
        EligibilityCriteria ec1 = new EligibilityCriteria();
        ec1.setId("1");
        ec1.setGrantProgramId("gp1");
        EligibilityCriteria ec2 = new EligibilityCriteria();
        ec2.setId("2");
        ec2.setGrantProgramId("gp1");
        when(eligibilityCriteriaRepository.findByGrantProgramId("gp1")).thenReturn(Arrays.asList(ec1, ec2));
        List<EligibilityCriteria> result = eligibilityCriteriaRepository.findByGrantProgramId("gp1");
        assertEquals(2, result.size());
        assertEquals("gp1", result.get(0).getGrantProgramId());
    }

    @Test
    void findByQuestionId() {
        EligibilityCriteria ec = new EligibilityCriteria();
        ec.setId("1");
        ec.setQuestionId("q1");
        when(eligibilityCriteriaRepository.findByQuestionId("q1")).thenReturn(Arrays.asList(ec));
        List<EligibilityCriteria> result = eligibilityCriteriaRepository.findByQuestionId("q1");
        assertEquals(1, result.size());
        assertEquals("q1", result.get(0).getQuestionId());
    }

    @Test
    void findByQuestionGroupId() {
        EligibilityCriteria ec = new EligibilityCriteria();
        ec.setId("1");
        ec.setQuestionGroupId("g1");
        when(eligibilityCriteriaRepository.findByQuestionGroupId("g1")).thenReturn(Arrays.asList(ec));
        List<EligibilityCriteria> result = eligibilityCriteriaRepository.findByQuestionGroupId("g1");
        assertEquals(1, result.size());
        assertEquals("g1", result.get(0).getQuestionGroupId());
    }

    @Test
    void findByRequired() {
        EligibilityCriteria ec = new EligibilityCriteria();
        ec.setId("1");
        ec.setRequired(true);
        when(eligibilityCriteriaRepository.findByRequired(true)).thenReturn(Arrays.asList(ec));
        List<EligibilityCriteria> result = eligibilityCriteriaRepository.findByRequired(true);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isRequired());
    }

    @Test
    void deleteByGrantProgramId() {
        doNothing().when(eligibilityCriteriaRepository).deleteByGrantProgramId("gp1");
        eligibilityCriteriaRepository.deleteByGrantProgramId("gp1");
        verify(eligibilityCriteriaRepository, times(1)).deleteByGrantProgramId("gp1");
    }
}