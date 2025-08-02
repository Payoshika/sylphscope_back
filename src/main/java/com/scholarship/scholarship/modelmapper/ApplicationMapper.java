package com.scholarship.scholarship.modelmapper;

import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.dto.EligibilityResultDto;
import com.scholarship.scholarship.model.Application;
import com.scholarship.scholarship.model.EligibilityResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EligibilityResultMapper.class})
public interface ApplicationMapper {
    ApplicationDto toDto(Application application);
    Application toEntity(ApplicationDto applicationDto);
    void updateEntityFromDto(ApplicationDto applicationDto, @MappingTarget Application application);
}