package com.scholarship.scholarship.controller;
import com.scholarship.scholarship.auth.Role;
import com.scholarship.scholarship.auth.User;
import com.scholarship.scholarship.auth.UserService;
import com.scholarship.scholarship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    @PutMapping("/users/{id}/roles/add")
    public ResponseEntity<User> addRoleToUser(
            @PathVariable String id,
            @RequestBody Map<String, String> roleRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + id));

        try {
            Role role = Role.valueOf(roleRequest.get("role"));
            if (!user.getRoles().contains(role)) {
                user.getRoles().add(role);
                userRepository.save(user);
            }
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid role: " + roleRequest.get("role"));
        }
    }

    @PutMapping("/users/{id}/roles/remove")
    public ResponseEntity<User> removeRoleFromUser(
            @PathVariable String id,
            @RequestBody Map<String, String> roleRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + id));

        try {
            Role role = Role.valueOf(roleRequest.get("role"));
            if (user.getRoles().contains(role) && user.getRoles().size() > 1) {
                user.getRoles().remove(role);
                userRepository.save(user);
                return ResponseEntity.ok(user);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Cannot remove role: either user doesn't have it or it's their only role");
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid role: " + roleRequest.get("role"));
        }
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<User> updateUserStatus(
            @PathVariable String id,
            @RequestBody Map<String, Boolean> statusRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + id));

        user.setEnabled(statusRequest.get("enabled"));
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}