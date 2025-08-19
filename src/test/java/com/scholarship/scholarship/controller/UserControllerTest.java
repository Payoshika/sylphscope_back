package com.scholarship.scholarship.controller;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.scholarship.scholarship.auth.User;
import com.scholarship.scholarship.auth.UserService;
import com.scholarship.scholarship.dto.*;
import com.scholarship.scholarship.repository.UserRepository;
import com.scholarship.scholarship.auth.JwtUtils;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private JwtUtils jwtUtils;
    @InjectMocks
    private UserController userController;
    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("testuser");
    }

    @Test
    void changePassword_success() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("hashed");
        PasswordChangeRequest req = new PasswordChangeRequest();
        req.setCurrentPassword("current");
        req.setNewPassword("newpass");
        req.setConfirmPassword("newpass");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("current", "hashed")).thenReturn(true);
        when(passwordEncoder.encode("newpass")).thenReturn("hashednew");
        when(userRepository.save(any(User.class))).thenReturn(user);
        ResponseEntity<?> response = userController.changePassword(req);
        assertEquals("Password changed successfully", ((MessageResponse)response.getBody()).getMessage());
    }

    @Test
    void changePassword_wrongCurrent() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("hashed");
        PasswordChangeRequest req = new PasswordChangeRequest();
        req.setCurrentPassword("wrong");
        req.setNewPassword("newpass");
        req.setConfirmPassword("newpass");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashed")).thenReturn(false);
        ResponseEntity<?> response = userController.changePassword(req);
        assertEquals("Current password is incorrect", ((MessageResponse)response.getBody()).getMessage());
    }

    @Test
    void changePassword_mismatchNew() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("hashed");
        PasswordChangeRequest req = new PasswordChangeRequest();
        req.setCurrentPassword("current");
        req.setNewPassword("newpass");
        req.setConfirmPassword("otherpass");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("current", "hashed")).thenReturn(true);
        ResponseEntity<?> response = userController.changePassword(req);
        assertEquals("New passwords don't match", ((MessageResponse)response.getBody()).getMessage());
    }

    @Test
    void updateProfile_success() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("old@test.com");
        ProfileChangeRequest req = new ProfileChangeRequest();
        req.setUsername("newuser");
        req.setEmail("new@test.com");
        UserDTO userDTO = new UserDTO();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("new@test.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userService.findByUsername("newuser")).thenReturn(userDTO);
        ResponseEntity<?> response = userController.updateProfile(req);
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void updateProfile_usernameTaken() {
        User user = new User();
        user.setUsername("testuser");
        ProfileChangeRequest req = new ProfileChangeRequest();
        req.setUsername("takenuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("takenuser")).thenReturn(Optional.of(new User()));
        ResponseEntity<?> response = userController.updateProfile(req);
        assertEquals("Username is already taken", ((MessageResponse)response.getBody()).getMessage());
    }

    @Test
    void updateProfile_emailTaken() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("old@test.com");
        ProfileChangeRequest req = new ProfileChangeRequest();
        req.setEmail("taken@test.com");
        User takenUser = new User();
        takenUser.setEmail("taken@test.com");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("taken@test.com")).thenReturn(Optional.of(takenUser));
        ResponseEntity<?> response = userController.updateProfile(req);
        assertEquals("Email is already in use", ((MessageResponse)response.getBody()).getMessage());
    }

    @Test
    void updateProfile_error() {
        ProfileChangeRequest req = new ProfileChangeRequest();
        when(userRepository.findByUsername(anyString())).thenThrow(new RuntimeException("fail"));
        ResponseEntity<?> response = userController.updateProfile(req);
        assertTrue(((MessageResponse)response.getBody()).getMessage().contains("Error updating profile"));
    }

    @Test
    void updateMfa_success() {
        User user = new User();
        user.setUsername("testuser");
        MfaUpdateRequest req = new MfaUpdateRequest();
        req.setEnableMfa(true);
        UserDTO userDTO = new UserDTO();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userService.findByUsername("testuser")).thenReturn(userDTO);
        ResponseEntity<?> response = userController.updateMfa(req);
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void updateMfa_disable() {
        User user = new User();
        user.setUsername("testuser");
        user.setMfaEnabled(true);
        user.setMfaSecret("secret");
        user.setMfaExpiry(new Date());
        user.setLastMfaVerifiedDate(new Date());
        MfaUpdateRequest req = new MfaUpdateRequest();
        req.setEnableMfa(false);
        UserDTO userDTO = new UserDTO();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userService.findByUsername("testuser")).thenReturn(userDTO);
        ResponseEntity<?> response = userController.updateMfa(req);
        assertEquals(userDTO, response.getBody());
        assertNull(user.getMfaSecret());
    }

    @Test
    void updateMfa_error() {
        MfaUpdateRequest req = new MfaUpdateRequest();
        when(userRepository.findByUsername(anyString())).thenThrow(new RuntimeException("fail"));
        ResponseEntity<?> response = userController.updateMfa(req);
        assertTrue(((MessageResponse)response.getBody()).getMessage().contains("Error updating MFA settings"));
    }
}