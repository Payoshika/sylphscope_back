package com.scholarship.scholarship.auth;

import com.scholarship.scholarship.dto.SignupRequest;
import com.scholarship.scholarship.dto.UserDTO;
import com.scholarship.scholarship.dto.UserMapper;
import com.scholarship.scholarship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    public UserDTO registerNewUser(SignupRequest signupRequest) {
        // Check if username already exists
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords don't match");
        }

        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }

        // Check if email already exists
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        // Create new user
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setRoles(Collections.singletonList(Role.RECEIVER)); // Default role
        user.setEnabled(true);

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO addRoleToUser(String username, Role role) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Check if user already has this role
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            user = userRepository.save(user);
        }

        return userMapper.toDTO(user);
    }

    public UserDTO removeRoleFromUser(String username, Role role) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (user.getRoles().contains(role) && user.getRoles().size() > 1) {
            user.getRoles().remove(role);
            user = userRepository.save(user);
        }

        return userMapper.toDTO(user);
    }

    public UserDTO findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return userMapper.toDTO(user);
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return userMapper.toDTO(user);
    }

}