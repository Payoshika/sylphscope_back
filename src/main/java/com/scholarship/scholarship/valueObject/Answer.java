package com.scholarship.scholarship.valueObject;

import com.scholarship.scholarship.model.Option;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @NotBlank(message = "Question ID is required")
    private String questionId;

    private Object simpleAnswer;

    @Valid
    private List<Option> selectedOptions;

    @Valid
    private List<Document> attachedDocuments;

    @Valid
    private List<AssessmentScore> assessmentScores;

    private Instant assessedAt;
}