package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.EligibilityCriteriaRequestDto;
import com.scholarship.scholarship.enums.EligibilityCriteriaType;
import com.scholarship.scholarship.model.EligibilityCriteria;
import com.scholarship.scholarship.modelmapper.EligibilityCriteriaMapper;
import com.scholarship.scholarship.repository.EligibilityCriteriaRepository;
import com.scholarship.scholarship.repository.GrantProgramRepository;
import com.scholarship.scholarship.valueObject.CombinationLogic;
import com.scholarship.scholarship.valueObject.QuestionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EligibilityCriteriaServiceTest {
    @Mock
    private GrantProgramRepository grantProgramRepository;
    @Mock
    private EligibilityCriteriaRepository eligibilityCriteriaRepository;
    @Mock
    private EligibilityCriteriaMapper eligibilityCriteriaMapper;
    @InjectMocks
    private EligibilityCriteriaService eligibilityCriteriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateEligibilityCriteria() {
        EligibilityCriteriaRequestDto dto = new EligibilityCriteriaRequestDto();
        dto.setId("1");
        dto.setGrantProgramId("gp1");
        EligibilityCriteria criteria = new EligibilityCriteria();
        criteria.setId("1");
        criteria.setGrantProgramId("gp1");
        when(eligibilityCriteriaRepository.findAll()).thenReturn(List.of(criteria));
        when(eligibilityCriteriaRepository.findById("1")).thenReturn(Optional.of(criteria));
        when(eligibilityCriteriaMapper.toEntity(dto)).thenReturn(criteria);
        when(eligibilityCriteriaRepository.save(any(EligibilityCriteria.class))).thenReturn(criteria);
        when(eligibilityCriteriaMapper.toDto(criteria)).thenReturn(dto);
        List<EligibilityCriteriaRequestDto> result = eligibilityCriteriaService.updateEligibilityCriteria(List.of(dto), "gp1");
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    void getCriteriaByGrantProgramId() {
        EligibilityCriteria criteria = new EligibilityCriteria();
        criteria.setId("1");
        criteria.setGrantProgramId("gp1");
        EligibilityCriteriaRequestDto dto = new EligibilityCriteriaRequestDto();
        dto.setId("1");
        dto.setGrantProgramId("gp1");
        when(eligibilityCriteriaRepository.findAll()).thenReturn(List.of(criteria));
        when(eligibilityCriteriaMapper.toDto(criteria)).thenReturn(dto);
        List<EligibilityCriteriaRequestDto> result = eligibilityCriteriaService.getCriteriaByGrantProgramId("gp1");
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    void createSimpleCriteria() {
        QuestionCondition condition = new QuestionCondition();
        EligibilityCriteria criteria = EligibilityCriteria.builder()
                .grantProgramId("gp1")
                .name("name")
                .description("desc")
                .required(true)
                .criteriaType(EligibilityCriteriaType.SINGLE)
                .questionId("q1")
                .simpleCondition(condition)
                .build();
        when(eligibilityCriteriaRepository.save(any(EligibilityCriteria.class))).thenReturn(criteria);
        EligibilityCriteria result = eligibilityCriteriaService.createSimpleCriteria("gp1", "name", "desc", true, "q1", condition, EligibilityCriteriaType.SINGLE);
        assertEquals("gp1", result.getGrantProgramId());
        assertEquals("q1", result.getQuestionId());
        assertEquals(EligibilityCriteriaType.SINGLE, result.getCriteriaType());
    }

    @Test
    void createComplexCriteria() {
        CombinationLogic logic = new CombinationLogic();
        List<QuestionCondition> conditions = List.of(new QuestionCondition());
        EligibilityCriteria criteria = EligibilityCriteria.builder()
                .grantProgramId("gp1")
                .name("name")
                .description("desc")
                .required(true)
                .criteriaType(EligibilityCriteriaType.QUESTION_GROUP)
                .questionGroupId("g1")
                .combinationLogic(logic)
                .questionConditions(conditions)
                .build();
        when(eligibilityCriteriaRepository.save(any(EligibilityCriteria.class))).thenReturn(criteria);
        EligibilityCriteria result = eligibilityCriteriaService.createComplexCriteria("gp1", "name", "desc", true, "g1", logic, conditions, EligibilityCriteriaType.QUESTION_GROUP);
        assertEquals("gp1", result.getGrantProgramId());
        assertEquals("g1", result.getQuestionGroupId());
        assertEquals(EligibilityCriteriaType.QUESTION_GROUP, result.getCriteriaType());
    }
}