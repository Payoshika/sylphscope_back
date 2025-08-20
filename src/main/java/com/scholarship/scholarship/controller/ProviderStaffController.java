package com.scholarship.scholarship.controller;
import com.scholarship.scholarship.dto.*;
import com.scholarship.scholarship.enums.StaffRole;

import com.scholarship.scholarship.model.ProviderInvitation;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.service.*;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
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
    @Autowired
    private ProviderInvitationService providerInvitationService;

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

    @DeleteMapping("/{providerStaffId}/remove-from-provider")
    public ResponseEntity<ProviderStaffDto> removeStaff(@PathVariable String providerStaffId) {
        try {
            // Get the provider staff
            ProviderStaffDto staff = providerStaffService.getProviderStaffById(providerStaffId)
                    .orElseThrow(() -> new ResourceNotFoundException("Provider staff not found with id: " + providerStaffId));

            String providerId = staff.getProviderId();
            System.out.println("Removing staff " + providerStaffId + " from provider " + providerId);

            // Get all grant programs for this provider
            List<GrantProgramDto> grantPrograms = grantProgramService.getGrantProgramsByProviderId(providerId);

            // Remove staff from grant programs where they are assigned or contact person
            for (GrantProgramDto grantProgram : grantPrograms) {
                boolean updated = false;

                // Check if staff is contact person
                if (grantProgram.getContactPerson() != null &&
                    providerStaffId.equals(grantProgram.getContactPerson().getId())) {
                    grantProgram.setContactPerson(null);
                    updated = true;
                    System.out.println("Removed staff as contact person from grant program: " + grantProgram.getId());
                }

                // Check if staff is in assigned staff list
                if (grantProgram.getAssignedStaff() != null) {
                    List<com.scholarship.scholarship.valueObject.AssignedStaff> updatedAssignedStaff =
                        grantProgram.getAssignedStaff().stream()
                            .filter(assignedStaff -> !providerStaffId.equals(assignedStaff.getStaffId()))
                            .collect(Collectors.toList());

                    if (updatedAssignedStaff.size() != grantProgram.getAssignedStaff().size()) {
                        grantProgram.setAssignedStaff(updatedAssignedStaff);
                        updated = true;
                        System.out.println("Removed staff from assigned staff list of grant program: " + grantProgram.getId());
                    }
                }

                // Update grant program if changes were made
                if (updated) {
                    grantProgramService.updateGrantProgram(grantProgram.getId(), grantProgram);
                }
            }

            // Remove providerId from staff (set to null or empty)
            staff.setProviderId(null);
            ProviderStaffDto updatedStaff = providerStaffService.updateProviderStaff(providerStaffId, staff);

            System.out.println("Successfully removed staff " + providerStaffId + " from provider " + providerId);
            return ResponseEntity.ok(updatedStaff);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println("Error removing staff: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

    // INVITATION SYSTEM ENDPOINTS

    @PostMapping("/invite")
    public ResponseEntity<Map<String, String>> inviteProviderStaff(
            @RequestBody InviteProviderStaffRequest request,
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Extract user ID from auth header (you'll need to implement this based on your auth system)
            String invitedByUserId = extractUserIdFromAuth(authHeader);

            ProviderInvitation invitation = providerInvitationService.createInvitation(request, invitedByUserId);

            // Generate invitation URL
            String invitationUrl = generateInvitationUrl(invitation.getToken());

            // TODO: Send email notification here
            System.out.println("Invitation created for " + request.getEmail() + " to join provider " + request.getProviderId());
            System.out.println("Invitation URL: " + invitationUrl);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Invitation sent successfully");
            response.put("invitationUrl", invitationUrl);
            response.put("token", invitation.getToken());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/invitation/{token}")
    public ResponseEntity<Map<String, Object>> getInvitationDetails(@PathVariable String token) {
        try {
            ProviderInvitation invitation = providerInvitationService.getInvitationByToken(token);
            ProviderDto provider = providerService.getProviderById(invitation.getProviderId()).orElse(null);
            Map<String, Object> response = new HashMap<>();
            response.put("email", invitation.getEmail());
            response.put("firstName", invitation.getFirstName());
            response.put("lastName", invitation.getLastName());
            response.put("role", invitation.getRole());
            response.put("providerName", provider != null ? provider.getOrganisationName() : "Unknown");
            response.put("expired", invitation.isExpired() || invitation.getExpiresAt().isBefore(java.time.Instant.now()));
            response.put("used", invitation.isUsed());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/invitation/accept")
    public ResponseEntity<Map<String, Object>> acceptInvitation(@RequestBody AcceptInvitationRequest request) {
        try {
            ProviderStaffDto newStaff = providerInvitationService.acceptInvitation(request);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invitation accepted successfully");
            response.put("providerStaff", newStaff);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/provider/{providerId}/pending-invitations")
    public ResponseEntity<List<ProviderInvitation>> getPendingInvitations(@PathVariable String providerId) {
        List<ProviderInvitation> pendingInvitations = providerInvitationService.getPendingInvitationsByProvider(providerId);
        return ResponseEntity.ok(pendingInvitations);
    }

    @DeleteMapping("/invitation/{token}/cancel")
    public ResponseEntity<Map<String, String>> cancelInvitation(
            @PathVariable String token,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String userId = extractUserIdFromAuth(authHeader);
            boolean cancelled = providerInvitationService.cancelInvitation(token, userId);

            Map<String, String> response = new HashMap<>();
            if (cancelled) {
                response.put("message", "Invitation cancelled successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Unable to cancel invitation");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ProviderStaffDto> updateStaffProfile(@RequestBody ProviderStaffDto providerStaffDto) {
        ProviderStaffDto updatedStaff = providerStaffService.updateProviderStaff(providerStaffDto.getId(), providerStaffDto);
        return ResponseEntity.ok(updatedStaff);
    }

    // Helper methods
    private String extractUserIdFromAuth(String authHeader) {
        // TODO: Implement based on your JWT/Auth system
        // This is a placeholder - you'll need to extract the user ID from your JWT token
        // For now, returning a dummy value
        return "current-user-id";
    }

    private String generateInvitationUrl(String token) {
        // TODO: Configure base URL from application properties
        String baseUrl = "http://localhost:3000"; // Frontend URL
        return baseUrl + "/invitation/accept/" + token;
    }
}
