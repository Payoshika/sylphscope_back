package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.QuestionOptionSetDto;
import com.scholarship.scholarship.service.QuestionOptionSetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/option-sets")
@RequiredArgsConstructor
public class QuestionOptionSetController {

    private final QuestionOptionSetService questionOptionSetService;

    @PostMapping
    public ResponseEntity<QuestionOptionSetDto> createOptionSet(@Valid @RequestBody QuestionOptionSetDto dto) {
        QuestionOptionSetDto created = questionOptionSetService.createQuestionOptionSet(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionOptionSetDto> getOptionSetById(@PathVariable String id) {
        QuestionOptionSetDto optionSet = questionOptionSetService.getById(id);
        return ResponseEntity.ok(optionSet);
    }

    @GetMapping
    public ResponseEntity<List<QuestionOptionSetDto>> getAllOptionSets() {
        List<QuestionOptionSetDto> optionSets = questionOptionSetService.getAllOptionSets();
        return ResponseEntity.ok(optionSets);
    }
}