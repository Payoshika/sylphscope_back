package com.scholarship.scholarship.valueObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderStaffAccessRights {
    private boolean canCreateGrant;
    private boolean canEditGrant;
    private boolean canApproveGrant;
    private boolean canViewApplications;
    private boolean canAssessApplications;
    private boolean canApproveApplications;
    private boolean canManageStaff;
}