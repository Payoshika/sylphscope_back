package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.OptionDto;
import com.scholarship.scholarship.dto.QuestionOptionSetDto;
import com.scholarship.scholarship.model.QuestionOptionSet;
import com.scholarship.scholarship.model.Option;
import com.scholarship.scholarship.repository.QuestionOptionSetRepository;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionOptionSetService {

    private final QuestionOptionSetRepository questionOptionSetRepository;
    private final OptionService optionService;

    public QuestionOptionSetDto createQuestionOptionSet(QuestionOptionSetDto dto) {
        log.info("Creating new question option set: {}", dto.getOptionSetLabel());
        // Create options first
        List<Option> options = dto.getOptions().stream()
                .map(optionDto -> Option.builder()
                        .label(optionDto.getLabel())
                        .value(optionDto.getValue())
                        .build())
                .toList();

        QuestionOptionSet questionOptionSet = QuestionOptionSet.builder()
                .optionSetLabel(dto.getOptionSetLabel())
                .description(dto.getDescription())
                .optionDataType(dto.getOptionDataType())
                .options(options)
                .createdAt(Instant.now())
                .build();

        QuestionOptionSet saved = questionOptionSetRepository.save(questionOptionSet);
        log.info("Question option set created successfully with id: {}", saved.getId());

        return mapToDto(saved);
    }

    public QuestionOptionSetDto getById(String id) {
        QuestionOptionSet optionSet = questionOptionSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question option set not found with id: " + id));
        return mapToDto(optionSet);
    }

    public QuestionOptionSetDto getByLabel(String label) {
        QuestionOptionSet optionSet = questionOptionSetRepository.findByOptionSetLabel(label)
                .orElseThrow(() -> new ResourceNotFoundException("Question option set not found with label: " + label));
        return mapToDto(optionSet);
    }

    public List<QuestionOptionSetDto> getAllOptionSets() {
        return questionOptionSetRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private QuestionOptionSetDto mapToDto(QuestionOptionSet optionSet) {
        return QuestionOptionSetDto.builder()
                .id(optionSet.getId())
                .optionSetLabel(optionSet.getOptionSetLabel())
                .description(optionSet.getDescription())
                .optionDataType(optionSet.getOptionDataType())
                .options(optionSet.getOptions().stream()
                        .map(option -> OptionDto.builder()
                                .id(option.getId())
                                .label(option.getLabel())
                                .value(option.getValue())
                                .build())
                        .toList())
                .createdAt(optionSet.getCreatedAt())
                .build();
    }
}