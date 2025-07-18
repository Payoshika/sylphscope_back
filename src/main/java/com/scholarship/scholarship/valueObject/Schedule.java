package com.scholarship.scholarship.valueObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Date applicationStartDate;
    private Date applicationEndDate;
    private Date decisionDate;
    private Date fundDisbursementDate;
}