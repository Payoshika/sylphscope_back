package com.scholarship.scholarship.dto.grantProgramDtos;

import com.scholarship.scholarship.enums.EvaluationScale;
import com.scholarship.scholarship.enums.GrantStatus;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.model.SelectionCriterion;
import com.scholarship.scholarship.valueObject.Schedule;
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
public class GrantProgramDto {
    private String id;
    private String title;
    private String description;
    private String providerId;
    private GrantStatus status;
    private Schedule schedule;
    private Instant createdAt;
    private Instant updatedAt;
    private ProviderStaff contactPerson;
    private List<String> assignedStaffIds; // List of staff IDs assigned to the grant program
    // New fields for eligibility system
    private List<String> questionIds;
    private List<String> questionGroupsIds; // Optional - for detailed views
    private List<SelectionCriterion> selectionCriteria;
    private EvaluationScale evaluationScale; // New attribute
    private List<Integer> award;
    private Integer numOfAward;
}