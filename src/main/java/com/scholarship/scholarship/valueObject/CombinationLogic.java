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
public class CombinationLogic {
    private LogicalOperator logicalOperator; // "AND", "OR", "NOT"
    private List<ConditionGroup> conditionGroups;
}