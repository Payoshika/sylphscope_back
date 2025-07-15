package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.EligibilityCriteriaRequestDto;
import com.scholarship.scholarship.enums.EligibilityCriteriaType;
import com.scholarship.scholarship.model.EligibilityCriteria;
import com.scholarship.scholarship.modelmapper.EligibilityCriteriaMapper;
import com.scholarship.scholarship.repository.EligibilityCriteriaRepository;
import com.scholarship.scholarship.repository.GrantProgramRepository;
import com.scholarship.scholarship.valueObject.CombinationLogic;
import com.scholarship.scholarship.valueObject.QuestionCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EligibilityCriteriaService {

    @Autowired
    private GrantProgramRepository grantProgramRepository;
    private final EligibilityCriteriaRepository eligibilityCriteriaRepository;
    private final EligibilityCriteriaMapper eligibilityCriteriaMapper;


    @Autowired
    public EligibilityCriteriaService(
            EligibilityCriteriaRepository eligibilityCriteriaRepository,
            EligibilityCriteriaMapper eligibilityCriteriaMapper
    ) {
        this.eligibilityCriteriaRepository = eligibilityCriteriaRepository;
        this.eligibilityCriteriaMapper = eligibilityCriteriaMapper;
    }

    public List<EligibilityCriteriaRequestDto> updateEligibilityCriteria(List<EligibilityCriteriaRequestDto> dtos, String grantProgramId) {
//        if (dtos.isEmpty()) return List.of();
        List<EligibilityCriteria> existingCriteria = eligibilityCriteriaRepository.findAll()
                .stream()
                .filter(c -> grantProgramId.equals(c.getGrantProgramId()))
                .toList();

        List<String> incomingIds = dtos.stream()
                .map(EligibilityCriteriaRequestDto::getId)
                .filter(id -> id != null)
                .toList();

        existingCriteria.stream()
                .filter(c -> !incomingIds.contains(c.getId()))
                .forEach(c -> eligibilityCriteriaRepository.deleteById(c.getId()));

        return dtos.stream()
                .map(dto -> {
                    EligibilityCriteria existing = dto.getId() != null
                            ? eligibilityCriteriaRepository.findById(dto.getId()).orElse(null)
                            : null;
                    EligibilityCriteria updated = eligibilityCriteriaMapper.toEntity(dto);
                    if (existing != null) {
                        updated.setId(existing.getId());
                    } else {
                        updated.setId(null);
                    }
                    updated.setGrantProgramId(grantProgramId);
                    EligibilityCriteria saved = eligibilityCriteriaRepository.save(updated);
                    return eligibilityCriteriaMapper.toDto(saved);
                })
                .toList();
    }


    public List<EligibilityCriteriaRequestDto> getCriteriaByGrantProgramId(String grantProgramId) {
        return eligibilityCriteriaRepository.findAll()
                .stream()
                .filter(c -> grantProgramId.equals(c.getGrantProgramId()))
                .map(eligibilityCriteriaMapper::toDto)
                .toList();
    }

    public EligibilityCriteria createSimpleCriteria(
            String grantProgramId,
            String name,
            String description,
            boolean required,
            String questionId,
            QuestionCondition simpleCondition,
            EligibilityCriteriaType criteriaType
    ) {
        if (grantProgramId == null || questionId == null || simpleCondition == null || criteriaType == null) {
            throw new IllegalArgumentException("Required fields are missing for simple criteria");
        }
        EligibilityCriteria criteria = EligibilityCriteria.builder()
                .grantProgramId(grantProgramId)
                .name(name)
                .description(description)
                .required(required)
                .criteriaType(criteriaType)
                .questionId(questionId)
                .simpleCondition(simpleCondition)
                .build();
        return eligibilityCriteriaRepository.save(criteria);
    }

    public List<EligibilityCriteria> createMultipleSimpleCriteria(List<EligibilityCriteriaRequestDto> dtos) {
        return dtos.stream()
                .map(dto -> createSimpleCriteria(
                        dto.getGrantProgramId(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.isRequired(),
                        dto.getQuestionId(),
                        dto.getSimpleCondition(),
                        dto.getCriteriaType()
                ))
                .toList();
    }

    public EligibilityCriteria createComplexCriteria(
            String grantProgramId,
            String name,
            String description,
            boolean required,
            String questionGroupId,
            CombinationLogic combinationLogic,
            List<QuestionCondition> questionConditions,
            EligibilityCriteriaType criteriaType
    ) {
        if (grantProgramId == null || questionGroupId == null || combinationLogic == null || questionConditions == null || criteriaType == null) {
            throw new IllegalArgumentException("Required fields are missing for complex criteria");
        }
        EligibilityCriteria criteria = EligibilityCriteria.builder()
                .grantProgramId(grantProgramId)
                .name(name)
                .description(description)
                .required(required)
                .criteriaType(criteriaType)
                .questionGroupId(questionGroupId)
                .combinationLogic(combinationLogic)
                .questionConditions(questionConditions)
                .build();
        return eligibilityCriteriaRepository.save(criteria);
    }
}