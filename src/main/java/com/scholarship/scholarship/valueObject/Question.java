package com.scholarship.scholarship.valueObject;

import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import com.scholarship.scholarship.enums.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Question ID is required")
    private String questionId;
    @NotBlank(message = "Question type is required")
    private QuestionType type;
    @NotNull(message = "Question text is required")
    private String questionText;
    @Size(max = 500, message = "description cannot exceed 500 characters")
    private String description;
    private InputType inputType;
    private Boolean isRequired;
    private String predefinedKey;
    private DataType dataType;
    @Valid
    private List<Option> options;
    private Boolean requiresConditionalUpload;
    private String conditionalUploadLabel;
    private Instant createdAt;
}