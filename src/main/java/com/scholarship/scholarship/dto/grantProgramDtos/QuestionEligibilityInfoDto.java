package com.scholarship.scholarship.dto.grantProgramDtos;

import com.scholarship.scholarship.model.Question;
import com.scholarship.scholarship.model.Option;
import com.scholarship.scholarship.enums.ComparisonOperator;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QuestionEligibilityInfoDto {
    private Question question;
    private List<Option> options;
    private List<ComparisonOperator> operators;

}