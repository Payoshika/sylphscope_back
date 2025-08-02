package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.EvaluationOfAnswerDto;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.service.EvaluationOfAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation-of-answers")
@RequiredArgsConstructor
public class EvaluationOfAnswerController {

    private final EvaluationOfAnswerService evaluationOfAnswerService;

    @GetMapping
    public ResponseEntity<List<EvaluationOfAnswerDto>> getAllEvaluations() {
        List<EvaluationOfAnswerDto> evaluations = evaluationOfAnswerService.getAllEvaluations();
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationOfAnswerDto> getEvaluationById(@PathVariable String id) {
        try {
            EvaluationOfAnswerDto evaluation = evaluationOfAnswerService.getEvaluationById(id);
            return ResponseEntity.ok(evaluation);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<EvaluationOfAnswerDto>> getEvaluationsByApplicationId(@PathVariable String applicationId) {
        List<EvaluationOfAnswerDto> evaluations = evaluationOfAnswerService.getEvaluationsByApplicationId(applicationId);
        System.out.println("Evaluations for application " + applicationId + ": " + evaluations);
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/student-answer/{studentAnswerId}")
    public ResponseEntity<List<EvaluationOfAnswerDto>> getEvaluationsByStudentAnswerId(@PathVariable String studentAnswerId) {
        List<EvaluationOfAnswerDto> evaluations = evaluationOfAnswerService.getEvaluationsByStudentAnswerId(studentAnswerId);
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/evaluator/{evaluatorId}")
    public ResponseEntity<List<EvaluationOfAnswerDto>> getEvaluationsByEvaluatorId(@PathVariable String evaluatorId) {
        List<EvaluationOfAnswerDto> evaluations = evaluationOfAnswerService.getEvaluationsByEvaluatorId(evaluatorId);
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/application/{applicationId}/evaluator/{evaluatorId}")
    public ResponseEntity<List<EvaluationOfAnswerDto>> getEvaluationsByApplicationIdAndEvaluatorId(
            @PathVariable String applicationId,
            @PathVariable String evaluatorId) {
        List<EvaluationOfAnswerDto> evaluations = evaluationOfAnswerService.getEvaluationsByApplicationIdAndEvaluatorId(applicationId, evaluatorId);
        System.out.println("Evaluations for application " + applicationId + " and evaluator " + evaluatorId + ": " + evaluations);
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/grant-program/{grantProgramId}")
    public ResponseEntity<List<EvaluationOfAnswerDto>> getEvaluationsByGrantProgramId(@PathVariable String grantProgramId) {
        List<EvaluationOfAnswerDto> evaluations = evaluationOfAnswerService.getEvaluationsByGrantProgramId(grantProgramId);
        System.out.println("Evaluations for grant program " + grantProgramId + ": " + evaluations.size() + " evaluations found");
        return ResponseEntity.ok(evaluations);
    }

    @PostMapping
    public ResponseEntity<EvaluationOfAnswerDto> createEvaluation(@Valid @RequestBody EvaluationOfAnswerDto evaluationDto) {
        EvaluationOfAnswerDto createdEvaluation = evaluationOfAnswerService.createEvaluation(evaluationDto);
        return new ResponseEntity<>(createdEvaluation, HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<EvaluationOfAnswerDto>> createEvaluations(@Valid @RequestBody List<EvaluationOfAnswerDto> evaluationDtos) {
        List<EvaluationOfAnswerDto> createdEvaluations = evaluationOfAnswerService.createEvaluations(evaluationDtos);
        System.out.println("Created " + createdEvaluations.size() + " evaluations");
        return new ResponseEntity<>(createdEvaluations, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationOfAnswerDto> updateEvaluation(
            @PathVariable String id,
            @Valid @RequestBody EvaluationOfAnswerDto evaluationDto) {
        try {
            EvaluationOfAnswerDto updatedEvaluation = evaluationOfAnswerService.updateEvaluation(id, evaluationDto);
            return ResponseEntity.ok(updatedEvaluation);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/batch")
    public ResponseEntity<List<EvaluationOfAnswerDto>> updateEvaluations(@Valid @RequestBody List<EvaluationOfAnswerDto> evaluationDtos) {
        List<EvaluationOfAnswerDto> updatedEvaluations = evaluationOfAnswerService.updateEvaluations(evaluationDtos);
        System.out.println("Updated " + updatedEvaluations.size() + " evaluations");
        return ResponseEntity.ok(updatedEvaluations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable String id) {
        boolean deleted = evaluationOfAnswerService.deleteEvaluation(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/application/{applicationId}")
    public ResponseEntity<Void> deleteEvaluationsByApplicationId(@PathVariable String applicationId) {
        evaluationOfAnswerService.deleteEvaluationsByApplicationId(applicationId);
        System.out.println("Deleted all evaluations for application: " + applicationId);
        return ResponseEntity.noContent().build();
    }
}
