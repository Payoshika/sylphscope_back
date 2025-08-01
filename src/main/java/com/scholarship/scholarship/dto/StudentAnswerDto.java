package com.scholarship.scholarship.dto;
import com.scholarship.scholarship.valueObject.Answer;
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
public class StudentAnswerDto {
    private String id;
    private String studentId;
    private String questionId;
    private List<String> applicationId;
    private String questionGroupId;
    private List<Answer> answer;
    // Context information
    private String questionText;
    private String optionText;
}