package com.scholarship.scholarship.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    private String id;

    private String organisationName;
    private String contactEmail;
    private String contactPhone;
    private String websiteUrl;
    private String organisationDescription;
    private String logoUrl;

    @CreatedDate
    private Instant createdAt;
}