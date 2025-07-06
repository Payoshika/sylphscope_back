// src/main/java/com/scholarship/scholarship/model/PredefinedQuestionGroup.java
package com.scholarship.scholarship.model;

import jakarta.validation.constraints.NotBlank;
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
@Document(collection = "predefined_question_groups")
public class PredefinedQuestionGroup {

    @Id
    private String id;

    @NotBlank(message = "Group key is required")
    @Size(max = 100, message = "Group key cannot exceed 100 characters")
    @Indexed(unique = true)
    private String key;

    @NotBlank(message = "Group name is required")
    @Size(max = 100, message = "Group name cannot exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private Integer displayOrder;

    @CreatedDate
    private Instant createdAt;

}