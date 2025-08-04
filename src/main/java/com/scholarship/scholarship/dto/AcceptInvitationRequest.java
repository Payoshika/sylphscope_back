package com.scholarship.scholarship.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcceptInvitationRequest {
    private String token;
    private String userId; // For existing users
    // For new users who need to register
    private String password;
    private boolean isNewUser;
}
