// PredefinedQuestionDefinitionService.java
package com.scholarship.scholarship.service;

import com.scholarship.scholarship.model.PredefinedQuestionDefinition;
import com.scholarship.scholarship.repository.PredefinedQuestionDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredefinedQuestionDefinitionService {

    @Autowired
    private PredefinedQuestionDefinitionRepository repository;

    public PredefinedQuestionDefinition createQuestionDefinition(PredefinedQuestionDefinition questionDefinition) {
        if (repository.existsByKey(questionDefinition.getKey())) {
            throw new IllegalArgumentException("Question definition with key " + questionDefinition.getKey() + " already exists");
        }
        return repository.save(questionDefinition);
    }

    public Optional<PredefinedQuestionDefinition> getQuestionDefinitionById(String id) {
        return repository.findById(id);
    }

    public Optional<PredefinedQuestionDefinition> getQuestionDefinitionByKey(String key) {
        return repository.findByKey(key);
    }

    public List<PredefinedQuestionDefinition> getQuestionDefinitionsByOptionSetKey(String optionSetKey) {
        return repository.findByPredefinedOptionSetKey(optionSetKey);
    }

    public List<PredefinedQuestionDefinition> getAllQuestionDefinitions() {
        return repository.findAll();
    }

    public PredefinedQuestionDefinition updateQuestionDefinition(String id, PredefinedQuestionDefinition questionDefinition) {
        Optional<PredefinedQuestionDefinition> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new IllegalArgumentException("Question definition with id " + id + " not found");
        }

        PredefinedQuestionDefinition entity = optionalEntity.get();

        // If key is being changed, check for duplicates
        if (!entity.getKey().equals(questionDefinition.getKey()) && repository.existsByKey(questionDefinition.getKey())) {
            throw new IllegalArgumentException("Question definition with key " + questionDefinition.getKey() + " already exists");
        }

        questionDefinition.setId(id);
        questionDefinition.setCreatedAt(entity.getCreatedAt());
        return repository.save(questionDefinition);
    }

    public void deleteQuestionDefinition(String id) {
        repository.deleteById(id);
    }
}