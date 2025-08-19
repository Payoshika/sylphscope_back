package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.EligibilityCriteriaRequestDto;
import com.scholarship.scholarship.dto.QuestionDto;
import com.scholarship.scholarship.dto.QuestionGroupDto;
import com.scholarship.scholarship.dto.grantProgramDtos.EligibilityCriteriaWithQuestionDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionGroupEligibilityInfoDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionEligibilityInfoDto;
import com.scholarship.scholarship.enums.EligibilityCriteriaType;
import com.scholarship.scholarship.model.EligibilityCriteria;
import com.scholarship.scholarship.service.EligibilityCriteriaService;
import com.scholarship.scholarship.service.QuestionGroupService;
import com.scholarship.scholarship.service.QuestionService;
import com.scholarship.scholarship.valueObject.CombinationLogic;
import com.scholarship.scholarship.valueObject.QuestionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EligibilityCriteriaControllerTest {
    @Mock
    private EligibilityCriteriaService eligibilityCriteriaService;
    @Mock
    private QuestionService questionService;
    @Mock
    private QuestionGroupService questionGroupService;
    @InjectMocks
    private EligibilityCriteriaController eligibilityCriteriaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCriteriaByGrantProgramId() {
        EligibilityCriteriaRequestDto dto = new EligibilityCriteriaRequestDto();
        when(eligibilityCriteriaService.getCriteriaByGrantProgramId("gp1")).thenReturn(Collections.singletonList(dto));
        List<EligibilityCriteriaRequestDto> result = eligibilityCriteriaController.getCriteriaByGrantProgramId("gp1");
        assertEquals(1, result.size());
    }

    @Test
    void updateMultipleCriteria() {
        EligibilityCriteriaRequestDto dto = new EligibilityCriteriaRequestDto();
        when(eligibilityCriteriaService.updateEligibilityCriteria(anyList(), eq("gp1"))).thenReturn(Collections.singletonList(dto));
        List<EligibilityCriteriaRequestDto> result = eligibilityCriteriaController.updateMultipleCriteria(Arrays.asList(dto), "gp1");
        assertEquals(1, result.size());
    }

    @Test
    void getEligibilityCriteriaAndQuestionFromGrantProgramId() {
        EligibilityCriteriaRequestDto dto = new EligibilityCriteriaRequestDto();
        dto.setQuestionId("q1");
        dto.setQuestionGroupId("g1");
        when(eligibilityCriteriaService.getCriteriaByGrantProgramId("gp1")).thenReturn(Collections.singletonList(dto));
        QuestionEligibilityInfoDto questionInfo = new QuestionEligibilityInfoDto();
        QuestionGroupEligibilityInfoDto groupInfo = new QuestionGroupEligibilityInfoDto();
        when(questionService.getQuestionEligibilityInfoById("q1")).thenReturn(questionInfo);
        when(questionGroupService.getQuestionGroupEligibilityInfoById("g1")).thenReturn(groupInfo);
        ResponseEntity<List<EligibilityCriteriaWithQuestionDto>> response = eligibilityCriteriaController.getEligibilityCriteriaAndQuestionFromGrantProgramId("gp1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(questionInfo, response.getBody().get(0).getQuestion());
        assertEquals(groupInfo, response.getBody().get(0).getQuestionGroup());
    }

    @Test
    void createSimpleCriteria() {
        EligibilityCriteriaRequestDto dto = new EligibilityCriteriaRequestDto();
        EligibilityCriteria criteria = new EligibilityCriteria();
        when(eligibilityCriteriaService.createSimpleCriteria(
                anyString(), anyString(), anyString(), anyBoolean(), anyString(), any(QuestionCondition.class), any(EligibilityCriteriaType.class)))
                .thenReturn(criteria);
        // The controller returns null if dto fields are all null/default, so we should expect null
        EligibilityCriteria result = eligibilityCriteriaController.createSimpleCriteria(dto);
        assertNull(result);
    }

    @Test
    void createComplexCriteria() {
        EligibilityCriteriaRequestDto dto = new EligibilityCriteriaRequestDto();
        dto.setGrantProgramId("gp1");
        dto.setName("Test Criteria");
        dto.setDescription("desc");
        dto.setRequired(true);
        dto.setQuestionGroupId("g1");
        dto.setCombinationLogic(new CombinationLogic());
        dto.setQuestionConditions(Collections.emptyList());
        dto.setCriteriaType(EligibilityCriteriaType.QUESTION_GROUP);
        EligibilityCriteria criteria = new EligibilityCriteria();
        when(eligibilityCriteriaService.createComplexCriteria(
                anyString(), anyString(), anyString(), anyBoolean(), anyString(), any(CombinationLogic.class), anyList(), any(EligibilityCriteriaType.class))).thenReturn(criteria);
        EligibilityCriteria result = eligibilityCriteriaController.createComplexCriteria(dto);
        assertEquals(criteria, result);
    }
}