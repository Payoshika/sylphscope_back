package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.EvaluationScale;
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
@Document(collection = "evaluation_of_answers")
public class EvaluationOfAnswer {
    @Id
    private String id;

    @Indexed
    private String studentAnswerId;

    @Indexed
    private String applicationId;

    @Indexed
    private String grantProgramId;  // Added grantProgramId field

    @Indexed
    private String evaluatorId; // providerStaffId

    private String questionId;

    private String questionGroupId;

    private Integer value;

    private EvaluationScale evaluationScale;

    private String comment;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
