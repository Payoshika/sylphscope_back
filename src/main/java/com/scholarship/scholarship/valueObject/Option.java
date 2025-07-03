package com.scholarship.scholarship.valueObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    private Object value;  // Can be String, Number, Boolean, etc.
    private String label;
    private String description;
}