package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.auth.Role;
import com.scholarship.scholarship.auth.User;
import com.scholarship.scholarship.enums.StaffRole;
import com.scholarship.scholarship.model.Student;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.auth.UserService;
import com.scholarship.scholarship.dto.*;
import com.scholarship.scholarship.repository.ProviderStaffRepository;
import com.scholarship.scholarship.repository.UserRepository;
import com.scholarship.scholarship.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.scholarship.scholarship.auth.JwtUtils;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProviderStaffRepository providerStaffRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request) {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Current password is incorrect"));
        }

        // Check if new password matches confirmation
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("New passwords don't match"));
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
    }

    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileChangeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        try {
            String newUsername = request.getUsername();
            String newEmail = request.getEmail();
            List<Role> newRole = request.getUserRoles();

            if (newUsername != null && !newUsername.equals(currentUsername) &&
                    userRepository.findByUsername(newUsername).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Username is already taken"));
            }
            if (newEmail != null &&
                    userRepository.findByEmail(newEmail).isPresent() &&
                    !userRepository.findByUsername(currentUsername).get().getEmail().equals(newEmail)) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Email is already in use"));
            }

            User user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (newUsername != null) {
                user.setUsername(newUsername);
            }
            if (newEmail != null) {
                user.setEmail(newEmail);
            }
            if (newRole != null && !newRole.equals(user.getRoles())) {
                user.setRoles(newRole);
                System.out.println("newRole.contains(Role.STUDENT)");
                System.out.println(newRole.contains(Role.STUDENT));
                System.out.println("newRole.contains(Role.PROVIDER)");
                System.out.println(newRole.contains(Role.PROVIDER));
                if (newRole.contains(Role.STUDENT)) {
                    Student student = new Student();
                    student.setUserId(user.getId());
                    student.setFirstName("");
                    student.setLastName("");
                    student.setPhoneNumber("");
                    // Set other fields to empty or default values as needed
                    studentRepository.save(student);
                }
                if (newRole.contains(Role.PROVIDER)) {
                    ProviderStaff providerStaff = new ProviderStaff();
                    providerStaff.setUserId(user.getId());
                    providerStaff.setProviderId("");
                    providerStaff.setFirstName("");
                    providerStaff.setLastName("");
                    providerStaff.setMiddleName("");
                    providerStaff.setRole(StaffRole.ADMINISTRATOR);
                    providerStaffRepository.save(providerStaff);
                }
            }

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(userService.findByUsername(updatedUser.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error updating profile: " + e.getMessage()));
        }
    }

    @PostMapping("/update-mfa")
    public ResponseEntity<?> updateMfa(@RequestBody MfaUpdateRequest request) {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            // Update MFA status
            user.setMfaEnabled(request.isEnableMfa());
            // If MFA is being disabled, clear related fields
            if (!request.isEnableMfa()) {
                user.setMfaSecret(null);
                user.setMfaExpiry(null);
                user.setLastMfaVerifiedDate(null);
            }
            // Save updated user
            User updatedUser = userRepository.save(user);
            // Return the updated user data
            return ResponseEntity.ok(userService.findByUsername(updatedUser.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error updating MFA settings: " + e.getMessage()));
        }
    }
}