package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.StaffRole;
import com.scholarship.scholarship.valueObject.ProviderStaffAccessRights;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Provider ID is required")
    private String providerId;

    @NotNull(message = "Staff role is required")
    private StaffRole role;

    @Valid
    private ProviderStaffAccessRights providerStaffAccessRights;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @Size(max = 100, message = "Middle name cannot exceed 100 characters")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;
}