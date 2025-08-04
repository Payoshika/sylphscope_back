package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "provider_invitations")
public class ProviderInvitation {
    @Id
    private String id;

    @Indexed
    private String token; // Unique token for the invitation URL

    @Indexed
    private String email; // Email of the person being invited

    @Indexed
    private String providerId; // Provider they're being invited to

    private String invitedByUserId; // Who sent the invitation

    private StaffRole role; // Role they'll have in the provider

    private String firstName;
    private String lastName;

    private boolean used; // Whether invitation has been used
    private boolean expired; // Whether invitation has expired

    @Indexed
    private Instant expiresAt; // When the invitation expires

    @CreatedDate
    private Instant createdAt;

    private Instant usedAt; // When the invitation was used
}
