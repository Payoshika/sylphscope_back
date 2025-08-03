package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteProviderStaffRequest {
    private String email;
    private String firstName;
    private String lastName;
    private StaffRole role;
    private String providerId;
}
