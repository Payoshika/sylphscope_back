package com.scholarship.scholarship.valueObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConditionGroup {
    private String logicalOperator; // "AND", "OR" for combining conditions within this group
    private List<String> questionIds;
}