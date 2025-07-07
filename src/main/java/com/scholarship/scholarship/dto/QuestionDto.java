package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private String id;

    @NotBlank(message = "Question name is required")
    private String name;

    @NotBlank(message = "Question text is required")
    private String questionText;

    @NotNull(message = "Input type is required")
    private InputType inputType;

    @NotNull(message = "Question data type is required")
    private DataType questionDataType;

    private boolean isRequired;
    private String description;
    private Boolean requiresConditionalUpload;
    private String conditionalUploadLabel;

    // For questions with options (RADIO, MULTISELECT)
    private String optionSetId;
    private QuestionOptionSetDto optionSet;
    private Instant createdAt;
}