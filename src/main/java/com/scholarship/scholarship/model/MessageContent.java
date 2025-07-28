package com.scholarship.scholarship.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "message_contents")
public class MessageContent {
    @Id
    private String id;

    @NotBlank(message = "Message ID is required")
    private String messageId;
    @NotBlank(message = "Sender name is required")
    private String senderName;
    @NotBlank(message = "Receiver name is required")
    private String receiverName;
    @NotBlank(message = "Text is required")
    private String text;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
