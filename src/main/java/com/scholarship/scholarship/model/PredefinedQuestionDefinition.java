package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.InputType;
import com.scholarship.scholarship.enums.DataType;

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

    @Indexed(unique = true)
    private String key;

    private String name;

    private String description;

    private InputType defaultInputType;

    private DataType valueDataType;

    private String predefinedOptionSetKey;

    @CreatedDate
    private Instant createdAt;
}
