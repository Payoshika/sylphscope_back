package com.scholarship.scholarship.model;

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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "applications")
public class Application {

    @Id
    private String id;

    @NotBlank(message = "Grant program ID is required")
    private String grantProgramId;

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    @NotNull(message = "Eligibility Result is required")
    private EligibilityResult eiligibilityResult;

    private Map<String, StudentAnswer> studentAnswers;

    private Instant submittedAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Valid
    private List<Answer> answers;
}