package com.scholarship.scholarship.valueObject;
import com.scholarship.scholarship.enums.LogicalOperator;

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
    private LogicalOperator logicalOperator;
    private List<String> questionConditionIds;
}