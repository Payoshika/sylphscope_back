package com.scholarship.scholarship.dto.grantProgramDtos;
import com.scholarship.scholarship.dto.EligibilityCriteriaRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityCriteriaWithQuestionDto {
    private EligibilityCriteriaRequestDto eligibilityCriteria;
    private QuestionEligibilityInfoDto question;
    private QuestionGroupEligibilityInfoDto questionGroup;
}