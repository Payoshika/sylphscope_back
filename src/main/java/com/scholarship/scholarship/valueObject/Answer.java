package com.scholarship.scholarship.valueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private String questionId;
    private List<Object> answer;
}