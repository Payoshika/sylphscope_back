// src/main/java/com/scholarship/scholarship/service/PredefinedQuestionGroupService.java
package com.scholarship.scholarship.service;

import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.model.PredefinedQuestionGroup;
import com.scholarship.scholarship.repository.PredefinedQuestionGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredefinedQuestionGroupService {

    @Autowired
    private PredefinedQuestionGroupRepository predefinedQuestionGroupRepository;

    public List<PredefinedQuestionGroup> getAllPredefinedQuestionGroups() {
        return predefinedQuestionGroupRepository.findAll();
    }

    public PredefinedQuestionGroup getPredefinedQuestionGroupByKey(String key) {
        return predefinedQuestionGroupRepository.findByKey(key)
                .orElseThrow(() -> new ResourceNotFoundException("Predefined question group not found with key: " + key));
    }

    public PredefinedQuestionGroup createPredefinedQuestionGroup(PredefinedQuestionGroup group) {
        return predefinedQuestionGroupRepository.save(group);
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
}