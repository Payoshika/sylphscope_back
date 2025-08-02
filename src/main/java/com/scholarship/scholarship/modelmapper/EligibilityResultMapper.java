package com.scholarship.scholarship.modelmapper;

import com.scholarship.scholarship.dto.EligibilityResultDto;
import com.scholarship.scholarship.model.EligibilityResult;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EligibilityResultMapper {
    // Now that both model and DTO use 'eligible', MapStruct can handle the mapping automatically
    EligibilityResultDto toDto(EligibilityResult eligibilityResult);

    EligibilityResult toEntity(EligibilityResultDto eligibilityResultDto);
}
