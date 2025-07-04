package com.scholarship.scholarship.valueObject;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    @NotBlank(message = "Option ID is required")
    private String optionId;

    @NotBlank(message = "Option text is required")
    private String text;

    private Integer value;
}