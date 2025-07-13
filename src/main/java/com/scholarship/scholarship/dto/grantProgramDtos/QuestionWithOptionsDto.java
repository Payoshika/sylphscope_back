// src/main/java/com/scholarship/scholarship/dto/QuestionWithOptionsDto.java
package com.scholarship.scholarship.dto.grantProgramDtos;
import com.scholarship.scholarship.dto.QuestionDto;
import com.scholarship.scholarship.dto.OptionDto;

import com.scholarship.scholarship.enums.DataType;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class QuestionWithOptionsDto {
    private QuestionDto question;
    private List<OptionDto> options;
}