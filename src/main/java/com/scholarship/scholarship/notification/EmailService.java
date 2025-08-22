package com.scholarship.scholarship.notification;

import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Message;
import com.postmarkapp.postmark.client.data.model.message.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${env.POSTMAN_SERVER_TOKEN}")
    private String postmarkToken;

    public void sendMfaCode(String toEmail, String code) {
        try {
            ApiClient client = Postmark.getApiClient(postmarkToken);
            Message message = new Message(fromEmail, toEmail, "Your Verification Code",
                    "Your verification code is: " + code + "\nIt will expire in 10 minutes.");
            message.setMessageStream("broadcast");
            MessageResponse response = client.deliverMessage(message);
            logger.info("Email sent to {} with response: {}", toEmail, response);

            if (response.getErrorCode() != 0) {
                throw new RuntimeException("Failed to send email: " + response.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error sending MFA email to {}: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Error sending MFA email", e);
        }
    }

    public void sendRegistrationConfirmation(String toEmail, String username) {
        String subject = "Registration Successful";
        String body = "Hello " + username + ",\n\nYour registration was successful. Welcome!";
        try {
            ApiClient client = Postmark.getApiClient(postmarkToken);
            Message message = new Message(fromEmail, toEmail, subject, body);
            message.setMessageStream("broadcast");
            MessageResponse response = client.deliverMessage(message);
            logger.info("Registration email sent to {} with response: {}", toEmail, response);
            if (response.getErrorCode() != 0) {
                throw new RuntimeException("Failed to send email: " + response.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error sending registration email to {}: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Error sending registration email", e);
        }
    }

    public void sendApplicationStatusNotification(String toEmail, String applicantName, String status) {
        String subject = "Application Status Update";
        String body = "Hello " + applicantName + ",\n\nYour application status is: " + status + ".";
        try {
            ApiClient client = Postmark.getApiClient(postmarkToken);
            Message message = new Message(fromEmail, toEmail, subject, body);
            message.setMessageStream("broadcast");
            MessageResponse response = client.deliverMessage(message);
            logger.info("Status email sent to {} with response: {}", toEmail, response);
            if (response.getErrorCode() != 0) {
                throw new RuntimeException("Failed to send email: " + response.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error sending status email to {}: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Error sending status email", e);
        }
    }


}