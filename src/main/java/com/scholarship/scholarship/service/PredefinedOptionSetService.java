package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.PredefinedOptionSetDto;
import com.scholarship.scholarship.model.PredefinedOptionSet;
import com.scholarship.scholarship.repository.PredefinedOptionSetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PredefinedOptionSetService {

    @Autowired
    private PredefinedOptionSetRepository repository;

    public PredefinedOptionSetDto createOptionSet(PredefinedOptionSetDto dto) {
        if (repository.existsByKey(dto.getKey())) {
            throw new IllegalArgumentException("Option set with key " + dto.getKey() + " already exists");
        }

        PredefinedOptionSet entity = new PredefinedOptionSet();
        BeanUtils.copyProperties(dto, entity);
        PredefinedOptionSet saved = repository.save(entity);

        PredefinedOptionSetDto result = new PredefinedOptionSetDto();
        BeanUtils.copyProperties(saved, result);
        return result;
    }

    public Optional<PredefinedOptionSetDto> getOptionSetById(String id) {
        return repository.findById(id)
                .map(entity -> {
                    PredefinedOptionSetDto dto = new PredefinedOptionSetDto();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                });
    }

    public Optional<PredefinedOptionSetDto> getOptionSetByKey(String key) {
        return repository.findByKey(key)
                .map(entity -> {
                    PredefinedOptionSetDto dto = new PredefinedOptionSetDto();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                });
    }

    public List<PredefinedOptionSetDto> getAllOptionSets() {
        return repository.findAll().stream()
                .map(entity -> {
                    PredefinedOptionSetDto dto = new PredefinedOptionSetDto();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public PredefinedOptionSetDto updateOptionSet(String id, PredefinedOptionSetDto dto) {
        Optional<PredefinedOptionSet> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new IllegalArgumentException("Option set with id " + id + " not found");
        }

        PredefinedOptionSet entity = optionalEntity.get();

        // If key is being changed, check for duplicates
        if (!entity.getKey().equals(dto.getKey()) && repository.existsByKey(dto.getKey())) {
            throw new IllegalArgumentException("Option set with key " + dto.getKey() + " already exists");
        }

        BeanUtils.copyProperties(dto, entity);
        PredefinedOptionSet updated = repository.save(entity);

        PredefinedOptionSetDto result = new PredefinedOptionSetDto();
        BeanUtils.copyProperties(updated, result);
        return result;
    }

    public void deleteOptionSet(String id) {
        repository.deleteById(id);
    }
}