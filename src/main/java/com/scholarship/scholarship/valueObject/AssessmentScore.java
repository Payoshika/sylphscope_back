package com.scholarship.scholarship.valueObject;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentScore {
    @NotBlank(message = "Assessor ID is required")
    private String assessorId;

    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score must be at least 0")
    private Integer score;

    @Size(max = 1000, message = "Comments cannot exceed 1000 characters")
    private String comments;

    @NotNull(message = "Assessment date is required")
    private Instant assessedAt;
}