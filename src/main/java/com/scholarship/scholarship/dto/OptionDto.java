package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.DataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionDto {
    private String id;
    private String label;
    private Object value;
}