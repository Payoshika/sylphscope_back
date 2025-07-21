package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.QuestionGroupDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionGroupEligibilityInfoDto;
import com.scholarship.scholarship.service.QuestionGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question-groups")
@RequiredArgsConstructor
public class QuestionGroupController {

    private final QuestionGroupService questionGroupService;

    @PostMapping
    public ResponseEntity<QuestionGroupDto> createQuestionGroup(@Valid @RequestBody QuestionGroupDto questionGroupDto) {
        QuestionGroupDto createdQuestionGroup = questionGroupService.createQuestionGroup(questionGroupDto);
        return new ResponseEntity<>(createdQuestionGroup, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionGroupDto> getQuestionGroupById(@PathVariable String id) {
        QuestionGroupDto questionGroup = questionGroupService.getQuestionGroupById(id);
        return ResponseEntity.ok(questionGroup);
    }

    @GetMapping("/groups-for-eligibility")
    public ResponseEntity<List<QuestionGroupEligibilityInfoDto>> getQuestionGroupsForEligibility() {
        List<QuestionGroupEligibilityInfoDto> result = questionGroupService.getQuestionGroupsForEligibility();
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<QuestionGroupDto>> getAllQuestionGroups() {
        List<QuestionGroupDto> questionGroups = questionGroupService.getAllQuestionGroups();
        return ResponseEntity.ok(questionGroups);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionGroupDto> updateQuestionGroup(
            @PathVariable String id,
            @Valid @RequestBody QuestionGroupDto questionGroupDto) {
        QuestionGroupDto updatedQuestionGroup = questionGroupService.updateQuestionGroup(id, questionGroupDto);
        return ResponseEntity.ok(updatedQuestionGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionGroup(@PathVariable String id) {
        questionGroupService.deleteQuestionGroup(id);
        return ResponseEntity.noContent().build();
    }
}