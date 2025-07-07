package com.scholarship.scholarship.dto;

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
public class EligibilityResultDto {
    private String id;
    private String studentId;
    private String applicationId;
    private String grantProgramId;
    private boolean isEligible;
    private Instant evaluatedAt;
    private Instant updatedAt;
    // Detailed breakdown
    private List<String> failedCriteria;
    private List<String> passedCriteria;
}