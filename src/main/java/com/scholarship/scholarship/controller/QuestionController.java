package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.QuestionDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionWithOptionsDto;
import com.scholarship.scholarship.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionEligibilityInfoDto;
import com.scholarship.scholarship.model.Question;
import java.util.Objects;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@Valid @RequestBody QuestionWithOptionsDto payload) {
        System.out.println("creating question + " + payload.getQuestion());
        System.out.println("creating options + " + payload.getOptions());
        QuestionDto createdQuestion = questionService.createQuestionWithOptions(payload.getQuestion(), payload.getOptions());
        System.out.println("created question " + createdQuestion);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable String id) {
        QuestionDto question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        List<QuestionDto> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(
            @PathVariable String id,
            @Valid @RequestBody QuestionDto questionDto) {
        QuestionDto updatedQuestion = questionService.updateQuestion(id, questionDto);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/questions-for-eligibility")
    public ResponseEntity<List<QuestionEligibilityInfoDto>> getQuestionsForEligibility() {
        List<Question> questions = questionService.getAllQuestionEntities().stream()
                .filter(Objects::nonNull)
                .toList();
        List<QuestionEligibilityInfoDto> result = questions.stream()
                .map(questionService::questionForEligibility)
                .toList();
        return ResponseEntity.ok(result);
    }
}