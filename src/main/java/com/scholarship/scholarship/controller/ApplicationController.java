package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.model.Application;
import com.scholarship.scholarship.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable String id) {
        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Application>> getApplicationsByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(applicationService.getApplicationsByStudentId(studentId));
    }

    @GetMapping("/grant-program/{grantProgramId}")
    public ResponseEntity<List<Application>> getApplicationsByGrantProgramId(@PathVariable String grantProgramId) {
        return ResponseEntity.ok(applicationService.getApplicationsByGrantProgramId(grantProgramId));
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@Valid @RequestBody Application application) {
        return new ResponseEntity<>(applicationService.createApplication(application), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable String id, @Valid @RequestBody Application application) {
        return applicationService.updateApplication(id, application)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable String id) {
        return applicationService.deleteApplication(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}