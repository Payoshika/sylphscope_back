package com.scholarship.scholarship.valueObject;

import com.scholarship.scholarship.enums.EvaluationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectionCriterion {
    private String selectionCriterionId;
    private String questionId;
    private Integer weight;
    private EvaluationType evaluationType;
    private List<AutoEvaluationRule> autoEvaluationRules;
    private String description;
    private Instant createdAt;
}