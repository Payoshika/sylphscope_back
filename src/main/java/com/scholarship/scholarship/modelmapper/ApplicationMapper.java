package com.scholarship.scholarship.modelmapper;

import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    // Direct mapping with matching field names
    ApplicationDto toDto(Application application);
    Application toEntity(ApplicationDto applicationDto);
    void updateEntityFromDto(ApplicationDto applicationDto, @MappingTarget Application application);
}