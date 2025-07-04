package com.scholarship.scholarship.valueObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoEvaluationRule {
    private String ruleId;
    private String condition;
    private String evaluationCriteria;
    private Object value;
    private List<Object> values;
    private Integer score;
    private Boolean isDefault;
    private String description;
}