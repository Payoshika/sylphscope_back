package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramApplicationDto;

import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.service.ApplicationService;
import com.scholarship.scholarship.service.GrantProgramService;
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
    private GrantProgramService grantProgramService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/student/{studentId}/grant-program-applications")
    public ResponseEntity<List<GrantProgramApplicationDto>> getGrantProgramAndApplicationByStudentId(
            @PathVariable String studentId) {
        List<ApplicationDto> applications = applicationService.getApplicationsByStudentId(studentId);
        List<GrantProgramApplicationDto> result = applications.stream()
                .map(app -> new GrantProgramApplicationDto(
                        grantProgramService.getGrantProgramById(app.getGrantProgramId()),
                        app))
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/student/{studentId}/grant-program/{grantProgramId}/grant-program-application")
    public ResponseEntity<GrantProgramApplicationDto> getGrantProgramAndApplicationByStudentIdAndGrantProgramId(
            @PathVariable String studentId,
            @PathVariable String grantProgramId) {
        List<ApplicationDto> applications = applicationService.getApplicationsByStudentId(studentId);
        ApplicationDto application = applications.stream()
                .filter(app -> grantProgramId.equals(app.getGrantProgramId()))
                .findFirst()
                .orElse(null);

        if (application == null) {
            System.out.println("application is null");
            return ResponseEntity.notFound().build();
        }

        GrantProgramApplicationDto result = new GrantProgramApplicationDto(
                grantProgramService.getGrantProgramById(grantProgramId),
                application
        );
        System.out.println("application found" + result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(applicationService.getApplicationsByStudentId(studentId));
    }

    @GetMapping("/grant-program/{grantProgramId}")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByGrantProgramId(@PathVariable String grantProgramId) {
        return ResponseEntity.ok(applicationService.getApplicationsByGrantProgramId(grantProgramId));
    }

    @PostMapping
    public ResponseEntity<ApplicationDto> createApplication(@Valid @RequestBody ApplicationDto applicationDto) {
        return new ResponseEntity<>(applicationService.createApplication(applicationDto), HttpStatus.CREATED);
    }

    @PostMapping("/student/{studentId}/grant-program/{grantProgramId}")
    public ResponseEntity<ApplicationDto> createApplicationByStudentIdAndGrantProgramId(
            @PathVariable String studentId,
            @PathVariable String grantProgramId) {
        ApplicationDto createdApplication = applicationService.createApplicationByStudentIdAndGrantProgramId(studentId, grantProgramId);
        return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDto> updateApplication(@PathVariable String id, @Valid @RequestBody ApplicationDto applicationDto) {
        try {
            return ResponseEntity.ok(applicationService.updateApplication(id, applicationDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable String id) {
        return applicationService.deleteApplication(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/grant-program/{grantProgramId}/application-count")
    public ResponseEntity<Integer> getApplicationCountForGrantProgram(@PathVariable String grantProgramId) {
        int count = applicationService.getApplicationsByGrantProgramId(grantProgramId).size();
        System.out.println("Application count for " + grantProgramId + " is " + count);
        return ResponseEntity.ok(count);
    }
}