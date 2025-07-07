package com.scholarship.scholarship.dto;
import com.scholarship.scholarship.model.Option;
import com.scholarship.scholarship.enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class QuestionDto {
    private String id;
    @NotBlank(message = "Question key is required")
    private String name;
    @NotBlank(message = "Question text is required")
    private String questionText;
    @NotNull(message = "Question type is required")
    private QuestionType questionType;
    private boolean isRequired;
    private String description;
    private Boolean requiresConditionalUpload;
    private String conditionalUploadLabel;
    private Instant createdAt;
}