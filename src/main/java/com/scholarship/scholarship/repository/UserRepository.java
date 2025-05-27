package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.auth.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByGoogleId(String googleId); // For future Google SSO
    Optional<User> findByEmail(String email); // Add this method
}