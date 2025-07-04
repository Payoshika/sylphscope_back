// PredefinedQuestionDefinitionController.java
package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.MessageResponse;
import com.scholarship.scholarship.model.PredefinedQuestionDefinition;
import com.scholarship.scholarship.service.PredefinedQuestionDefinitionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question-definitions")
public class PredefinedQuestionDefinitionController {

    @Autowired
    private PredefinedQuestionDefinitionService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createQuestionDefinition(@Valid @RequestBody PredefinedQuestionDefinition questionDefinition) {
        try {
            PredefinedQuestionDefinition created = service.createQuestionDefinition(questionDefinition);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionDefinitionById(@PathVariable String id) {
        return service.getQuestionDefinitionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<?> getQuestionDefinitionByKey(@PathVariable String key) {
        return service.getQuestionDefinitionByKey(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/option-set/{optionSetKey}")
    public ResponseEntity<List<PredefinedQuestionDefinition>> getQuestionDefinitionsByOptionSetKey(
            @PathVariable String optionSetKey) {
        return ResponseEntity.ok(service.getQuestionDefinitionsByOptionSetKey(optionSetKey));
    }

    @GetMapping
    public ResponseEntity<List<PredefinedQuestionDefinition>> getAllQuestionDefinitions() {
        return ResponseEntity.ok(service.getAllQuestionDefinitions());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateQuestionDefinition(
            @PathVariable String id, @Valid @RequestBody PredefinedQuestionDefinition questionDefinition) {
        try {
            PredefinedQuestionDefinition updated = service.updateQuestionDefinition(id, questionDefinition);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteQuestionDefinition(@PathVariable String id) {
        service.deleteQuestionDefinition(id);
        return ResponseEntity.ok(new MessageResponse("Question definition deleted successfully"));
    }
}