package com.scholarship.scholarship.dto.grantProgramDtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionGroupEligibilityInfoDto {
    private String id;
    private String name;
    private String description;
    private List<QuestionEligibilityInfoDto> questions;
}