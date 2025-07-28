package com.scholarship.scholarship.controller;
import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.dto.ProviderDto;
import com.scholarship.scholarship.enums.StaffRole;

import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.service.ProviderStaffService;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import com.scholarship.scholarship.dto.StudentDto;
import com.scholarship.scholarship.service.GrantProgramService;
import com.scholarship.scholarship.service.ApplicationService;
import com.scholarship.scholarship.service.StudentService;
import com.scholarship.scholarship.service.ProviderService;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/provider-staff")
public class ProviderStaffController {

    @Autowired
    private ProviderStaffService providerStaffService;
    @Autowired
    private GrantProgramService grantProgramService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ProviderService providerService;

    @PostMapping
    public ResponseEntity<ProviderStaffDto> createProviderStaff(@RequestBody ProviderStaffDto providerStaffDto) {
        ProviderStaffDto createdStaff = providerStaffService.createProviderStaff(providerStaffDto);
        return new ResponseEntity<>(createdStaff, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderStaffDto> getProviderStaffById(@PathVariable String id) {
        return providerStaffService.getProviderStaffById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ProviderStaffDto>> getStaffByProviderId(@PathVariable String providerId) {
        List<ProviderStaffDto> staffList = providerStaffService.getStaffByProviderId(providerId);
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/user/{userId}/provider/{providerId}")
    public ResponseEntity<ProviderStaffDto> getStaffByUserIdAndProviderId(
            @PathVariable String userId,
            @PathVariable String providerId) {
        return providerStaffService.getStaffByUserIdAndProviderId(userId, providerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ProviderStaffDto> getStaffByUserId(@PathVariable String userId) {
        List<ProviderStaffDto> staffList = providerStaffService.getStaffByUserId(userId);
        if (staffList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(staffList.get(0));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<ProviderStaffDto>> getStaffByRole(@PathVariable String role) {
        StaffRole staffRole = StaffRole.fromValue(role);
        if (staffRole == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ProviderStaffDto> staffList = providerStaffService.getStaffByRole(staffRole);
        return ResponseEntity.ok(staffList);
    }

    @GetMapping
    public ResponseEntity<List<ProviderStaffDto>> getAllProviderStaff() {
        List<ProviderStaffDto> staffList = providerStaffService.getAllProviderStaff();
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Map<String, String>>> getAllRoles() {
        return ResponseEntity.ok(
                Arrays.stream(StaffRole.values())
                        .map(role -> Map.of(
                                "value", role.getValue(),
                                "label", role.getValue()
                        ))
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderStaffDto> updateProviderStaff(
            @PathVariable String id,
            @RequestBody ProviderStaffDto providerStaffDto) {
        ProviderStaffDto updatedStaff = providerStaffService.updateProviderStaff(id, providerStaffDto);
        if (updatedStaff != null) {
            return ResponseEntity.ok(updatedStaff);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProviderStaff(@PathVariable String id) {
        providerStaffService.deleteProviderStaff(id);
        return ResponseEntity.noContent().build();
    }
}
