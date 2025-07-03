package com.scholarship.scholarship.dto;
import com.scholarship.scholarship.auth.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private List<String> roles;
    private boolean mfaEnabled;
    private boolean enabled;

    public void setRoles(@NotEmpty(message = "User must have at least one role") List<Role> roles) {
        if (roles != null && !roles.isEmpty()) {
            this.roles = roles.stream()
                    .map(Role::getAuthority)
                    .toList();
        } else {
            throw new IllegalArgumentException("User must have at least one role");
        }
    }
}