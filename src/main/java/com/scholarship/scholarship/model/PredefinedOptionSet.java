package com.scholarship.scholarship.model;

import com.scholarship.scholarship.valueObject.Option;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "predefined_option_sets")
public class PredefinedOptionSet {

    @Id
    private String id;

    @NotBlank(message = "Key is required")
    @Size(max = 100, message = "Key cannot exceed 100 characters")
    @Indexed(unique = true)
    private String key;

    @Size(max = 500, message = "Default question text cannot exceed 500 characters")
    private String defaultQuestionText;

    @Size(max = 1000, message = "Default description cannot exceed 1000 characters")
    private String defaultDescription;

    @Valid
    @NotEmpty(message = "Options list cannot be empty")
    private List<Option> options;

    @CreatedDate
    private Instant createdAt;
}