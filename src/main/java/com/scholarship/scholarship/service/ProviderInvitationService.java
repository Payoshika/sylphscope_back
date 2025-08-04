package com.scholarship.scholarship.service;

import com.scholarship.scholarship.auth.UserService;
import com.scholarship.scholarship.dto.AcceptInvitationRequest;
import com.scholarship.scholarship.dto.InviteProviderStaffRequest;
import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.enums.StaffRole;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.model.ProviderInvitation;
import com.scholarship.scholarship.auth.User;
import com.scholarship.scholarship.repository.ProviderInvitationRepository;
import com.scholarship.scholarship.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderInvitationService {

    private final ProviderInvitationRepository invitationRepository;
    private final ProviderStaffService providerStaffService;
    private final UserRepository userRepository;
    private final UserService userService;

    public ProviderInvitation createInvitation(InviteProviderStaffRequest request, String invitedByUserId) {
        // Check if there's already a pending invitation
        Optional<ProviderInvitation> existingInvitation =
            invitationRepository.findByEmailAndProviderId(request.getEmail(), request.getProviderId());

        if (existingInvitation.isPresent() && !existingInvitation.get().isUsed() && !existingInvitation.get().isExpired()) {
            throw new RuntimeException("Invitation already exists for this email and provider");
        }

        // Create new invitation
        ProviderInvitation invitation = ProviderInvitation.builder()
                .token(UUID.randomUUID().toString())
                .email(request.getEmail())
                .providerId(request.getProviderId())
                .invitedByUserId(invitedByUserId)
                .role(request.getRole())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .used(false)
                .expired(false)
                .expiresAt(Instant.now().plus(7, ChronoUnit.DAYS)) // 7 days expiry
                .build();

        return invitationRepository.save(invitation);
    }

    public ProviderInvitation getInvitationByToken(String token) {
        return invitationRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found"));
    }

    public ProviderStaffDto acceptInvitation(AcceptInvitationRequest request) {
        ProviderInvitation invitation = getInvitationByToken(request.getToken());

        // Check if invitation is valid
        if (invitation.isUsed()) {
            throw new RuntimeException("Invitation has already been used");
        }

        if (invitation.isExpired() || invitation.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Invitation has expired");
        }

        User user;

        if (request.isNewUser()) {
            // Create new user account
            user = createNewUserFromInvitation(invitation, request.getPassword());
        } else {
            // Use existing user
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            // Verify email matches
            if (!user.getEmail().equals(invitation.getEmail())) {
                throw new RuntimeException("Email mismatch");
            }
        }

        // Create or update ProviderStaff
        ProviderStaffDto providerStaff = createProviderStaffFromInvitation(invitation, user);

        // Mark invitation as used
        invitation.setUsed(true);
        invitation.setUsedAt(Instant.now());
        invitationRepository.save(invitation);

        return providerStaff;
    }

    private User createNewUserFromInvitation(ProviderInvitation invitation, String password) {
        // Check if user with email already exists
        Optional<User> existingUser = userRepository.findByEmail(invitation.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        // Create new user
        User newUser = User.builder()
                .email(invitation.getEmail())
                .password(password) // This should be encoded by UserService
                .username(invitation.getFirstName()+invitation.getLastName())
                .build();
        return userService.createUser(newUser);
    }

    private ProviderStaffDto createProviderStaffFromInvitation(ProviderInvitation invitation, User user) {
        // Check if user is already a staff member of this provider
        Optional<ProviderStaffDto> existing = providerStaffService.getStaffByUserIdAndProviderId(
                user.getId(), invitation.getProviderId());

        if (existing.isPresent()) {
            // Update existing staff member
            ProviderStaffDto staff = existing.get();
            staff.setRole(invitation.getRole());
            return providerStaffService.updateProviderStaff(staff.getId(), staff);
        } else {
            // Create new provider staff
            ProviderStaffDto newStaff = ProviderStaffDto.builder()
                    .userId(user.getId())
                    .providerId(invitation.getProviderId())
                    .role(invitation.getRole())
                    .firstName(invitation.getFirstName())
                    .lastName(invitation.getLastName())
                    .build();

            return providerStaffService.createProviderStaff(newStaff);
        }
    }

    public List<ProviderInvitation> getPendingInvitationsByProvider(String providerId) {
        return invitationRepository.findByProviderId(providerId).stream()
                .filter(inv -> !inv.isUsed() && !inv.isExpired() && inv.getExpiresAt().isAfter(Instant.now()))
                .collect(Collectors.toList());
    }

    public void expireOldInvitations() {
        List<ProviderInvitation> expiredInvitations =
            invitationRepository.findByUsedFalseAndExpiredFalseAndExpiresAtBefore(Instant.now());

        expiredInvitations.forEach(inv -> {
            inv.setExpired(true);
            invitationRepository.save(inv);
        });
    }

    public boolean cancelInvitation(String token, String userId) {
        Optional<ProviderInvitation> invitation = invitationRepository.findByToken(token);
        if (invitation.isPresent() && invitation.get().getInvitedByUserId().equals(userId)) {
            invitation.get().setExpired(true);
            invitationRepository.save(invitation.get());
            return true;
        }
        return false;
    }
}
