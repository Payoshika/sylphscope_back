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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eligibility-criteria")
public class EligibilityCriteriaController {

    private final EligibilityCriteriaService eligibilityCriteriaService;
    private final QuestionService questionService;
    private final QuestionGroupService questionGroupService;

    @Autowired
    public EligibilityCriteriaController(
            EligibilityCriteriaService eligibilityCriteriaService,
            QuestionService questionService,
            QuestionGroupService questionGroupService) {
        this.eligibilityCriteriaService = eligibilityCriteriaService;
        this.questionService = questionService;
        this.questionGroupService = questionGroupService;
    }

    @GetMapping("/by-grant-program/{grantProgramId}")
    public List<EligibilityCriteriaRequestDto> getCriteriaByGrantProgramId(@PathVariable String grantProgramId) {
        return eligibilityCriteriaService.getCriteriaByGrantProgramId(grantProgramId);
    }

    //mostly use this function for updating the criteria
    @PutMapping("/batch")
    public List<EligibilityCriteriaRequestDto> updateMultipleCriteria(@RequestBody List<EligibilityCriteriaRequestDto> dtos, @RequestParam String grantProgramId) {
        return eligibilityCriteriaService.updateEligibilityCriteria(dtos, grantProgramId);
    }

    @GetMapping("/criteria-and-question/by-grant-program/{grantProgramId}")
    public ResponseEntity<List<EligibilityCriteriaWithQuestionDto>> getEligibilityCriteriaAndQuestionFromGrantProgramId(
            @PathVariable String grantProgramId) {
        List<EligibilityCriteriaRequestDto> criteriaList = eligibilityCriteriaService.getCriteriaByGrantProgramId(grantProgramId);

        List<EligibilityCriteriaWithQuestionDto> result = criteriaList.stream().map(dto -> {
            QuestionEligibilityInfoDto question = null;
            QuestionGroupEligibilityInfoDto questionGroup = null;

            if (dto.getQuestionId() != null) {
                question = questionService.getQuestionEligibilityInfoById(dto.getQuestionId());
            }
            if (dto.getQuestionGroupId() != null) {
                questionGroup = questionGroupService.getQuestionGroupEligibilityInfoById(dto.getQuestionGroupId());
            }
            return EligibilityCriteriaWithQuestionDto.builder()
                    .eligibilityCriteria(dto)
                    .question(question)
                    .questionGroup(questionGroup)
                    .build();
        }).toList();

        return ResponseEntity.ok(result);
    }

    // Defined but used in the testing purpose
    @PostMapping("/simple")
    public EligibilityCriteria createSimpleCriteria(@RequestBody EligibilityCriteriaRequestDto dto) {
        return eligibilityCriteriaService.createSimpleCriteria(
                dto.getGrantProgramId(),
                dto.getName(),
                dto.getDescription(),
                dto.isRequired(),
                dto.getQuestionId(),
                dto.getSimpleCondition(),
                dto.getCriteriaType()
        );
    }

    @PostMapping("/complex")
    public EligibilityCriteria createComplexCriteria(@RequestBody EligibilityCriteriaRequestDto dto) {
        return eligibilityCriteriaService.createComplexCriteria(
                dto.getGrantProgramId(),
                dto.getName(),
                dto.getDescription(),
                dto.isRequired(),
                dto.getQuestionGroupId(),
                dto.getCombinationLogic(),
                dto.getQuestionConditions(),
                dto.getCriteriaType()
        );
    }
}