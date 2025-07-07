package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.DataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAnswerDto {
    private String id;
    private String studentId;
    private String questionId;
    private String applicationId;
    private Object value;
    private String selectedOptionId;
    private DataType valueDataType;
    private Instant answeredAt;
    private Instant updatedAt;
    // Context information
    private String questionText;
    private String optionText;
}