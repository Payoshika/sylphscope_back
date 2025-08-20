package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.auth.*;
import com.scholarship.scholarship.dto.JwtResponse;
import com.scholarship.scholarship.dto.LoginRequest;
import com.scholarship.scholarship.dto.MessageResponse;
import com.scholarship.scholarship.dto.SignupRequest;
import com.scholarship.scholarship.dto.UserDTO;
import com.scholarship.scholarship.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MfaService mfaService;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private UserService userService;
    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("pass");
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setId("id1");
        user.setUsername("user");
        user.setEmail("user@example.com");
        user.setPassword("pass");
        user.setRoles(Collections.singletonList(Role.STUDENT));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwt-token");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof JwtResponse);
    }

    @Test
    void registerUser_success() {
        SignupRequest signupRequest = new SignupRequest();
        UserDTO userDTO = new UserDTO();
        when(userService.registerNewUser(signupRequest)).thenReturn(userDTO);
        ResponseEntity<?> response = authController.registerUser(signupRequest);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof MessageResponse);
    }

    @Test
    void registerUser_failure() {
        SignupRequest signupRequest = new SignupRequest();
        when(userService.registerNewUser(signupRequest)).thenThrow(new RuntimeException("fail"));
        ResponseEntity<?> response = authController.registerUser(signupRequest);
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof MessageResponse);
    }

    @Test
    void validateToken_valid() {
        String token = "Bearer validtoken";
        when(jwtUtils.validateJwtToken("validtoken")).thenReturn(true);
        when(jwtUtils.getUserIdFromJwtToken("validtoken")).thenReturn("id1");
        UserDTO userDTO = new UserDTO();
        userDTO.setMfaEnabled(false);
        when(userService.findById("id1")).thenReturn(userDTO);
        ResponseEntity<?> response = authController.validateToken(token);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void validateToken_invalid() {
        String token = "Bearer invalidtoken";
        when(jwtUtils.validateJwtToken("invalidtoken")).thenReturn(false);
        ResponseEntity<?> response = authController.validateToken(token);
        assertEquals(401, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof MessageResponse);
    }

    @Test
    void handleOAuth2Redirect_valid() {
        String token = "validtoken";
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getUsernameFromJwtToken(token)).thenReturn("user");
        UserDTO userDTO = new UserDTO();
        when(userService.findByUsername("user")).thenReturn(userDTO);
        ResponseEntity<?> response = authController.handleOAuth2Redirect(token);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void handleOAuth2Redirect_invalid() {
        String token = "invalidtoken";
        when(jwtUtils.validateJwtToken(token)).thenReturn(false);
        ResponseEntity<?> response = authController.handleOAuth2Redirect(token);
        assertEquals(401, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof MessageResponse);
    }

    @Test
    void requestMfaCode_success() {
        String email = "test@example.com";
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(mfaService.generateAndSendVerificationCode(user)).thenReturn("123456");
        ResponseEntity<?> response = authController.requestMfaCode(Map.of("email", email));
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof MessageResponse);
    }

    @Test
    void requestMfaCode_failure() {
        String email = "fail@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        ResponseEntity<?> response = authController.requestMfaCode(Map.of("email", email));
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof MessageResponse);
    }

    @Test
    void verifyMfaCode_success() {
        String email = "test@example.com";
        String code = "123456";
        String token = "token";
        when(mfaService.verifyCode(email, code)).thenReturn(true);
        when(jwtUtils.generateMfaVerifiedToken(token)).thenReturn("fullAccessToken");
        UserDTO userDTO = new UserDTO();
        userDTO.setId("id1");
        userDTO.setUsername("user");
        userDTO.setEmail(email);
        userDTO.setRoles(Collections.singletonList(Role.STUDENT));
        when(userService.findByEmail(email)).thenReturn(userDTO);
        ResponseEntity<?> response = authController.verifyMfaCode(Map.of("email", email, "code", code, "token", token));
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof JwtResponse);
    }

    @Test
    void verifyMfaCode_failure() {
        String email = "test@example.com";
        String code = "wrongcode";
        String token = "token";
        when(mfaService.verifyCode(email, code)).thenReturn(false);
        ResponseEntity<?> response = authController.verifyMfaCode(Map.of("email", email, "code", code, "token", token));
        assertEquals(401, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof MessageResponse);
    }
}