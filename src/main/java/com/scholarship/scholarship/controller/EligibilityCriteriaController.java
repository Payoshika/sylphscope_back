package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.EligibilityCriteriaRequestDto;
import com.scholarship.scholarship.enums.EligibilityCriteriaType;
import com.scholarship.scholarship.model.EligibilityCriteria;
import com.scholarship.scholarship.service.EligibilityCriteriaService;
import com.scholarship.scholarship.valueObject.CombinationLogic;
import com.scholarship.scholarship.valueObject.QuestionCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eligibility-criteria")
public class EligibilityCriteriaController {

    private final EligibilityCriteriaService eligibilityCriteriaService;

    @Autowired
    public EligibilityCriteriaController(EligibilityCriteriaService eligibilityCriteriaService) {
        this.eligibilityCriteriaService = eligibilityCriteriaService;
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