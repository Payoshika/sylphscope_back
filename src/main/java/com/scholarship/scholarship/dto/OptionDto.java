package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.DataType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OptionDto {
    private String id;
    private String label;
    private Object value;
}