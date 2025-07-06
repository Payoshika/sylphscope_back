package com.scholarship.scholarship.valueObject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Rule ID is required")
    private String ruleId;

    @NotBlank(message = "Condition is required")
    private String condition;

    @NotBlank(message = "Evaluation criteria is required")
    private String evaluationCriteria;

    private Object value;

    private List<Object> values;

    @NotNull(message = "Score is required")
    private Integer score;

    @NotNull(message = "Default flag is required")
    private Boolean isDefault;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}