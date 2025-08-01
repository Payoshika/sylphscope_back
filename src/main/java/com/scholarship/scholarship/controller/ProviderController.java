package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.dto.ProviderDto;
import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.dto.StudentDto;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.service.ApplicationService;
import com.scholarship.scholarship.service.GrantProgramService;
import com.scholarship.scholarship.service.ProviderService;
import com.scholarship.scholarship.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    @Autowired
    private ProviderService providerService;
    @Autowired
    private GrantProgramService grantProgramService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<ProviderDto> createProvider(@RequestBody ProviderDto providerDto) {
        ProviderDto createdProvider = providerService.createProvider(providerDto);
        return new ResponseEntity<>(createdProvider, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDto> getProviderById(@PathVariable String id) {
        return providerService.getProviderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProviderDto>> getAllProviders() {
        List<ProviderDto> providers = providerService.getAllProviders();
        return ResponseEntity.ok(providers);
    }

    @GetMapping("/organisation/{name}")
    public ResponseEntity<ProviderDto> getProviderByOrganisationName(@PathVariable String name) {
        return providerService.getProviderByOrganisationName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDto> updateProvider(@PathVariable String id, @RequestBody ProviderDto providerDto) {
        ProviderDto updatedProvider = providerService.updateProvider(id, providerDto);
        if (updatedProvider != null) {
            return ResponseEntity.ok(updatedProvider);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable String id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{providerId}/assign-contact-person")
    public ResponseEntity<ProviderDto> assignContactPerson(@PathVariable String providerId, @RequestBody ProviderStaffDto providerStaffDto) {
        ProviderDto updatedProvider = providerService.assignContactPerson(providerId, providerStaffDto);
        if (updatedProvider != null) {
            return ResponseEntity.ok(updatedProvider);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{providerId}/staff")
    public ResponseEntity<List<ProviderStaffDto>> getStaff(@PathVariable String providerId) {
        List<ProviderStaffDto> staffList = providerService.getStaff(providerId);
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/managed-students/{providerId}")
    public ResponseEntity<List<Map<String, Object>>> getListOfStudentForProvider(@PathVariable String providerId) {
        ProviderDto provider = providerService.getProviderById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found with id: " + providerId));
        List<GrantProgramDto> managedPrograms = grantProgramService.getGrantProgramsByProviderId(providerId);
        List<Map<String, Object>> result = new ArrayList<>();
        for (GrantProgramDto gp : managedPrograms) {
            List<ApplicationDto> applications = applicationService.getApplicationsByGrantProgramId(gp.getId());
            List<StudentDto> students = applications.stream()
                    .map(app -> studentService.getStudentById(app.getStudentId()).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
            Map<String, Object> entry = new HashMap<>();
            entry.put("grantProgram", gp);
            entry.put("students", students);
            result.add(entry);
        }
        return ResponseEntity.ok(result);
    }
}