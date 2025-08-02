package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.EvaluationOfAnswerDto;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.model.EvaluationOfAnswer;
import com.scholarship.scholarship.modelmapper.EvaluationOfAnswerMapper;
import com.scholarship.scholarship.repository.EvaluationOfAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationOfAnswerService {

    private final EvaluationOfAnswerRepository evaluationOfAnswerRepository;
    private final EvaluationOfAnswerMapper evaluationOfAnswerMapper;
    private final ApplicationService applicationService; // Add ApplicationService for fallback

    public List<EvaluationOfAnswerDto> getAllEvaluations() {
        return evaluationOfAnswerRepository.findAll().stream()
                .map(evaluationOfAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    public EvaluationOfAnswerDto getEvaluationById(String id) {
        return evaluationOfAnswerRepository.findById(id)
                .map(evaluationOfAnswerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with id: " + id));
    }

    public List<EvaluationOfAnswerDto> getEvaluationsByApplicationId(String applicationId) {
        return evaluationOfAnswerRepository.findByApplicationId(applicationId).stream()
                .map(evaluationOfAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EvaluationOfAnswerDto> getEvaluationsByStudentAnswerId(String studentAnswerId) {
        return evaluationOfAnswerRepository.findByStudentAnswerId(studentAnswerId).stream()
                .map(evaluationOfAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EvaluationOfAnswerDto> getEvaluationsByEvaluatorId(String evaluatorId) {
        return evaluationOfAnswerRepository.findByEvaluatorId(evaluatorId).stream()
                .map(evaluationOfAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EvaluationOfAnswerDto> getEvaluationsByApplicationIdAndEvaluatorId(String applicationId, String evaluatorId) {
        return evaluationOfAnswerRepository.findByApplicationIdAndEvaluatorId(applicationId, evaluatorId).stream()
                .map(evaluationOfAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EvaluationOfAnswerDto> getEvaluationsByGrantProgramId(String grantProgramId) {
        return evaluationOfAnswerRepository.findByGrantProgramId(grantProgramId).stream()
                .map(evaluationOfAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    public EvaluationOfAnswerDto createEvaluation(EvaluationOfAnswerDto evaluationDto) {
        EvaluationOfAnswer evaluation = evaluationOfAnswerMapper.toEntity(evaluationDto);
        evaluation.setCreatedAt(Instant.now());
        evaluation.setUpdatedAt(Instant.now());
        EvaluationOfAnswer savedEvaluation = evaluationOfAnswerRepository.save(evaluation);
        return evaluationOfAnswerMapper.toDto(savedEvaluation);
    }

    public List<EvaluationOfAnswerDto> createEvaluations(List<EvaluationOfAnswerDto> evaluationDtos) {
        List<EvaluationOfAnswer> evaluations = evaluationDtos.stream()
                .map(dto -> {
                    EvaluationOfAnswer evaluation = evaluationOfAnswerMapper.toEntity(dto);
                    evaluation.setCreatedAt(Instant.now());
                    evaluation.setUpdatedAt(Instant.now());
                    return evaluation;
                })
                .collect(Collectors.toList());

        List<EvaluationOfAnswer> savedEvaluations = evaluationOfAnswerRepository.saveAll(evaluations);
        return savedEvaluations.stream()
                .map(evaluationOfAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    public EvaluationOfAnswerDto updateEvaluation(String id, EvaluationOfAnswerDto evaluationDto) {
        EvaluationOfAnswer existingEvaluation = evaluationOfAnswerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with id: " + id));

        evaluationOfAnswerMapper.updateEntityFromDto(evaluationDto, existingEvaluation);
        existingEvaluation.setUpdatedAt(Instant.now());

        EvaluationOfAnswer updatedEvaluation = evaluationOfAnswerRepository.save(existingEvaluation);
        return evaluationOfAnswerMapper.toDto(updatedEvaluation);
    }

    public List<EvaluationOfAnswerDto> updateEvaluations(List<EvaluationOfAnswerDto> evaluationDtos) {
        List<EvaluationOfAnswer> updatedEvaluations = evaluationDtos.stream()
                .map(dto -> {
                    if (dto.getId() != null && !dto.getId().isEmpty()) {
                        // Update existing evaluation
                        Optional<EvaluationOfAnswer> existingOpt = evaluationOfAnswerRepository.findById(dto.getId());
                        if (existingOpt.isPresent()) {
                            EvaluationOfAnswer existing = existingOpt.get();
                            evaluationOfAnswerMapper.updateEntityFromDto(dto, existing);
                            existing.setUpdatedAt(Instant.now());
                            return existing;
                        }
                    }
                    // Create new evaluation if id is null, empty string, or not found
                    EvaluationOfAnswer newEvaluation = evaluationOfAnswerMapper.toEntity(dto);
                    newEvaluation.setId(null); // Ensure ID is null for new entities
                    newEvaluation.setCreatedAt(Instant.now());
                    newEvaluation.setUpdatedAt(Instant.now());
                    return newEvaluation;
                })
                .collect(Collectors.toList());

        List<EvaluationOfAnswer> savedEvaluations = evaluationOfAnswerRepository.saveAll(updatedEvaluations);
        return savedEvaluations.stream()
                .map(evaluationOfAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean deleteEvaluation(String id) {
        if (!evaluationOfAnswerRepository.existsById(id)) {
            return false;
        }
        evaluationOfAnswerRepository.deleteById(id);
        return true;
    }

    public void deleteEvaluationsByApplicationId(String applicationId) {
        List<EvaluationOfAnswer> evaluations = evaluationOfAnswerRepository.findByApplicationId(applicationId);
        evaluationOfAnswerRepository.deleteAll(evaluations);
    }
}
