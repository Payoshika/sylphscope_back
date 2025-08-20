package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.auth.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByUsername() {
        User user = new User();
        user.setId("1");
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Optional<User> result = userRepository.findByUsername("testuser");
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void findByGoogleId() {
        User user = new User();
        user.setId("1");
        user.setGoogleId("google123");
        when(userRepository.findByGoogleId("google123")).thenReturn(Optional.of(user));
        Optional<User> result = userRepository.findByGoogleId("google123");
        assertTrue(result.isPresent());
        assertEquals("google123", result.get().getGoogleId());
    }

    @Test
    void findByEmail() {
        User user = new User();
        user.setId("1");
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        Optional<User> result = userRepository.findByEmail("test@example.com");
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
    }
}