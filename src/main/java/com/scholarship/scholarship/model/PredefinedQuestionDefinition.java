package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.InputType;
import com.scholarship.scholarship.enums.DataType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "predefined_question_definitions")
public class PredefinedQuestionDefinition {

    @Id
    private String id;

    @NotBlank(message = "Key is required")
    @Size(max = 100, message = "Key cannot exceed 100 characters")
    @Indexed(unique = true)
    private String key;

    private String groupId;

    // Order within the group
    private Integer displayOrder;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 200 characters")
    private String name;

    @Size(max = 200, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Default input type is required")
    private InputType defaultInputType;

    @NotNull(message = "Value data type is required")
    private DataType valueDataType;

    @Size(max = 100, message = "Predefined option set key cannot exceed 100 characters")
    private String predefinedOptionSetKey;

    @CreatedDate
    private Instant createdAt;
}