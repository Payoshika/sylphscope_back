package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.GrantStatus;
import com.scholarship.scholarship.valueObject.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    // New fields for eligibility system
    private List<String> questionIds;
    private List<String> questionGroupsIds; // Optional - for detailed views
}