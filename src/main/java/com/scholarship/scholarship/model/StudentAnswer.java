package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.DataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "student_answers")
public class StudentAnswer {
    @Id
    private String id;

    @Indexed
    private String studentId;

    @Indexed
    private String applicationId;

    @Indexed
    private String questionId;

    private Object value; // The actual answer value
    private String selectedOptionId; // Reference to selected option
    private DataType valueDataType;

    // Metadata for better tracking and evaluation
    private String questionText;
    private String optionText; // Text of selected option if applicable

    @CreatedDate
    private Instant answeredAt;

    @LastModifiedDate
    private Instant updatedAt;
}