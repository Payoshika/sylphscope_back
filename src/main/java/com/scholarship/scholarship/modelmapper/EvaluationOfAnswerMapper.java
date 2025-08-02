package com.scholarship.scholarship.modelmapper;

import com.scholarship.scholarship.dto.EvaluationOfAnswerDto;
import com.scholarship.scholarship.model.EvaluationOfAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EvaluationOfAnswerMapper {
    EvaluationOfAnswerDto toDto(EvaluationOfAnswer evaluationOfAnswer);
    EvaluationOfAnswer toEntity(EvaluationOfAnswerDto evaluationOfAnswerDto);
    void updateEntityFromDto(EvaluationOfAnswerDto dto, @MappingTarget EvaluationOfAnswer entity);
}
