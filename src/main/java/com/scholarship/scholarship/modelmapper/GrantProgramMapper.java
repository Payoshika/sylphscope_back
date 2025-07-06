package com.scholarship.scholarship.modelmapper;

import com.scholarship.scholarship.dto.GrantProgramDto;
import com.scholarship.scholarship.model.GrantProgram;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GrantProgramMapper {
    GrantProgramDto toDto(GrantProgram entity);
    GrantProgram toEntity(GrantProgramDto dto);
}