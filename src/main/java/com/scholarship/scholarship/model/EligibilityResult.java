package com.scholarship.scholarship.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "eligibility_results")
@Getter
@Setter
public class EligibilityResult {
    @Id
    private String id;
    @Indexed
    private String applicationId;
    @Indexed
    private String studentId;
    @Indexed
    private String grantProgramId;
    private boolean eligible;  // Changed from isEligible to eligible
    private List<String> passedCriteria;
    private List<String> failedCriteria;
    @CreatedDate
    private Instant evaluatedAt;
    @LastModifiedDate
    private Instant updatedAt;

    // Keep this method for backward compatibility during migration
    public void setIsEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public boolean isEligible() {
        return this.eligible;
    }
}