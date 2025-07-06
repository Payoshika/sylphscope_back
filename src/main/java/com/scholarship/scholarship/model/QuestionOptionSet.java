package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.model.Option;
import jakarta.validation.constraints.NotBlank;
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
@Document(collection = "question_option_sets")
public class QuestionOptionSet {
    @Id
    private String id;
    @Indexed(unique = true)
    @NotBlank(message = "Option set Label is required")
    private String optionSetLabel;
    private String description;
    @NotBlank(message = "Option data type is required")
    private DataType optionDataType;
    private List<Option> options;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}