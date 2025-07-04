package com.scholarship.scholarship.valueObject;

import com.scholarship.scholarship.enums.ComparisonCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityCriterion {
    private String criterionId;
    private String questionId;
    private ComparisonCondition condition;
    private Object value;
    private List<Object> values;
    private String description;
}