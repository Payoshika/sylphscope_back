package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.StaffRole;
import com.scholarship.scholarship.valueObject.ProviderStaffAccessRights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderStaffDto {
    private String id;
    private String userId;
    private String providerId;
    private StaffRole role;
    private ProviderStaffAccessRights providerStaffAccessRights;
    private String firstName;
    private String middleName;
    private String lastName;
}