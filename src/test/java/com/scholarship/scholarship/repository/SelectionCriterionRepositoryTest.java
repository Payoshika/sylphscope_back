package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.SelectionCriterion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SelectionCriterionRepositoryTest {
    @Mock
    private SelectionCriterionRepository selectionCriterionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByGrantProgramId() {
        SelectionCriterion sc1 = new SelectionCriterion();
        sc1.setId("1");
        sc1.setGrantProgramId("gp1");
        SelectionCriterion sc2 = new SelectionCriterion();
        sc2.setId("2");
        sc2.setGrantProgramId("gp1");
        when(selectionCriterionRepository.findByGrantProgramId("gp1")).thenReturn(Arrays.asList(sc1, sc2));
        List<SelectionCriterion> result = selectionCriterionRepository.findByGrantProgramId("gp1");
        assertEquals(2, result.size());
        assertEquals("gp1", result.get(0).getGrantProgramId());
        assertEquals("gp1", result.get(1).getGrantProgramId());
    }
}