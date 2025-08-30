package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.SelectionCriterionDto;
import com.scholarship.scholarship.enums.EvaluationScale;
import com.scholarship.scholarship.enums.EvaluationType;
import com.scholarship.scholarship.model.SelectionCriterion;
import com.scholarship.scholarship.model.GrantProgram;
import com.scholarship.scholarship.repository.SelectionCriterionRepository;
import com.scholarship.scholarship.repository.GrantProgramRepository;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SelectionCriterionServiceTest {
    @Mock
    private SelectionCriterionRepository selectionCriterionRepository;
    @Mock
    private GrantProgramRepository grantProgramRepository;
    @InjectMocks
    private SelectionCriterionService selectionCriterionService;

    @Test
    void create() {
        SelectionCriterionDto dto = SelectionCriterionDto.builder()
                .grantProgramId("gp2")
                .criterionName("Criterion")
                .questionType("SINGLE_CHOICE")
                .questionId("q1")
                .weight(1)
                .evaluationType(EvaluationType.MANUAL)
                .evaluationScale(EvaluationScale.HUNDRED)
                .build();
        SelectionCriterion criterion = SelectionCriterion.builder()
                .grantProgramId("gp2")
                .criterionName("Criterion")
                .questionType("SINGLE_CHOICE")
                .questionId("q1")
                .weight(1)
                .evaluationType(EvaluationType.MANUAL)
                .evaluationScale(EvaluationScale.HUNDRED)
                .build();
        when(selectionCriterionRepository.save(any(SelectionCriterion.class))).thenReturn(criterion);
        SelectionCriterion result = selectionCriterionService.create(dto);
        assertEquals(dto.getGrantProgramId(), result.getGrantProgramId());
        assertEquals(dto.getCriterionName(), result.getCriterionName());
    }

    @Test
    void update() {
        String id = "sc2";
        SelectionCriterionDto dto = SelectionCriterionDto.builder()
                .criterionName("Updated")
                .questionType("SINGLE_CHOICE")
                .questionId("q2")
                .weight(2)
                .evaluationType(EvaluationType.MANUAL)
                .evaluationScale(EvaluationScale.HUNDRED)
                .build();
        SelectionCriterion existing = SelectionCriterion.builder().id(id).build();
        when(selectionCriterionRepository.findById(id)).thenReturn(Optional.of(existing));
        when(selectionCriterionRepository.save(any(SelectionCriterion.class))).thenReturn(existing);
        SelectionCriterion result = selectionCriterionService.update(id, dto);
        assertEquals("Updated", result.getCriterionName());
        assertEquals("q2", result.getQuestionId());
    }

    @Test
    void batchUpdate() {
        String grantProgramId = "gp3";
        SelectionCriterionDto dto1 = SelectionCriterionDto.builder().id(null).grantProgramId(grantProgramId).criterionName("New1").build();
        SelectionCriterionDto dto2 = SelectionCriterionDto.builder().id("sc3").grantProgramId(grantProgramId).criterionName("Existing").build();
        SelectionCriterion existing = SelectionCriterion.builder().id("sc3").grantProgramId(grantProgramId).criterionName("Existing").build();
        GrantProgram grantProgram = GrantProgram.builder().id(grantProgramId).selectionCriteria(List.of(existing)).build();
        when(grantProgramRepository.findById(grantProgramId)).thenReturn(Optional.of(grantProgram));
        when(selectionCriterionRepository.save(any(SelectionCriterion.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(grantProgramRepository.save(any(GrantProgram.class))).thenReturn(grantProgram);
        List<SelectionCriterion> result = selectionCriterionService.batchUpdate(grantProgramId, List.of(dto1, dto2));
        assertTrue(result.stream().anyMatch(sc -> "New1".equals(sc.getCriterionName())));
        assertTrue(result.stream().anyMatch(sc -> "Existing".equals(sc.getCriterionName())));
    }

}