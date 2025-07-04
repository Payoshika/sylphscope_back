package com.scholarship.scholarship.valueObject;

import com.scholarship.scholarship.enums.EvaluationType;
import com.scholarship.scholarship.enums.MarkingScale;
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
    private MarkingScale markingScale;
    private List<AutoEvaluationRule> autoEvaluationRules;
    private String description;
    private Instant createdAt;
}