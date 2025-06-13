package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.auth.User;
import com.scholarship.scholarship.auth.UserService;
import com.scholarship.scholarship.dto.*;
import com.scholarship.scholarship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.scholarship.scholarship.auth.JwtUtils;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    private UserRepository userRepository;

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
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        try {
            // Check if username already exists (if being changed)
            String newUsername = request.getUsername();
            if (newUsername != null && !newUsername.equals(currentUsername) &&
                    userRepository.findByUsername(newUsername).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Username is already taken"));
            }
            // Check if email already exists (if being changed)
            String newEmail = request.getEmail();
            if (newEmail != null &&
                    userRepository.findByEmail(newEmail).isPresent() &&
                    !userRepository.findByUsername(currentUsername).get().getEmail().equals(newEmail)) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Email is already in use"));
            }
            // Get user
            User user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            // Update fields
            if (newUsername != null) {
                user.setUsername(newUsername);
            }
            if (newEmail != null) {
                user.setEmail(newEmail);
            }

            // Save updated user
            User updatedUser = userRepository.save(user);
            // Return the updated user data
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