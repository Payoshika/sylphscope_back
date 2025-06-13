package com.scholarship.scholarship.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileChangeRequest {
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "email is required")
    private String email;
}
