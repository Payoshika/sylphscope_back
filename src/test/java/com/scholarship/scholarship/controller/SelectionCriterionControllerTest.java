package com.scholarship.scholarship.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.scholarship.scholarship.dto.SelectionCriterionDto;
import com.scholarship.scholarship.model.SelectionCriterion;
import com.scholarship.scholarship.service.SelectionCriterionService;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SelectionCriterionControllerTest {
    @Mock
    private SelectionCriterionService selectionCriterionService;
    @InjectMocks
    private SelectionCriterionController selectionCriterionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByGrantProgramId() {
        List<SelectionCriterion> criteria = List.of(new SelectionCriterion());
        when(selectionCriterionService.getByGrantProgramId("gp1")).thenReturn(criteria);
        List<SelectionCriterion> result = selectionCriterionController.getByGrantProgramId("gp1");
        assertEquals(criteria, result);
    }

    @Test
    void create() {
        SelectionCriterionDto dto = new SelectionCriterionDto();
        SelectionCriterion criterion = new SelectionCriterion();
        when(selectionCriterionService.create(dto)).thenReturn(criterion);
        SelectionCriterion result = selectionCriterionController.create(dto);
        assertEquals(criterion, result);
    }

    @Test
    void update() {
        SelectionCriterionDto dto = new SelectionCriterionDto();
        SelectionCriterion criterion = new SelectionCriterion();
        when(selectionCriterionService.update("id1", dto)).thenReturn(criterion);
        SelectionCriterion result = selectionCriterionController.update("id1", dto);
        assertEquals(criterion, result);
    }

    @Test
    void batchUpdate() {
        List<SelectionCriterionDto> dtos = List.of(new SelectionCriterionDto());
        List<SelectionCriterion> criteria = List.of(new SelectionCriterion());
        when(selectionCriterionService.batchUpdate("gp1", dtos)).thenReturn(criteria);
        List<SelectionCriterion> result = selectionCriterionController.batchUpdate("gp1", dtos);
        assertEquals(criteria, result);
    }

}