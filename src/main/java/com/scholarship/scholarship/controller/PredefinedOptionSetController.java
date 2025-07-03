package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.MessageResponse;
import com.scholarship.scholarship.dto.PredefinedOptionSetDto;
import com.scholarship.scholarship.service.PredefinedOptionSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/option-sets")
public class PredefinedOptionSetController {

    @Autowired
    private PredefinedOptionSetService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createOptionSet(@RequestBody PredefinedOptionSetDto dto) {
        try {
            PredefinedOptionSetDto created = service.createOptionSet(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOptionSetById(@PathVariable String id) {
        return service.getOptionSetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<?> getOptionSetByKey(@PathVariable String key) {
        return service.getOptionSetByKey(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PredefinedOptionSetDto>> getAllOptionSets() {
        return ResponseEntity.ok(service.getAllOptionSets());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateOptionSet(@PathVariable String id, @RequestBody PredefinedOptionSetDto dto) {
        try {
            PredefinedOptionSetDto updated = service.updateOptionSet(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteOptionSet(@PathVariable String id) {
        service.deleteOptionSet(id);
        return ResponseEntity.ok(new MessageResponse("Option set deleted successfully"));
    }
}