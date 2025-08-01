package com.scholarship.scholarship.dto.grantProgramDtos;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateGrantProgramRequestDto {
    private String providerStaffId;
    private GrantProgramDto grantProgramDto;
}

