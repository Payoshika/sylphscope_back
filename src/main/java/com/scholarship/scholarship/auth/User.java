package com.scholarship.scholarship.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Indexed(unique = true)
    private String username;

    @NotBlank
    @Email(message = "Email should be valid")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    //User account status, default is enabled
    private boolean enabled = true;

    private List<Role> roles;

    @CreatedDate
    private Instant createdAt;

    //MFA related fields
    private boolean mfaEnabled = false;
    private String mfaSecret; // To store OTP secret or verification code
    private Date mfaExpiry; // To track when the verification code expires
    private Date lastMfaVerifiedDate; // To track the last time MFA was verified
    // Additional fields for SSO
    private String googleId;
    private String pictureUrl;
}