package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.ApplicationStatus;
import com.scholarship.scholarship.valueObject.Answer;
import com.scholarship.scholarship.valueObject.AssessmentScore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ApplicationDto {
    private String id;

    @NotBlank(message = "Grant program ID is required")
    private String grantProgramId;

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    private Instant submittedAt;

    private Instant updatedAt;

    @Valid
    private List<Answer> answers;

    @Valid
    private List<AssessmentScore> assessmentScores;
}