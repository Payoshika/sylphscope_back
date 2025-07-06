package com.scholarship.scholarship.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    @Id
    private String id;

    @NotBlank(message = "option label is required")
    private String label;

    @NotBlank(message = "option value is required")
    private Object value;
}