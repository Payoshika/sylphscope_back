package com.scholarship.scholarship.notification;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendMfaCode(String toEmail, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Your Verification Code");
            message.setText("Your verification code is: " + code + "\nIt will expire in 10 minutes.");

            mailSender.send(message);
            logger.info("MFA code sent to: {}", toEmail);
        } catch (Exception e) {
            logger.error("Failed to send MFA email", e);
            throw new RuntimeException("Failed to send MFA email", e);
        }
    }
}