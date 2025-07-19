// src/main/java/com/scholarship/scholarship/dto/GrantProgramApplicationDto.java
package com.scholarship.scholarship.dto.grantProgramDtos;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import com.scholarship.scholarship.dto.ApplicationDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GrantProgramApplicationDto {
    private GrantProgramDto grantProgram;
    private ApplicationDto application;
}