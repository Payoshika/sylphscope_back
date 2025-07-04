// PredefinedOptionSetService.java
package com.scholarship.scholarship.service;

import com.scholarship.scholarship.model.PredefinedOptionSet;
import com.scholarship.scholarship.repository.PredefinedOptionSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredefinedOptionSetService {

    @Autowired
    private PredefinedOptionSetRepository repository;

    public PredefinedOptionSet createOptionSet(PredefinedOptionSet optionSet) {
        if (repository.existsByKey(optionSet.getKey())) {
            throw new IllegalArgumentException("Option set with key " + optionSet.getKey() + " already exists");
        }
        return repository.save(optionSet);
    }

    public Optional<PredefinedOptionSet> getOptionSetById(String id) {
        return repository.findById(id);
    }

    public Optional<PredefinedOptionSet> getOptionSetByKey(String key) {
        return repository.findByKey(key);
    }

    public List<PredefinedOptionSet> getAllOptionSets() {
        return repository.findAll();
    }

    public PredefinedOptionSet updateOptionSet(String id, PredefinedOptionSet optionSet) {
        Optional<PredefinedOptionSet> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new IllegalArgumentException("Option set with id " + id + " not found");
        }

        PredefinedOptionSet entity = optionalEntity.get();

        // If key is being changed, check for duplicates
        if (!entity.getKey().equals(optionSet.getKey()) && repository.existsByKey(optionSet.getKey())) {
            throw new IllegalArgumentException("Option set with key " + optionSet.getKey() + " already exists");
        }

        optionSet.setId(id);
        optionSet.setCreatedAt(entity.getCreatedAt());
        return repository.save(optionSet);
    }

    public void deleteOptionSet(String id) {
        repository.deleteById(id);
    }
}