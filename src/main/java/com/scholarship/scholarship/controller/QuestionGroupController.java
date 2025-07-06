// src/main/java/com/scholarship/scholarship/controller/QuestionGroupController.java
package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.MessageResponse;
import com.scholarship.scholarship.service.QuestionGroupService;
import com.scholarship.scholarship.valueObject.QuestionGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/programs/{programId}/question-groups")
public class QuestionGroupController {

    @Autowired
    private QuestionGroupService questionGroupService;

    @PostMapping
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<QuestionGroup> createQuestionGroup(
            @PathVariable String programId,
            @Valid @RequestBody QuestionGroup questionGroup) {
        QuestionGroup createdGroup = questionGroupService.createQuestionGroup(programId, questionGroup);
        return ResponseEntity.ok(createdGroup);
    }

    @GetMapping
    public ResponseEntity<List<QuestionGroup>> getQuestionGroups(@PathVariable String programId) {
        List<QuestionGroup> groups = questionGroupService.getQuestionGroups(programId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<QuestionGroup> getQuestionGroup(
            @PathVariable String programId,
            @PathVariable String groupId) {
        QuestionGroup group = questionGroupService.getQuestionGroup(programId, groupId);
        return ResponseEntity.ok(group);
    }
}