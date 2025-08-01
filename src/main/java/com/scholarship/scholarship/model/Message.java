package com.scholarship.scholarship.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class Message {
    @Id
    private String id;

    @NotBlank(message = "Student ID is required")
    private String studentId;
    @NotBlank(message = "Student name is required")
    private String studentName;
    @NotBlank(message = "Provider ID is required")
    private String providerId;
    @NotBlank(message = "Provider name is required")
    private String providerName;
    @NotBlank(message = "Provider staff ID is required")
    private String providerStaffId;
    @NotBlank(message = "Provider staff name is required")
    private String providerStaffName;
    @NotBlank(message = "Grant program ID is required")
    private String grantProgramId;
    @NotBlank(message = "Grant program name is required")
    private String grantProgramTitle;
    @NotNull(message = "Messages list cannot be null")
    private List<MessageContent> messages;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
