package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.auth.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class ProfileChangeRequest {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "email is required")
    private String email;
    private List<Role> userRoles;
}
