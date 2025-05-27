package com.scholarship.scholarship.config;

import com.scholarship.scholarship.auth.Role;
import com.scholarship.scholarship.auth.User;
import com.scholarship.scholarship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Check if admin user exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("1234"));
            adminUser.setRoles(Arrays.asList(Role.ADMIN));
            adminUser.setEnabled(true);
            userRepository.save(adminUser);
            System.out.println("Admin user created successfully");
        }
    }
}