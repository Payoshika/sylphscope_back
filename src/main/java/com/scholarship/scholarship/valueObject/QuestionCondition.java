package com.scholarship.scholarship.valueObject;

import com.scholarship.scholarship.enums.ComparisonOperator;
import com.scholarship.scholarship.enums.DataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCondition {
    private String questionId;
    private String description; // Optional description for clarity
    private ComparisonOperator comparisonOperator;
    private List<Object> values;
    private DataType valueDataType;
}