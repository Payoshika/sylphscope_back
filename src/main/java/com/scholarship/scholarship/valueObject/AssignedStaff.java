package com.scholarship.scholarship.valueObject;
import com.scholarship.scholarship.enums.StaffRole;
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
    private String staffId;
    private StaffRole roleOnProgram;
    private Date assignedAt;
}