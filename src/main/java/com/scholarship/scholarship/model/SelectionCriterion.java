// src/main/java/com/scholarship/scholarship/model/SelectionCriterion.java
package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.EvaluationType;
import com.scholarship.scholarship.enums.EvaluationScale;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "selection_criteria")
@Getter
public class SelectionCriterion {
    @Id
    private String id;

    private String criterionName; // Renamed from name

    private String grantProgramId;

    private String questionType; // Renamed from criteriaType

    private String questionId; // questionId or questionGroupId

    private int weight;

    private EvaluationType evaluationType;

    private EvaluationScale evaluationScale;
}