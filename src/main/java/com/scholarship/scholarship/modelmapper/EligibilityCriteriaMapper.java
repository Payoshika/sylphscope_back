package com.scholarship.scholarship.modelmapper;

import com.scholarship.scholarship.dto.EligibilityCriteriaRequestDto;
import com.scholarship.scholarship.model.EligibilityCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EligibilityCriteriaMapper {
    EligibilityCriteriaRequestDto toDto(EligibilityCriteria entity);
    EligibilityCriteria toEntity(EligibilityCriteriaRequestDto dto);
}