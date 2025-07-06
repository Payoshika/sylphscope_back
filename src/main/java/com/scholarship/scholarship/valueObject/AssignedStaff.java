package com.scholarship.scholarship.valueObject;
import com.scholarship.scholarship.enums.StaffRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignedStaff {
    @NotBlank(message = "Staff ID is required")
    private String staffId;

    @NotNull(message = "Role on program is required")
    private StaffRole roleOnProgram;

    @NotNull(message = "Assigned date is required")
    @PastOrPresent(message = "Assigned date must be in the past or present")
    private Date assignedAt;
}