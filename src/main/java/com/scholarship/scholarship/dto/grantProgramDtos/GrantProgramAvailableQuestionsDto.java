package com.scholarship.scholarship.dto.grantProgramDtos;

import lombok.Data;
import java.util.List;

@Data
public class GrantProgramAvailableQuestionsDto {
    private List<QuestionEligibilityInfoDto> questions;
    private List<QuestionGroupEligibilityInfoDto> questionGroups;
}