package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.ApplicationStatus;
import com.scholarship.scholarship.model.StudentAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {
    private String id;
    private String studentId;
    private String grantProgramId;
    private ApplicationStatus status;
    private Instant submittedAt;
    private Instant updatedAt;
    private EligibilityResultDto eligibilityResult;
    private Map<String, StudentAnswerDto> studentAnswers;
}