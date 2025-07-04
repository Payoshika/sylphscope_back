package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.model.GrantProgram;
import com.scholarship.scholarship.service.GrantProgramService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grant-programs")
public class GrantProgramController {

    private final GrantProgramService grantProgramService;

    @Autowired
    public GrantProgramController(GrantProgramService grantProgramService) {
        this.grantProgramService = grantProgramService;
    }

    @GetMapping
    public ResponseEntity<List<GrantProgram>> getAllGrantPrograms() {
        return ResponseEntity.ok(grantProgramService.getAllGrantPrograms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrantProgram> getGrantProgramById(@PathVariable String id) {
        return grantProgramService.getGrantProgramById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<GrantProgram>> getGrantProgramsByProviderId(@PathVariable String providerId) {
        return ResponseEntity.ok(grantProgramService.getGrantProgramsByProviderId(providerId));
    }

    @PostMapping
    public ResponseEntity<GrantProgram> createGrantProgram(@Valid @RequestBody GrantProgram grantProgram) {
        return new ResponseEntity<>(grantProgramService.createGrantProgram(grantProgram), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrantProgram> updateGrantProgram(@PathVariable String id, @Valid @RequestBody GrantProgram grantProgram) {
        return grantProgramService.updateGrantProgram(id, grantProgram)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrantProgram(@PathVariable String id) {
        return grantProgramService.deleteGrantProgram(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}