package com.scholarship.scholarship.service;

import com.scholarship.scholarship.enums.ComparisonOperator;
import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.valueObject.QuestionCondition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionConditionService {

    public QuestionCondition createQuestionCondition(
            String questionId,
            String description,
            ComparisonOperator comparisonOperator,
            List<Object> values,
            DataType valueDataType
    ) {
        // Add validation logic as needed
        if (questionId == null || comparisonOperator == null || valueDataType == null) {
            throw new IllegalArgumentException("Required fields are missing");
        }

        return QuestionCondition.builder()
                .questionId(questionId)
                .description(description)
                .comparisonOperator(comparisonOperator)
                .values(values)
                .valueDataType(valueDataType)
                .build();
    }
}