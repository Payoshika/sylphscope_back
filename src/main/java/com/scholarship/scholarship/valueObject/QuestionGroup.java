// src/main/java/com/scholarship/scholarship/valueObject/QuestionGroup.java
package com.scholarship.scholarship.valueObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class QuestionGroup {
    @NotBlank(message = "Group ID is required")
    private String groupId;

    @NotBlank(message = "Group name is required")
    private String name;

    private String description;

    private String predefinedGroupKey;

    @Valid
    private List<String> questionIds;

    private Integer displayOrder;

    public void setQuestions(List<String> questionIds) {
        this.questionIds = questionIds;
    }
}