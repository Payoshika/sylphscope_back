package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.EligibilityCriteriaType;
import com.scholarship.scholarship.valueObject.CombinationLogic;
import com.scholarship.scholarship.valueObject.QuestionCondition;
import lombok.Data;

import java.util.List;

@Data
public class EligibilityCriteriaRequestDto {
    private String grantProgramId;
    private String name;
    private String description;
    private boolean required;
    private EligibilityCriteriaType criteriaType;

    // For simple criteria
    private String questionId;
    private QuestionCondition simpleCondition;

    // For complex criteria
    private String questionGroupId;
    private CombinationLogic combinationLogic;
    private List<QuestionCondition> questionConditions;
}