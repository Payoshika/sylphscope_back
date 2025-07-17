// src/main/java/com/scholarship/scholarship/controller/SelectionCriterionController.java
package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.SelectionCriterionDto;
import com.scholarship.scholarship.model.SelectionCriterion;
import com.scholarship.scholarship.service.SelectionCriterionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/selection-criteria")
@RequiredArgsConstructor
public class SelectionCriterionController {

    private final SelectionCriterionService selectionCriterionService;

    @GetMapping("/by-grant-program/{grantProgramId}")
    public List<SelectionCriterion> getByGrantProgramId(@PathVariable String grantProgramId) {
        return selectionCriterionService.getByGrantProgramId(grantProgramId);
    }

    @PostMapping
    public SelectionCriterion create(@RequestBody SelectionCriterionDto dto) {
        return selectionCriterionService.create(dto);
    }

    @PutMapping("/{id}")
    public SelectionCriterion update(@PathVariable String id, @RequestBody SelectionCriterionDto dto) {
        return selectionCriterionService.update(id, dto);
    }

    @PutMapping("/batch-update/{grantProgramId}")
    public List<SelectionCriterion> batchUpdate(@PathVariable String grantProgramId, @RequestBody List<SelectionCriterionDto> dtos) {
        return selectionCriterionService.batchUpdate(grantProgramId, dtos);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        selectionCriterionService.delete(id);
    }
}