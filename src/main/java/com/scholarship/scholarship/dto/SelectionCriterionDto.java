// src/main/java/com/scholarship/scholarship/dto/SelectionCriterionDto.java
package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.EvaluationType;
import com.scholarship.scholarship.enums.EvaluationScale;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SelectionCriterionDto {
    private String id;
    private String criterionName;
    private String grantProgramId;
    private String questionType;
    private String questionId;
    private int weight;
    private EvaluationType evaluationType;
    private EvaluationScale evaluationScale;
}