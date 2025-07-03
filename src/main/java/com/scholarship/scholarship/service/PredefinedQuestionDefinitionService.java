package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.PredefinedQuestionDefinitionDto;
import com.scholarship.scholarship.model.PredefinedQuestionDefinition;
import com.scholarship.scholarship.repository.PredefinedQuestionDefinitionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PredefinedQuestionDefinitionService {

    @Autowired
    private PredefinedQuestionDefinitionRepository repository;

    public PredefinedQuestionDefinitionDto createQuestionDefinition(PredefinedQuestionDefinitionDto dto) {
        if (repository.existsByKey(dto.getKey())) {
            throw new IllegalArgumentException("Question definition with key " + dto.getKey() + " already exists");
        }

        PredefinedQuestionDefinition entity = new PredefinedQuestionDefinition();
        BeanUtils.copyProperties(dto, entity);
        PredefinedQuestionDefinition saved = repository.save(entity);

        PredefinedQuestionDefinitionDto result = new PredefinedQuestionDefinitionDto();
        BeanUtils.copyProperties(saved, result);
        return result;
    }

    public Optional<PredefinedQuestionDefinitionDto> getQuestionDefinitionById(String id) {
        return repository.findById(id)
                .map(entity -> {
                    PredefinedQuestionDefinitionDto dto = new PredefinedQuestionDefinitionDto();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                });
    }

    public Optional<PredefinedQuestionDefinitionDto> getQuestionDefinitionByKey(String key) {
        return repository.findByKey(key)
                .map(entity -> {
                    PredefinedQuestionDefinitionDto dto = new PredefinedQuestionDefinitionDto();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                });
    }

    public List<PredefinedQuestionDefinitionDto> getQuestionDefinitionsByOptionSetKey(String optionSetKey) {
        return repository.findByPredefinedOptionSetKey(optionSetKey).stream()
                .map(entity -> {
                    PredefinedQuestionDefinitionDto dto = new PredefinedQuestionDefinitionDto();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<PredefinedQuestionDefinitionDto> getAllQuestionDefinitions() {
        return repository.findAll().stream()
                .map(entity -> {
                    PredefinedQuestionDefinitionDto dto = new PredefinedQuestionDefinitionDto();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public PredefinedQuestionDefinitionDto updateQuestionDefinition(String id, PredefinedQuestionDefinitionDto dto) {
        Optional<PredefinedQuestionDefinition> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new IllegalArgumentException("Question definition with id " + id + " not found");
        }

        PredefinedQuestionDefinition entity = optionalEntity.get();

        // If key is being changed, check for duplicates
        if (!entity.getKey().equals(dto.getKey()) && repository.existsByKey(dto.getKey())) {
            throw new IllegalArgumentException("Question definition with key " + dto.getKey() + " already exists");
        }

        BeanUtils.copyProperties(dto, entity);
        PredefinedQuestionDefinition updated = repository.save(entity);

        PredefinedQuestionDefinitionDto result = new PredefinedQuestionDefinitionDto();
        BeanUtils.copyProperties(updated, result);
        return result;
    }

    public void deleteQuestionDefinition(String id) {
        repository.deleteById(id);
    }
}