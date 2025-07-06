// PredefinedQuestionDefinitionService.java
package com.scholarship.scholarship.service;

import com.scholarship.scholarship.model.PredefinedQuestionDefinition;
import com.scholarship.scholarship.model.PredefinedQuestionGroup;
import com.scholarship.scholarship.repository.PredefinedQuestionDefinitionRepository;
import com.scholarship.scholarship.repository.PredefinedQuestionGroupRepository;
import com.scholarship.scholarship.valueObject.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredefinedQuestionDefinitionService {

    @Autowired
    private PredefinedQuestionDefinitionRepository repository;

    @Autowired
    public PredefinedQuestionGroupRepository predefinedQuestionGroupRepository;

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

    public List<Question> getQuestionsByGroupId(String groupId) {
        List<PredefinedQuestionDefinition> definitions = repository.findByGroupId(groupId);

        return definitions.stream()
                .map(this::convertToQuestion)
                .collect(Collectors.toList());
    }

    public List<PredefinedQuestionDefinition> getQuestionDefinitionsByGroupKey(String groupKey) {
        PredefinedQuestionGroup group = predefinedQuestionGroupRepository.findByKey(groupKey)
                .orElseThrow(() -> new ResourceNotFoundException("Predefined question group not found"));

        return predefinedQuestionDefinitionRepository.findByGroupId(group.getId());
    }

    public List<Question> getQuestionsByGroupKey(String groupKey) {
        // Single query to get the group
        PredefinedQuestionGroup group = predefinedQuestionGroupRepository.findByKey(groupKey)
                .orElseThrow(() -> new ResourceNotFoundException("Predefined question group not found"));

        // Single query to get all questions in the group, ordered by display order
        List<PredefinedQuestionDefinition> definitions =
                predefinedQuestionDefinitionRepository.findByGroupIdOrderByDisplayOrder(group.getId());

        return definitions.stream()
                .map(this::convertToQuestion)
                .collect(Collectors.toList());
    }

    public void deleteQuestionDefinition(String id) {
        repository.deleteById(id);
    }
}