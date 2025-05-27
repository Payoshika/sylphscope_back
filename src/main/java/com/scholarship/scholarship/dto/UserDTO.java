package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.auth.Role;
import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private List<Role> roles;
    private boolean enabled;
}