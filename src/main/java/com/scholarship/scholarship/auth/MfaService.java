package com.scholarship.scholarship.auth;

import com.scholarship.scholarship.notification.EmailService;
import com.scholarship.scholarship.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class MfaService {
    private static final Logger logger = LoggerFactory.getLogger(MfaService.class);
    private static final int CODE_LENGTH = 6;
    private static final int EXPIRY_MINUTES = 10;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService; // You'll need to create this

    public String generateAndSendVerificationCode(User user) {
        // Generate random numeric code
        String code = generateRandomCode();

        // Update user with the code and expiry
        user.setMfaSecret(code);
        user.setMfaExpiry(calculateExpiry());
        userRepository.save(user);

        // Send email with code
        emailService.sendMfaCode(user.getEmail(), code);

        return code;
    }

    public boolean verifyCode(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getMfaSecret() == null || user.getMfaExpiry() == null) {
            return false;
        }

        if (new Date().after(user.getMfaExpiry())) {
            return false; // Code expired
        }

        return user.getMfaSecret().equals(code);
    }

    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private Date calculateExpiry() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, EXPIRY_MINUTES);
        return calendar.getTime();
    }
}