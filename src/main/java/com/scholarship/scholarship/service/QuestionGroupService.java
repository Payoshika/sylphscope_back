package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.QuestionGroupDto;
import com.scholarship.scholarship.model.QuestionGroup;
import com.scholarship.scholarship.repository.QuestionGroupRepository;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionGroupService {

    private final QuestionGroupRepository questionGroupRepository;

    public QuestionGroupDto createQuestionGroup(QuestionGroupDto questionGroupDto) {
        log.info("Creating new question group: {}", questionGroupDto.getName());

        QuestionGroup questionGroup = QuestionGroup.builder()
                .name(questionGroupDto.getName())
                .description(questionGroupDto.getDescription())
                .questionIds(questionGroupDto.getQuestionIds())
                .displayOrder(questionGroupDto.getDisplayOrder())
                .build();

        QuestionGroup savedQuestionGroup = questionGroupRepository.save(questionGroup);
        log.info("Question group created successfully with id: {}", savedQuestionGroup.getId());

        return mapToDto(savedQuestionGroup);
    }

    public QuestionGroupDto getQuestionGroupById(String id) {
        QuestionGroup questionGroup = questionGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question group not found with id: " + id));
        return mapToDto(questionGroup);
    }

    public List<QuestionGroupDto> getAllQuestionGroups() {
        return questionGroupRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public QuestionGroupDto updateQuestionGroup(String id, QuestionGroupDto questionGroupDto) {
        QuestionGroup existingQuestionGroup = questionGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question group not found with id: " + id));

        existingQuestionGroup.setName(questionGroupDto.getName());
        existingQuestionGroup.setDescription(questionGroupDto.getDescription());
        existingQuestionGroup.setQuestions(questionGroupDto.getQuestionIds());
        existingQuestionGroup.setDisplayOrder(questionGroupDto.getDisplayOrder());

        QuestionGroup updatedQuestionGroup = questionGroupRepository.save(existingQuestionGroup);
        return mapToDto(updatedQuestionGroup);
    }

    public void deleteQuestionGroup(String id) {
        if (!questionGroupRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question group not found with id: " + id);
        }
        questionGroupRepository.deleteById(id);
        log.info("Question group deleted successfully with id: {}", id);
    }

    private QuestionGroupDto mapToDto(QuestionGroup questionGroup) {
        return QuestionGroupDto.builder()
                .id(questionGroup.getId())
                .name(questionGroup.getName())
                .description(questionGroup.getDescription())
                .questionIds(questionGroup.getQuestionIds())
                .displayOrder(questionGroup.getDisplayOrder())
                .build();
    }
}