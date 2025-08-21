package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.model.Provider;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.auth.User;
import com.scholarship.scholarship.repository.ProviderStaffRepository;
import com.scholarship.scholarship.repository.UserRepository;


import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.dto.ProviderDto;
import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.dto.StudentDto;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import com.scholarship.scholarship.dto.InvitationCodeRequest;
import com.scholarship.scholarship.dto.SwitchManagerRequest;
import com.scholarship.scholarship.enums.StaffRole;
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
    @Autowired
    private ProviderStaffRepository providerStaffRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private com.scholarship.scholarship.repository.ProviderRepository providerRepository;

    @PostMapping
    public ResponseEntity<ProviderDto> createProvider(@RequestBody ProviderDto providerDto) {
        ProviderDto createdProvider = providerService.createProvider(providerDto);
        return new ResponseEntity<>(createdProvider, HttpStatus.CREATED);
    }

    @PostMapping("/empty")
    public ResponseEntity<ProviderDto> createEmptyProvider(@RequestParam String userId) {
        // Get ProviderStaff by userId
        ProviderStaff providerStaff = providerStaffRepository.findByUserId(userId)
                .stream().findFirst().orElse(null);
        if (providerStaff == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Get User by userId
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Convert ProviderStaff to ProviderStaffDto
        ProviderStaffDto providerStaffDto = new ProviderStaffDto();
        providerStaffDto.setId(providerStaff.getId());
        providerStaffDto.setUserId(providerStaff.getUserId());
        providerStaffDto.setProviderId(providerStaff.getProviderId());
        providerStaffDto.setRole(providerStaff.getRole());
        providerStaffDto.setProviderStaffAccessRights(providerStaff.getProviderStaffAccessRights());
        providerStaffDto.setFirstName(providerStaff.getFirstName());
        providerStaffDto.setMiddleName(providerStaff.getMiddleName());
        providerStaffDto.setLastName(providerStaff.getLastName());

        ProviderDto emptyProvider = new ProviderDto();
        emptyProvider.setContactPerson(providerStaffDto);
        emptyProvider.setContactEmail(user.getEmail());
        emptyProvider.setContactPhone("tentative phone number");
        // Set other fields to empty or default values as needed

        ProviderDto createdProvider = providerService.createProvider(emptyProvider);
        // Set providerStaff.providerId to createdProvider.id
        providerStaff.setProviderId(createdProvider.getId());
        providerStaff.setRole(StaffRole.MANAGER);
        providerStaffRepository.save(providerStaff);
        return new ResponseEntity<>(createdProvider, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDto> getProviderById(@PathVariable String id) {
        return providerService.getProviderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(null));
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

    @PostMapping("/{id}/invitation-code")
    public ResponseEntity<ProviderDto> setInvitationCode(@PathVariable String id, @RequestBody InvitationCodeRequest request) {
        Optional<ProviderDto> providerOpt = providerService.getProviderById(id);
        if (providerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProviderDto providerDto = providerOpt.get();
        String invitationCode = request.getInvitationCode();
        System.out.println("Received invitationCode = " + invitationCode);
        providerDto.setInvitationCode(invitationCode);
        try {
            ProviderDto updatedProvider = providerService.updateProvider(id, providerDto);
            return ResponseEntity.ok(updatedProvider);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("/add-staff-member")
    public ResponseEntity<ProviderStaffDto> addStaffMember(@RequestParam String userId, @RequestParam String invitationCode) {
        ProviderStaff providerStaff = providerStaffRepository.findByUserId(userId)
                .stream().findFirst().orElse(null);
        if (providerStaff == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        System.out.println("providerStaff = " + providerStaff);
        System.out.println("invitationCode = " + invitationCode);
        //this part is buggy, not creating a new providerStaff if it does not exist
        Optional<Provider> providerOpt = providerRepository.findByInvitationCode(invitationCode);
        if (providerOpt.isEmpty()) {
            System.out.println("provider option is empty");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        System.out.println("providerOpt = " + providerOpt.get());
        providerStaff.setProviderId(providerOpt.get().getId());
        providerStaffRepository.save(providerStaff);
        System.out.println("providerStaff is updated = " + providerStaff);
        ProviderStaffDto providerStaffDto = new ProviderStaffDto();
        providerStaffDto.setId(providerStaff.getId());
        providerStaffDto.setUserId(providerStaff.getUserId());
        providerStaffDto.setProviderId(providerStaff.getProviderId());
        providerStaffDto.setRole(providerStaff.getRole());
        providerStaffDto.setProviderStaffAccessRights(providerStaff.getProviderStaffAccessRights());
        providerStaffDto.setFirstName(providerStaff.getFirstName());
        providerStaffDto.setMiddleName(providerStaff.getMiddleName());
        providerStaffDto.setLastName(providerStaff.getLastName());
        return ResponseEntity.ok(providerStaffDto);
    }

    @PostMapping("/switch-manager")
    public ResponseEntity<List<ProviderStaffDto>> switchManager(@RequestBody SwitchManagerRequest request) {
        String managerId = request.getManagerId();
        String otherStaffId = request.getOtherStaffId();
        Optional<ProviderStaff> managerOpt = providerStaffRepository.findById(managerId);
        if (managerOpt.isEmpty() || managerOpt.get().getRole() != StaffRole.MANAGER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Optional<ProviderStaff> otherOpt = providerStaffRepository.findById(otherStaffId);
        if (otherOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ProviderStaff manager = managerOpt.get();
        ProviderStaff otherStaff = otherOpt.get();
        manager.setRole(StaffRole.ADMINISTRATOR);
        otherStaff.setRole(StaffRole.MANAGER);
        providerStaffRepository.save(manager);
        providerStaffRepository.save(otherStaff);
        ProviderStaffDto managerDto = new ProviderStaffDto();
        ProviderStaffDto otherDto = new ProviderStaffDto();
        org.springframework.beans.BeanUtils.copyProperties(manager, managerDto);
        org.springframework.beans.BeanUtils.copyProperties(otherStaff, otherDto);
        List<ProviderStaffDto> result = new java.util.ArrayList<>();
        result.add(managerDto);
        result.add(otherDto);
        return ResponseEntity.ok(result);
    }
}
