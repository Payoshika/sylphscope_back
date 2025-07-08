package com.scholarship.scholarship.service;

import com.scholarship.scholarship.enums.EligibilityCriteriaType;
import com.scholarship.scholarship.model.EligibilityCriteria;
import com.scholarship.scholarship.repository.EligibilityCriteriaRepository;
import com.scholarship.scholarship.valueObject.CombinationLogic;
import com.scholarship.scholarship.valueObject.QuestionCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EligibilityCriteriaService {

    private final EligibilityCriteriaRepository eligibilityCriteriaRepository;

    @Autowired
    public EligibilityCriteriaService(EligibilityCriteriaRepository eligibilityCriteriaRepository) {
        this.eligibilityCriteriaRepository = eligibilityCriteriaRepository;
    }

    public EligibilityCriteria createSimpleCriteria(
            String grantProgramId,
            String name,
            String description,
            boolean required,
            String questionId,
            QuestionCondition simpleCondition,
            EligibilityCriteriaType criteriaType
    ) {
        if (grantProgramId == null || questionId == null || simpleCondition == null || criteriaType == null) {
            throw new IllegalArgumentException("Required fields are missing for simple criteria");
        }
        EligibilityCriteria criteria = EligibilityCriteria.builder()
                .grantProgramId(grantProgramId)
                .name(name)
                .description(description)
                .required(required)
                .criteriaType(criteriaType)
                .questionId(questionId)
                .simpleCondition(simpleCondition)
                .build();
        return eligibilityCriteriaRepository.save(criteria);
    }

    public EligibilityCriteria createComplexCriteria(
            String grantProgramId,
            String name,
            String description,
            boolean required,
            String questionGroupId,
            CombinationLogic combinationLogic,
            List<QuestionCondition> questionConditions,
            EligibilityCriteriaType criteriaType
    ) {
        if (grantProgramId == null || questionGroupId == null || combinationLogic == null || questionConditions == null || criteriaType == null) {
            throw new IllegalArgumentException("Required fields are missing for complex criteria");
        }
        EligibilityCriteria criteria = EligibilityCriteria.builder()
                .grantProgramId(grantProgramId)
                .name(name)
                .description(description)
                .required(required)
                .criteriaType(criteriaType)
                .questionGroupId(questionGroupId)
                .combinationLogic(combinationLogic)
                .questionConditions(questionConditions)
                .build();
        return eligibilityCriteriaRepository.save(criteria);
    }
}