package com.scholarship.scholarship.service;
import com.scholarship.scholarship.enums.LogicalOperator;
import com.scholarship.scholarship.valueObject.ConditionGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionGroupService {

    public ConditionGroup createConditionGroup(LogicalOperator logicalOperator, List<String> questionConditionIds) {
        if (logicalOperator == null || questionConditionIds == null || questionConditionIds.isEmpty()) {
            throw new IllegalArgumentException("Logical operator and questionIds are required");
        }
        return ConditionGroup.builder()
                .logicalOperator(logicalOperator)
                .questionConditionIds(questionConditionIds)
                .build();
    }
}