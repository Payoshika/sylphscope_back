package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.EvaluationScale;
import com.scholarship.scholarship.enums.GrantStatus;
import com.scholarship.scholarship.enums.MarkingScale;
import com.scholarship.scholarship.valueObject.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "grant_programs")
public class GrantProgram {

    @Id
    private String id;

    @NotBlank(message = "Provider ID is required")
    private String providerId;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Valid
    private List<AssignedStaff> assignedStaff;

    @NotNull(message = "Status is required")
    private GrantStatus status;

    @Valid
    @NotNull(message = "Schedule is required")
    private Schedule schedule;

    @Valid
    private List<String> questionGroupsIds;

    @Valid
    private List<String> questionIds;

    @Valid
    private List<SelectionCriterion> selectionCriteria;

    private EvaluationScale evaluationScale;

    private List<Integer> award;
    private Integer numOfAward;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}