package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.DataType;
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
public class QuestionOptionSetDto {
    private String id;

    @NotBlank(message = "Option set label is required")
    private String optionSetLabel;

    private String description;

    @NotNull(message = "Option data type is required")
    private DataType optionDataType;

    private List<OptionDto> options;
    private Instant createdAt;
}