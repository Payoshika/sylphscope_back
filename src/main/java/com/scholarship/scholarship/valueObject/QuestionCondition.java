package com.scholarship.scholarship.valueObject;

import com.scholarship.scholarship.enums.ComparisonOperator;
import com.scholarship.scholarship.enums.DataType;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QuestionCondition {
    private String questionId;
    private String description; // Optional description for clarity
    private ComparisonOperator comparisonOperator;
    private List<Object> values;
    private DataType valueDataType;
}