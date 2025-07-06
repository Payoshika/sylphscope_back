// src/main/java/com/scholarship/scholarship/service/QuestionGroupService.java
package com.scholarship.scholarship.service;

import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.model.GrantProgram;
import com.scholarship.scholarship.repository.GrantProgramRepository;
import com.scholarship.scholarship.valueObject.Question;
import com.scholarship.scholarship.valueObject.QuestionGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionGroupService {

    @Autowired
    private GrantProgramRepository grantProgramRepository;
    @Autowired
    private PredefinedQuestionDefinitionService predefinedQuestionDefinitionService;

    public QuestionGroup createQuestionGroup(String programId, QuestionGroup questionGroup) {
        GrantProgram program = grantProgramRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Grant program not found"));

        // Generate ID if not provided
        if (questionGroup.getGroupId() == null || questionGroup.getGroupId().isEmpty()) {
            questionGroup.setGroupId(UUID.randomUUID().toString());
        }

        if (program.getQuestionGroups() == null) {
            program.setQuestionGroups(new ArrayList<>());
        }

        program.getQuestionGroups().add(questionGroup);
        grantProgramRepository.save(program);

        return questionGroup;
    }

    public List<QuestionGroup> getQuestionGroups(String programId) {
        GrantProgram program = grantProgramRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Grant program not found"));

        return program.getQuestionGroups() != null ? program.getQuestionGroups() : new ArrayList<>();
    }

    public QuestionGroup getQuestionGroup(String programId, String groupId) {
        GrantProgram program = grantProgramRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Grant program not found"));

        return program.getQuestionGroups().stream()
                .filter(group -> group.getGroupId().equals(groupId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Question group not found"));
    }

    // In QuestionGroupService or GrantProgramService
    public QuestionGroup loadQuestionsForGroup(QuestionGroup group) {
        if (group.getPredefinedGroupKey() != null) {
            // Load all questions for this predefined group at once
            List<Question> questions = predefinedQuestionDefinitionService
                    .getQuestionsByGroupKey(group.getPredefinedGroupKey());
            group.setQuestions(questions);
        }
        return group;
    }

    public List<QuestionGroup> loadQuestionsForAllGroups(List<QuestionGroup> groups) {
        return groups.stream()
                .map(this::loadQuestionsForGroup)
                .collect(Collectors.toList());
    }
}