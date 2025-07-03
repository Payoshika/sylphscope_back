package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.valueObject.Option;
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
public class PredefinedOptionSetDto {
    private String id;
    private String key;
    private String defaultQuestionText;
    private String defaultDescription;
    private List<Option> options;
    private Instant createdAt;
    private Instant updatedAt;
}