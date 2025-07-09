package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import com.scholarship.scholarship.service.GrantProgramService;
import com.scholarship.scholarship.dto.QuestionIdRequest;
import com.scholarship.scholarship.dto.QuestionGroupIdRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grant-programs")
@RequiredArgsConstructor
public class GrantProgramController {

    private final GrantProgramService grantProgramService;

    @PostMapping
    public ResponseEntity<GrantProgramDto> createGrantProgram(@Valid @RequestBody GrantProgramDto grantProgramDto) {
        GrantProgramDto created = grantProgramService.createGrantProgram(grantProgramDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrantProgramDto> getGrantProgramById(@PathVariable String id) {
        GrantProgramDto grantProgram = grantProgramService.getGrantProgramById(id);
        return ResponseEntity.ok(grantProgram);
    }

    @GetMapping
    public ResponseEntity<Page<GrantProgramDto>> getAllGrantPrograms(Pageable pageable) {
        Page<GrantProgramDto> grantPrograms = grantProgramService.getAllGrantPrograms(pageable);
        return ResponseEntity.ok(grantPrograms);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<Page<GrantProgramDto>> getGrantProgramsByProviderId(
            @PathVariable String providerId, Pageable pageable) {
        Page<GrantProgramDto> grantPrograms = grantProgramService.getGrantProgramsByProviderId(providerId, pageable);
        return ResponseEntity.ok(grantPrograms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrantProgramDto> updateGrantProgram(
            @PathVariable String id, @Valid @RequestBody GrantProgramDto grantProgramDto) {
        System.out.println(id);
        System.out.println("updating");
        grantProgramDto.setId(id);
        GrantProgramDto updated = grantProgramService.updateGrantProgram(id, grantProgramDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrantProgram(@PathVariable String id) {
        grantProgramService.deleteGrantProgram(id);
        return ResponseEntity.noContent().build();
    }

    //add question group and question endpoints
    @PostMapping("/{grantProgramId}/questions")
    public ResponseEntity<GrantProgramDto> addQuestion(
            @PathVariable String grantProgramId,
            @RequestBody QuestionIdRequest request) {
        GrantProgramDto updated = grantProgramService.addQuestionToGrantProgram(grantProgramId, request.getQuestionId());
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{grantProgramId}/question-groups")
    public ResponseEntity<GrantProgramDto> addQuestionGroup(
            @PathVariable String grantProgramId,
            @RequestBody QuestionGroupIdRequest request) {
        GrantProgramDto updated = grantProgramService.addQuestionGroupToGrantProgram(grantProgramId, request.getQuestionGroupId());
        return ResponseEntity.ok(updated);
    }
}