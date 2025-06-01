package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.auth.JwtUtils;
import com.scholarship.scholarship.auth.UserDetailsImpl;
import com.scholarship.scholarship.auth.UserService;
import com.scholarship.scholarship.dto.JwtResponse;
import com.scholarship.scholarship.dto.LoginRequest;
import com.scholarship.scholarship.dto.MessageResponse;
import com.scholarship.scholarship.dto.SignupRequest;
import com.scholarship.scholarship.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            UserDTO userDTO = userService.registerNewUser(signupRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extract the token from the Authorization header
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                System.out.println("Validating token: " + token);

                // Validate the token
                if (jwtUtils.validateJwtToken(token)) {
                    // Extract username from token (which could be an email for OAuth2 users)
                    String usernameOrEmail = jwtUtils.getUsernameFromJwtToken(token);

                    // Try to find user by both username and email
                    UserDTO userDTO;
                    try {
                        // First try by username
                        userDTO = userService.findByUsername(usernameOrEmail);
                    } catch (Exception e) {
                        // If not found by username, try by email
                        if (usernameOrEmail.contains("@")) {
                            userDTO = userService.findByEmail(usernameOrEmail);
                        } else {
                            throw e; // Re-throw if not an email
                        }
                    }

                    return ResponseEntity.ok(userDTO);
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid or expired token"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Error validating token: " + e.getMessage()));
        }
    }

    @GetMapping("/oauth2/redirect")
    public ResponseEntity<?> handleOAuth2Redirect(@RequestParam String token) {
        // Validate the token
        System.out.println("Handling OAuth2 redirect with token: " + token);
        if (jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUsernameFromJwtToken(token);
            UserDTO userDTO = userService.findByUsername(username);
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Invalid token"));
    }

}