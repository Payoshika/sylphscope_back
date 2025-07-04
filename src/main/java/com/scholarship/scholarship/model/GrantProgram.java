package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.GrantStatus;
import com.scholarship.scholarship.valueObject.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "grant_programs")
public class GrantProgram {

    @Id
    private String id;
    private String providerId;
    private String title;
    private String description;
    private List<AssignedStaff> assignedStaff;
    private GrantStatus status;
    private Schedule schedule;
    private List<Question> questions;
    private List<EligibilityCriterion> eligibilityCriteria;
    private List<SelectionCriterion> selectionCriteria;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

}