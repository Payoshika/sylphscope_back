package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "questions")
public class Question {
    @Id
    private String id;
    @NotBlank(message = "Question name is required")
    private String name;
    @NotBlank(message = "Question input type is required")
    private InputType inputType;
    @NotBlank(message = "Question data type is required")
    private DataType questionDataType;
    @NotNull(message = "Question text is required")
    private String questionText;
    @Size(max = 500, message = "description cannot exceed 500 characters")
    private String description;
    private String optionSetId;
    private Boolean isRequired;
    private Boolean requiresConditionalUpload;
    private String conditionalUploadLabel;
    private Instant createdAt;
}