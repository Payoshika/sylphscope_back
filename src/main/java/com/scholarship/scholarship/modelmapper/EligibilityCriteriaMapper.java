package com.scholarship.scholarship.modelmapper;

import com.scholarship.scholarship.dto.EligibilityCriteriaRequestDto;
import com.scholarship.scholarship.model.EligibilityCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EligibilityCriteriaMapper {
    @Mapping(source = "id", target = "id")
    EligibilityCriteriaRequestDto toDto(EligibilityCriteria entity);

    @Mapping(source = "id", target = "id")
    EligibilityCriteria toEntity(EligibilityCriteriaRequestDto dto);
}