package com.scholarship.scholarship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDto {
    private String id;
    private String organisationName;
    private String contactEmail;
    private String contactPhone;
    private ProviderStaffDto contactPerson;
    private String websiteUrl;
    private String organisationDescription;
    private String logoUrl;
    private Instant createdAt;
    private String invitationCode;
}