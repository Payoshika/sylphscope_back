package com.scholarship.scholarship.modelmapper;

import com.scholarship.scholarship.dto.StudentAnswerDto;
import com.scholarship.scholarship.model.StudentAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentAnswerMapper {
    StudentAnswerDto toDto(StudentAnswer entity);
    StudentAnswer toEntity(StudentAnswerDto dto);
    void updateEntityFromDto(StudentAnswerDto dto, @MappingTarget StudentAnswer entity);
}