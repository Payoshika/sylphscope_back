package com.scholarship.scholarship.dto.grantProgramDtos;

import lombok.Data;
import java.util.List;

@Data
public class QuestionGroupEligibilityInfoDto {
    private String id;
    private String name;
    private String description;
    private List<QuestionEligibilityInfoDto> questions;
}