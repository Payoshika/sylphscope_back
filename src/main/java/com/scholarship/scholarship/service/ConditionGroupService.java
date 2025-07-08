package com.scholarship.scholarship.service;

import com.scholarship.scholarship.valueObject.ConditionGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionGroupService {

    public ConditionGroup createConditionGroup(String logicalOperator, List<String> questionIds) {
        if (logicalOperator == null || questionIds == null || questionIds.isEmpty()) {
            throw new IllegalArgumentException("Logical operator and questionIds are required");
        }
        return ConditionGroup.builder()
                .logicalOperator(logicalOperator)
                .questionIds(questionIds)
                .build();
    }
}