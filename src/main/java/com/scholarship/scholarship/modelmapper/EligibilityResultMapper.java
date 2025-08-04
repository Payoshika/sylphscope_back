package com.scholarship.scholarship.modelmapper;

import com.scholarship.scholarship.dto.EligibilityResultDto;
import com.scholarship.scholarship.model.EligibilityResult;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EligibilityResultMapper {
    // Use explicit mapping for isEligible field to handle the boolean property with "is" prefix correctly
    EligibilityResultDto toDto(EligibilityResult eligibilityResult);

    EligibilityResult toEntity(EligibilityResultDto eligibilityResultDto);

    @Named("mapEligibility")
    default boolean mapEligibility(boolean value) {
        return value;
    }
}
