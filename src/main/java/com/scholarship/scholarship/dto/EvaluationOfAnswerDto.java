package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.EvaluationScale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationOfAnswerDto {
    private String id;
    private String studentAnswerId;
    private String applicationId;
    private String grantProgramId;  // Added grantProgramId field
    private String evaluatorId; // providerStaffId
    private String questionId;
    private String questionGroupId;
    private Integer value;
    private EvaluationScale evaluationScale;
    private Instant createdAt;
    private Instant updatedAt;
}
