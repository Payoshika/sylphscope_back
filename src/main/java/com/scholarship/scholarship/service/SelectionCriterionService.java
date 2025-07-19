// src/main/java/com/scholarship/scholarship/service/SelectionCriterionService.java
package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.SelectionCriterionDto;
import com.scholarship.scholarship.model.SelectionCriterion;
import com.scholarship.scholarship.model.GrantProgram;
import com.scholarship.scholarship.exception.ResourceNotFoundException;

import com.scholarship.scholarship.repository.SelectionCriterionRepository;
import com.scholarship.scholarship.repository.GrantProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SelectionCriterionService {

    private final SelectionCriterionRepository selectionCriterionRepository;
    private final GrantProgramRepository grantProgramRepository;

    public List<SelectionCriterion> getByGrantProgramId(String grantProgramId) {
        return selectionCriterionRepository.findByGrantProgramId(grantProgramId);
    }

    public SelectionCriterion create(SelectionCriterionDto dto) {
        SelectionCriterion criterion = SelectionCriterion.builder()
                .grantProgramId(dto.getGrantProgramId())
                .criterionName(dto.getCriterionName())
                .questionType(dto.getQuestionType())
                .questionId(dto.getQuestionId())
                .weight(dto.getWeight())
                .evaluationType(dto.getEvaluationType())
                .evaluationScale(dto.getEvaluationScale())
                .build();
        return selectionCriterionRepository.save(criterion);
    }

    public SelectionCriterion update(String id, SelectionCriterionDto dto) {
        SelectionCriterion existing = selectionCriterionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        existing.setQuestionType(dto.getQuestionType());
        existing.setQuestionId(dto.getQuestionId());
        existing.setCriterionName(dto.getCriterionName());
        existing.setWeight(dto.getWeight());
        existing.setEvaluationType(dto.getEvaluationType());
        existing.setEvaluationScale(dto.getEvaluationScale());
        return selectionCriterionRepository.save(existing);
    }

// In SelectionCriterionService.java
public List<SelectionCriterion> batchUpdate(String grantProgramId, List<SelectionCriterionDto> dtos) {
    GrantProgram grantProgram = grantProgramRepository.findById(grantProgramId)
            .orElseThrow(() -> new ResourceNotFoundException("GrantProgram not found"));

    List<SelectionCriterion> currentCriteria = grantProgram.getSelectionCriteria() == null
            ? new ArrayList<>()
            : new ArrayList<>(grantProgram.getSelectionCriteria());

    List<String> incomingIds = dtos.stream()
            .map(SelectionCriterionDto::getId)
            .filter(Objects::nonNull)
            .toList();

    List<SelectionCriterion> toDelete = currentCriteria.stream()
            .filter(sc -> sc.getId() != null && !incomingIds.contains(sc.getId()))
            .collect(Collectors.toList());

    // Delete from database
    toDelete.forEach(sc -> selectionCriterionRepository.deleteById(sc.getId()));

    // Remove from currentCriteria
    currentCriteria.removeIf(sc -> sc.getId() != null && !incomingIds.contains(sc.getId()));
    for (SelectionCriterionDto dto : dtos) {
        if (dto.getId() == null) {
            // New criterion
            SelectionCriterion newCriterion = mapDtoToEntity(dto);
            SelectionCriterion saved = selectionCriterionRepository.save(newCriterion);
            currentCriteria.add(saved);
        } else {
            // Update existing
            SelectionCriterion existing = currentCriteria.stream()
                    .filter(sc -> dto.getId().equals(sc.getId()))
                    .findFirst()
                    .orElse(null);
            if (existing != null) {
                existing.setCriterionName(dto.getCriterionName());
                existing.setQuestionType(dto.getQuestionType());
                existing.setQuestionId(dto.getQuestionId());
                existing.setWeight(dto.getWeight());
                existing.setEvaluationType(dto.getEvaluationType());
                existing.setEvaluationScale(dto.getEvaluationScale());
                selectionCriterionRepository.save(existing);
            } else {
                // If not found in current, create new
                SelectionCriterion newCriterion = mapDtoToEntity(dto);
                SelectionCriterion saved = selectionCriterionRepository.save(newCriterion);
                currentCriteria.add(saved);
            }
        }
    }

    grantProgram.setSelectionCriteria(currentCriteria);
    // Set evaluationScale if any criterion has it
    dtos.stream()
            .map(SelectionCriterionDto::getEvaluationScale)
            .filter(Objects::nonNull)
            .findFirst()
            .ifPresent(grantProgram::setEvaluationScale);
    grantProgramRepository.save(grantProgram);
    return currentCriteria;
}

    public void delete(String id) {
        selectionCriterionRepository.deleteById(id);
    }

    private SelectionCriterion mapDtoToEntity(SelectionCriterionDto dto) {
        return SelectionCriterion.builder()
                .id(dto.getId())
                .grantProgramId(dto.getGrantProgramId())
                .criterionName(dto.getCriterionName())
                .questionType(dto.getQuestionType())
                .questionId(dto.getQuestionId())
                .weight(dto.getWeight())
                .evaluationType(dto.getEvaluationType())
                .evaluationScale(dto.getEvaluationScale())
                .build();
    }
}