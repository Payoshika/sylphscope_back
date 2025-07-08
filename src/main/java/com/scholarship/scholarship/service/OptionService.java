package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.OptionDto;
import com.scholarship.scholarship.model.Option;
import com.scholarship.scholarship.repository.OptionRepository;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionDto createOption(OptionDto optionDto) {
        log.info("Creating new option: {}", optionDto.getLabel());

        Option option = Option.builder()
                .label(optionDto.getLabel())
                .value(optionDto.getValue())
                .build();

        Option savedOption = optionRepository.save(option);
        log.info("Option created successfully with id: {}", savedOption.getId());

        return mapToDto(savedOption);
    }

    public List<OptionDto> createOptions(List<OptionDto> optionDtos) {
        log.info("Creating {} options", optionDtos.size());

        List<Option> options = optionDtos.stream()
                .map(dto -> Option.builder()
                        .label(dto.getLabel())
                        .value(dto.getValue())
                        .build())
                .toList();

        List<Option> savedOptions = optionRepository.saveAll(options);
        log.info("Created {} options successfully", savedOptions.size());

        return savedOptions.stream()
                .map(this::mapToDto)
                .toList();
    }

    public OptionDto getOptionById(String id) {
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found with id: " + id));
        return mapToDto(option);
    }

    public List<OptionDto> getAllOptions() {
        return optionRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public void deleteOption(String id) {
        if (!optionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Option not found with id: " + id);
        }
        optionRepository.deleteById(id);
        log.info("Option deleted successfully with id: {}", id);
    }

    private OptionDto mapToDto(Option option) {
        return OptionDto.builder()
                .id(option.getId())
                .label(option.getLabel())
                .value(option.getValue())
                .build();
    }
}