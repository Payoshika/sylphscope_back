package com.scholarship.scholarship.model;

import com.scholarship.scholarship.valueObject.Answer;
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
import java.util.List;

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
    private List<String> applicationId;

    @Indexed
    private String questionId;

    @Indexed
    private String questionGroupId;

    private List<Answer> answer;

    @CreatedDate
    private Instant answeredAt;

    @LastModifiedDate
    private Instant updatedAt;
}