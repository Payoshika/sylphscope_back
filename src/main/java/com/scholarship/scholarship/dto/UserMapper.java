package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.auth.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        dto.setEnabled(user.isEnabled());
        dto.setMfaEnabled(user.isMfaEnabled());
        return dto;
    }
}