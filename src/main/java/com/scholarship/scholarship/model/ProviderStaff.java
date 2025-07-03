package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.StaffRole;

import com.scholarship.scholarship.valueObject.ProviderStaffAccessRights;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "providerStaff")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderStaff {
    @Id
    private String id;

    private String userId;
    private String providerId;
    private StaffRole role;
    private ProviderStaffAccessRights providerStaffAccessRights;
    private String firstName;
    private String middleName;
    private String lastName;

}