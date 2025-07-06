package com.scholarship.scholarship.valueObject;

import com.scholarship.scholarship.enums.ComparisonCondition;
import com.scholarship.scholarship.model.Option;
import jakarta.validation.Valid;
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
    private Object simpleValue;
    @Valid
    private List<Option> multipleChoiceValues;
    private String description;
}