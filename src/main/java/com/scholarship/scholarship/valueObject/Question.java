package com.scholarship.scholarship.valueObject;

import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import com.scholarship.scholarship.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String questionId; // Unique identifier for the question
    private QuestionType type;
    private String questionText;
    private String description;
    private InputType inputType;
    private Boolean isRequired;
    private String predefinedKey;
    private DataType dataType;
    private List<Option> options;
    private Boolean requiresConditionalUpload;
    private String conditionalUploadLabel;
    private Instant createdAt;
}