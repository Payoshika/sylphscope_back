// src/main/java/com/scholarship/scholarship/valueObject/QuestionGroup.java
package com.scholarship.scholarship.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionGroup {
    @Id
    private String id;

    @NotBlank(message = "Group name is required")
    private String name;

    private String description;
    @Valid
    private List<String> questionIds;

    private Integer displayOrder;

    public void setQuestions(List<String> questionIds) {
        this.questionIds = questionIds;
    }
}