package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.EligibilityCriteriaType;
import com.scholarship.scholarship.valueObject.QuestionCondition;
import com.scholarship.scholarship.valueObject.CombinationLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "eligibility_criteria")
public class EligibilityCriteria {
    @Id
    private String id;

    @Indexed
    private String grantProgramId;

    private String name;
    private String description;
    private boolean required;
    private EligibilityCriteriaType criteriaType;

    // For simple criteria
    private String questionId;
    private QuestionCondition simpleCondition;

    // For complex criteria with multiple conditions
    private String questionGroupId;
    private CombinationLogic combinationLogic; // "ALL", "ANY", "NONE"
    private List<QuestionCondition> questionConditions;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}