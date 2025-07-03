package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredefinedQuestionDefinitionDto {
    private String id;
    private String key;
    private String name;
    private String description;
    private InputType defaultInputType;
    private DataType valueDataType;
    private String predefinedOptionSetKey;
    private Instant createdAt;
    private Instant updatedAt;
}